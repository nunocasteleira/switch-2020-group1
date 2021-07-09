package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ParentCategoryIdJPATest {

    @Test
    void noArgsConstructor() {
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA();

        assertNotNull(parentCategoryIdJPA);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "100", "123456789", "https://vs118.dei.isep.ipp.pt:8080/categories/standard/1"})
    void testEquals(String id) {
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(id);
        //noinspection UnnecessaryLocalVariable
        ParentCategoryIdJPA parentCategoryIdJPAEqual = parentCategoryIdJPA;
        ParentCategoryIdJPA parentCategoryIdJPASame = new ParentCategoryIdJPA(id);
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA("Candy");
        ParentCategoryIdJPA parentCategoryIdJPANull = null;
        ParentCategoryIdJPA parentCategoryIdJPADifferent = new ParentCategoryIdJPA(50);

        assertEquals(parentCategoryIdJPA, parentCategoryIdJPAEqual);
        assertSame(parentCategoryIdJPA, parentCategoryIdJPAEqual);
        assertEquals(parentCategoryIdJPA.hashCode(), parentCategoryIdJPAEqual.hashCode());
        assertEquals(parentCategoryIdJPA, parentCategoryIdJPASame);
        assertNotSame(parentCategoryIdJPA, parentCategoryIdJPASame);
        assertEquals(parentCategoryIdJPA.hashCode(), parentCategoryIdJPASame.hashCode());
        assertNotEquals(0, parentCategoryIdJPA.hashCode());
        assertNotEquals(parentCategoryIdJPA, parentCategoryIdJPANull);
        assertNotEquals(parentCategoryIdJPA, categoryNameJPA);
        assertNotEquals(parentCategoryIdJPA.hashCode(), categoryNameJPA.hashCode());
        assertEquals(parentCategoryIdJPA, parentCategoryIdJPA);
        assertSame(parentCategoryIdJPA, parentCategoryIdJPA);
        assertNotEquals(parentCategoryIdJPA, id);
        assertNotEquals(parentCategoryIdJPA, parentCategoryIdJPADifferent);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "100", "123456789", "https://vs118.dei.isep.ipp.pt:8080/categories/standard/1"})
    void getId(String expected) {
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(expected);

        String result = parentCategoryIdJPA.getId();

        assertEquals(expected, result);
    }

}