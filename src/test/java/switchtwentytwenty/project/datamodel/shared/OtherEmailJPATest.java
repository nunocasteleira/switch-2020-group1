package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.person.PersonJPA;

import static org.junit.jupiter.api.Assertions.*;

class OtherEmailJPATest {

    PersonJPA personJPA;
    String email;
    OtherEmailJPA.OtherEmailId otherEmailId;

    @Test
    void noArgsConstructor() {
        OtherEmailJPA.OtherEmailId otherEmail = new OtherEmailJPA.OtherEmailId();

        assertNotNull(otherEmail);
    }

    @Test
    void equalsAndHashCode() {
        otherEmailId = new OtherEmailJPA.OtherEmailId(personJPA, email);
        OtherEmailJPA.OtherEmailId sameOtherEmailId = otherEmailId;
        OtherEmailJPA.OtherEmailId otherOtherEmailId =
                new OtherEmailJPA.OtherEmailId(personJPA, email);
        OtherEmailJPA.OtherEmailId differentOtherEmailId =
                new OtherEmailJPA.OtherEmailId(personJPA, "otherEmail");

        assertEquals(otherEmailId, sameOtherEmailId);
        assertSame(otherEmailId, sameOtherEmailId);
        assertEquals(otherEmailId.hashCode(), sameOtherEmailId.hashCode());
        assertEquals(otherEmailId, otherOtherEmailId);
        assertNotSame(otherEmailId, otherOtherEmailId);
        assertEquals(otherEmailId.hashCode(), otherOtherEmailId.hashCode());
        assertNotEquals(0, otherEmailId.hashCode());
        assertNotEquals(otherEmailId, differentOtherEmailId);
        assertNotEquals(otherEmailId, personJPA);
        assertNotEquals(null, otherEmailId);
        assertTrue(otherEmailId.equals(sameOtherEmailId));
        assertTrue(otherEmailId.equals(otherOtherEmailId));
        assertFalse(otherEmailId.equals(differentOtherEmailId));
        assertFalse(otherEmailId.equals(personJPA));
    }

    @Test
    void getEmail() {

        email = "joao@gmail.com";
        String expected = "joao@gmail.com";

        OtherEmailJPA otherEmailJPA = new OtherEmailJPA();
        otherEmailJPA.setOtherEmail(email);
        otherEmailJPA.setPerson(personJPA);
        String result;

        result = otherEmailJPA.getOtherEmail();

        assertEquals(result, expected);
    }
}
