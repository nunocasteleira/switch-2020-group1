package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressTest {

    @Test
    void CreatingValidStreetAddress() {
        String postalCode = "4000-123";
        String location = "Porto";
        Address street = new Address("Rua da Cruz", location, postalCode);
        Assertions.assertNotNull(street);
    }

    @Test
    void CreatingInvalidStreetAddressWithSpecialCharacter() {
        String postalCode = "4000-123";
        String location = "Porto";
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua da/ Cruz", location, postalCode));
    }

    @Test
    void ensureNullStreetAddressIsNotCreated() {
        String postalCode = "4000-123";
        String location = "Porto";
        assertThrows(IllegalArgumentException.class, () -> new Address(null, location, postalCode));
    }

    @Test
    void ensureBlankStreetAddressIsNotCreated() {
        String postalCode = "4000-123";
        String location = "Porto";
        String blankStreet = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(blankStreet, location, postalCode));
    }

    @Test
    void CreatingValidPostalCode() {
        String street = "Rua da Cruz";
        String location = "Porto";
        Address postalCode = new Address(street, location, "4000-123");
        Assertions.assertNotNull(postalCode);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0000-000", "400-000","4000-00", "4/000-000"})
    @NullSource
    void createInvalidPostalCode(String candidate) {
        String street = "Rua da Cruz";
        String location = "Porto";
        assertThrows(IllegalArgumentException.class, () -> new Address(street, location, candidate));
    }

    @Test
    void ensureBlankPostalCodeIsNotCreated() {
        String street = "Rua da Cruz";
        String location = "Porto";
        String blankPostalCode = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(street, location, blankPostalCode));
    }

    @Test
    void CreatingValidLocation() {
        String postalCode = "4000-123";
        String street = "Rua da Cruz";
        Address location = new Address(street, "Porto", postalCode);
        Assertions.assertNotNull(location);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "Porto/"})
    @NullSource
    void CreatingInvalidLocation(String candidate) {
        String postalCode = "4000-123";
        String street = "Rua da Cruz";
        assertThrows(IllegalArgumentException.class, () -> new Address(street, candidate, postalCode));
    }

    @Test
    void ensureBlankLocationIsNotCreated() {
        String postalCode = "4000-123";
        String street = "Rua da Cruz";
        String blankLocation = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(street, blankLocation, postalCode));
    }

    @Test
    void testToString() {
        String postalCode = "4000-123";
        String street = "Rua da Cruz";
        String location = "Porto";
        Address address = new Address(street, location, postalCode);
        String expected = "Rua da Cruz, Porto, 4000-123";

        String result = address.toString();

        assertEquals(expected, result);
    }

    @Test
    void getStreet() {
        String postalCode = "4000-123";
        String expected = "Rua da Cruz";
        String location = "Porto";
        Address address = new Address(expected, location, postalCode);

        String result = address.getStreet();

        assertEquals(expected, result);
    }

    @Test
    void getLocation() {
        String postalCode = "4000-123";
        String street = "Rua da Cruz";
        String expected = "Porto";
        Address address = new Address(street, expected, postalCode);

        String result = address.getLocation();

        assertEquals(expected, result);
    }

    @Test
    void getPostalCode() {
        String expected = "4000-123";
        String street = "Rua da Cruz";
        String location = "Porto";
        Address address = new Address(street, location, expected);

        String result = address.getPostalCode();

        assertEquals(expected, result);
    }
}
