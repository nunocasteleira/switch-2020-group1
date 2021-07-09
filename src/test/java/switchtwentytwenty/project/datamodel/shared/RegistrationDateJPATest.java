package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationDateJPATest {

    @Test
    void noArgsConstructor() {
        RegistrationDateJPA registrationDateJPA = new RegistrationDateJPA();

        assertNotNull(registrationDateJPA);
    }

    @Test
    void testEquals() {
        String date = "01/02/2020";
        String otherDate = "02/02/2020";
        RegistrationDateJPA registrationDateJPA = new RegistrationDateJPA(date);
        //noinspection UnnecessaryLocalVariable
        RegistrationDateJPA registrationDateJPASame = registrationDateJPA;
        RegistrationDateJPA registrationDateJPAOther = new RegistrationDateJPA(date);
        RegistrationDateJPA registrationDateJPADifferent = new RegistrationDateJPA(otherDate);
        RegistrationDateJPA nullRegistrationData = null;
        PersonNameJPA personNameJPA = new PersonNameJPA("some");

        assertSame(registrationDateJPA, registrationDateJPASame);
        assertEquals(registrationDateJPA, registrationDateJPASame);
        assertEquals(registrationDateJPA.hashCode(), registrationDateJPASame.hashCode());
        assertNotSame(registrationDateJPA, registrationDateJPAOther);
        assertEquals(registrationDateJPA, registrationDateJPAOther);
        assertEquals(registrationDateJPA.hashCode(), registrationDateJPAOther.hashCode());
        assertNotSame(registrationDateJPA, registrationDateJPADifferent);
        assertNotEquals(registrationDateJPA, registrationDateJPADifferent);
        assertNotEquals(registrationDateJPA.hashCode(), registrationDateJPADifferent.hashCode());
        assertNotEquals(0, registrationDateJPA.hashCode());
        assertNotEquals(registrationDateJPA, date);
        assertNotEquals(registrationDateJPA, nullRegistrationData);
        assertNotEquals(registrationDateJPA, personNameJPA);
    }

    @Test
    void getDate() {
        String expected = "01/02/2020";
        RegistrationDateJPA registrationDateJPA = new RegistrationDateJPA(expected);

        String result = registrationDateJPA.getDate();

        assertEquals(expected, result);
    }
}