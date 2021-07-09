package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.applicationservices.implservices.CategoryService;
import switchtwentytwenty.project.applicationservices.iservices.IFamilyMemberService;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.dto.category.*;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.repositories.irepositories.ICategoryRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
@Profile("CategoryControllerItTest")
class CategoryControllerItTest {

    @Autowired
    CategoryController categoryController;

    @Autowired
    IFamilyMemberService familyMemberService;

    @Autowired
    CategoryService categoryService;

    FamilyOutputDTO familyOutputDTO;

    @Autowired
    ICategoryRepositoryJPA iCategoryRepositoryJPA;

    @Autowired
    IPersonRepositoryJPA iPersonRepositoryJPA;

    @BeforeEach
    void createFamily() {
        iCategoryRepositoryJPA.deleteAll();
        iPersonRepositoryJPA.deleteAll();
        String adminId = "beatriz_costa@gmail.com";
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("Beatriz");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("Rua das Flores");
        familyInputDTO.setLocation("Lisboa");
        familyInputDTO.setPostCode("4775-321");
        familyInputDTO.setPhoneNumber("913614345");
        familyInputDTO.setVat("143456789");
        familyInputDTO.setBirthDate("13/05/1948");
        familyInputDTO.setFamilyName("Costa");
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
    }


    @Test
    void ensureRootStandardCategoryIsCreated() {
        //arrange
        String name = "Shopping";
        StandardCategory standardCategory = new StandardCategory(new CategoryName(name));
        int categoryId = standardCategory.hashCode();
        ResponseEntity<Object> result;

        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        //idDatabase should not be hardcoded, categoryId was obtained through the hashCode of the standardCategory
        //idDatabase is being ignored in
        StandardCategoryOutputDTO expectedDTO = new StandardCategoryOutputDTO(categoryId, name.toUpperCase(), null, 5);
        Link selfLink = linkTo(CategoryController.class).slash("categories").withSelfRel();
        expectedDTO.add(selfLink);

        //act
        result = categoryController.createStandardCategory(categoryInputDTO);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedDTO, result.getBody());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void ensureRootStandardCategoryNotCreatedWhenInvalidName(String name) {
        //arrange
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createStandardCategory(categoryInputDTO);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureChildStandardCategoryNotCreatedWithNonExistingParent() {
        //arrange
        String childName = "Vegetables";
        int childParentId = new Random().nextInt();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(childName);
        categoryInputDTO.setParentId(childParentId);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createStandardCategory(categoryInputDTO);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureExistingStandardRootCategoryNotCreatedAgain() {
        //arrange
        String name = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        categoryController.createStandardCategory(categoryInputDTO);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createStandardCategory(categoryInputDTO);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureChildStandardCategoryIsCreated() {
        //arrange
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        StandardCategoryOutputDTO categoryDTO =
                (StandardCategoryOutputDTO) categoryController.createStandardCategory(categoryInputDTO).getBody();
        Object parentId = categoryDTO.getCategoryId();

        String childName = "Clothing";
        CategoryInputDTO childInputDTO = new CategoryInputDTO();
        childInputDTO.setName(childName);
        childInputDTO.setParentId(parentId);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.CREATED;

        //act
        result = categoryController.createStandardCategory(childInputDTO);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureExistingStandardChildCategoryNotCreatedAgain() {
        //arrange
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        StandardCategoryOutputDTO categoryDTO =
                (StandardCategoryOutputDTO) categoryController.createStandardCategory(categoryInputDTO).getBody();

        Object childParentId = categoryDTO.getCategoryId();
        String childName = "Clothing";
        CategoryInputDTO childInputDTO = new CategoryInputDTO();
        childInputDTO.setName(childName);
        childInputDTO.setParentId(childParentId);
        categoryController.createStandardCategory(childInputDTO);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createStandardCategory(childInputDTO);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void getStandardCategoriesTree_Sucessfully() {
        //arrange
        String name1 = "Groceries";
        String name2 = "Fruits";
        CategoriesTreeDTO expected;
        ResponseEntity<Object> result;

        CategoryInputDTO categoryInputDTO1 = new CategoryInputDTO();
        categoryInputDTO1.setName(name1);
        StandardCategoryOutputDTO categoryDTO1 = (StandardCategoryOutputDTO) categoryController.createStandardCategory(categoryInputDTO1).getBody();

        CategoryInputDTO categoryInputDTO2 = new CategoryInputDTO();
        categoryInputDTO2.setName(name2);
        categoryInputDTO2.setParentId(categoryDTO1.getCategoryId());
        StandardCategoryOutputDTO categoryDTO2 = (StandardCategoryOutputDTO) categoryController.createStandardCategory(categoryInputDTO2).getBody();

        //act
        List<CategoryDTO> standardCategoriesDTO = new ArrayList<>();

        String rootCategoryName = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);

        String childCategoryName = "Fruits";
        Object childCategoryParentId = categoryDTO1.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(childCategoryParentId);

        CategoryDTO aCategoryDTO = new CategoryDTO(categoryDTO1.getCategoryId(), categoryDTO1.getCategoryName(), categoryDTO1.getIdDatabase());
        CategoryDTO aCategoryChildDTO = new CategoryDTO(categoryDTO2.getCategoryId(), categoryDTO2.getCategoryName(), categoryDTO2.getParentCategoryId(), categoryDTO2.getIdDatabase());

        List<CategoryDTO> rootChildCategories = new ArrayList<>();
        rootChildCategories.add(aCategoryChildDTO);

        List<CategoryDTO> childCategories = new ArrayList<>();

        aCategoryDTO.setChildCategories(rootChildCategories);
        aCategoryChildDTO.setChildCategories(childCategories);

        standardCategoriesDTO.add(aCategoryDTO);

        expected = new CategoriesTreeDTO(standardCategoriesDTO);


        result = categoryController.getStandardCategoriesTree();

        //assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expected, result.getBody());
    }

    @Test
    void getEmptyStandardCategoriesTree() {
        //arrange
        List<CategoryDTO> standardCategoriesDTO = new ArrayList<>();
        CategoriesTreeDTO expectedCategoriesTreeDTO = new CategoriesTreeDTO(standardCategoriesDTO);

        //act
        ResponseEntity<Object> result = categoryController.getStandardCategoriesTree();

        //assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedCategoriesTreeDTO, result.getBody());
    }

    @Test
    void ensureRootFamilyCategoryIsCreated() {
        //arrange
        String name = "Shopping";
        long familyId = familyOutputDTO.getFamilyId();
        ResponseEntity<Object> result;

        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        //act
        result = categoryController.createFamilyCategory(categoryInputDTO, familyId);

        //assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void ensureExistingFamilyRootCategoryNotCreatedAgain() {
        //arrange
        String name = "Groceries";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        categoryController.createFamilyCategory(categoryInputDTO, familyId);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createFamilyCategory(categoryInputDTO, familyId);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureFamilyCategoryChildOfStandardCategoryIsCreated() {
        //arrange
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        StandardCategoryOutputDTO categoryDTO =
                (StandardCategoryOutputDTO) categoryController.createStandardCategory(categoryInputDTO).getBody();
        Object parentId = categoryDTO.getCategoryId();

        String childName = "Clothing";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO childInputDTO = new CategoryInputDTO();
        childInputDTO.setName(childName);
        childInputDTO.setParentId(parentId);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.CREATED;

        //act
        result = categoryController.createFamilyCategory(childInputDTO, familyId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureExistingFamilyChildCategoryNotCreatedAgain() {
        //arrange
        String name = "Shopping";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        FamilyCategoryOutputDTO categoryDTO =
                (FamilyCategoryOutputDTO) categoryController.createFamilyCategory(categoryInputDTO, familyId).getBody();

        Object childParentId = categoryDTO.getCategoryId();
        String childName = "Clothing";
        CategoryInputDTO childInputDTO = new CategoryInputDTO();
        childInputDTO.setName(childName);
        childInputDTO.setParentId(childParentId);
        categoryController.createFamilyCategory(childInputDTO, familyId);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createFamilyCategory(childInputDTO, familyId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureFamilyRootCategoryNotCreatedWhenFamilyDoesNotExist() {
        //arrange
        String name = "Groceries";
        long familyId = 123456789;
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createFamilyCategory(categoryInputDTO, familyId);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void getFamilyCategoryTree() {
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        StandardCategoryOutputDTO categoryDTO =
                (StandardCategoryOutputDTO) categoryController.createStandardCategory(categoryInputDTO).getBody();
        Object parentId = categoryDTO.getCategoryId();

        String famRoot = "Health";
        long familyId = familyOutputDTO.getFamilyId();
        CategoryInputDTO rootFamDTO = new CategoryInputDTO();
        rootFamDTO.setName(famRoot);
        FamilyCategoryOutputDTO categoryRootDTO = (FamilyCategoryOutputDTO) categoryController.createFamilyCategory(rootFamDTO, familyId).getBody();

        String childName = "Clothing";
        CategoryInputDTO childInputDTO = new CategoryInputDTO();
        childInputDTO.setName(childName);
        childInputDTO.setParentId(parentId);
        FamilyCategoryOutputDTO categoryChildDTO = (FamilyCategoryOutputDTO) categoryController.createFamilyCategory(childInputDTO, familyId).getBody();

        String secondChildName = "supermarket";
        CategoryInputDTO secondChildDTO = new CategoryInputDTO();
        secondChildDTO.setName(secondChildName);
        secondChildDTO.setParentId(parentId);
        StandardCategoryOutputDTO categorySecondChildDTO =
                (StandardCategoryOutputDTO) categoryController.createStandardCategory(secondChildDTO).getBody();

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.OK;

        CategoriesTreeDTO expectedDTO;

        CategoryDTO aCategoryDTO = new CategoryDTO(categoryDTO.getCategoryId(), categoryDTO.getCategoryName(), categoryDTO.getIdDatabase());
        CategoryDTO aCategoryChildDTO = new CategoryDTO(categoryChildDTO.getCategoryId(), categoryChildDTO.getCategoryName(), categoryChildDTO.getParentCategoryId(), categoryChildDTO.getIdDatabase());
        CategoryDTO aCategoryChildTwoDTO = new CategoryDTO(categorySecondChildDTO.getCategoryId(), categorySecondChildDTO.getCategoryName(), categorySecondChildDTO.getParentCategoryId(), categorySecondChildDTO.getIdDatabase());
        CategoryDTO rootFamCategoryDTO = new CategoryDTO(categoryRootDTO.getCategoryId(), categoryRootDTO.getCategoryName(), categoryRootDTO.getIdDatabase());


        List<CategoryDTO> rootChildCategories = new ArrayList<>();
        rootChildCategories.add(aCategoryChildTwoDTO);
        rootChildCategories.add(aCategoryChildDTO);

        List<CategoryDTO> childCategories = new ArrayList<>();

        aCategoryDTO.setChildCategories(rootChildCategories);
        aCategoryChildDTO.setChildCategories(childCategories);
        aCategoryChildTwoDTO.setChildCategories(childCategories);
        rootFamCategoryDTO.setChildCategories(childCategories);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryDTOS.add(aCategoryDTO);
        categoryDTOS.add(rootFamCategoryDTO);

        expectedDTO = new CategoriesTreeDTO(categoryDTOS);
        Link selfLink = linkTo(CategoryController.class).slash("categories").slash(familyId).withRel(String.valueOf(familyId));
        expectedDTO.add(selfLink);

        result = categoryController.getFamilyCategoriesTree(familyId);

        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
        assertEquals(expectedDTO, result.getBody());
    }

    @Test
    void tryToGetFamilyCategoriesTreeWithNonExistingFamily() {
        long family = 65422L;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        ResponseEntity<Object> result = categoryController.getFamilyCategoriesTree(family);

        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void tryToGetFamilyCategoriesTreeWhenThereAreNoCategories() {
        long familyId = familyOutputDTO.getFamilyId();
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        ResponseEntity<Object> result = categoryController.getFamilyCategoriesTree(familyId);

        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void getStandardCategoriesListWithExternalCategories_Successfully() {
        String name = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        categoryController.createStandardCategory(categoryInputDTO);

        ResponseEntity<Object> result = categoryController.getStandardCategoriesListWithExternalCategories();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }

    @Test
    void getStandardCategoriesListWithExternalCategories_Successfully_WithoutInternalStandardCategories() {
        ResponseEntity<Object> result = categoryController.getStandardCategoriesListWithExternalCategories();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }

    @Test
    void getStandardCategoriesList() {
        //arrange
        String name1 = "Groceries";
        String name2 = "Fruits";
        StandardCategoriesDTO expected;
        ResponseEntity<Object> result;

        CategoryInputDTO categoryInputDTO1 = new CategoryInputDTO();
        categoryInputDTO1.setName(name1);
        StandardCategoryOutputDTO categoryDTO1 = (StandardCategoryOutputDTO) categoryController.createStandardCategory(categoryInputDTO1).getBody();

        CategoryInputDTO categoryInputDTO2 = new CategoryInputDTO();
        categoryInputDTO2.setName(name2);
        categoryInputDTO2.setParentId(categoryDTO1.getCategoryId());
        StandardCategoryOutputDTO categoryDTO2 = (StandardCategoryOutputDTO) categoryController.createStandardCategory(categoryInputDTO2).getBody();

        //act
        List<CategoryDTO> standardCategoriesDTO = new ArrayList<>();

        String rootCategoryName = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(rootCategoryName);

        String childCategoryName = "Fruits";
        Object childCategoryParentId = categoryDTO1.getCategoryId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childCategoryName);
        categoryChildInputDTO.setParentId(childCategoryParentId);

        CategoryDTO aCategoryDTO = new CategoryDTO(categoryDTO1.getCategoryId(), categoryDTO1.getCategoryName(), categoryDTO1.getIdDatabase());
        CategoryDTO aCategoryChildDTO = new CategoryDTO(categoryDTO2.getCategoryId(), categoryDTO2.getCategoryName(), categoryDTO2.getParentCategoryId(), categoryDTO2.getIdDatabase());

        standardCategoriesDTO.add(aCategoryDTO);
        standardCategoriesDTO.add(aCategoryChildDTO);

        expected = new StandardCategoriesDTO(standardCategoriesDTO);


        result = categoryController.getStandardCategories();

        //assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expected, result.getBody());
    }

    @Test
    void getEmptyStandardCategoriesList() {
        //arrange
        List<CategoryDTO> standardCategoriesDTO = new ArrayList<>();
        StandardCategoriesDTO expectedCategoriesDTO = new StandardCategoriesDTO(standardCategoriesDTO);

        //act
        ResponseEntity<Object> result = categoryController.getStandardCategories();

        //assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedCategoriesDTO, result.getBody());
    }

    @Test
    void getStandardAndFamilyCategories(){
        //Arrange
        CategoryInputDTO categoryOneInputDTO = new CategoryInputDTO();
        categoryOneInputDTO.setName("categoryOneName");
        StandardCategoryOutputDTO categoryOneOutputDTO= categoryService.createStandardCategory(categoryOneInputDTO);
        CategoryDTO categoryOne = new CategoryDTO(categoryOneOutputDTO.getCategoryId(), categoryOneOutputDTO.getCategoryName(), categoryOneOutputDTO.getIdDatabase());


        CategoryInputDTO categoryTwoInputDTO = new CategoryInputDTO();
        categoryTwoInputDTO.setName("categoryTwoName");
        StandardCategoryOutputDTO categoryTwoOutputDTO= categoryService.createStandardCategory(categoryTwoInputDTO);
        CategoryDTO categoryTwo = new CategoryDTO(categoryTwoOutputDTO.getCategoryId(), categoryTwoOutputDTO.getCategoryName(), categoryTwoOutputDTO.getIdDatabase());

        List<CategoryDTO> DTOList = new ArrayList<>();
        DTOList.add(categoryOne);
        DTOList.add(categoryTwo);

        StandardCategoriesDTO expectedDTO = new StandardCategoriesDTO(DTOList);
        Link selfLink = linkTo(methodOn(CategoryController.class).getStandardCategories()).withSelfRel();
        expectedDTO.add(selfLink);

        //Act
        ResponseEntity<Object> result = categoryController.getStandardAndFamilyCategories(1);

        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedDTO, result.getBody());
    }

    @Test
    void getStandardAnd(){
        //Arrange
        CategoryInputDTO categoryOneInputDTO = new CategoryInputDTO();
        categoryOneInputDTO.setName("categoryOneName");
        StandardCategoryOutputDTO categoryOneOutputDTO= categoryService.createStandardCategory(categoryOneInputDTO);
        CategoryDTO categoryOne = new CategoryDTO(categoryOneOutputDTO.getCategoryId(), categoryOneOutputDTO.getCategoryName(), categoryOneOutputDTO.getIdDatabase());


        CategoryInputDTO categoryTwoInputDTO = new CategoryInputDTO();
        categoryTwoInputDTO.setName("categoryTwoName");
        StandardCategoryOutputDTO categoryTwoOutputDTO= categoryService.createStandardCategory(categoryTwoInputDTO);
        CategoryDTO categoryTwo = new CategoryDTO(categoryTwoOutputDTO.getCategoryId(), categoryTwoOutputDTO.getCategoryName(), categoryTwoOutputDTO.getIdDatabase());

        List<CategoryDTO> DTOList = new ArrayList<>();
        DTOList.add(categoryOne);
        DTOList.add(categoryTwo);

        StandardCategoriesDTO expectedDTO = new StandardCategoriesDTO(DTOList);
        Link selfLink = linkTo(methodOn(CategoryController.class).getStandardCategories()).withSelfRel();
        expectedDTO.add(selfLink);

        //Act
        ResponseEntity<Object> result = categoryController.getStandardAndFamilyCategories(1);

        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedDTO, result.getBody());
    }
}