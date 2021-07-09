package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FamilyIdJPATest {

    @Test
    void testEquals() {
        int id = 50;
        FamilyIdJPA familyIdJPA = new FamilyIdJPA(id);
        //noinspection UnnecessaryLocalVariable
        FamilyIdJPA familyIdJPASame = familyIdJPA;
        FamilyIdJPA familyIdJPAOther = new FamilyIdJPA(id);
        FamilyIdJPA familyIdJPADifferent = new FamilyIdJPA(100);
        FamilyIdJPA nullFamilyId = null;
        PersonNameJPA personNameJPA = new PersonNameJPA("some");

        assertEquals(familyIdJPA, familyIdJPASame);
        assertSame(familyIdJPA, familyIdJPASame);
        assertEquals(familyIdJPA.hashCode(), familyIdJPASame.hashCode());
        assertEquals(familyIdJPA, familyIdJPAOther);
        assertNotSame(familyIdJPA, familyIdJPAOther);
        assertEquals(familyIdJPA.hashCode(), familyIdJPAOther.hashCode());
        assertNotEquals(familyIdJPA, familyIdJPADifferent);
        assertNotSame(familyIdJPA, familyIdJPADifferent);
        assertNotEquals(familyIdJPA.hashCode(), familyIdJPADifferent.hashCode());
        assertNotEquals(0, familyIdJPA.hashCode());
        assertNotEquals(familyIdJPA, id);
        assertNotEquals(familyIdJPA, nullFamilyId);
        assertNotEquals(familyIdJPA, personNameJPA);
    }

    @Test
    void getFamilyId() {
        int expected = 50;
        FamilyIdJPA familyIdJPA = new FamilyIdJPA(expected);

        long result = familyIdJPA.getFamilyId();

        assertEquals(expected, result);
    }

    @Test
    void ensureNoArgsIsWorking(){
        FamilyIdJPA familyIdJPA = new FamilyIdJPA();

        assertNotNull(familyIdJPA);
    }
}