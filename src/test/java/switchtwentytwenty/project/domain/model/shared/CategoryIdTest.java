package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CategoryIdTest {


    @Test
    void ensureIdIsEqual() {
        int expected = new Random().nextInt();
        CategoryId aCategoryId = new CategoryId(expected);
        Object result;

        result = aCategoryId.getId();

        assertEquals(expected, result);
    }

    @Test
    void ensureCategoryIdIsEqual() {
        int id = new Random().nextInt();

        CategoryId aCategoryId = new CategoryId(id);

        assertTrue(aCategoryId.equals(aCategoryId));
    }

    @Test
    void ensureCategoryIdIsNotEqual() {
        int id = new Random().nextInt();

        CategoryId aCategoryId = new CategoryId(id);
        CategoryId otherCategoryId = null;

        assertFalse(aCategoryId.equals(otherCategoryId));
    }

    @Test
    void ensureCategoryIdsAreEqual() {
        int id = new Random().nextInt();
        int sameId = id;

        CategoryId aCategoryId = new CategoryId(id);
        CategoryId sameCategoryId = new CategoryId(sameId);

        assertEquals(aCategoryId, sameCategoryId);
    }

    @Test
    void ensureCategoryNamesAreNotEqual() {
        int id = new Random().nextInt();
        int otherId = new Random().nextInt();

        CategoryId aCategoryId = new CategoryId(id);
        CategoryId otherCategoryId = new CategoryId(otherId);

        assertNotEquals(aCategoryId, otherCategoryId);
    }


    @Test
    void ensureHashCodeEquals() {

        int id = new Random().nextInt();
        int sameId = id;

        CategoryId aCategoryId = new CategoryId(id);
        CategoryId sameCategoryId = new CategoryId(sameId);

        assertEquals(aCategoryId.hashCode(), sameCategoryId.hashCode());
    }

    @Test
    void ensureHashCodeNotEquals() {

        int id = new Random().nextInt();
        int otherId = new Random().nextInt();

        CategoryId aCategoryId = new CategoryId(id);
        CategoryId otherCategoryId = new CategoryId(otherId);

        assertNotEquals(aCategoryId.hashCode(), otherCategoryId.hashCode());
    }

    @Test
    void ensureGetIdIsEqual() {
        int id = new Random().nextInt();
        Object result;

        CategoryId aCategoryId = new CategoryId(id);
        result = aCategoryId.getId();

        assertEquals(id, result);
    }

    @Test
    void ensureGetIdIsNotEqual() {
        int id = new Random().nextInt();
        int randomId = new Random().nextInt();
        Object result;

        CategoryId aCategoryId = new CategoryId(randomId);
        result = aCategoryId.getId();

        assertNotEquals(id, result);
    }

    @Test
    void ensureCategoryIdIsNotEqualToCategoryName() {
        int id = new Random().nextInt();
        String name = "Chocolate";

        CategoryId aCategoryId = new CategoryId(id);
        CategoryName aCategoryName = new CategoryName(name);


        assertFalse(aCategoryId.equals(aCategoryName));
    }


}