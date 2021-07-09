package switchtwentytwenty.project.repositories;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.category.BaseCategory;
import switchtwentytwenty.project.domain.model.category.FamilyCategory;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.repositories.irepositories.ICategoryRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CategoryRepositoryItTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ICategoryRepositoryJPA categoryRepositoryJPA;

    @AfterEach
    void clean() {
        categoryRepositoryJPA.deleteAll();
    }

    @Test
    void saveStandardRootCategorySuccessfully() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);
        StandardCategory result;

        //act
        result = (StandardCategory) categoryRepository.saveCategory(aCategory);

        //assert
        assertNotNull(result);
        assertNotSame(aCategory, result);
        assertEquals(aCategory, result);
        assertEquals(aCategory.getId(), result.getId());
    }

    @Test
    void saveStandardChildCategorySuccessfully() {
        //arrange
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        StandardCategory aCategory = new StandardCategory(categoryName);

        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        Object parentId = aCategory.getId().getId();
        CategoryId categoryParentId = new CategoryId(parentId);
        StandardCategory aCategoryChild = new StandardCategory(childCategoryName, categoryParentId);

        StandardCategory result;

        //act
        categoryRepository.saveCategory(aCategory);

        result = (StandardCategory) categoryRepository.saveCategory(aCategoryChild);

        //assert
        assertNotNull(result);
        assertEquals(aCategoryChild, result);
        assertNotSame(aCategoryChild, result);
        assertEquals(aCategoryChild.getId(), result.getId());
        assertEquals(aCategoryChild.getParentId(), result.getParentId());
    }

    @Test
    void ensureSavedStandardRootCategoryNotEqualsToOtherCategory() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);

        String otherName = "Shopping";
        CategoryName otherCategoryName = new CategoryName(otherName);
        StandardCategory otherCategory = new StandardCategory(otherCategoryName);

        StandardCategory result;

        //act
        result = (StandardCategory) categoryRepository.saveCategory(aCategory);

        //assert
        assertNotNull(result);
        assertNotEquals(otherCategory, result);
        assertNotEquals(otherCategory.getId(), result.getId());
    }


    @Test
    void ensureSavedStandardChildCategoryNotEqualsToOtherCategory() {
        //arrange
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        StandardCategory aCategory = new StandardCategory(categoryName);

        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        Object parentId = aCategory.getId().getId();
        CategoryId categoryParentId = new CategoryId(parentId);
        StandardCategory aCategoryChild = new StandardCategory(childCategoryName, categoryParentId);

        //This child category has the same parentId, but different name
        String otherChildName = "Chocolates";
        CategoryName otherCategoryName = new CategoryName(otherChildName);
        StandardCategory otherChildCategory = new StandardCategory(otherCategoryName, categoryParentId);

        StandardCategory result;

        //act
        categoryRepository.saveCategory(aCategory);

        result = (StandardCategory) categoryRepository.saveCategory(aCategoryChild);

        //assert
        assertNotNull(result);
        assertNotEquals(otherChildCategory, result);
        assertEquals(aCategoryChild.getId(), result.getId());
        assertEquals(aCategoryChild.getParentId(), result.getParentId());
    }

    @Test
    void ensureStandardRootCategoryIsSavedInRepository() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);

        long expected = 1;
        long result;
        categoryRepository.saveCategory(aCategory);

        //act
        result = categoryRepository.getCategoryListSize();

        //assert
        assertEquals(expected, result);
    }

    @Test
    void saveStandardChildCategoryWithRootAndParentSuccessfully() {
        //arrange
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        StandardCategory aCategory = new StandardCategory(categoryName);

        String parentName = "Fruits";
        CategoryName parentCategoryName = new CategoryName(parentName);
        CategoryId parentId = aCategory.getId();
        StandardCategory aParentCategory = new StandardCategory(parentCategoryName, parentId);
        categoryRepository.saveCategory(aCategory);

        String childName = "Apple";
        CategoryName childCategoryName = new CategoryName(childName);
        CategoryId categoryParentId = aParentCategory.getId();
        StandardCategory aChildCategory = new StandardCategory(childCategoryName, categoryParentId);
        categoryRepository.saveCategory(aParentCategory);

        StandardCategory result;


        //act
        result = (StandardCategory) categoryRepository.saveCategory(aChildCategory);


        //assert
        assertNotNull(result);
        assertEquals(aChildCategory.getId(), result.getId());
        assertEquals(aChildCategory.getParentId(), result.getParentId());
    }


    @Test
    void ensureNotValidWhenParentCategoryDoesNotExist() {
        int parentId = new Random().nextInt();
        CategoryId categoryId = new CategoryId(parentId);

        assertThrows(ObjectDoesNotExistException.class,
                () -> categoryRepository.validateCategoryParent(categoryId));
    }

    @Test
    void ensureCategoryListSizeIsOneWhenSavingStandardRootCategory() {
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);
        long expected = 1;
        long result;

        categoryRepository.saveCategory(aCategory);
        result = categoryRepository.getCategoryListSize();

        assertEquals(expected, result);
    }

    @Test
    void ensureCategoryListSizeIsZeroWhenStandardRootCategoryNotSaved() {
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        new StandardCategory(categoryName);
        long expected = 0;
        long result;

        result = categoryRepository.getCategoryListSize();

        assertEquals(expected, result);
    }

    @Test
    void ensureCategoryListSizeIsNotTwoWhenOneStandardRootCategoryIsSaved() {
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);
        categoryRepository.saveCategory(aCategory);
        long expected = 1;
        long unexpected = 2;
        long result;

        result = categoryRepository.getCategoryListSize();

        assertEquals(expected, result);
        assertNotEquals(unexpected, result);
    }


    @Test
    void ensureNotValidWhenSavingCategoryThatExists() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);

        categoryRepository.saveCategory(aCategory);

        // act + arrange
        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> categoryRepository.saveCategory(aCategory));
    }

    @Test
    void ensureStandardChildCategoryNotSavedWhenAlreadyExists() {
        //arrange
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        StandardCategory aCategory = new StandardCategory(categoryName);

        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        Object parentId = aCategory.getId().getId();
        CategoryId categoryParentId = new CategoryId(parentId);
        StandardCategory aCategoryChild = new StandardCategory(childCategoryName, categoryParentId);

        categoryRepository.saveCategory(aCategory);
        categoryRepository.saveCategory(aCategoryChild);

        // act + arrange
        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> categoryRepository.saveCategory(aCategoryChild));
    }

    @Test
    void ensureSetIdDatabaseIsWorkingCorrectly() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);
        long idDatabase = new Random().nextLong();
        StandardCategory result;

        //act
        result = (StandardCategory) categoryRepository.saveCategory(aCategory);
        aCategory.setIdDatabase(idDatabase);

        //assert
        assertNotNull(result);
        assertEquals(idDatabase, aCategory.getIdDatabase());
    }

    @Test
    void ensureSetIdDatabaseIsNotEquals() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);
        long randomIdDatabase = new Random().nextLong();
        StandardCategory result;

        //act
        result = (StandardCategory) categoryRepository.saveCategory(aCategory);

        //assert
        assertNotNull(result);
        assertNotEquals(randomIdDatabase, aCategory.getIdDatabase());
    }

    @Test
    void getStandardCategoriesSuccessfully() {
        //arrange
        List<StandardCategory> result;
        List<StandardCategory> expected = new ArrayList<>();

        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        StandardCategory aCategory = new StandardCategory(categoryName);

        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        Object parentId = aCategory.getId().getId();
        CategoryId categoryParentId = new CategoryId(parentId);
        StandardCategory aCategoryChild = new StandardCategory(childCategoryName, categoryParentId);

        //act
        expected.add(aCategory);
        expected.add(aCategoryChild);

        categoryRepository.saveCategory(aCategory);
        categoryRepository.saveCategory(aCategoryChild);
        result = categoryRepository.getStandardCategories();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
        assertNotEquals(0, result.get(1).getIdDatabase());
    }

    @Test
    void getZeroStandardCategories() {
        List<StandardCategory> result;
        List<StandardCategory> expected = new ArrayList<>();

        //act
        result = categoryRepository.getStandardCategories();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void saveFamilyRootCategorySuccessfully() {
        //arrange
        String name = "Groceries";
        long familyId = 123456789;
        CategoryName categoryName = new CategoryName(name);
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aCategory = new FamilyCategory(categoryName, familyIdVO);
        FamilyCategory result;

        //act
        result = (FamilyCategory) categoryRepository.saveCategory(aCategory);

        //assert
        assertNotNull(result);
        assertNotSame(aCategory, result);
        assertEquals(aCategory, result);
        assertEquals(aCategory.getId(), result.getId());
        assertEquals(aCategory.getFamilyId(), familyIdVO);
    }

    @Test
    void saveFamilyChildCategorySuccessfully() {
        //arrange
        //family root category
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aCategory = new FamilyCategory(categoryName, familyIdVO);

        //family child category
        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        Object parentId = aCategory.getId().getId();
        CategoryId categoryParentId = new CategoryId(parentId);
        FamilyCategory aCategoryChild = new FamilyCategory(childCategoryName, categoryParentId, familyIdVO);

        FamilyCategory result;

        //act
        categoryRepository.saveCategory(aCategory);

        result = (FamilyCategory) categoryRepository.saveCategory(aCategoryChild);

        //assert
        assertNotNull(result);
        assertEquals(aCategoryChild, result);
        assertNotSame(aCategoryChild, result);
        assertEquals(aCategoryChild.getId(), result.getId());
        assertEquals(aCategoryChild.getParentId(), result.getParentId());
        assertEquals(aCategoryChild.getFamilyId(), familyIdVO);
    }

    @Test
    void saveFamilyChildOfStandardCategorySuccessfully() {
        //arrange
        //standard root category
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        StandardCategory standardRootCategory = new StandardCategory(categoryName);

        //family child category
        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        CategoryId categoryParentId = standardRootCategory.getId();
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory familyChildCategory = new FamilyCategory(childCategoryName, categoryParentId, familyIdVO);

        FamilyCategory result;

        //act
        categoryRepository.saveCategory(standardRootCategory);
        result = (FamilyCategory) categoryRepository.saveCategory(familyChildCategory);

        //assert
        assertNotNull(result);
        assertEquals(familyChildCategory, result);
        assertNotSame(familyChildCategory, result);
        assertEquals(familyChildCategory.getId(), result.getId());
        assertEquals(familyChildCategory.getParentId(), result.getParentId());
        assertEquals(familyChildCategory.getFamilyId(), familyIdVO);
    }

    @Test
    void ensureGetFamilyCategoriesIsWorking() {
        //arrange
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aCategory = new FamilyCategory(categoryName, familyIdVO);

        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        Object parentId = aCategory.getId().getId();
        CategoryId categoryParentId = new CategoryId(parentId);
        FamilyCategory aCategoryChild = new FamilyCategory(childCategoryName, categoryParentId, familyIdVO);
        categoryRepository.saveCategory(aCategory);
        categoryRepository.saveCategory(aCategoryChild);
        List<FamilyCategory> expected = new ArrayList<>();
        expected.add(aCategory);
        expected.add(aCategoryChild);

        List<FamilyCategory> result = categoryRepository.getFamilyCategories(familyIdVO);

        assertEquals(expected, result);
        assertNotNull(result.get(1).getIdDatabase());
        assertNotEquals(0, result.get(1).getIdDatabase());
    }

    @Test
    void tryToGetFamilyCategoriesWithoutFamilyCategories(){
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);

        assertThrows(ObjectDoesNotExistException.class, () -> categoryRepository.getFamilyCategories(familyIdVO));
    }


    @Test
    void ensureSetDatabaseId() {
        // We need to save two categories in order to get a different database ID than 0
        // So we save two categories and we get the database ID
        // And we assert they are different from zero
        //arrange
        String rootCategoryName = "Groceries";
        CategoryName categoryName = new CategoryName(rootCategoryName);
        StandardCategory aCategory = new StandardCategory(categoryName);

        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        Object parentId = aCategory.getId().getId();
        CategoryId categoryParentId = new CategoryId(parentId);
        StandardCategory aCategoryChild = new StandardCategory(childCategoryName, categoryParentId);

        categoryRepository.saveCategory(aCategory);
        categoryRepository.saveCategory(aCategoryChild);
        BaseCategory categoryParent = categoryRepository.getCategory(aCategory.getId());
        BaseCategory categoryChild = categoryRepository.getCategory(aCategoryChild.getId());

        //act
        long resultParent = categoryParent.getIdDatabase();
        long resultChild = categoryChild.getIdDatabase();

        //assert
        assertNotEquals(0, resultParent);
        assertNotEquals(0, resultChild);
    }
}