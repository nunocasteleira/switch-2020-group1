package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelationshipTypeTest {
    @Test
    void valueOf_Successfully_ValueOne() {
        //arrange
        int numericValue = 1;
        //act
        RelationshipType expected = RelationshipType.SPOUSE;
        RelationshipType result = RelationshipType.valueOf(numericValue);
        //assert
        assertEquals(expected, result);
    }

    @Test
    void valueOf_Successfully_ValueTen() {
        //arrange
        int numericValue = 10;
        //act
        RelationshipType expected = RelationshipType.COUSIN;
        RelationshipType result = RelationshipType.valueOf(numericValue);
        //assert
        assertEquals(expected, result);
    }

    @Test
    void valueOf_Exception_ValueTwelve() {
        //arrange
        int numericValue = 12;
        //act
        //assert
        assertThrows(IllegalArgumentException.class,
                () -> RelationshipType.valueOf(numericValue));
    }

    @Test
    void valueOf_Exception_ValueZero() {
        //arrange
        int numericValue = 0;
        //act
        //assert
        assertThrows(IllegalArgumentException.class,
                () -> RelationshipType.valueOf(numericValue));
    }

    @Test
    void toString_Successfully_SPOUSE_SPOUSE() {
        String expected = "SPOUSE";
        String result = RelationshipType.SPOUSE.toString();
        assertEquals(expected, result);
    }

    @Test
    void toString_Successfully_ValueFive_SIBLING() {
        int numericValue = 5;
        String expected = "SIBLING";
        String result = RelationshipType.valueOf(numericValue).toString();
        assertEquals(expected, result);
    }

    @Test
    void getNumericValue_Successfully_COUSIN_10() {
        RelationshipType relationshipType = RelationshipType.COUSIN;
        int expectedNumericValue = 10;

        int relationshipNumericValue = relationshipType.getNumericValue();

        assertEquals(expectedNumericValue, relationshipNumericValue);
    }

    @Test
    void getNumericValue_Successfully_SPOUSE_1() {
        RelationshipType relationshipType = RelationshipType.SPOUSE;
        int expectedNumericValue = 1;

        int relationshipNumericValue = relationshipType.getNumericValue();

        assertEquals(expectedNumericValue, relationshipNumericValue);
    }
}
