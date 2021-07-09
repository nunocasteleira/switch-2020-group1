package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.person.PersonJPA;
import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberJPATest {

    PersonJPA personJPA;
    String phoneNumber;
    PhoneNumberJPA.OtherPhoneId otherPhoneId;

    @Test
    void noArgsConstructor() {
        PhoneNumberJPA.OtherPhoneId otherPhoneNumberJPA = new PhoneNumberJPA.OtherPhoneId();

        assertNotNull(otherPhoneNumberJPA);
    }

    @Test
    void equalsAndHashCode() {
        otherPhoneId = new PhoneNumberJPA.OtherPhoneId(personJPA, phoneNumber);
        PhoneNumberJPA.OtherPhoneId sameOtherPhoneId = otherPhoneId;
        PhoneNumberJPA.OtherPhoneId otherOtherPhoneId =
                new PhoneNumberJPA.OtherPhoneId(personJPA, phoneNumber);
        PhoneNumberJPA.OtherPhoneId differentOtherPhoneId =
                new PhoneNumberJPA.OtherPhoneId(personJPA, "123");

        assertEquals(otherPhoneId, sameOtherPhoneId);
        assertSame(otherPhoneId, sameOtherPhoneId);
        assertEquals(otherPhoneId.hashCode(), sameOtherPhoneId.hashCode());
        assertEquals(otherPhoneId, otherOtherPhoneId);
        assertEquals(otherPhoneId.toString(), otherOtherPhoneId.toString());
        assertNotSame(otherPhoneId, otherOtherPhoneId);
        assertEquals(otherPhoneId.hashCode(), otherOtherPhoneId.hashCode());
        assertNotEquals(0, otherPhoneId.hashCode());
        assertNotEquals(otherPhoneId, differentOtherPhoneId);
        assertNotEquals(otherPhoneId.toString(), differentOtherPhoneId.toString());
        assertNotEquals(otherPhoneId.hashCode(), differentOtherPhoneId.hashCode());
        assertNotEquals(otherPhoneId, personJPA);
        assertNotEquals(null, otherPhoneId);
        assertFalse(otherPhoneId.equals(null));
        assertFalse(otherPhoneId.equals(personJPA));
        assertTrue(otherPhoneId.equals(sameOtherPhoneId));
    }

    @Test
    void getPhoneNumber(){

        phoneNumber = "914058888";
        String expected = "914058888";

        PhoneNumberJPA phoneNumberJPA = new PhoneNumberJPA();
        phoneNumberJPA.setPhoneNumber(phoneNumber);
        phoneNumberJPA.setPerson(personJPA);
        String result;

        result = phoneNumberJPA.getPhoneNumber();

        assertEquals(result, expected);

    }
}
