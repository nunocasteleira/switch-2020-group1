package switchtwentytwenty.project.datamodel.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryNameJPA;
import switchtwentytwenty.project.datamodel.shared.ParentCategoryIdJPA;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FamilyCategoryJPATest {

    @Test
    void ensureNoArgs() {
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA();

        assertNotNull(familyCategoryJPA);
    }

    @Test
    void createFamilyRootCategoryJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Vegetables");
        long familyId = 123456789;

        //act
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA, categoryNameJPA, familyId);

        //assert
        assertNotNull(familyCategoryJPA);
    }

    @Test
    void createFamilyChildCategoryJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        long familyId = 123456789;

        //act
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, familyId);

        //assert
        assertNotNull(familyCategoryJPA);
    }

    @Test
    void getCategoryNameJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Vegetables");
        long familyId = 123456789;
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA, categoryNameJPA, familyId);

        CategoryNameJPA result;

        //act
        result = familyCategoryJPA.getName();

        //assert
        assertEquals(categoryNameJPA, result);
    }

    @Test
    void getCategoryIdJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Groceries");
        long familyId = 123456789;
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA, categoryNameJPA, familyId);

        CategoryIdJPA result;

        //act
        result = familyCategoryJPA.getCategoryId();

        //assert
        assertEquals(categoryIdJPA, result);
    }

    @Test
    void getCategoryParentIdJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        long familyId = 123456789;
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, familyId);
        ParentCategoryIdJPA result;

        //act
        result = familyCategoryJPA.getParentId();

        //assert
        assertEquals(parentCategoryIdJPA, result);
    }

    @Test
    void getFamilyId() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        long familyId = 123456789;
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, familyId);
        long result;

        //act
        result = familyCategoryJPA.getFamilyId();

        //assert
        assertEquals(familyId, result);
    }

    @Test
    void ensureGetFamilyIdNotEqualsRandomFamilyId() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        long familyId = 123456789;
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, familyId);
        long unexpected = 987654321;
        long result;

        //act
        result = familyCategoryJPA.getFamilyId();

        //assert
        assertNotEquals(unexpected, result);
    }

    @Test
    void ensureCategoryIdDatabaseNotEquals() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        long familyId = 123456789;
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, familyId);
        long randomLong = new Random().nextLong();
        long result;


        //act
        result = familyCategoryJPA.getId();

        //assert
        assertNotEquals(randomLong, result);
    }


    @Test
    void ensureCategoryIdDatabaseIsEqual() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        long familyId = 123456789;
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, familyId);
        long randomIdDatabase = new Random().nextLong();
        familyCategoryJPA.setId(randomIdDatabase);
        long result;

        //act
        result = familyCategoryJPA.getId();

        //assert
        assertEquals(randomIdDatabase, result);
    }

    @Test
    void ensureCategoryIdDatabaseIsSetAndEqual() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        long familyId = 123456789;
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, familyId);
        long idDatabase = new Random().nextLong();
        long result;


        //act
        familyCategoryJPA.setId(idDatabase);
        result = familyCategoryJPA.getId();

        //assert
        assertEquals(idDatabase, result);
    }


    @Test
    void ensureCategoryIdDatabaseSetNotEqualsToRandom() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        long familyId = 123456789;
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, familyId);
        long randomIdDatabase = new Random().nextLong();
        long randomNumber = new Random().nextLong();
        long result;


        //act
        familyCategoryJPA.setId(randomIdDatabase);
        result = familyCategoryJPA.getId();

        //assert
        assertNotEquals(randomNumber, result);
    }

    @Test
    void ensureFamilyCategoryJPAIsNotStandard() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        long familyId = 123456789;
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, familyId);
        boolean result;

        //act
        result = familyCategoryJPA.isStandard();

        //assert
        assertFalse(result);
    }


    @Test
    void testsEqualsAndHashCode() {
        //arrange

        // CategoryIdJPA
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryIdJPA rootCategoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryIdJPA otherCategoryIdJPA = new CategoryIdJPA(new Random().nextInt());

        // CategoryNameJPA
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        CategoryNameJPA otherCategoryNameJPA = new CategoryNameJPA("Bananas");

        // ParentCategoryIdJPA
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        ParentCategoryIdJPA otherParentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());

        // familyId
        long familyId = 123456789;
        long otherFamilyId = 987654321;
        Object objectFamilyId = 123456789L;
        Object otherObjectFamilyId = 987654321L;

        //idDatabase
        long differentIdDatabase = 1234976431;

        //act
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, familyId);

        FamilyCategoryJPA familyCategoryJPANull = null;

        FamilyCategoryJPA sameFamilyCategoryJPA = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, familyId);

        FamilyCategoryJPA equalFamilyCategoryJPA = familyCategoryJPA;

        FamilyCategoryJPA otherFamilyCategoryJPA = new FamilyCategoryJPA(otherCategoryIdJPA,
                otherCategoryNameJPA, otherParentCategoryIdJPA, otherFamilyId);
        otherFamilyCategoryJPA.setId(differentIdDatabase);

        FamilyCategoryJPA familyCategoryJPADifferentFamilyId = new FamilyCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, otherFamilyId);

        //assert
        assertNotEquals(familyCategoryJPA, otherFamilyCategoryJPA);
        assertNotEquals(familyCategoryJPA, familyCategoryJPANull);
        assertEquals(familyCategoryJPA, sameFamilyCategoryJPA);
        assertNotSame(familyCategoryJPA, sameFamilyCategoryJPA);
        assertEquals(familyCategoryJPA, equalFamilyCategoryJPA);
        assertSame(familyCategoryJPA, equalFamilyCategoryJPA);
        assertTrue(familyCategoryJPA.equals(familyCategoryJPA));
        assertFalse(familyCategoryJPA.equals(familyCategoryJPANull));
        assertFalse(familyCategoryJPA.equals(otherFamilyCategoryJPA));
        assertNotEquals(familyCategoryJPA.hashCode(), rootCategoryIdJPA.hashCode());
        assertFalse(familyCategoryJPA.equals(categoryIdJPA));
        assertEquals(familyCategoryJPA.getId(), sameFamilyCategoryJPA.getId());
        assertTrue(familyCategoryJPA.getCategoryId().equals(sameFamilyCategoryJPA.getCategoryId()));
        assertTrue(familyCategoryJPA.getName().equals(sameFamilyCategoryJPA.getName()));
        assertTrue(familyCategoryJPA.getParentId().equals(sameFamilyCategoryJPA.getParentId()));
        assertFalse(familyCategoryJPA.getCategoryId().equals(otherFamilyCategoryJPA.getCategoryId()));
        assertFalse(familyCategoryJPA.getName().equals(otherCategoryNameJPA));
        assertFalse(familyCategoryJPA.getParentId().equals(otherFamilyCategoryJPA.getParentId()));
        assertNotEquals(familyCategoryJPA.getId(), otherFamilyCategoryJPA.getId());
        assertEquals(familyCategoryJPA.hashCode(), sameFamilyCategoryJPA.hashCode());
        assertFalse(familyCategoryJPA.equals(categoryIdJPA));
        assertFalse(familyCategoryJPA.equals(categoryNameJPA));
        assertFalse(familyCategoryJPA.equals(parentCategoryIdJPA));
        assertFalse(familyCategoryJPA.equals(familyId));
        assertEquals(familyCategoryJPA.getFamilyId(), sameFamilyCategoryJPA.getFamilyId());
        assertNotEquals(familyCategoryJPA.getFamilyId(), otherFamilyCategoryJPA.getFamilyId());
        assertNotEquals(0, familyCategoryJPA.hashCode());
        assertEquals(familyCategoryJPA.getFamilyId(), objectFamilyId);
        assertNotEquals(familyCategoryJPA.getFamilyId(), otherObjectFamilyId);
        assertNotEquals(familyCategoryJPA, familyCategoryJPADifferentFamilyId);
    }
}