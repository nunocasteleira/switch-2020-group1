package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailJPATest {

    @Test
    void ensureEquals() {
        String email = "equals@gmail.com";
        String other = "other@gmail.com";
        EmailJPA emailJPA = new EmailJPA(email);
        EmailJPA secondEmail = new EmailJPA(email);
        EmailJPA thirdEmail = new EmailJPA(other);
        //noinspection UnnecessaryLocalVariable
        EmailJPA sameEmail = emailJPA;

        assertEquals(emailJPA, secondEmail);
        assertNotEquals(emailJPA, thirdEmail);
        assertEquals(emailJPA.hashCode(), secondEmail.hashCode());
        assertNotEquals(emailJPA.hashCode(), thirdEmail.hashCode());
        assertNotEquals(emailJPA, email);
        assertSame(emailJPA, sameEmail);
        assertEquals(emailJPA, sameEmail);
        assertFalse(emailJPA.equals(null));
        assertNotEquals(0, emailJPA.hashCode());
        assertNotEquals(emailJPA, email);
    }

    @Test
    void getEmail() {
        String expected = "equals@gmail.com";
        EmailJPA emailJPA = new EmailJPA(expected);

        String result = emailJPA.getEmailAddress();

        assertEquals(expected, result);
    }
}