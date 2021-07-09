package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CategoryIdJPATest {

    @ParameterizedTest
    @ValueSource(ints = {1, 100, Integer.MAX_VALUE})
    void testEquals(int id) {
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(id);
        //noinspection UnnecessaryLocalVariable
        CategoryIdJPA categoryIdJPASame = categoryIdJPA;
        CategoryIdJPA categoryIdJPAOther = new CategoryIdJPA(id);
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Candy");
        CategoryIdJPA categoryIdJPANull = null;
        CategoryIdJPA categoryIdJPADifferent = new CategoryIdJPA(50);

        assertEquals(categoryIdJPA, categoryIdJPASame);
        assertSame(categoryIdJPA, categoryIdJPASame);
        assertEquals(categoryIdJPA.hashCode(), categoryIdJPASame.hashCode());
        assertEquals(categoryIdJPA, categoryIdJPAOther);
        assertNotSame(categoryIdJPA, categoryIdJPAOther);
        assertEquals(categoryIdJPA.hashCode(), categoryIdJPAOther.hashCode());
        assertNotEquals(0, categoryIdJPA.hashCode());
        assertNotEquals(categoryIdJPA, categoryIdJPANull);
        assertNotEquals(categoryIdJPA, categoryNameJPA);
        assertNotEquals(categoryIdJPA.hashCode(), categoryNameJPA.hashCode());
        assertEquals(categoryIdJPA, categoryIdJPA);
        assertSame(categoryIdJPA, categoryIdJPA);
        assertNotEquals(categoryIdJPA, id);
        assertNotEquals(categoryIdJPA, categoryIdJPADifferent);
    }

    @Test
    void noArgsConstructor() {
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA();

        assertNotNull(categoryIdJPA);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 100, Integer.MAX_VALUE})
    void getId(int expected) {
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(expected);

        Object result = categoryIdJPA.getId();

        assertEquals(expected, result);
    }
}