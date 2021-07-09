package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import switchtwentytwenty.project.domain.exceptions.InvalidNameException;

import static org.junit.jupiter.api.Assertions.*;

class FamilyNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"Silva", "Borges-Silva", "Borges Silva"})
    void createFamilyName_Successfully_NoFamilies(String name) {
        String expected = name;

        FamilyName result = new FamilyName(name);

        assertNotNull(result);
        assertEquals(expected, result.getPersonName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Borges-Silva", "Borges Silva"})
    void createFamilyName_Invalid_DifferentName(String name) {
        String expected = "Silva";

        FamilyName result = new FamilyName(name);

        assertNotNull(result);
        assertNotEquals(expected, result.getPersonName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"Filipe203", "123", "Filipe_Antonio"})
    void createFamilyName_Invalid_NullName(String name) {
        assertThrows(InvalidNameException.class, () -> new FamilyName(name));
    }

    @Test
    void testEquals_test1_DifferentObjects() {
        String name = "Silva";
        FamilyName familyName = new FamilyName(name);
        FamilyName otherFamilyName = new FamilyName(name);

        assertNotSame(familyName, otherFamilyName);
        assertEquals(familyName, otherFamilyName);
    }

    @Test
    void testEquals_test2_SameObjects() {
        String name = "Silva";
        FamilyName familyName = new FamilyName(name);
        FamilyName otherFamilyName = familyName;

        assertSame(familyName, otherFamilyName);
        assertEquals(familyName, otherFamilyName);
    }

    @Test
    void testEquals_test3_DifferentObjectsType() {
        String name = "Silva";
        String emailAddress = "tito@gmail.com";
        FamilyName familyName = new FamilyName(name);
        Email email = new Email(emailAddress);

        assertNotEquals(familyName, email);
    }

    @Test
    void testEquals_test4_NotEqualObjects() {
        String name = "Silva";
        String otherName = "Oliveira";
        FamilyName familyName = new FamilyName(name);
        FamilyName otherFamilyName = new FamilyName(otherName);

        assertNotEquals(familyName, otherFamilyName);
        assertNotEquals(null, familyName);
    }

    @Test
    void testHashCode() {
        String name = "Silva";
        FamilyName familyName = new FamilyName(name);
        FamilyName otherFamilyName = new FamilyName(name);

        assertNotSame(familyName, otherFamilyName);
        assertEquals(familyName.hashCode(), otherFamilyName.hashCode());
    }

    @Test
    void testHashCode_false() {
        String name = "Silva";
        String otherName = "Oliveira";
        FamilyName familyName = new FamilyName(name);
        FamilyName otherFamilyName = new FamilyName(otherName);

        assertNotEquals(familyName.hashCode(), otherFamilyName.hashCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Silva", "Borges-Silva", "Borges Silva"})
    void toString(String expected) {
        FamilyName familyName = new FamilyName(expected);
        String result = familyName.toString();

        assertEquals(expected, result);
    }
}