package switchtwentytwenty.project.domain.model.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StandardCategoryTest {

    @Test
    void createStandardRootCategorySuccessfully() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);

        //act
        StandardCategory aStandardCategory = new StandardCategory(categoryName);

        //assert
        assertNotNull(aStandardCategory);
    }

    @Test
    void createStandardChildCategorySuccessfully() {
        //arrange
        String name = "Vegetables";
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);

        //act
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);

        //assert
        assertNotNull(aStandardCategory);
    }


    @Test
    void ensureCategoriesWithDifferentNamesAreNotEquals() {
        //arrange
        String name = "Shopping";
        CategoryName categoryName = new CategoryName(name);
        String otherName = "Groceries";
        CategoryName otherCategoryName = new CategoryName(otherName);

        //act
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        StandardCategory otherStandardCategory = new StandardCategory(otherCategoryName);

        //assert
        assertNotEquals(aStandardCategory, otherStandardCategory);
    }

    @Test
    void ensureCategoriesWithSameNameAreEquals() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        String sameName = "Groceries";
        CategoryName sameCategoryName = new CategoryName(sameName);

        //act
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        StandardCategory sameStandardCategory = new StandardCategory(sameCategoryName);

        //assert
        assertEquals(aStandardCategory, sameStandardCategory);

    }

    @Test
    void ensureCategoriesWithSameNameAndDifferentParentNameAreNotEquals() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        String sameName = "Groceries";
        CategoryName sameCategoryName = new CategoryName(sameName);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        int otherParentId = new Random().nextInt();
        CategoryId otherParentCategoryId = new CategoryId(otherParentId);

        //act
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);
        StandardCategory otherStandardCategory = new StandardCategory(sameCategoryName, otherParentCategoryId);

        //assert
        assertNotEquals(aStandardCategory, otherStandardCategory);

    }

    @Test
    void ensureCategoriesWithSameNameAndParentNameAreEquals() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        String sameName = "Groceries";
        CategoryName sameCategoryName = new CategoryName(sameName);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);

        //act
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);
        StandardCategory sameStandardCategory = new StandardCategory(sameCategoryName, parentCategoryId);

        //assert
        assertEquals(aStandardCategory, sameStandardCategory);
    }

    @Test
    void ensureHashCodeNotEquals() {
        //arrange
        String name = "Shopping";
        CategoryName categoryName = new CategoryName(name);
        String otherName = "Groceries";
        CategoryName otherCategoryName = new CategoryName(otherName);

        //act
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        StandardCategory otherStandardCategory = new StandardCategory(otherCategoryName);
        //assert
        assertNotEquals(aStandardCategory.hashCode(), otherStandardCategory.hashCode());
    }

    @Test
    void ensureHashCodeNotEqualsWithChildCategories() {
        //arrange
        String childName = "Clothing";
        CategoryName childCategoryName = new CategoryName(childName);
        String otherChildName = "Vegetables";
        CategoryName otherChildCategoryName = new CategoryName(otherChildName);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        int otherParentId = new Random().nextInt();
        CategoryId otherParentCategoryId = new CategoryId(otherParentId);

        //act
        StandardCategory aStandardCategory = new StandardCategory(childCategoryName, parentCategoryId);
        StandardCategory otherStandardCategory = new StandardCategory(otherChildCategoryName, otherParentCategoryId);

        //assert
        assertNotEquals(aStandardCategory.hashCode(), otherStandardCategory.hashCode());
    }

    @Test
    void ensureHashCodeEquals() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        String sameName = "Groceries";
        CategoryName sameCategoryName = new CategoryName(sameName);

        //act
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        StandardCategory sameStandardCategory = new StandardCategory(sameCategoryName);

        //assert
        assertEquals(aStandardCategory.hashCode(), sameStandardCategory.hashCode());
    }

    @Test
    void ensureHashCodeEqualsWithChildCategories() {
        //arrange
        int parentId = new Random().nextInt();
        String childName = "Clothing";
        CategoryName categoryName = new CategoryName(childName);
        CategoryId parentCategoryId = new CategoryId(parentId);
        String sameChildName = "Clothing";
        CategoryName sameCategoryName = new CategoryName(sameChildName);
        CategoryId sameParentCategoryId = new CategoryId(parentId);

        //act
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);
        StandardCategory sameStandardCategory = new StandardCategory(sameCategoryName, sameParentCategoryId);

        //assert
        assertEquals(aStandardCategory.hashCode(), sameStandardCategory.hashCode());
    }


    @Test
    void ensureCategoryIsEqual() {
        //arrange
        String childName = "Clothing";
        CategoryName categoryName = new CategoryName(childName);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);

        //act
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);

        //assert
        assertTrue(aStandardCategory.equals(aStandardCategory));
    }

    @Test
    void ensureCategoriesAreNotEqual() {
        //arrange
        String childName = "Clothing";
        CategoryName categoryName = new CategoryName(childName);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);

        //assert
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);
        StandardCategory otherStandardCategory = null;

        //act
        assertFalse(aStandardCategory.equals(otherStandardCategory));
    }

    @Test
    void ensureCategoryIdIsEqual() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        int id = aStandardCategory.hashCode();
        CategoryId expected = new CategoryId(id);

        //act
        CategoryId result = aStandardCategory.getId();

        //assert
        assertEquals(expected, result);
    }

    @Test
    void ensureCategoryIdNotEqual() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        int id = new Random().nextInt();
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        CategoryId expected = new CategoryId(id);

        //act
        CategoryId result = aStandardCategory.getId();

        //assert
        assertNotEquals(expected, result);
    }

    @Test
    void ensureCategoryDoesNotHaveId() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        int id = new Random().nextInt();
        CategoryId categoryId = new CategoryId(id);
        StandardCategory aStandardCategory = new StandardCategory(categoryName);

        //act
        boolean result = aStandardCategory.hasId(categoryId);

        //assert
        assertFalse(result);
    }

    @Test
    void ensureCategoryHasId() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        int id = aStandardCategory.hashCode();
        CategoryId categoryId = new CategoryId(id);

        //act
        boolean result = aStandardCategory.hasId(categoryId);

        //assert
        assertTrue(result);
    }

    @Test
    void ensureParentIdIsEqual() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);

        //act
        CategoryId result = aStandardCategory.getParentId();

        //assert
        assertEquals(parentCategoryId, result);
    }

    @Test
    void ensureParentIdIsNotEqual() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);
        int otherParentId = new Random().nextInt();
        CategoryId otherParentCategoryId = new CategoryId(otherParentId);

        //act
        CategoryId result = aStandardCategory.getParentId();

        //assert
        assertNotEquals(otherParentCategoryId, result);
    }

    @Test
    void ensureGetNameCorrectly() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aStandardCategory = new StandardCategory(categoryName);

        //act
        CategoryName result = aStandardCategory.getName();

        //assert
        assertEquals(categoryName, result);
    }

    @Test
    void ensureIdIsSetCorrectly() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        int newId = new Random().nextInt();
        CategoryId categoryId = new CategoryId(newId);
        CategoryId result;

        //act
        aStandardCategory.setId(categoryId);
        result = aStandardCategory.getId();

        //assert
        assertEquals(categoryId, result);
    }

    @Test
    void ensureSetIdNotEquals() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        int newId = new Random().nextInt();
        CategoryId categoryId = new CategoryId(newId);
        CategoryId otherCategoryId = new CategoryId(new Random().nextInt());
        CategoryId result;

        //act
        aStandardCategory.setId(categoryId);
        result = aStandardCategory.getId();

        //assert
        assertNotEquals(otherCategoryId, result);
    }

    @Test
    void ensureIdDatabaseIsNotEqualToCategoryId() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);
        Object categoryId = aStandardCategory.getId().getId();
        long result;

        //act
        result = aStandardCategory.getIdDatabase();

        //assert
        assertNotEquals(categoryId, result);
    }

    @Test
    void ensureIdDatabaseIsEquals() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);
        long idDatabase = 12345;
        aStandardCategory.setIdDatabase(idDatabase);
        long result;

        //act
        result = aStandardCategory.getIdDatabase();

        //assert
        assertEquals(idDatabase, result);
    }

    @Test
    void ensureIdDatabaseIsSetCorrectly() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        long idDatabase = new Random().nextLong();
        long result;

        //act
        aStandardCategory.setIdDatabase(idDatabase);
        result = aStandardCategory.getIdDatabase();

        //assert
        assertEquals(idDatabase, result);
    }

    @Test
    void ensureSetIdDatabaseNotEquals() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        long idDatabase = new Random().nextLong();
        long otherIdDatabase = new Random().nextLong();
        long result;

        //act
        aStandardCategory.setIdDatabase(idDatabase);
        result = aStandardCategory.getIdDatabase();

        //assert
        assertNotEquals(otherIdDatabase, result);
    }


    @Test
    void ensureCategoryAndCategoryNameAreNotEquals() {
        //arrange
        String childName = "Electronic";
        CategoryName name = new CategoryName(childName);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);

        //assert
        StandardCategory aStandardCategory = new StandardCategory(name, parentCategoryId);
        CategoryName categoryName = new CategoryName(childName);

        //act
        assertFalse(aStandardCategory.equals(categoryName));
    }

    @Test
    void ensureIsStandardEqualsTrue() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);
        boolean result;

        //act
        result = aStandardCategory.isStandard();

        //assert
        assertTrue(result);
    }

    @Test
    void ensureIsStandardNotEqualsFalse() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentCategoryId);
        boolean result;

        //act
        result = aStandardCategory.isStandard();

        //assert
        assertNotEquals(false, result);
    }

    @Test
    void createStandardChildCategoryWithADefinedId() {
        StandardCategory aCategory = new StandardCategory(new CategoryId(1), new CategoryName("categoryName"), new CategoryId(3));

        assertNotNull(aCategory);

    }

}