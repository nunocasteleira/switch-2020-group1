package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressJPATest {

    @Test
    void testEquals() {
        String street = "Rua X";
        String street2 = "Rua Y";
        String location = "Porto";
        String location2 = "Braga";
        String postalCode = "1234-123";
        String postalCode2 = "4000-400";
        AddressJPA addressJPA = new AddressJPA(street, location, postalCode);
        AddressJPA addressJPASame = addressJPA;
        AddressJPA addressJPAEqual = new AddressJPA(street, location, postalCode);
        AddressJPA addressJPAOtherStreet = new AddressJPA(street2, location, postalCode);
        AddressJPA addressJPAOtherLocation = new AddressJPA(street, location2, postalCode);
        AddressJPA addressJPAOtherCode = new AddressJPA(street, street, postalCode2);
        AddressJPA nullAddress = null;
        PersonNameJPA personNameJPA = new PersonNameJPA("some");

        assertEquals(addressJPA, addressJPASame);
        assertEquals(addressJPA, addressJPAEqual);
        assertSame(addressJPA, addressJPASame);
        assertNotSame(addressJPA, addressJPAEqual);
        assertEquals(addressJPA.hashCode(), addressJPASame.hashCode());
        assertEquals(addressJPA.getStreet(), addressJPASame.getStreet());
        assertEquals(addressJPA.getLocation(), addressJPASame.getLocation());
        assertEquals(addressJPA.getPostalCode(), addressJPASame.getPostalCode());
        assertEquals(addressJPA.hashCode(), addressJPAEqual.hashCode());
        assertEquals(addressJPA.getStreet(), addressJPAEqual.getStreet());
        assertEquals(addressJPA.getLocation(), addressJPAEqual.getLocation());
        assertEquals(addressJPA.getPostalCode(), addressJPAEqual.getPostalCode());
        assertNotEquals(addressJPA, addressJPAOtherStreet);
        assertNotEquals(addressJPA.hashCode(), addressJPAOtherStreet.hashCode());
        assertNotEquals(addressJPA.getStreet(), addressJPAOtherStreet.getStreet());
        assertNotEquals(addressJPA, addressJPAOtherStreet);
        assertNotEquals(addressJPA.hashCode(), addressJPAOtherLocation.hashCode());
        assertNotEquals(addressJPA.getLocation(), addressJPAOtherLocation.getLocation());
        assertNotEquals(addressJPA, addressJPAOtherStreet);
        assertNotEquals(addressJPA.hashCode(), addressJPAOtherCode.hashCode());
        assertNotEquals(addressJPA.getPostalCode(), addressJPAOtherCode.getPostalCode());
        assertTrue(addressJPA.equals(addressJPASame));
        assertFalse(addressJPA.equals(addressJPAOtherLocation));
        assertFalse(addressJPA.equals(location));
        assertNotEquals(addressJPA,nullAddress);
        assertNotEquals(addressJPA,personNameJPA);
        assertNotEquals(0,addressJPA.hashCode());
    }

    @Test
    void getStreet() {
        String expected = "Rua X";
        String location = "Porto";
        String postalCode = "1234-123";
        AddressJPA addressJPA = new AddressJPA(expected, location, postalCode);

        String result = addressJPA.getStreet();

        assertEquals(expected, result);
    }

    @Test
    void getLocation() {
        String street = "Rua X";
        String expected = "Porto";
        String postalCode = "1234-123";
        AddressJPA addressJPA = new AddressJPA(street, expected, postalCode);

        String result = addressJPA.getLocation();

        assertEquals(expected, result);
    }

    @Test
    void getPostalCode() {
        String street = "Rua X";
        String location = "Porto";
        String expected = "1234-123";
        AddressJPA addressJPA = new AddressJPA(street, location, expected);

        String result = addressJPA.getPostalCode();

        assertEquals(expected, result);
    }
}