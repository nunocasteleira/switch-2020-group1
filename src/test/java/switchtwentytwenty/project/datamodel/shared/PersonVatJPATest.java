package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonVatJPATest {

    @Test
    void testEquals() {
        String personVat = "223322333";
        String secondVat = "222222333";
        PersonVatJPA personVatJPA = new PersonVatJPA(personVat);
        PersonVatJPA personVatJPASame = personVatJPA;
        PersonVatJPA secondPersonVatJPA = new PersonVatJPA(personVat);
        PersonVatJPA thirdPersonVatJPA = new PersonVatJPA(secondVat);
        PersonVatJPA nullPersonVat = null;
        PersonNameJPA personNameJPA = new PersonNameJPA("some");

        assertEquals(personVatJPA, secondPersonVatJPA);
        assertEquals(personVatJPA, personVatJPASame);
        assertSame(personVatJPA, personVatJPASame);
        assertNotSame(personVatJPA, secondPersonVatJPA);
        assertNotEquals(secondPersonVatJPA, thirdPersonVatJPA);
        assertNotEquals(secondPersonVatJPA.getVat(), thirdPersonVatJPA.getVat());
        assertNotEquals(personVatJPA.hashCode(), thirdPersonVatJPA.hashCode());
        assertEquals(personVatJPA.hashCode(), secondPersonVatJPA.hashCode());
        assertNotEquals(0, personVatJPA.hashCode());
        assertNotEquals(personVatJPA, personVat);
        assertEquals(personVatJPA, personVatJPASame);
        assertNotEquals(nullPersonVat, personVatJPA);
        assertNotEquals(personVatJPA, personNameJPA);
        assertNotEquals(null, personVatJPA);
        assertFalse(personVatJPA.equals(null));
        assertTrue(personVatJPA.equals(secondPersonVatJPA));
        assertFalse(personVatJPA.equals(thirdPersonVatJPA));
    }

    @Test
    void getVatTest() {
        String expected = "223322333";
        PersonVatJPA personVatJPA = new PersonVatJPA(expected);

        String result = personVatJPA.getVat();

        assertEquals(expected, result);
    }
}