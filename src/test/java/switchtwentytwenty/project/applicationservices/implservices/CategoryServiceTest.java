package switchtwentytwenty.project.applicationservices.implservices;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.assemblers.CategoryAssembler;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.category.FamilyCategory;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.dto.category.*;
import switchtwentytwenty.project.repositories.CategoryRepository;
import switchtwentytwenty.project.repositories.ExternalCategoryRepositoryFour;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.irepositories.IExternalCategoryRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Profile("CategoryServiceTest")
class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    IExternalCategoryRepository iExternalCategoryRepository;

    @Mock
    CategoryAssembler categoryAssembler;

    @Mock
    CategoryMapper categoryMapper;

    @Mock
    StandardCategoriesTreeMapper standardCategoriesTreeMapper;

    @Mock
    MergeCategoriesListMapper mergeCategoriesListMapper;

    @Mock
    FamilyRepository familyRepository;


    @Test
    void createStandardRootCategorySuccessfully() {
        //arrange
        String name = "Groceries";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        CategoryName categoryName = new CategoryName(name);
        StandardCategory standardCategory = new StandardCategory(categoryName);
        CategoryId categoryId = standardCategory.getId();
        long idDatabase = standardCategory.getIdDatabase();

        StandardCategoryOutputDTO categoryOutputDTO = new StandardCategoryOutputDTO(categoryId.getId(), name, idDatabase);

        StandardCategoryOutputDTO result;

        when(categoryAssembler.fromDTOToCategoryName(categoryInputDTO)).thenReturn(categoryName);
        when(categoryRepository.saveCategory(standardCategory)).thenReturn(standardCategory);
        when(categoryMapper.standardCategoryToDTO(standardCategory)).thenReturn(categoryOutputDTO);

        //act
        result = categoryService.createStandardCategory(categoryInputDTO);

        //assert
        assertNotNull(result);
        assertEquals(categoryOutputDTO, result);
    }


    @Test
    void createStandardChildCategorySuccessfully() {
        //arrange
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        StandardCategory rootCategory = new StandardCategory(categoryName);

        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        CategoryId childCategoryParentId = rootCategory.getId();
        StandardCategory childCategory = new StandardCategory(childCategoryName,
                childCategoryParentId);

        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(childName);
        categoryInputDTO.setParentId(childCategoryParentId.getId());
        long idDatabase = childCategory.getIdDatabase();


        StandardCategoryOutputDTO expected = new StandardCategoryOutputDTO(childCategory.getId().getId(), childName, childCategoryParentId.getId(),idDatabase);

        StandardCategoryOutputDTO result;

        when(categoryAssembler.fromDTOToCategoryName(categoryInputDTO)).thenReturn(childCategoryName);
        when(categoryAssembler.fromDTOToCategoryParent(categoryInputDTO)).thenReturn(childCategoryParentId);
        doNothing().when(categoryRepository).validateCategoryParent(childCategoryParentId);
        when(categoryRepository.saveCategory(childCategory)).thenReturn(childCategory);
        when(categoryMapper.standardCategoryToDTO(childCategory)).thenReturn(expected);

        //act
        result = categoryService.createStandardCategory(categoryInputDTO);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }


    @Test
    void createStandardRootParentAndChildCategoriesSuccessfully() {
        //arrange
        //root standard category
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        StandardCategory rootCategory = new StandardCategory(categoryName);

        //root parent category (child of root category)
        String parentName = "Fruits";
        CategoryName parentCategoryName = new CategoryName(parentName);
        CategoryId categoryParentId = rootCategory.getId();
        StandardCategory parentCategory = new StandardCategory(parentCategoryName,
                categoryParentId);

        //child category (child of parent category)
        String childName = "Apple";
        CategoryName childCategoryName = new CategoryName(childName);
        CategoryId childCategoryParentId = parentCategory.getId();
        StandardCategory childCategory = new StandardCategory(childCategoryName,
                childCategoryParentId);
        long idDatabase = childCategory.getIdDatabase();


        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(childName);
        categoryInputDTO.setParentId(parentCategory.getId().getId());

        StandardCategoryOutputDTO expected = new StandardCategoryOutputDTO(childCategory.getId().getId(), childName, childCategoryParentId.getId(),idDatabase);
        StandardCategoryOutputDTO result;

        when(categoryAssembler.fromDTOToCategoryName(categoryInputDTO)).thenReturn(childCategoryName);
        when(categoryAssembler.fromDTOToCategoryParent(categoryInputDTO)).thenReturn(childCategoryParentId);
        doNothing().when(categoryRepository).validateCategoryParent(childCategoryParentId);
        when(categoryRepository.saveCategory(childCategory)).thenReturn(childCategory);
        when(categoryMapper.standardCategoryToDTO(childCategory)).thenReturn(expected);

        //act
        result = categoryService.createStandardCategory(categoryInputDTO);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }


    @Test
    void ensureStandardChildCategoryNotCreatedWhenNonExistingParentCategory() {
        //arrange
        String name = "Snickers";
        int categoryParentId = new Random().nextInt();
        CategoryId categoryId = new CategoryId(categoryParentId);
        CategoryName categoryName = new CategoryName(name);

        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        categoryInputDTO.setParentId(categoryParentId);

        when(categoryAssembler.fromDTOToCategoryName(categoryInputDTO)).thenReturn(categoryName);
        when(categoryAssembler.fromDTOToCategoryParent(categoryInputDTO)).thenReturn(categoryId);
        doThrow(ObjectDoesNotExistException.class).when(categoryRepository).validateCategoryParent(categoryId);

        //act + assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> categoryService.createStandardCategory(categoryInputDTO));
    }

    @Test
    void ensureStandardCategoryNotCreatedWhenAlreadyExists() {
        //arrange
        String name = "Snickers";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryName categoryName = new CategoryName(name);
        StandardCategory standardCategory = new StandardCategory(categoryName);
        CategoryId categoryId = standardCategory.getId();
        long idDatabase = standardCategory.getIdDatabase();
        StandardCategoryOutputDTO categoryOutputDTO = new StandardCategoryOutputDTO(categoryId.getId(), name, idDatabase);

        when(categoryAssembler.fromDTOToCategoryName(categoryInputDTO)).thenReturn(categoryName);
        when(categoryRepository.saveCategory(standardCategory)).thenReturn(standardCategory);
        when(categoryMapper.standardCategoryToDTO(standardCategory)).thenReturn(categoryOutputDTO);
        categoryService.createStandardCategory(categoryInputDTO);

        CategoryName sameCategoryName = new CategoryName(name);
        StandardCategory sameStandardCategory = new StandardCategory(sameCategoryName);

        when(categoryRepository.saveCategory(sameStandardCategory)).thenThrow(DuplicateObjectException.class);


        //act + assert
        assertThrows(DuplicateObjectException.class,
                () -> categoryService.createStandardCategory(categoryInputDTO));
    }

    @Test
    void createFamilyRootCategorySuccessfully() {
        //arrange
        String name = "Groceries";
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);

        CategoryName categoryName = new CategoryName(name);
        FamilyCategory familyCategory = new FamilyCategory(categoryName, familyIdVO);
        CategoryId categoryId = familyCategory.getId();
        long idDatabase = familyCategory.getIdDatabase();

        FamilyCategoryOutputDTO categoryOutputDTO = new FamilyCategoryOutputDTO(categoryId.getId(), name, familyId, idDatabase);

        FamilyCategoryOutputDTO result;

        when(categoryAssembler.fromDTOToCategoryName(categoryInputDTO)).thenReturn(categoryName);
        when(familyRepository.existsById(familyIdVO)).thenReturn(true);
        when(categoryRepository.saveCategory(familyCategory)).thenReturn(familyCategory);
        when(categoryMapper.familyCategoryToDTO(familyCategory)).thenReturn(categoryOutputDTO);

        //act
        result = categoryService.createFamilyCategory(categoryInputDTO, familyId);

        //assert
        assertNotNull(result);
        assertEquals(categoryOutputDTO, result);
    }


    @Test
    void createFamilyChildCategorySuccessfully() {
        //arrange
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory rootCategory = new FamilyCategory(categoryName, familyIdVO);


        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        CategoryId childCategoryParentId = rootCategory.getId();
        FamilyCategory childCategory = new FamilyCategory(childCategoryName,
                childCategoryParentId, familyIdVO);
        long idDatabase = childCategory.getIdDatabase();

        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(childName);
        categoryInputDTO.setParentId(childCategoryParentId.getId());

        FamilyCategoryOutputDTO expected = new FamilyCategoryOutputDTO(childCategory.getId().getId(), childName, childCategoryParentId.getId(), familyId, idDatabase);

        FamilyCategoryOutputDTO result;

        when(categoryAssembler.fromDTOToCategoryName(categoryInputDTO)).thenReturn(childCategoryName);
        when(familyRepository.existsById(familyIdVO)).thenReturn(true);
        when(categoryAssembler.fromDTOToCategoryParent(categoryInputDTO)).thenReturn(childCategoryParentId);
        when(categoryRepository.saveCategory(childCategory)).thenReturn(childCategory);
        when(categoryMapper.familyCategoryToDTO(childCategory)).thenReturn(expected);

        //act
        result = categoryService.createFamilyCategory(categoryInputDTO, familyId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }


    @Test
    void createStandardRootParentAndFamilyChildCategoriesSuccessfully() {
        //arrange
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        StandardCategory rootCategory = new StandardCategory(categoryName);

        String parentName = "Fruits";
        CategoryName parentCategoryName = new CategoryName(parentName);
        CategoryId categoryParentId = rootCategory.getId();
        StandardCategory parentCategory = new StandardCategory(parentCategoryName,
                categoryParentId);

        String childName = "Apple";
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        CategoryName childCategoryName = new CategoryName(childName);
        CategoryId childCategoryParentId = parentCategory.getId();
        FamilyCategory childCategory = new FamilyCategory(childCategoryName,
                childCategoryParentId, familyIdVO);
        long idDatabase = childCategory.getIdDatabase();


        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(childName);
        categoryInputDTO.setParentId(parentCategory.getId().getId());

        FamilyCategoryOutputDTO expected = new FamilyCategoryOutputDTO(childCategory.getId().getId(), childName, childCategoryParentId.getId(), familyId, idDatabase);
        FamilyCategoryOutputDTO result;

        when(categoryAssembler.fromDTOToCategoryName(categoryInputDTO)).thenReturn(childCategoryName);
        when(familyRepository.existsById(familyIdVO)).thenReturn(true);
        when(categoryAssembler.fromDTOToCategoryParent(categoryInputDTO)).thenReturn(childCategoryParentId);
        when(categoryRepository.saveCategory(childCategory)).thenReturn(childCategory);
        when(categoryMapper.familyCategoryToDTO(childCategory)).thenReturn(expected);

        //act
        result = categoryService.createFamilyCategory(categoryInputDTO, familyId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void ensureFamilyCategoryNotCreatedWhenAlreadyExists() {
        //arrange
        String name = "Snickers";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryName categoryName = new CategoryName(name);
        long familyId = 123455789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory familyCategory = new FamilyCategory(categoryName, familyIdVO);
        CategoryId categoryId = familyCategory.getId();
        long idDatabase = familyCategory.getIdDatabase();
        FamilyCategoryOutputDTO categoryOutputDTO = new FamilyCategoryOutputDTO(categoryId.getId(), name, familyId, idDatabase);

        when(categoryAssembler.fromDTOToCategoryName(categoryInputDTO)).thenReturn(categoryName);
        when(familyRepository.existsById(familyIdVO)).thenReturn(true);
        when(categoryRepository.saveCategory(familyCategory)).thenReturn(familyCategory);
        when(categoryMapper.familyCategoryToDTO(familyCategory)).thenReturn(categoryOutputDTO);
        categoryService.createFamilyCategory(categoryInputDTO, familyId);

        CategoryName sameCategoryName = new CategoryName(name);
        FamilyCategory sameFamilyCategory = new FamilyCategory(sameCategoryName, familyIdVO);

        when(categoryRepository.saveCategory(sameFamilyCategory)).thenThrow(DuplicateObjectException.class);


        //act + assert
        assertThrows(DuplicateObjectException.class,
                () -> categoryService.createFamilyCategory(categoryInputDTO, familyId));
    }

    @Test
    void ensureFamilyCategoryNotCreatedWhenFamilyDoesNotExist() {
        //arrange
        String name = "Snickers";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryName categoryName = new CategoryName(name);
        long familyId = 123455789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory familyCategory = new FamilyCategory(categoryName, familyIdVO);

        when(categoryAssembler.fromDTOToCategoryName(categoryInputDTO)).thenReturn(categoryName);
        when(familyRepository.existsById(familyIdVO)).thenReturn(true);
        when(categoryRepository.saveCategory(familyCategory)).thenThrow(ObjectDoesNotExistException.class);

        //act + assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> categoryService.createFamilyCategory(categoryInputDTO, familyId));
    }

    @Test
    void getStandardCategoriesSuccessfully() {
        //arrange
        List<StandardCategory> standardCategoryList = new ArrayList<>();
        List<CategoryDTO> standardCategoryDTOList = new ArrayList<>();
        CategoriesTreeDTO expected;
        CategoriesTreeDTO result;

        //Root Category
        String rootCategoryName = "Groceries";
        CategoryName rootName = new CategoryName(rootCategoryName);
        StandardCategory aCategory = new StandardCategory(rootName);

        //Child Category
        String childCategoryName = "Fruits";
        CategoryName childName = new CategoryName(childCategoryName);
        Object parentId = aCategory.getId().getId();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aCategoryChild = new StandardCategory(childName, parentCategoryId);

        standardCategoryList.add(aCategory);
        standardCategoryList.add(aCategoryChild);

        when(categoryRepository.getStandardCategories()).thenReturn(standardCategoryList);

        //act
        CategoryDTO aCategoryDTO = new CategoryDTO(aCategory.getId().getId(), aCategory.getName().toString(), aCategory.getIdDatabase());
        CategoryDTO aCategoryChildDTO = new CategoryDTO(aCategoryChild.getId().getId(), aCategoryChild.getName().toString(), aCategoryChild.getParentId().getId(), aCategoryChild.getIdDatabase());

        List<CategoryDTO> rootChildCategories = new ArrayList<>();
        rootChildCategories.add(aCategoryChildDTO);

        List<CategoryDTO> childCategories = new ArrayList<>();

        aCategoryDTO.setChildCategories(rootChildCategories);
        aCategoryChildDTO.setChildCategories(childCategories);

        standardCategoryDTOList.add(aCategoryDTO);

        expected = new CategoriesTreeDTO(standardCategoryDTOList);

        result = categoryService.getStandardCategoriesTree();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void getMergedStandardCategoriesList_Successfully_OnlyInternal() throws JSONException, IOException {
        //arrange
        List<StandardCategory> standardCategories = new ArrayList<>();
        //create groceries standard category
        String name = "Groceries";
        CategoryInputDTO groceriesInputDTO = new CategoryInputDTO();
        groceriesInputDTO.setName(name);
        CategoryName categoryName = new CategoryName(name);
        StandardCategory groceriesOutputDTO = new StandardCategory(categoryName);
        standardCategories.add(groceriesOutputDTO);

        when(categoryRepository.getStandardCategories()).thenReturn(standardCategories);

        List<CategoryDTO> standardCategoriesDTO = new ArrayList<>();
        CategoryDTO groceriesDTO = new CategoryDTO(groceriesOutputDTO.getId().getId(), groceriesOutputDTO.getName().toString(), groceriesOutputDTO.getIdDatabase());
        standardCategoriesDTO.add(groceriesDTO);

        List<StandardCategory> externalStandardCategories = new ArrayList<>();
        when(iExternalCategoryRepository.getExternalStandardCategories()).thenReturn(externalStandardCategories);

        //act
        MergedStandardCategoriesListDTO expected = new MergedStandardCategoriesListDTO(standardCategoriesDTO);

        //act
        MergedStandardCategoriesListDTO result = categoryService.getMergedStandardCategoriesList();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void getMergedStandardCategoriesList_Successfully_EmptyList() throws JSONException, IOException {
        //arrange
        List<StandardCategory> standardCategories = new ArrayList<>();
        when(categoryRepository.getStandardCategories()).thenReturn(standardCategories);
        List<StandardCategory> externalStandardCategories = new ArrayList<>();
        when(iExternalCategoryRepository.getExternalStandardCategories()).thenReturn(externalStandardCategories);

        List<CategoryDTO> mergecategories = new ArrayList<>();

        MergedStandardCategoriesListDTO expected = new MergedStandardCategoriesListDTO(mergecategories);

        //act
        MergedStandardCategoriesListDTO result = categoryService.getMergedStandardCategoriesList();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void getMergedStandardCategoriesList_Invalid_IOException() throws JSONException, IOException {
        //arrange
        List<StandardCategory> standardCategories = new ArrayList<>();
        //create groceries standard category
        String name = "Groceries";
        CategoryInputDTO groceriesInputDTO = new CategoryInputDTO();
        groceriesInputDTO.setName(name);
        CategoryName categoryName = new CategoryName(name);
        StandardCategory groceriesOutputDTO = new StandardCategory(categoryName);
        standardCategories.add(groceriesOutputDTO);

        when(categoryRepository.getStandardCategories()).thenReturn(standardCategories);
        when(iExternalCategoryRepository.getExternalStandardCategories()).thenThrow(IOException.class);

        //act && assert
        assertThrows(IOException.class, () -> categoryService.getMergedStandardCategoriesList());
    }
}
