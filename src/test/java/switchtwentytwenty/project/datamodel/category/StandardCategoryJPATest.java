package switchtwentytwenty.project.datamodel.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryNameJPA;
import switchtwentytwenty.project.datamodel.shared.ParentCategoryIdJPA;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StandardCategoryJPATest {

    @Test
    void ensureNoArgs() {
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA();

        assertNotNull(standardCategoryJPA);
    }

    @Test
    void createStandardRootCategoryJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Vegetables");

        //act
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA, categoryNameJPA);

        //assert
        assertNotNull(standardCategoryJPA);
    }

    @Test
    void createStandardChildCategoryJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());

        //act
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);

        //assert
        assertNotNull(standardCategoryJPA);
    }

    @Test
    void getCategoryNameJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Vegetables");
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA, categoryNameJPA);
        CategoryNameJPA result;

        //act
        result = standardCategoryJPA.getName();

        //assert
        assertEquals(categoryNameJPA, result);
    }

    @Test
    void getCategoryIdJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Groceries");
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA, categoryNameJPA);
        CategoryIdJPA result;

        //act
        result = standardCategoryJPA.getCategoryId();

        //assert
        assertEquals(categoryIdJPA, result);
    }

    @Test
    void getCategoryParentIdJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);
        ParentCategoryIdJPA result;

        //act
        result = standardCategoryJPA.getParentId();

        //assert
        assertEquals(parentCategoryIdJPA, result);
    }

    @Test
    void ensureCategoryIdDatabaseNotEquals() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);
        long randomLong = new Random().nextLong();
        long result;


        //act
        result = standardCategoryJPA.getId();

        //assert
        assertNotEquals(randomLong, result);
    }


    @Test
    void ensureCategoryIdDatabaseIsEqual() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);
        long randomIdDatabase = new Random().nextLong();
        standardCategoryJPA.setId(randomIdDatabase);
        long result;

        //act
        result = standardCategoryJPA.getId();

        //assert
        assertEquals(randomIdDatabase, result);
    }

    @Test
    void ensureCategoryIdDatabaseIsSetAndEqual() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);
        long idDatabase = new Random().nextLong();
        long result;


        //act
        standardCategoryJPA.setId(idDatabase);
        result = standardCategoryJPA.getId();

        //assert
        assertEquals(idDatabase, result);
    }

    @Test
    void ensureStandardCategoryJPAIsStandard() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);
        boolean result;


        //act

        result = standardCategoryJPA.isStandard();

        //assert
        assertTrue(result);
    }


    @Test
    void ensureCategoryIdDatabaseSetNotEqualsToRandom() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);
        long randomIdDatabase = new Random().nextLong();
        long randomNumber = new Random().nextLong();
        long result;


        //act
        standardCategoryJPA.setId(randomIdDatabase);
        result = standardCategoryJPA.getId();

        //assert
        assertNotEquals(randomNumber, result);
    }


    @Test
    void ensureEqualsAndHashCodeAreWorking() {
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());
        CategoryNameJPA otherCategoryNameJPA = new CategoryNameJPA("Bananas");

        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);

        CategoryIdJPA rootCategoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        StandardCategoryJPA rootStandardCategoryJPA = new StandardCategoryJPA(rootCategoryIdJPA,
                categoryNameJPA);
        rootStandardCategoryJPA.setId(new Random().nextLong());

        StandardCategoryJPA standardCategoryJPANull = null;

        StandardCategoryJPA sameStandardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);

        assertNotEquals(standardCategoryJPA, rootStandardCategoryJPA);
        assertNotEquals(standardCategoryJPA, standardCategoryJPANull);
        assertEquals(standardCategoryJPA, sameStandardCategoryJPA);
        assertNotSame(standardCategoryJPA, sameStandardCategoryJPA);
        assertTrue(standardCategoryJPA.equals(standardCategoryJPA));
        assertFalse(standardCategoryJPA.equals(standardCategoryJPANull));
        assertFalse(standardCategoryJPA.equals(rootStandardCategoryJPA));
        assertNotEquals(standardCategoryJPA.hashCode(), rootCategoryIdJPA.hashCode());
        assertFalse(standardCategoryJPA.equals(categoryIdJPA));
        assertEquals(standardCategoryJPA.getId(), sameStandardCategoryJPA.getId());
        assertTrue(standardCategoryJPA.getCategoryId().equals(sameStandardCategoryJPA.getCategoryId()));
        assertTrue(standardCategoryJPA.getName().equals(sameStandardCategoryJPA.getName()));
        assertTrue(standardCategoryJPA.getParentId().equals(sameStandardCategoryJPA.getParentId()));
        assertFalse(standardCategoryJPA.getCategoryId().equals(rootStandardCategoryJPA.getCategoryId()));
        assertFalse(standardCategoryJPA.getName().equals(otherCategoryNameJPA));
        assertFalse(standardCategoryJPA.getParentId().equals(rootStandardCategoryJPA.getParentId()));
        assertNotEquals(standardCategoryJPA.getId(), rootStandardCategoryJPA.getId());
    }

    @Test
    void ensureHashCodeNotEquals() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");

        CategoryIdJPA otherCategoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA otherCategoryNameJPA = new CategoryNameJPA("Bananas");

        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA);
        StandardCategoryJPA otherStandardCategoryJPA = new StandardCategoryJPA(otherCategoryIdJPA,
                otherCategoryNameJPA);
        //act + assert
        assertNotEquals(standardCategoryJPA.hashCode(), otherStandardCategoryJPA.hashCode());
    }

    @Test
    void ensureHashCodeNotEqualsWithChildCategoriesJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());

        CategoryIdJPA otherCategoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA otherCategoryNameJPA = new CategoryNameJPA("Bananas");
        ParentCategoryIdJPA otherParentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());

        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);
        StandardCategoryJPA otherStandardCategoryJPA = new StandardCategoryJPA(otherCategoryIdJPA,
                otherCategoryNameJPA, otherParentCategoryIdJPA);

        //act + assert
        assertNotEquals(standardCategoryJPA.hashCode(), otherStandardCategoryJPA.hashCode());
    }

    @Test
    void ensureHashCodeEquals() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");

        CategoryNameJPA sameCategoryNameJPA = new CategoryNameJPA("Carrots");

        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA);
        StandardCategoryJPA sameStandardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                sameCategoryNameJPA);

        //act + assert
        assertEquals(standardCategoryJPA.hashCode(), sameStandardCategoryJPA.hashCode());
    }

    @Test
    void ensureHashCodeEqualsWithChildCategoriesJPA() {
        //arrange
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(new Random().nextInt());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Carrots");
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(new Random().nextInt());

        CategoryNameJPA sameCategoryNameJPA = new CategoryNameJPA("Carrots");

        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                categoryNameJPA, parentCategoryIdJPA);
        StandardCategoryJPA sameStandardCategoryJPA = new StandardCategoryJPA(categoryIdJPA,
                sameCategoryNameJPA, parentCategoryIdJPA);
        //act + assert
        assertEquals(standardCategoryJPA.hashCode(), sameStandardCategoryJPA.hashCode());
    }
}
