package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PersonNameTest {

    @Test
    void CreatingValidName() {
        PersonName personName = new PersonName("Mangala");
        Assertions.assertNotNull(personName);
    }

    @Test
    void CreatingInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> new PersonName("123"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void ensureNullNameIsNotCreated(String candidate) {
        assertThrows(IllegalArgumentException.class, () -> new PersonName(candidate));
    }

    @Test
    void testEquals() {
        String name = "Person Name";
        PersonName personName = new PersonName(name);
        //noinspection UnnecessaryLocalVariable
        PersonName personNameSame = personName;
        PersonName personNameOther = new PersonName(name);

        assertEquals(personName, personNameSame);
        assertSame(personName, personNameSame);
        assertEquals(personName.hashCode(), personNameSame.hashCode());
        assertEquals(personName, personNameOther);
        assertNotSame(personName, personNameOther);
        assertEquals(personName.hashCode(), personNameOther.hashCode());
        assertNotEquals(personName, name);
        assertNotEquals(0, personName.hashCode());
        assertFalse(personName.equals(null));
        assertTrue(personName.equals(personNameOther));
    }

    @Test
    void testEqualsDifferentNames() {
        String firstName = "Person Name";
        String secondName = "Other Person Name";
        PersonName personName = new PersonName(firstName);
        PersonName personNameOther = new PersonName(secondName);

        assertNotSame(personName, personNameOther);
        assertNotEquals(personName, personNameOther);
        assertNotEquals(0, personName.hashCode());
        assertNotEquals(0, personNameOther.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Person Name";
        PersonName personName = new PersonName(expected);

        String result = personName.toString();

        assertEquals(expected, result);
    }

}
