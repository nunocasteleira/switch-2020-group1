package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.CategoryName;

import static org.junit.jupiter.api.Assertions.*;

class CategoryNameJPATest {

    @Test
    void testEqualsAndHashCode() {
        String name = "Test Category";
        String otherCategory = "Other Category";
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA(name);
        //noinspection UnnecessaryLocalVariable
        CategoryNameJPA categoryNameJPASame = categoryNameJPA;
        CategoryNameJPA categoryNameJPAOther = new CategoryNameJPA(name);
        CategoryNameJPA categoryNameJPADifferent = new CategoryNameJPA(otherCategory);

        assertEquals(categoryNameJPA, categoryNameJPASame);
        assertSame(categoryNameJPA, categoryNameJPASame);
        assertEquals(categoryNameJPA.hashCode(), categoryNameJPASame.hashCode());
        assertEquals(categoryNameJPA, categoryNameJPAOther);
        assertNotSame(categoryNameJPA, categoryNameJPAOther);
        assertEquals(categoryNameJPA.hashCode(), categoryNameJPAOther.hashCode());
        assertNotEquals(0, categoryNameJPA.hashCode());
        assertNotEquals(name, categoryNameJPA);
        assertNotEquals(categoryNameJPA, categoryNameJPADifferent);
    }

    @Test
    void testEquals() {
        String name = "TestCategory";
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA(name);
        CategoryName categoryName = new CategoryName(name);
        CategoryNameJPA categoryNameJPANull = null;

        assertNotEquals(categoryNameJPA, categoryName);
        assertNotEquals(categoryNameJPANull, categoryNameJPA);
    }

    @Test
    void getName() {
        String name = "TestCategory";
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA(name);
        String result;

        result = categoryNameJPA.getName();

        assertEquals(name, result);
    }

    @Test
    void noArgsConstructor() {
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA();

        assertNotNull(categoryNameJPA);
    }
}