package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void CreatingValidPhoneNumber() {
        PhoneNumber phoneNumber = new PhoneNumber("964054870");
        Assertions.assertNotNull(phoneNumber);
    }

    @ParameterizedTest
    @ValueSource(strings = {"191958568", "991958568", "23955662"})
    void CreatingPhoneNumberWithAnInvalidFirstDigit(String candidate) {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber(candidate));
    }

    @Test
    void ensureNullPhoneNumberIsNotCreated() {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber(null));
    }

    @Test
    void testToString() {
        String expected = "964054870";
        PhoneNumber phoneNumber = new PhoneNumber(expected);

        String result = phoneNumber.toString();

        assertEquals(expected, result);
    }

    @Test
    void equalsAndHashCode(){
        String phone = "964054870";
        PhoneNumber phoneNumber = new PhoneNumber(phone);
        PhoneNumber phoneNumber1 = phoneNumber;
        PhoneNumber other = new PhoneNumber("923333333");

        assertEquals(phoneNumber, phoneNumber1);
        assertNotEquals(phoneNumber, other);
        assertNotEquals(phoneNumber.hashCode(), other.hashCode());
        assertEquals(phoneNumber1.hashCode(), phoneNumber.hashCode());
        assertNotEquals(phone, other);
        assertNotEquals(null, phoneNumber);
        assertFalse(phoneNumber.equals(null));
        assertFalse(phoneNumber.equals(new FamilyId(12)));
    }
}
