package switchtwentytwenty.project.datamodel.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryNameJPA;
import switchtwentytwenty.project.datamodel.shared.ParentCategoryIdJPA;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BaseCategoryJPATest {


    @Test
    void ensureNoArgs() {
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA();

        assertNotNull(baseCategoryJPA);
    }

    @Test
    void createBaseRootCategoryJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Vegetables");
        boolean isStandard = true;


        //act
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA, categoryNameJPA, isStandard);

        //assert
        assertNotNull(baseCategoryJPA);
    }

    @Test
    void createBaseChildCategoryJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        boolean isStandard = false;

        //act
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, isStandard);

        //assert
        assertNotNull(baseCategoryJPA);
    }

    @Test
    void getCategoryNameJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Vegetables");
        boolean isStandard = false;
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA, categoryNameJPA, isStandard);

        CategoryNameJPA result;

        //act
        result = baseCategoryJPA.getName();

        //assert
        assertEquals(categoryNameJPA, result);
    }

    @Test
    void getCategoryIdJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Groceries");
        boolean isStandard = true;
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA, categoryNameJPA, isStandard);

        CategoryIdJPA result;

        //act
        result = baseCategoryJPA.getCategoryId();

        //assert
        assertEquals(categoryIdJPA, result);
    }

    @Test
    void getCategoryParentIdJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        boolean isStandard = true;
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, isStandard);
        ParentCategoryIdJPA result;

        //act
        result = baseCategoryJPA.getParentId();

        //assert
        assertEquals(parentCategoryIdJPA, result);
    }

    @Test
    void ensureCategoryIdDatabaseNotEquals() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        boolean isStandard = true;
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, isStandard);
        long randomLong = new Random().nextLong();
        long result;

        //act
        result = baseCategoryJPA.getId();

        //assert
        assertNotEquals(randomLong, result);
    }


    @Test
    void ensureCategoryIdDatabaseIsEqual() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        boolean isStandard = false;
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, isStandard);
        long randomIdDatabase = new Random().nextLong();
        baseCategoryJPA.setId(randomIdDatabase);
        long result;

        //act
        result = baseCategoryJPA.getId();

        //assert
        assertEquals(randomIdDatabase, result);
    }

    @Test
    void ensureCategoryIdDatabaseIsSetAndEqual() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        boolean isStandard = false;
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, isStandard);
        long idDatabase = new Random().nextLong();
        long result;


        //act
        baseCategoryJPA.setId(idDatabase);
        result = baseCategoryJPA.getId();

        //assert
        assertEquals(idDatabase, result);
    }


    @Test
    void ensureCategoryIdDatabaseSetNotEqualsToRandom() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        boolean isStandard = false;
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, isStandard);
        long randomIdDatabase = new Random().nextLong();
        long randomNumber = new Random().nextLong();
        long result;

        //act
        baseCategoryJPA.setId(randomIdDatabase);
        result = baseCategoryJPA.getId();

        //assert
        assertNotEquals(randomNumber, result);
    }

    @Test
    void isStandard() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Groceries");
        boolean isStandard = true;
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA, categoryNameJPA, isStandard);

        boolean result;

        //act
        result = baseCategoryJPA.isStandard();

        //assert
        assertEquals(isStandard, result);
    }

    @Test
    void isNotStandard() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Groceries");
        boolean notStandard = false;
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA, categoryNameJPA, notStandard);

        boolean result;

        //act
        result = baseCategoryJPA.isStandard();

        //assert
        assertEquals(notStandard, result);
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

        // isStandadrd
        boolean isStandard = true;
        boolean notStandard = false;

        //idDatabase
        long differentIdDatabase = 1234976431;

        //act
        BaseCategoryJPA baseCategoryJPA = new BaseCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, isStandard);

        BaseCategoryJPA baseCategoryJPANull = null;

        BaseCategoryJPA sameBaseCategoryJPA = new BaseCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, isStandard);

        BaseCategoryJPA equalBaseCategoryJPA = baseCategoryJPA;

        BaseCategoryJPA otherBaseCategoryJPA = new BaseCategoryJPA(otherCategoryIdJPA,
                otherCategoryNameJPA, otherParentCategoryIdJPA, isStandard);
        otherBaseCategoryJPA.setId(differentIdDatabase);

        BaseCategoryJPA baseCategoryJPADifferentIsStandard = new BaseCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA, notStandard);

        //assert
        assertNotEquals(baseCategoryJPA, otherBaseCategoryJPA);
        assertNotEquals(baseCategoryJPA, baseCategoryJPANull);
        assertEquals(baseCategoryJPA, sameBaseCategoryJPA);
        assertNotSame(baseCategoryJPA, sameBaseCategoryJPA);
        assertEquals(baseCategoryJPA, equalBaseCategoryJPA);
        assertSame(baseCategoryJPA, equalBaseCategoryJPA);
        assertTrue(baseCategoryJPA.equals(baseCategoryJPA));
        assertFalse(baseCategoryJPA.equals(baseCategoryJPANull));
        assertFalse(baseCategoryJPA.equals(otherBaseCategoryJPA));
        assertNotEquals(baseCategoryJPA.hashCode(), rootCategoryIdJPA.hashCode());
        assertFalse(baseCategoryJPA.equals(categoryIdJPA));
        assertEquals(baseCategoryJPA.getId(), sameBaseCategoryJPA.getId());
        assertTrue(baseCategoryJPA.getCategoryId().equals(sameBaseCategoryJPA.getCategoryId()));
        assertTrue(baseCategoryJPA.getName().equals(sameBaseCategoryJPA.getName()));
        assertTrue(baseCategoryJPA.getParentId().equals(sameBaseCategoryJPA.getParentId()));
        assertFalse(baseCategoryJPA.getCategoryId().equals(otherBaseCategoryJPA.getCategoryId()));
        assertFalse(baseCategoryJPA.getName().equals(otherCategoryNameJPA));
        assertFalse(baseCategoryJPA.getParentId().equals(otherBaseCategoryJPA.getParentId()));
        assertNotEquals(baseCategoryJPA.getId(), otherBaseCategoryJPA.getId());
        assertEquals(baseCategoryJPA.hashCode(), sameBaseCategoryJPA.hashCode());
        assertFalse(baseCategoryJPA.equals(categoryIdJPA));
        assertFalse(baseCategoryJPA.equals(categoryNameJPA));
        assertFalse(baseCategoryJPA.equals(parentCategoryIdJPA));
        assertFalse(baseCategoryJPA.equals(isStandard));
        assertEquals(baseCategoryJPA.getCategoryId(), sameBaseCategoryJPA.getCategoryId());
        assertNotEquals(baseCategoryJPA.getCategoryId(), otherBaseCategoryJPA.getCategoryId());
        assertNotEquals(0, baseCategoryJPA.hashCode());
        assertNotEquals(baseCategoryJPA, baseCategoryJPADifferentIsStandard);
    }


}