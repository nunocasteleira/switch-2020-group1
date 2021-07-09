package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationDateTest {

    @Test
    void createRegistrationDate_Successfully_SameDate() {
        LocalDate expected = LocalDate.now();

        RegistrationDate result = new RegistrationDate();

        assertNotNull(result);
        assertEquals(expected, result.getDate());
    }

    @Test
    void createRegistrationDate_Invalid_DifferentDate() {
        LocalDate expected = LocalDate.of(2020, 3, 2);

        RegistrationDate result = new RegistrationDate();

        assertNotNull(result);
        assertNotEquals(expected, result.getDate());
    }

    @Test
    void testEquals_test1_DifferentObjects() {
        RegistrationDate registrationDate = new RegistrationDate();
        RegistrationDate otherRegistrationDate = new RegistrationDate();
        RegistrationDate nullRegistration = null;
        PersonName personName = new PersonName("some");

        assertNotSame(registrationDate, otherRegistrationDate);
        assertEquals(registrationDate, otherRegistrationDate);
        assertNotEquals(registrationDate, nullRegistration);
        assertNotEquals(registrationDate, personName);
    }

    @Test
    void testEquals_test2_SameObjects() {
        RegistrationDate registrationDate = new RegistrationDate();
        //noinspection UnnecessaryLocalVariable
        RegistrationDate otherRegistrationDate = registrationDate;

        assertSame(registrationDate, otherRegistrationDate);
        assertEquals(registrationDate, otherRegistrationDate);
    }

    @Test
    void testEquals_test3_DifferentObjectsType() {
        String emailAddress = "tito@gmail.com";
        RegistrationDate registrationDate = new RegistrationDate();
        Email email = new Email(emailAddress);

        assertNotEquals(registrationDate, email);
    }

    @Test
    void testEquals_test4_NotEqualObjects() {
        LocalDate date = LocalDate.of(2020, 3, 2);
        RegistrationDate registrationDate = new RegistrationDate();

        assertNotEquals(date, registrationDate.getDate());
    }

    @Test
    void testHashCode() {
        RegistrationDate registrationDate = new RegistrationDate();
        RegistrationDate otherRegistrationDate = new RegistrationDate();

        assertNotSame(registrationDate, otherRegistrationDate);
        assertEquals(registrationDate.hashCode(), otherRegistrationDate.hashCode());
        assertNotEquals(0, registrationDate.hashCode());
    }

    @Test
    void testHashCode_false() {
        LocalDate date = LocalDate.of(2020, 3, 2);
        RegistrationDate registrationDate = new RegistrationDate();

        assertNotEquals(date.hashCode(), registrationDate.getDate().hashCode());
    }

    @Test
    void toStringTest() {
        String expected = "02/03/2020";
        RegistrationDate registrationDate = new RegistrationDate(expected);

        String result = registrationDate.toString();

        assertEquals(expected, result);
    }

    @Test
    void equalsSameDateDifferentObjects() {
        String date = "02/03/2020";
        RegistrationDate registrationDate = new RegistrationDate(date);
        RegistrationDate otherRegistrationDate = new RegistrationDate(date);

        assertNotSame(registrationDate, otherRegistrationDate);
        assertEquals(registrationDate, otherRegistrationDate);
    }

    @Test
    void equalsDifferentDateDifferentObjects() {
        String firstDate = "02/03/2020";
        String secondDate = "03/03/2020";
        RegistrationDate registrationDate = new RegistrationDate(firstDate);
        RegistrationDate otherRegistrationDate = new RegistrationDate(secondDate);

        assertNotSame(registrationDate, otherRegistrationDate);
        assertNotEquals(registrationDate, otherRegistrationDate);
    }
}