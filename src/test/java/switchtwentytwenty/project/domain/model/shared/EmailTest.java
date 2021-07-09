package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {"12345@isep.ipp.pt", "abcdefgh@isep.ipp.pt", "abcdefgh@gmail.com"})
    void createNewEmailWithNumbers_Letters_AndDomainWithThreeFields(String candidate) {
        //Act
        Email newEmail = new Email(candidate);
        //Assert
        assertNotNull(newEmail);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefgh@isep.ipp.p", "abcdefgh", "abcdefgh@1234.567.89"})
    void creatingEmailWithNoDomainThrowsException(String emailAddress) {
        //Assert
        assertThrows(IllegalArgumentException.class, () -> new Email(emailAddress));
    }

    @Test
    void creatingNullEmailThrowsException() {
        //Assert
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }

    @Test
    void creatingEmptyEmailThrowsException() {
        //Assert
        assertThrows(IllegalArgumentException.class, () -> new Email(""));
    }

    @Test
    void creatingBlankEmailThrowsException() {
        //Assert
        assertThrows(IllegalArgumentException.class, () -> new Email("   "));
    }

    @Test
    void ensureEqualsIsOk() {
        Email email = new Email("antonio@gmail.com");
        String phoneNumber = "911111111";

        assertFalse(email.equals(null));
        assertFalse(email.equals(phoneNumber));
        assertNotEquals(null, email);
        assertNotEquals(phoneNumber, email);
    }

    @Test
    void testSameEquals() {
        Email emailAddress = new Email("antonio@sapo.pt");
        Email emailAddress1 = emailAddress;
        boolean result;

        result = emailAddress.equals(emailAddress1);

        assertTrue(result);
        assertEquals(emailAddress.hashCode(), emailAddress1.hashCode());
    }

    @Test
    void testNotSameEquals() {
        Email emailAddress = new Email("antonio@sapo.pt");
        Email emailAddress1 = new Email("manuel@sapo.pt");

        assertNotEquals(emailAddress.hashCode(), emailAddress1.hashCode());
    }

    @Test
    void getEmailAddress() {
        String expected = "antonio@gmail.com";
        Email email = new Email(expected);
        String result;

        result = email.getEmailAddress();

        assertEquals(expected, result);
    }

    @Test
    void setEmailAddress() {
        String expected = "antonio@gmail.com";
        Email email = new Email();
        String result;

        email.setEmailAddress(expected);
        result = email.getEmailAddress();

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345@isep.ipp.pt", "abcdefgh@isep.ipp.pt", "abcdefgh@gmail.com"})
    void toString(String expected) {
        Email newEmail = new Email(expected);
        //Act
        String result = newEmail.toString();
        //Assert
        assertEquals(expected, result);
    }
}
