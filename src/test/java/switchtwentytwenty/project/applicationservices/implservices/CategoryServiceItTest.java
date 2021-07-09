package switchtwentytwenty.project.applicationservices.implservices;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import switchtwentytwenty.project.applicationservices.iservices.IFamilyMemberService;
import switchtwentytwenty.project.domain.exceptions.InvalidNameException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.dto.*;
import switchtwentytwenty.project.dto.category.*;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.repositories.CategoryRepository;
import switchtwentytwenty.project.repositories.irepositories.ICategoryRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IExternalCategoryRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Profile("CategoryServiceTest")
class CategoryServiceItTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    IFamilyMemberService familyMemberService;

    @Autowired
    CategoryRepository categoryRepository;

    FamilyOutputDTO familyOutputDTO;

    @Autowired
    ICategoryRepositoryJPA iCategoryRepositoryJPA;

    @BeforeEach
    void createFamily() {

        iCategoryRepositoryJPA.deleteAll();
        String adminId = "antonio_costa@gmail.com";
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("António");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("Rua das Flores");
        familyInputDTO.setLocation("Lisboa");
        familyInputDTO.setPostCode("4774-321");
        familyInputDTO.setPhoneNumber("915614345");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("13/05/1998");
        familyInputDTO.setFamilyName("Costa");
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
    }

    @Test
    void createStandardRootCategorySuccessfully() {
        //arrange
        String name = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        StandardCategoryOutputDTO result;

        //act
        result = categoryService.createStandardCategory(categoryInputDTO);

        //assert
        assertNotNull(result);
    }

    @Test
    void createStandardChildCategorySuccessfully() {
        //arrange
        String rootCategoryName = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);
        StandardCategoryOutputDTO rootCategory = categoryService.createStandardCategory(categoryInputDTO);

        String childCategoryName = "Fruits";
        Object childCategoryParentId = rootCategory.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(childCategoryParentId);

        StandardCategoryOutputDTO result;

        //act
        result = categoryService.createStandardCategory(categoryChildInputDTO);

        //assert
        assertNotNull(result);
    }

    @Test
    void createStandardRootParentAndChildCategoriesSuccessfully() {
        //arrange
        //Root Category
        String rootCategoryName = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);
        StandardCategoryOutputDTO rootCategory = categoryService.createStandardCategory(categoryInputDTO);

        //Parent Category
        String parentCategoryName = "Fruits";
        Object rootCategoryId = rootCategory.getCategoryId();
        CategoryInputDTO parentChildInputDTO = new CategoryInputDTO();
        parentChildInputDTO.setName(parentCategoryName);
        parentChildInputDTO.setParentId(rootCategoryId);
        StandardCategoryOutputDTO parentCategory = categoryService.createStandardCategory(parentChildInputDTO);

        //Child Category
        String childCategoryName = "Apple";
        Object parentCategoryId = parentCategory.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(parentCategoryId);

        StandardCategoryOutputDTO result;

        //act
        result = categoryService.createStandardCategory(categoryChildInputDTO);

        //assert
        assertNotNull(result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void ensureRootCategoryWithInvalidNameNotCreated(String name) {
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        assertThrows(InvalidNameException.class,
                () -> categoryService.createStandardCategory(categoryInputDTO));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void ensureChildCategoryWithInvalidNameNotCreated(String name) {
        String parentName = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(parentName);
        StandardCategoryOutputDTO parentCategory = categoryService.createStandardCategory(categoryInputDTO);
        Object parentId = parentCategory.getCategoryId();

        CategoryInputDTO categoryChildDTO = new CategoryInputDTO();
        categoryChildDTO.setName(name);
        categoryChildDTO.setParentId(parentId);

        assertThrows(InvalidNameException.class,
                () -> categoryService.createStandardCategory(categoryChildDTO));
    }

    @Test
    void ensureExistingStandardRootCategoryNotCreated() {
        String name = "Chocolate";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        categoryService.createStandardCategory(categoryInputDTO);

        assertThrows(InvalidDataAccessApiUsageException.class, () -> categoryService.createStandardCategory(categoryInputDTO));
    }

    @Test
    void ensureExistingStandardChildCategoryNotCreated() {
        String name = "Chocolate";
        CategoryInputDTO parentInputDTO = new CategoryInputDTO();
        parentInputDTO.setName(name);
        StandardCategoryOutputDTO parentCategoryId = categoryService.createStandardCategory(parentInputDTO);

        String childName = "Snickers";
        Object categoryParentId = parentCategoryId.getCategoryId();
        CategoryInputDTO childCategoryInputDTO = new CategoryInputDTO();
        childCategoryInputDTO.setName(childName);
        childCategoryInputDTO.setParentId(categoryParentId);
        categoryService.createStandardCategory(childCategoryInputDTO);

        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> categoryService.createStandardCategory(childCategoryInputDTO));
    }

    @Test
    void ensureStandardChildCategoryNotCreatedWhenNonExistingParentCategory() {
        String childCategoryName = "Fruits";
        int childCategoryParentId = new Random().nextInt();
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(childCategoryName);
        inputDTO.setParentId(childCategoryParentId);

        assertThrows(ObjectDoesNotExistException.class,
                () -> categoryService.createStandardCategory(inputDTO));
    }

    @Test
    void ensureStandardChildCategoriesAreCreatedWithSameNameButDifferentParent() {
        String rootName = "Groceries";
        CategoryInputDTO rootInputDTO = new CategoryInputDTO();
        rootInputDTO.setName(rootName);
        StandardCategoryOutputDTO rootCategory = categoryService.createStandardCategory(rootInputDTO);

        String childName = "Chocolate";
        Object childParentId = rootCategory.getCategoryId();
        CategoryInputDTO childInputDTO = new CategoryInputDTO();
        childInputDTO.setName(childName);
        childInputDTO.setParentId(childParentId);
        StandardCategoryOutputDTO childCategory = categoryService.createStandardCategory(childInputDTO);

        String otherRootName = "Shopping";
        CategoryInputDTO otherRootInputDTO = new CategoryInputDTO();
        otherRootInputDTO.setName(otherRootName);
        StandardCategoryOutputDTO otherRootCategory = categoryService.createStandardCategory(otherRootInputDTO);

        String otherChildName = "Chocolate";
        Object otherChildParentId = otherRootCategory.getCategoryId();
        CategoryInputDTO otherChildInputDTO = new CategoryInputDTO();
        otherChildInputDTO.setName(otherChildName);
        otherChildInputDTO.setParentId(otherChildParentId);
        StandardCategoryOutputDTO result;

        result = categoryService.createStandardCategory(otherChildInputDTO);

        assertNotNull(result);
        assertNotSame(childCategory, result);
        assertNotEquals(childCategory, result);
    }

    @Test
    void ensureStandardChildCategoriesAreCreatedWithSameParentDifferentNames() {
        String rootName = "Groceries";
        CategoryInputDTO rootInputDTO = new CategoryInputDTO();
        rootInputDTO.setName(rootName);
        StandardCategoryOutputDTO rootCategory = categoryService.createStandardCategory(rootInputDTO);

        Object childParentId = rootCategory.getCategoryId();
        String childName = "Chocolate";
        CategoryInputDTO childInputDTO = new CategoryInputDTO();
        childInputDTO.setName(childName);
        childInputDTO.setParentId(childParentId);
        StandardCategoryOutputDTO childCategory = categoryService.createStandardCategory(childInputDTO);

        String otherChildName = "Shopping";
        CategoryInputDTO otherChildInputDTO = new CategoryInputDTO();
        otherChildInputDTO.setName(otherChildName);
        otherChildInputDTO.setParentId(childParentId);
        StandardCategoryOutputDTO result;

        result = categoryService.createStandardCategory(otherChildInputDTO);

        assertNotNull(result);
        assertNotSame(childCategory, result);
        assertNotEquals(childCategory, result);
    }

    @Test
    void ensureStandardChildCategoryIsCreatedWithSameNameAsParent() {
        //arrange
        String rootName = "Shopping";
        CategoryInputDTO rootInputDTO = new CategoryInputDTO();
        rootInputDTO.setName(rootName);
        StandardCategoryOutputDTO rootCategory = categoryService.createStandardCategory(rootInputDTO);

        String childName = "Vegetables";
        Object childParentId = rootCategory.getCategoryId();
        CategoryInputDTO childInputDTO = new CategoryInputDTO();
        childInputDTO.setName(childName);
        childInputDTO.setParentId(childParentId);
        categoryService.createStandardCategory(childInputDTO);

        String otherChildName = "Shopping";
        CategoryInputDTO otherChildInputDTO = new CategoryInputDTO();
        otherChildInputDTO.setName(otherChildName);
        otherChildInputDTO.setParentId(childParentId);

        StandardCategoryOutputDTO result;

        //act
        result = categoryService.createStandardCategory(otherChildInputDTO);

        //assert
        assertNotNull(result);
        assertNotSame(rootCategory, result);
        assertNotEquals(rootCategory, result);
    }


    @Test
    void ensureExistingStandardChildCategoryNotCreatedWithSameNameLowerCaseAndParent() {
        String name = "Chocolate";
        CategoryInputDTO rootInputDTO = new CategoryInputDTO();
        rootInputDTO.setName(name);
        StandardCategoryOutputDTO categoryParent = categoryService.createStandardCategory(rootInputDTO);
        Object parentId = categoryParent.getCategoryId();

        String childName = "Snickers";
        CategoryInputDTO childInputDTO = new CategoryInputDTO();
        childInputDTO.setName(childName);
        childInputDTO.setParentId(parentId);
        categoryService.createStandardCategory(childInputDTO);

        String childNameLowerCase = "snickers";
        CategoryInputDTO anInputDTO = new CategoryInputDTO();
        anInputDTO.setName(childNameLowerCase);
        anInputDTO.setParentId(parentId);

        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> categoryService.createStandardCategory(anInputDTO));
    }

    @Test
    void ensureRootCategoryWithSameNameNotCreated_UpperCase() {
        String name = "Chocolate";
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        categoryService.createStandardCategory(inputDTO);

        String categoryName = "CHOCOLATE";
        CategoryInputDTO anInputDTO = new CategoryInputDTO();
        anInputDTO.setName(categoryName);

        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> categoryService.createStandardCategory(anInputDTO));
    }

    @Test
    void createFamilyRootCategorySuccessfully() {
        //arrange
        String name = "Groceries";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        FamilyCategoryOutputDTO result;

        //act
        result = categoryService.createFamilyCategory(categoryInputDTO, familyId);

        //assert
        assertNotNull(result);
    }

    @Test
    void createFamilyChildCategorySuccessfully() {
        //arrange
        String rootCategoryName = "Groceries";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);
        FamilyCategoryOutputDTO rootCategory = categoryService.createFamilyCategory(categoryInputDTO, familyId);

        String childCategoryName = "Fruits";
        Object childCategoryParentId = rootCategory.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(childCategoryParentId);

        FamilyCategoryOutputDTO result;

        //act
        result = categoryService.createFamilyCategory(categoryChildInputDTO, familyId);

        //assert
        assertNotNull(result);
    }

    @Test
    void createFamilyRootParentAndChildCategoriesSuccessfully() {
        //arrange
        //Root Category
        String rootCategoryName = "Groceries";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);
        FamilyCategoryOutputDTO rootCategory = categoryService.createFamilyCategory(categoryInputDTO, familyId);

        //Parent Category
        String parentCategoryName = "Fruits";
        Object rootCategoryId = rootCategory.getCategoryId();
        CategoryInputDTO parentChildInputDTO = new CategoryInputDTO();
        parentChildInputDTO.setName(parentCategoryName);
        parentChildInputDTO.setParentId(rootCategoryId);
        FamilyCategoryOutputDTO parentCategory = categoryService.createFamilyCategory(parentChildInputDTO, familyId);

        //Child Category
        String childCategoryName = "Apple";
        Object parentCategoryId = parentCategory.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(parentCategoryId);

        FamilyCategoryOutputDTO result;

        //act
        result = categoryService.createFamilyCategory(categoryChildInputDTO, familyId);

        //assert
        assertNotNull(result);
    }

    @Test
    void createFamilyChildOfStandardCategorySuccessfully() {
        //arrange
        //standard root category
        String rootCategoryName = "Groceries";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);
        StandardCategoryOutputDTO rootCategory = categoryService.createStandardCategory(categoryInputDTO);

        //family child category
        String childCategoryName = "Fruits";
        Object childCategoryParentId = rootCategory.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(childCategoryParentId);

        FamilyCategoryOutputDTO result;

        //act
        result = categoryService.createFamilyCategory(categoryChildInputDTO, familyId);

        //assert
        assertNotNull(result);
    }

    @Test
    void ensureExistingFamilyRootCategoryNotCreated() {
        String name = "Chocolate";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        categoryService.createFamilyCategory(categoryInputDTO, familyId);

        assertThrows(InvalidDataAccessApiUsageException.class, () -> categoryService.createFamilyCategory(categoryInputDTO, familyId));
    }

    @Test
    void ensureExistingFamilyChildCategoryNotCreated() {
        String name = "Chocolate";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO parentInputDTO = new CategoryInputDTO();
        parentInputDTO.setName(name);
        FamilyCategoryOutputDTO parentCategoryId = categoryService.createFamilyCategory(parentInputDTO, familyId);

        String childName = "Snickers";
        Object categoryParentId = parentCategoryId.getCategoryId();
        CategoryInputDTO childCategoryInputDTO = new CategoryInputDTO();
        childCategoryInputDTO.setName(childName);
        childCategoryInputDTO.setParentId(categoryParentId);
        categoryService.createFamilyCategory(childCategoryInputDTO, familyId);

        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> categoryService.createFamilyCategory(childCategoryInputDTO, familyId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void ensureFamilyRootCategoryWithInvalidNameNotCreated(String name) {
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        assertThrows(InvalidNameException.class,
                () -> categoryService.createFamilyCategory(categoryInputDTO, familyId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void ensureFamilyChildCategoryWithInvalidNameNotCreated(String name) {
        String parentName = "Groceries";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(parentName);
        FamilyCategoryOutputDTO parentCategory = categoryService.createFamilyCategory(categoryInputDTO, familyId);
        Object parentId = parentCategory.getCategoryId();

        CategoryInputDTO categoryChildDTO = new CategoryInputDTO();
        categoryChildDTO.setName(name);
        categoryChildDTO.setParentId(parentId);

        assertThrows(InvalidNameException.class,
                () -> categoryService.createFamilyCategory(categoryChildDTO, familyId));
    }

    @Test
    void ensureFamilyRootCategoryNotCreatedWhenFamilyDoesNotExist() {
        //arrange
        String name = "Groceries";
        long familyId = 123456789;
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        //act + assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> categoryService.createFamilyCategory(categoryInputDTO, familyId));
    }

    @Test
    void ensureFamilyChildCategoryNotCreatedWhenFamilyDoesNotExist() {
        //arrange
        String rootCategoryName = "Groceries";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);
        FamilyCategoryOutputDTO rootCategory = categoryService.createFamilyCategory(categoryInputDTO, familyId);

        String childCategoryName = "Fruits";
        long otherFamilyId = 123456789;
        Object childCategoryParentId = rootCategory.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(childCategoryParentId);

        //act + assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> categoryService.createFamilyCategory(categoryChildInputDTO, otherFamilyId));
    }

    @Test
    void getStandardCategories_Successfully() {
        //arrange
        List<CategoryDTO> standardCategoriesDTO = new ArrayList<>();

        CategoriesTreeDTO expected;
        CategoriesTreeDTO result;

        String rootCategoryName = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);
        StandardCategoryOutputDTO aCategory = categoryService.createStandardCategory(categoryInputDTO);

        String childCategoryName = "Fruits";
        Object childCategoryParentId = aCategory.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(childCategoryParentId);
        StandardCategoryOutputDTO aCategoryChild = categoryService.createStandardCategory(categoryChildInputDTO);

        CategoryDTO aCategoryDTO = new CategoryDTO(aCategory.getCategoryId(), aCategory.getCategoryName(), aCategory.getIdDatabase());
        CategoryDTO aCategoryChildDTO = new CategoryDTO(aCategoryChild.getCategoryId(), aCategoryChild.getCategoryName(), aCategoryChild.getParentCategoryId(), aCategoryChild.getIdDatabase());

        List<CategoryDTO> rootChildCategories = new ArrayList<>();
        rootChildCategories.add(aCategoryChildDTO);

        List<CategoryDTO> childCategories = new ArrayList<>();

        aCategoryDTO.setChildCategories(rootChildCategories);
        aCategoryChildDTO.setChildCategories(childCategories);

        standardCategoriesDTO.add(aCategoryDTO);

        expected = new CategoriesTreeDTO(standardCategoriesDTO);

        result = categoryService.getStandardCategoriesTree();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void getEmptyStandardCategoriesTree() {
        List<CategoryDTO> categoriesDTOList = new ArrayList<>();
        CategoriesTreeDTO expectedCategoriesDTO = new CategoriesTreeDTO(categoriesDTOList);
        CategoriesTreeDTO resultCategoriesDTO = categoryService.getStandardCategoriesTree();

        assertEquals(expectedCategoriesDTO, resultCategoriesDTO);
        assertNotNull(resultCategoriesDTO);
    }

    @Test
    void getFamilyCategories_Successfully() {
        //arrange
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        long familyId = familyOutputDTO.getFamilyId();

        String rootCategoryName = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);
        StandardCategoryOutputDTO aCategory = categoryService.createStandardCategory(categoryInputDTO);

        String rootFamCategoryName = "Car";
        CategoryInputDTO categoryRootInputDTO = new CategoryInputDTO();
        categoryRootInputDTO.setName(rootFamCategoryName);
        FamilyCategoryOutputDTO rootFamCategory = categoryService.createFamilyCategory(categoryRootInputDTO, familyId);

        String childCategoryName = "Fruits";
        Object childCategoryParentId = aCategory.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(childCategoryParentId);
        StandardCategoryOutputDTO aCategoryChild = categoryService.createStandardCategory(categoryChildInputDTO);

        String childFamCategoryName = "Vegetables";
        Object childFamCategoryParentId = aCategory.getCategoryId();
        CategoryInputDTO categoryFamChildInputDTO = new CategoryInputDTO();
        categoryFamChildInputDTO.setName(childFamCategoryName);
        categoryFamChildInputDTO.setParentId(childFamCategoryParentId);
        FamilyCategoryOutputDTO aFamCategoryChild = categoryService.createFamilyCategory(categoryFamChildInputDTO, familyId);

        CategoriesTreeDTO expected;
        CategoriesTreeDTO result;

        CategoryDTO aCategoryDTO = new CategoryDTO(aCategory.getCategoryId(), aCategory.getCategoryName(), aCategory.getIdDatabase());
        CategoryDTO aCategoryChildDTO = new CategoryDTO(aCategoryChild.getCategoryId(), aCategoryChild.getCategoryName(), aCategoryChild.getParentCategoryId(), aCategoryChild.getIdDatabase());
        CategoryDTO aCategoryChildTwoDTO = new CategoryDTO(aFamCategoryChild.getCategoryId(), aFamCategoryChild.getCategoryName(), aFamCategoryChild.getParentCategoryId(), aFamCategoryChild.getIdDatabase());
        CategoryDTO rootFamCategoryDTO = new CategoryDTO(rootFamCategory.getCategoryId(), rootFamCategory.getCategoryName(), rootFamCategory.getIdDatabase());


        List<CategoryDTO> rootChildCategories = new ArrayList<>();
        rootChildCategories.add(aCategoryChildDTO);
        rootChildCategories.add(aCategoryChildTwoDTO);

        List<CategoryDTO> childCategories = new ArrayList<>();

        aCategoryDTO.setChildCategories(rootChildCategories);
        aCategoryChildDTO.setChildCategories(childCategories);
        aCategoryChildTwoDTO.setChildCategories(childCategories);
        rootFamCategoryDTO.setChildCategories(childCategories);

        categoryDTOS.add(aCategoryDTO);
        categoryDTOS.add(rootFamCategoryDTO);

        expected = new CategoriesTreeDTO(categoryDTOS);

        result = categoryService.getFamilyCategoryTree(familyId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void getStandardCategories() {
        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        StandardCategoriesDTO expected;
        StandardCategoriesDTO result;

        String rootCategoryName = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);
        StandardCategoryOutputDTO aCategory = categoryService.createStandardCategory(categoryInputDTO);

        String childCategoryName = "Fruits";
        Object childCategoryParentId = aCategory.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(childCategoryParentId);
        StandardCategoryOutputDTO aCategoryChild = categoryService.createStandardCategory(categoryChildInputDTO);

        CategoryDTO aCategoryDTO = new CategoryDTO(aCategory.getCategoryId(), aCategory.getCategoryName(), aCategory.getIdDatabase());
        CategoryDTO aCategoryChildDTO = new CategoryDTO(aCategoryChild.getCategoryId(), aCategoryChild.getCategoryName(), aCategoryChild.getParentCategoryId(), aCategoryChild.getIdDatabase());

        categoryDTOs.add(aCategoryDTO);
        categoryDTOs.add(aCategoryChildDTO);

        expected = new StandardCategoriesDTO(categoryDTOs);

        result = categoryService.getStandardCategories();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void getMergedStandardCategoriesList_Successfully_WithoutExternalCategories() throws JSONException, IOException {
        //arrange
        List<StandardCategory> standardCategories = new ArrayList<>();
        //create groceries standard category
        CategoryInputDTO groceriesInputDTO = new CategoryInputDTO();
        groceriesInputDTO.setName("groceries");
        StandardCategoryOutputDTO groceriesOutputDTO = categoryService.createStandardCategory(groceriesInputDTO);
        StandardCategory groceries = new StandardCategory(new CategoryName(groceriesOutputDTO.getCategoryName()));

        //create fruits standard category
        CategoryInputDTO fruitsInputDTO = new CategoryInputDTO();
        fruitsInputDTO.setName("fruits");
        StandardCategoryOutputDTO fruitsOutputDTO = categoryService.createStandardCategory(fruitsInputDTO);
        StandardCategory fruits = new StandardCategory(new CategoryName(fruitsOutputDTO.getCategoryName()));

        standardCategories.add(groceries);
        standardCategories.add(fruits);

        //convert StandardCategory into categoryDTO and add to list
        StandardCategoriesTreeMapper mapper = new StandardCategoriesTreeMapper();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        CategoryDTO groceriesDTO = mapper.toDTO(groceries);
        CategoryDTO fruitsDTO = mapper.toDTO(fruits);

        categoriesDTO.add(groceriesDTO);
        categoriesDTO.add(fruitsDTO);

        MergedStandardCategoriesListDTO expected = new MergedStandardCategoriesListDTO(categoriesDTO);

        //act
        MergedStandardCategoriesListDTO result = categoryService.getMergedStandardCategoriesList();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void getMergedStandardCategoriesList_Successfully_EmptyList() throws JSONException, IOException {
        //arrange
        List<CategoryDTO> categoriesDTO = new ArrayList<>();

        MergedStandardCategoriesListDTO expected = new MergedStandardCategoriesListDTO(categoriesDTO);

        //act
        MergedStandardCategoriesListDTO result = categoryService.getMergedStandardCategoriesList();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    //THIS TEST ONLY WORKS WITH VPN
/*    @Test
    void getMergedStandardCategoriesList_GroupTwo_Successfully() throws JSONException, IOException {
        //arrange
        List<StandardCategory> standardCategories = new ArrayList<>();
        //create groceries internal standard category
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName("groceries");
        StandardCategoryOutputDTO categoryOutputDTO = categoryService.createStandardCategory(categoryInputDTO);
        StandardCategory groceriesInternal = new StandardCategory(new CategoryName(categoryOutputDTO.getCategoryName()));

        standardCategories.add(groceriesInternal);

        List<StandardCategory> externalStandardCategories = new ArrayList<>();
        //create groceries standard category
        CategoryId categoryIdGroceries = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/bcda81d9-0366-44c1-bfc5-db995720c805");
        CategoryName categoryNameGroceries = new CategoryName("Groceries");
        StandardCategory groceries = new StandardCategory(categoryIdGroceries, categoryNameGroceries);

        //create vegetables standard category
        CategoryId categoryIdVegetables = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/91f157e3-8692-4d39-b0d3-10ec1581b53e");
        CategoryName categoryNameVegetables = new CategoryName("Vegetables");
        CategoryId parentIdVegetables = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/bcda81d9-0366-44c1-bfc5-db995720c805");
        StandardCategory vegetables = new StandardCategory(categoryIdVegetables, categoryNameVegetables, parentIdVegetables);

        //create fruits standard category
        CategoryId categoryIdFruits = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/297c0cd0-9368-455e-b396-4e71a2e5e2d0");
        CategoryName categoryNameFruits = new CategoryName("Fruits");
        CategoryId parentIdFruits = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/bcda81d9-0366-44c1-bfc5-db995720c805");
        StandardCategory fruits = new StandardCategory(categoryIdFruits, categoryNameFruits, parentIdFruits);

        //create sausages standard category
        CategoryId categoryIdSausages = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/dfd770cd-74d8-45ad-b292-d8c6d0abca55");
        CategoryName categoryNameSausages = new CategoryName("Sausages");
        CategoryId parentIdSausages = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/bcda81d9-0366-44c1-bfc5-db995720c805");
        StandardCategory sausages = new StandardCategory(categoryIdSausages, categoryNameSausages, parentIdSausages);

        //create transportation standard category
        CategoryId categoryIdTransportation = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/845bacd7-422d-4e06-b93b-79b4bd1f056c");
        CategoryName categoryNameTransportation = new CategoryName("Transportation");
        StandardCategory transportation = new StandardCategory(categoryIdTransportation, categoryNameTransportation);

        //create bus standard category
        CategoryId categoryIdBus = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/15ebe282-2617-4b22-8f77-7cb97b06feb0");
        CategoryName categoryNameBus = new CategoryName("Bus");
        CategoryId parentIdBus = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/845bacd7-422d-4e06-b93b-79b4bd1f056c");
        StandardCategory bus = new StandardCategory(categoryIdBus, categoryNameBus, parentIdBus);

        //create train standard category
        CategoryId categoryIdTrain = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/c1f01faf-f7ac-45de-a501-f38492583320");
        CategoryName categoryNameTrain = new CategoryName("Train");
        CategoryId parentIdTrain = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/845bacd7-422d-4e06-b93b-79b4bd1f056c");
        StandardCategory train = new StandardCategory(categoryIdTrain, categoryNameTrain, parentIdTrain);

        //create education standard category
        CategoryId categoryIdEducation = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/8a78bb00-bfc7-49b5-9d85-811d5d792706");
        CategoryName categoryNameEducation = new CategoryName("Education");
        StandardCategory education = new StandardCategory(categoryIdEducation, categoryNameEducation);

        //add categories to the list
        externalStandardCategories.add(groceries);
        externalStandardCategories.add(vegetables);
        externalStandardCategories.add(fruits);
        externalStandardCategories.add(sausages);
        externalStandardCategories.add(transportation);
        externalStandardCategories.add(bus);
        externalStandardCategories.add(train);
        externalStandardCategories.add(education);

        //create merged list with internal and external categories
        List<StandardCategory> mergedCategories = new ArrayList<>();
        mergedCategories.addAll(standardCategories);
        mergedCategories.addAll(externalStandardCategories);

        //convert StandardCategory into categoryDTO and add to list
        StandardCategoriesTreeMapper mapper = new StandardCategoriesTreeMapper();
        List<CategoryDTO> mergedCategoriesDTO = new ArrayList<>();
        CategoryDTO groceriesInternalDTO = mapper.toDTO(groceriesInternal);
        CategoryDTO groceriesDTO = mapper.toDTO(groceries);
        CategoryDTO vegetablesDTO = mapper.toDTO(vegetables);
        CategoryDTO fruitsDTO = mapper.toDTO(fruits);
        CategoryDTO sausagesDTO = mapper.toDTO(sausages);
        CategoryDTO transportationDTO = mapper.toDTO(transportation);
        CategoryDTO busDTO = mapper.toDTO(bus);
        CategoryDTO trainDTO = mapper.toDTO(train);
        CategoryDTO educationDTO = mapper.toDTO(education);
        mergedCategoriesDTO.add(groceriesInternalDTO);
        mergedCategoriesDTO.add(groceriesDTO);
        mergedCategoriesDTO.add(vegetablesDTO);
        mergedCategoriesDTO.add(fruitsDTO);
        mergedCategoriesDTO.add(sausagesDTO);
        mergedCategoriesDTO.add(transportationDTO);
        mergedCategoriesDTO.add(busDTO);
        mergedCategoriesDTO.add(trainDTO);
        mergedCategoriesDTO.add(educationDTO);

        MergedStandardCategoriesListDTO expected = new MergedStandardCategoriesListDTO(mergedCategoriesDTO);

        //act
        MergedStandardCategoriesListDTO result = categoryService.getMergedStandardCategoriesList();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }*/

    //THIS TEST ONLY WORKS WITH VPN
/*    @Test
    void getMergedStandardCategoriesList_GroupThree_Successfully() throws JSONException, IOException {
        //arrange
        List<StandardCategory> standardCategories = new ArrayList<>();
        //create groceries internal standard category
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName("groceries");
        StandardCategoryOutputDTO categoryOutputDTO = categoryService.createStandardCategory(categoryInputDTO);
        StandardCategory groceriesInternal = new StandardCategory(new CategoryName(categoryOutputDTO.getCategoryName()));

        standardCategories.add(groceriesInternal);

        List<StandardCategory> externalStandardCategories = new ArrayList<>();
        //create G3PRENDAS PARA O GRUPO 3! standard category
        CategoryId categoryIdPrendas = new CategoryId("http://vs116.dei.isep.ipp.pt:8080/categories/4");
        CategoryName categoryNamePrendas = new CategoryName("G3PRENDAS PARA O GRUPO 3!");
        StandardCategory prendas = new StandardCategory(categoryIdPrendas, categoryNamePrendas);

        //create G3FOLHADO standard category
        CategoryId categoryIdFolhado = new CategoryId("http://vs116.dei.isep.ipp.pt:8080/categories/5");
        CategoryName categoryNameFolhado = new CategoryName("G3FOLHADO");
        StandardCategory folhado = new StandardCategory(categoryIdFolhado, categoryNameFolhado);

        //add categories to the list
        externalStandardCategories.add(prendas);
        externalStandardCategories.add(folhado);

        //create merged list with internal and external categories
        List<StandardCategory> mergedCategories = new ArrayList<>();
        mergedCategories.addAll(standardCategories);
        mergedCategories.addAll(externalStandardCategories);

        //convert StandardCategory into categoryDTO and add to list
        StandardCategoriesTreeMapper mapper = new StandardCategoriesTreeMapper();
        List<CategoryDTO> mergedCategoriesDTO = new ArrayList<>();
        CategoryDTO groceriesInternalDTO = mapper.toDTO(groceriesInternal);
        CategoryDTO prendasDTO = mapper.toDTO(prendas);
        CategoryDTO folhadoDTO = mapper.toDTO(folhado);
        mergedCategoriesDTO.add(groceriesInternalDTO);
        mergedCategoriesDTO.add(prendasDTO);
        mergedCategoriesDTO.add(folhadoDTO);

        MergedStandardCategoriesListDTO expected = new MergedStandardCategoriesListDTO(mergedCategoriesDTO);

        //act
        MergedStandardCategoriesListDTO result = categoryService.getMergedStandardCategoriesList();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }*/

    //THIS TEST ONLY WORKS WITH VPN
/*    @Test
    void getMergedStandardCategoriesList_GroupFour_Successfully() throws JSONException, IOException {
        //arrange
        List<StandardCategory> standardCategories = new ArrayList<>();
        //create groceries internal standard category
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName("groceries");
        StandardCategoryOutputDTO categoryOutputDTO = categoryService.createStandardCategory(categoryInputDTO);
        StandardCategory groceriesInternal = new StandardCategory(new CategoryName(categoryOutputDTO.getCategoryName()));

        standardCategories.add(groceriesInternal);

        //create Health standard category
        CategoryId categoryIdHealth = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/0490a571-824a-4e41-b9f3-b195de67abc6");
        CategoryName categoryNameHealth = new CategoryName("Health");
        StandardCategory health = new StandardCategory(categoryIdHealth, categoryNameHealth);

        //create Television standard category
        CategoryId categoryIdTelevision = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/aa967e58-4332-4e60-a042-a3b971c85636");
        CategoryName categoryNameTelevision = new CategoryName("Television");
        StandardCategory television = new StandardCategory(categoryIdTelevision, categoryNameTelevision);

        //create Gym standard category
        CategoryId categoryIdGym = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/d98c85f7-9489-4e21-abb0-b9cdd88aabb9");
        CategoryName categoryNameGym = new CategoryName("Gym");
        CategoryId parentIdGym = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/0490a571-824a-4e41-b9f3-b195de67abc6");
        StandardCategory gym = new StandardCategory(categoryIdGym, categoryNameGym, parentIdGym);

        //create Games standard category
        CategoryId categoryIdGames = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/1");
        CategoryName categoryNameGames = new CategoryName("Games");
        StandardCategory games = new StandardCategory(categoryIdGames, categoryNameGames);

        //create Ear plugs standard category
        CategoryId categoryIdEarplugs = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/3fa09af4-bfdf-481d-90d1-feafb3daf0ed");
        CategoryName categoryNameEarplugs = new CategoryName("Ear plugs");
        StandardCategory earplugs = new StandardCategory(categoryIdEarplugs, categoryNameEarplugs);

        List<StandardCategory> externalStandardCategories = new ArrayList<>();
        //add categories to the list
        externalStandardCategories.add(health);
        externalStandardCategories.add(television);
        externalStandardCategories.add(gym);
        externalStandardCategories.add(games);
        externalStandardCategories.add(earplugs);

        //convert StandardCategory into categoryDTO and add to list
        StandardCategoriesTreeMapper mapper = new StandardCategoriesTreeMapper();
        List<CategoryDTO> mergedCategoriesDTO = new ArrayList<>();
        CategoryDTO groceriesInternalDTO = mapper.toDTO(groceriesInternal);
        CategoryDTO healthDTO = mapper.toDTO(health);
        CategoryDTO televisionDTO = mapper.toDTO(television);
        CategoryDTO gymDTO = mapper.toDTO(gym);
        CategoryDTO gamesDTO = mapper.toDTO(games);
        CategoryDTO earplugsDTO = mapper.toDTO(earplugs);
        mergedCategoriesDTO.add(groceriesInternalDTO);
        mergedCategoriesDTO.add(healthDTO);
        mergedCategoriesDTO.add(televisionDTO);
        mergedCategoriesDTO.add(gymDTO);
        mergedCategoriesDTO.add(gamesDTO);
        mergedCategoriesDTO.add(earplugsDTO);

        MergedStandardCategoriesListDTO expected = new MergedStandardCategoriesListDTO(mergedCategoriesDTO);

        //act
        MergedStandardCategoriesListDTO result = categoryService.getMergedStandardCategoriesList();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }*/
}