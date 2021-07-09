package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import switchtwentytwenty.project.domain.exceptions.ObjectCanNotBeNullException;

import static org.junit.jupiter.api.Assertions.*;

class PersonVatTest {

    @Test
    void ensureVatIsCreated(){
        String vat = "222333444";

        PersonVat personVat = new PersonVat(vat);

        assertNotNull(personVat);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void ensureNullOrEmptyVatIsNotCreated(String candidate){
        assertThrows(ObjectCanNotBeNullException.class, () -> new PersonVat(candidate));
    }


    @Test
    void ensureInvalidVatIsNotCreated(){
        String vat = "999999999";

        assertThrows(IllegalArgumentException.class, () -> new PersonVat(vat));
    }

    @Test
    void testToString() {
        String expected = "222333444";
        PersonVat personVat = new PersonVat(expected);

        String result = personVat.toString();

        assertEquals(expected, result);
    }
}