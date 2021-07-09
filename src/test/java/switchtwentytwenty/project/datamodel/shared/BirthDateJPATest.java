package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BirthDateJPATest {

    @Test
    void testEquals() {
        String date = "01/02/2020";
        String differentDate = "02/02/2020";
        BirthDateJPA birthDateJPA = new BirthDateJPA(date);
        //noinspection UnnecessaryLocalVariable
        BirthDateJPA birthDateJPASame = birthDateJPA;
        BirthDateJPA birthDateJPAOther = new BirthDateJPA(date);
        BirthDateJPA birthDateJPADifferent = new BirthDateJPA(differentDate);
        BirthDateJPA nullBirthDate = null;
        PersonNameJPA personNameJPA = new PersonNameJPA("some");

        assertEquals(birthDateJPA, birthDateJPASame);
        assertSame(birthDateJPA, birthDateJPASame);
        assertEquals(birthDateJPA.hashCode(), birthDateJPASame.hashCode());
        assertEquals(birthDateJPA, birthDateJPAOther);
        assertNotSame(birthDateJPA, birthDateJPAOther);
        assertEquals(birthDateJPA.hashCode(), birthDateJPAOther.hashCode());
        assertNotEquals(birthDateJPA, birthDateJPADifferent);
        assertNotSame(birthDateJPA, birthDateJPADifferent);
        assertNotEquals(birthDateJPA.hashCode(), birthDateJPADifferent.hashCode());
        assertNotEquals(0, birthDateJPA.hashCode());
        assertNotEquals(birthDateJPA, date);
        assertNotEquals(birthDateJPA, nullBirthDate);
        assertNotEquals(birthDateJPA, personNameJPA);
    }

    @Test
    void getDate() {
        String expected = "01/02/2020";
        BirthDateJPA birthDateJPA = new BirthDateJPA(expected);

        String result = birthDateJPA.getDate();

        assertEquals(expected, result);
    }
}