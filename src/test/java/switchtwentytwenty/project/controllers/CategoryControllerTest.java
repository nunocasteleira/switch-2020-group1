package switchtwentytwenty.project.controllers;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.applicationservices.iservices.ICategoryService;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.InvalidNameException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.dto.category.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("CategoryControllerTest")
class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private ICategoryService categoryService;

    @Test
    void createRootStandardCategory() {
        //arrange
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryId categoryId = new CategoryId(new Random().nextInt());
        long idDatabase = new Random().nextLong();

        StandardCategoryOutputDTO categoryDTO = new StandardCategoryOutputDTO(categoryId.getId(), name, idDatabase);
        when(categoryService.createStandardCategory(categoryInputDTO)).thenReturn(categoryDTO);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.CREATED;

        //act
        result = categoryController.createStandardCategory(categoryInputDTO);


        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
        assertEquals(categoryDTO, result.getBody());
    }

    @Test
    void createChildStandardCategory() {
        //arrange
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryId categoryId = new CategoryId(new Random().nextInt());
        long idDatabase = new Random().nextLong();
        StandardCategoryOutputDTO categoryDTO = new StandardCategoryOutputDTO(categoryId.getId(), name, idDatabase);
        when(categoryService.createStandardCategory(categoryInputDTO)).thenReturn(categoryDTO);
        categoryController.createStandardCategory(categoryInputDTO);


        String childName = "Clothing";
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childName);
        categoryChildInputDTO.setParentId(categoryId.getId());
        CategoryId childId = new CategoryId(new Random().nextInt());
        long idDatabaseChild = new Random().nextLong();
        StandardCategoryOutputDTO categoryChildDTO = new StandardCategoryOutputDTO(childId.getId(), childName, categoryId.getId(), idDatabaseChild);
        when(categoryService.createStandardCategory(categoryChildInputDTO)).thenReturn(categoryChildDTO);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.CREATED;

        //act
        result = categoryController.createStandardCategory(categoryChildInputDTO);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
        assertEquals(categoryChildDTO, result.getBody());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"Groceries Shopping"})
    void ensureRootStandardCategoryNotCreatedWhenInvalidName(String name) {
        //arrange
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        when(categoryService.createStandardCategory(categoryInputDTO)).thenThrow(InvalidNameException.class);

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
        when(categoryService.createStandardCategory(categoryInputDTO)).thenThrow(ObjectDoesNotExistException.class);

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
        String name = "Shopping";
        CategoryId categoryId = new CategoryId(new Random().nextInt());
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        long idDatabase = new Random().nextLong();
        StandardCategoryOutputDTO categoryDTO = new StandardCategoryOutputDTO(categoryId.getId(), name, idDatabase);
        when(categoryService.createStandardCategory(categoryInputDTO)).thenReturn(categoryDTO);
        categoryController.createStandardCategory(categoryInputDTO);

        when(categoryService.createStandardCategory(categoryInputDTO)).thenThrow(DuplicateObjectException.class);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createStandardCategory(categoryInputDTO);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureExistingStandardChildCategoryNotCreatedAgain() {
        //arrange
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryId categoryId = new CategoryId(new Random().nextInt());
        long idDatabase = new Random().nextLong();
        StandardCategoryOutputDTO categoryDTO = new StandardCategoryOutputDTO(categoryId.getId(), name, idDatabase);
        when(categoryService.createStandardCategory(categoryInputDTO)).thenReturn(categoryDTO);
        categoryController.createStandardCategory(categoryInputDTO);

        String childName = "Clothing";
        Object childParentId = categoryId.getId();
        CategoryInputDTO childCategoryInputDTO = new CategoryInputDTO();
        childCategoryInputDTO.setName(childName);
        childCategoryInputDTO.setParentId(childParentId);
        CategoryId childCategoryId = new CategoryId(new Random().nextInt());
        long idDatabaseChild = new Random().nextLong();
        StandardCategoryOutputDTO categoryChildDTO = new StandardCategoryOutputDTO(childCategoryId.getId(), childName, childParentId, idDatabaseChild);
        when(categoryService.createStandardCategory(childCategoryInputDTO)).thenReturn(categoryChildDTO);
        categoryController.createStandardCategory(childCategoryInputDTO);
        when(categoryService.createStandardCategory(childCategoryInputDTO)).thenThrow(DuplicateObjectException.class);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createStandardCategory(childCategoryInputDTO);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void getStandardCategoriesListWithExternalCategories_Successfully() throws IOException, JSONException {
        //Internal categories:
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryId categoryId = new CategoryId(new Random().nextInt());
        long idDatabase = new Random().nextLong();
        StandardCategoryOutputDTO categoryDTO = new StandardCategoryOutputDTO(categoryId.getId(), name, idDatabase);
        when(categoryService.createStandardCategory(categoryInputDTO)).thenReturn(categoryDTO);
        categoryController.createStandardCategory(categoryInputDTO);

        //External categories:
        String healthCategoryId = "http://vs260.dei.isep.ipp.pt:8080/api/categories/0490a571-824a-4e41-b9f3-b195de67abc6";
        String healthCategoryName = "HEALTH";
        CategoryDTO health = new CategoryDTO(healthCategoryId,healthCategoryName, 0);
        String televisionCategoryId = "http://vs260.dei.isep.ipp.pt:8080/api/categories/aa967e58-4332-4e60-a042-a3b971c85636";
        String televisionCategoryName = "TELEVISION";
        CategoryDTO television = new CategoryDTO(televisionCategoryId,televisionCategoryName, 0);
        String gymCategoryId = "http://vs260.dei.isep.ipp.pt:8080/api/categories/d98c85f7-9489-4e21-abb0-b9cdd88aabb9";
        String gymCategoryName = "GYM";
        String gymParentId = "http://vs260.dei.isep.ipp.pt:8080/api/categories/0490a571-824a-4e41-b9f3-b195de67abc6";
        CategoryDTO gym = new CategoryDTO(gymCategoryId,gymCategoryName, gymParentId, 0);
        String gamesCategoryId = "http://vs260.dei.isep.ipp.pt:8080/api/categories/1";
        String gamesCategoryName = "GAMES";
        CategoryDTO games = new CategoryDTO(gamesCategoryId,gamesCategoryName, 0);
        String earCategoryId = "http://vs260.dei.isep.ipp.pt:8080/api/categories/3fa09af4-bfdf-481d-90d1-feafb3daf0ed";
        String earCategoryName = "EAR PLUGS";
        CategoryDTO ear = new CategoryDTO(earCategoryId, earCategoryName, 0);
        CategoryDTO internal = new CategoryDTO(categoryId.getId(), name, idDatabase);
        List<CategoryDTO> expectedStandardCategories = new ArrayList<>();
        expectedStandardCategories.add(internal);
        expectedStandardCategories.add(health);
        expectedStandardCategories.add(television);
        expectedStandardCategories.add(gym);
        expectedStandardCategories.add(games);
        expectedStandardCategories.add(ear);
        MergedStandardCategoriesListDTO expectedMergedList = new MergedStandardCategoriesListDTO(expectedStandardCategories);
        when(categoryService.getMergedStandardCategoriesList()).thenReturn(expectedMergedList);

        ResponseEntity<Object> result = categoryController.getStandardCategoriesListWithExternalCategories();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedMergedList, result.getBody());
    }

    @Test
    void getStandardCategoriesListWithExternalCategories_Successfully_WithoutExternalCategories() throws IOException, JSONException {
        //Internal categories:
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryId categoryId = new CategoryId(new Random().nextInt());
        long idDatabase = new Random().nextLong();
        StandardCategoryOutputDTO categoryDTO = new StandardCategoryOutputDTO(categoryId.getId(), name, idDatabase);
        when(categoryService.createStandardCategory(categoryInputDTO)).thenReturn(categoryDTO);
        categoryController.createStandardCategory(categoryInputDTO);

        CategoryDTO internal = new CategoryDTO(categoryId.getId(), name, idDatabase);
        List<CategoryDTO> expectedStandardCategories = new ArrayList<>();
        expectedStandardCategories.add(internal);

        MergedStandardCategoriesListDTO expectedMergedList = new MergedStandardCategoriesListDTO(expectedStandardCategories);
        when(categoryService.getMergedStandardCategoriesList()).thenReturn(expectedMergedList);

        ResponseEntity<Object> result = categoryController.getStandardCategoriesListWithExternalCategories();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedMergedList, result.getBody());
    }
    @Test
    void getStandardCategoriesListWithExternalCategories_IOException() throws IOException, JSONException {
        when(categoryService.getMergedStandardCategoriesList()).thenThrow(IOException.class);

        ResponseEntity<Object> result = categoryController.getStandardCategoriesListWithExternalCategories();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void getStandardCategoriesListWithExternalCategories_JSONException() throws IOException, JSONException {
        when(categoryService.getMergedStandardCategoriesList()).thenThrow(JSONException.class);

        ResponseEntity<Object> result = categoryController.getStandardCategoriesListWithExternalCategories();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void createRootFamilyCategory() {
        //arrange
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryId categoryId = new CategoryId(new Random().nextInt());
        long idDatabase = new Random().nextLong();
        long familyId = new Random().nextLong();

        FamilyCategoryOutputDTO categoryDTO = new FamilyCategoryOutputDTO(categoryId.getId(), name, familyId, idDatabase);
        when(categoryService.createFamilyCategory(categoryInputDTO, familyId)).thenReturn(categoryDTO);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.CREATED;

        //act
        result = categoryController.createFamilyCategory(categoryInputDTO, familyId);


        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
        assertEquals(categoryDTO, result.getBody());
    }

    @Test
    void createChildFamilyCategory() {
        //arrange
        //parent category
        String name = "Clothing";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryId categoryId = new CategoryId(new Random().nextInt());
        long idDatabase = new Random().nextLong();
        long familyId = new Random().nextLong();

        FamilyCategoryOutputDTO categoryDTO = new FamilyCategoryOutputDTO(categoryId.getId(), name, familyId, idDatabase);
        when(categoryService.createFamilyCategory(categoryInputDTO, familyId)).thenReturn(categoryDTO);
        categoryController.createFamilyCategory(categoryInputDTO, familyId);

        //child category
        String childName = "Shopping";
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childName);
        categoryChildInputDTO.setParentId(categoryId.getId());
        CategoryId childId = new CategoryId(new Random().nextInt());
        long idDatabaseChild = new Random().nextLong();
        FamilyCategoryOutputDTO categoryChildDTO = new FamilyCategoryOutputDTO(childId.getId(), childName, categoryId.getId(), familyId, idDatabaseChild);
        when(categoryService.createFamilyCategory(categoryChildInputDTO, familyId)).thenReturn(categoryChildDTO);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.CREATED;

        //act
        result = categoryController.createFamilyCategory(categoryChildInputDTO, familyId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
        assertEquals(categoryChildDTO, result.getBody());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void ensureRootFamilyCategoryNotCreatedWhenInvalidName(String name) {
        //arrange
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        long familyId = new Random().nextLong();
        when(categoryService.createFamilyCategory(categoryInputDTO, familyId)).thenThrow(InvalidNameException.class);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createFamilyCategory(categoryInputDTO, familyId);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureExistingFamilyRootCategoryNotCreatedAgain() {
        //arrange
        String name = "Shopping";
        CategoryId categoryId = new CategoryId(new Random().nextInt());
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        long idDatabase = new Random().nextLong();
        long familyId = new Random().nextLong();
        FamilyCategoryOutputDTO categoryDTO = new FamilyCategoryOutputDTO(categoryId.getId(), name, familyId, idDatabase);
        when(categoryService.createFamilyCategory(categoryInputDTO, familyId)).thenReturn(categoryDTO);
        categoryController.createFamilyCategory(categoryInputDTO, familyId);

        when(categoryService.createFamilyCategory(categoryInputDTO, familyId)).thenThrow(DuplicateObjectException.class);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createFamilyCategory(categoryInputDTO, familyId);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureExistingFamilyChildCategoryNotCreatedAgain() {
        //arrange
        //root category
        String name = "Shopping";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        CategoryId categoryId = new CategoryId(new Random().nextInt());
        long idDatabase = new Random().nextLong();
        long familyId = new Random().nextLong();
        FamilyCategoryOutputDTO categoryDTO = new FamilyCategoryOutputDTO(categoryId.getId(), name, familyId, idDatabase);
        when(categoryService.createFamilyCategory(categoryInputDTO, familyId)).thenReturn(categoryDTO);
        categoryController.createFamilyCategory(categoryInputDTO, familyId);

        //child category
        String childName = "Clothing";
        Object childParentId = categoryId.getId();
        CategoryInputDTO childCategoryInputDTO = new CategoryInputDTO();
        childCategoryInputDTO.setName(childName);
        childCategoryInputDTO.setParentId(childParentId);
        CategoryId childCategoryId = new CategoryId(new Random().nextInt());
        long idDatabaseChild = new Random().nextLong();
        FamilyCategoryOutputDTO categoryChildDTO = new FamilyCategoryOutputDTO(childCategoryId.getId(), childName, childParentId, familyId, idDatabaseChild);
        when(categoryService.createFamilyCategory(childCategoryInputDTO, familyId)).thenReturn(categoryChildDTO);
        categoryController.createFamilyCategory(childCategoryInputDTO, familyId);
        when(categoryService.createFamilyCategory(childCategoryInputDTO, familyId)).thenThrow(DuplicateObjectException.class);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //act
        result = categoryController.createFamilyCategory(childCategoryInputDTO, familyId);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void createChildFamilyCategoryWithMockedParentExternalCategory() {
        //arrange
        //external parent standard category
        String healthCategoryId = "http://vs260.dei.isep.ipp.pt:8080/api/categories/0490a571-824a-4e41-b9f3-b195de67abc6";
        String healthCategoryName = "HEALTH";
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(healthCategoryName);
        CategoryId categoryId = new CategoryId(healthCategoryId);
        long idDatabase = 0;
        long familyId = new Random().nextLong();
        FamilyCategoryOutputDTO categoryDTO = new FamilyCategoryOutputDTO(categoryId.getId(), healthCategoryName, familyId, idDatabase);

        when(categoryService.createFamilyCategory(categoryInputDTO, familyId)).thenReturn(categoryDTO);
        categoryController.createFamilyCategory(categoryInputDTO, familyId);

        //family child category
        String childName = "Shopping";
        Object parentId = categoryId.getId();
        CategoryInputDTO categoryChildInputDTO = new CategoryInputDTO();
        categoryChildInputDTO.setName(childName);
        categoryChildInputDTO.setParentId(parentId);
        CategoryId childId = new CategoryId(new Random().nextInt());
        long idDatabaseChild = new Random().nextLong();
        FamilyCategoryOutputDTO categoryChildDTO = new FamilyCategoryOutputDTO(childId.getId(), childName, parentId, familyId, idDatabaseChild);
        when(categoryService.createFamilyCategory(categoryChildInputDTO, familyId)).thenReturn(categoryChildDTO);

        ResponseEntity<Object> result;
        HttpStatus expected = HttpStatus.CREATED;

        //act
        result = categoryController.createFamilyCategory(categoryChildInputDTO, familyId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
        assertEquals(categoryChildDTO, result.getBody());
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

        when(categoryService.createFamilyCategory(categoryInputDTO, familyId)).thenThrow(ObjectDoesNotExistException.class);

        //act
        result = categoryController.createFamilyCategory(categoryInputDTO, familyId);

        //assert
        assertEquals(expected, result.getStatusCode());
    }


}