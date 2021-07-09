package switchtwentytwenty.project.domain.model.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.domain.model.shared.FamilyId;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FamilyCategoryTest {

    @Test
    void createFamilyRootCategorySuccessfully() {
        //arrange
        String name = "Groceries";
        long familyId = 123456789;
        CategoryName categoryName = new CategoryName(name);
        FamilyId familyIdVO = new FamilyId(familyId);

        //act
        FamilyCategory aFamilyCategory = new FamilyCategory(categoryName, familyIdVO);

        //assert
        assertNotNull(aFamilyCategory);
    }

    @Test
    void createFamilyChildCategorySuccessfully() {
        //arrange
        String name = "Vegetables";
        long familyId = 123456789;
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        FamilyId familyIdVO = new FamilyId(familyId);

        //act
        FamilyCategory aFamilyCategory = new FamilyCategory(categoryName, parentCategoryId, familyIdVO);

        //assert
        assertNotNull(aFamilyCategory);
    }

    @Test
    void testEquals() {
        //arrange
        String name = "Vegetables";
        long familyId = 123456789;
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        FamilyId familyIdVO = new FamilyId(familyId);

        //aFamilyCategoryDifferent
        String differentName = "Chocolates";
        long differentFamilyId = 1234567898;
        CategoryName differentCategoryName = new CategoryName(differentName);
        int differentParentId = new Random().nextInt();
        CategoryId differentParentCategoryId = new CategoryId(differentParentId);
        FamilyId differentFamilyIdVO = new FamilyId(differentFamilyId);

        //standardCategory
        String nameStandardCategory = "Groceries";
        CategoryName categoryNameVO = new CategoryName(nameStandardCategory);


        //act
        FamilyCategory aFamilyCategory = new FamilyCategory(categoryName, parentCategoryId, familyIdVO);
        //noinspection UnnecessaryLocalVariable
        FamilyCategory aFamilyCategoryEqual = aFamilyCategory;
        FamilyCategory aFamilyCategorySame = new FamilyCategory(categoryName, parentCategoryId, familyIdVO);
        FamilyCategory aFamilyCategoryDifferent = new FamilyCategory(differentCategoryName, differentParentCategoryId, differentFamilyIdVO);
        StandardCategory aStandardCategory = new StandardCategory(categoryNameVO);

        //assert
        assertSame(aFamilyCategory, aFamilyCategoryEqual);
        assertEquals(aFamilyCategory, aFamilyCategoryEqual);
        assertEquals(aFamilyCategory.hashCode(), aFamilyCategoryEqual.hashCode());
        assertNotSame(aFamilyCategory, aFamilyCategorySame);
        assertEquals(aFamilyCategory, aFamilyCategorySame);
        assertEquals(aFamilyCategory.hashCode(), aFamilyCategorySame.hashCode());
        assertNotSame(aFamilyCategory, aFamilyCategoryDifferent);
        assertNotEquals(aFamilyCategory, aFamilyCategoryDifferent);
        assertNotEquals(aFamilyCategory.hashCode(), aFamilyCategoryDifferent.hashCode());
        assertNotEquals(aFamilyCategory, aStandardCategory);
        assertNotEquals(aFamilyCategory, name);
        assertNotEquals(0, aFamilyCategory.hashCode());
        assertTrue(aFamilyCategory.getFamilyId().equals(aFamilyCategoryEqual.getFamilyId()));
        assertNotEquals(aFamilyCategory.getFamilyId(), aFamilyCategory.getIdDatabase());
        assertNotEquals(null, aFamilyCategory);
        assertFalse(aFamilyCategory.equals(null));
        assertFalse(aFamilyCategory.equals(aStandardCategory));
        assertTrue(aFamilyCategory.getFamilyId().equals(aFamilyCategorySame.getFamilyId()));
        assertFalse(aFamilyCategory.getFamilyId().equals(aFamilyCategoryDifferent.getFamilyId()));
    }

    @Test
    void ensureGetFamilyIdEquals() {
        //arrange
        String name = "Groceries";
        long familyId = 123456789;
        CategoryName categoryName = new CategoryName(name);
        FamilyId expected = new FamilyId(familyId);
        FamilyCategory aFamilyCategory = new FamilyCategory(categoryName, expected);
        FamilyId result;

        //act
        result = aFamilyCategory.getFamilyId();

        //assert
        assertEquals(expected, result);
    }

    @Test
    void ensureGetFamilyIdNotEquals() {
        //arrange
        String name = "Groceries";
        long familyId = 123456789;
        CategoryName categoryName = new CategoryName(name);
        FamilyId expected = new FamilyId(familyId);
        FamilyId unexpected = new FamilyId(987654321);
        FamilyCategory aFamilyCategory = new FamilyCategory(categoryName, expected);
        FamilyId result;

        //act
        result = aFamilyCategory.getFamilyId();

        //assert
        assertNotEquals(unexpected, result);
    }

    @Test
    void ensureIsStandardEqualsFalse() {
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aFamilyCategory = new FamilyCategory(categoryName, parentCategoryId, familyIdVO);
        boolean result;

        //act
        result = aFamilyCategory.isStandard();

        //assert
        assertFalse(result);
    }

    @Test
    void ensureIsStandardNotEqualsTrue() {
        //arrange
        String name = "Electronic";
        CategoryName categoryName = new CategoryName(name);
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aFamilyCategory = new FamilyCategory(categoryName, parentCategoryId, familyIdVO);
        boolean result;

        //act
        result = aFamilyCategory.isStandard();

        //assert
        assertNotEquals(true, result);
    }


    @Test
    void ensureFamilyIdComparisonInEquals() {
        //arrange
        // Original
        String nameOriginal = "Original";
        CategoryName categoryNameOriginal = new CategoryName(nameOriginal);
        int parentIdOriginal = new Random().nextInt();
        CategoryId parentCategoryIdOriginal = new CategoryId(parentIdOriginal);
        long familyIdOriginal = 123456789;
        FamilyId familyIdVOOriginal = new FamilyId(familyIdOriginal);
        FamilyCategory aFamilyCategoryOriginal = new FamilyCategory(categoryNameOriginal, parentCategoryIdOriginal, familyIdVOOriginal);

        // Same FamilyID
        FamilyCategory aFamilyCategorySame = new FamilyCategory(categoryNameOriginal, parentCategoryIdOriginal, familyIdVOOriginal);
        
        // Different FamilyID
        long familyIdDifferent = 987654321;
        FamilyId familyIdVODifferent = new FamilyId(familyIdDifferent);
        FamilyCategory aFamilyCategoryDifferent = new FamilyCategory(categoryNameOriginal, parentCategoryIdOriginal, familyIdVODifferent);

        //assert
        assertEquals(aFamilyCategoryOriginal, aFamilyCategorySame);
        assertNotEquals(aFamilyCategoryOriginal, aFamilyCategoryDifferent);
    }
}