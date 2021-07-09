package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import switchtwentytwenty.project.domain.exceptions.InvalidNameException;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CategoryNameTest {

    @Test
    void ensureCategoryNameIsEqual() {
        String name = "Groceries";

        CategoryName aCategoryName = new CategoryName(name);

        assertTrue(aCategoryName.equals(aCategoryName));
    }

    @Test
    void ensureCategoryNameIsNotEqual() {
        String name = "Groceries";

        CategoryName aCategoryName = new CategoryName(name);
        CategoryName otherCategoryName = null;

        assertFalse(aCategoryName.equals(otherCategoryName));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "Category *"})
    void ensureCategoryNameIsInvalid(String name) {

        assertThrows(InvalidNameException.class, () -> new CategoryName(name));
    }

    @Test
    void ensureCategoryNamesAreEqual() {
        String name = "Shopping";
        String sameName = "Shopping";

        CategoryName aCategoryName = new CategoryName(name);
        CategoryName sameCategoryName = new CategoryName(sameName);

        assertEquals(aCategoryName, sameCategoryName);
    }

    @Test
    void ensureCategoryNameIsNotEqualToCategoryId() {
        String name = "Shopping";
        int id = new Random().nextInt();

        CategoryName aCategoryName = new CategoryName(name);
        CategoryId categoryId = new CategoryId(id);

        assertFalse(aCategoryName.equals(categoryId));
    }

    @Test
    void ensureCategoryNamesAreNotEqual() {
        String name = "Shopping";
        String otherName = "Groceries";

        CategoryName aCategoryName = new CategoryName(name);
        CategoryName otherCategoryName = new CategoryName(otherName);

        assertNotEquals(aCategoryName, otherCategoryName);
    }


    @Test
    void ensureHashCodeEquals() {

        String name = "Shopping";
        String sameName = "Shopping";

        CategoryName aCategoryName = new CategoryName(name);
        CategoryName sameCategoryName = new CategoryName(sameName);

        assertEquals(aCategoryName.hashCode(), sameCategoryName.hashCode());
    }

    @Test
    void ensureHashCodeNotEquals() {

        String name = "Shopping";
        String otherName = "Groceries";

        CategoryName aCategoryName = new CategoryName(name);
        CategoryName otherCategoryName = new CategoryName(otherName);

        assertNotEquals(aCategoryName.hashCode(), otherCategoryName.hashCode());
    }

    @Test
    void ensureToStringIsCorrect() {

        String name = "Shopping";
        String expected = name.toUpperCase();
        String result;

        CategoryName aCategoryName = new CategoryName(name);
        result = aCategoryName.toString();

        assertEquals(expected, result);
    }
}