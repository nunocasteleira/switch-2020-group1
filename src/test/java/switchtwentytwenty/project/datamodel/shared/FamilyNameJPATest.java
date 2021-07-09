package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FamilyNameJPATest {

    @Test
    void testEquals() {
        String name = "Test Family Name";
        String otherFamilyName = "Other Family Name";
        FamilyNameJPA familyNameJPA = new FamilyNameJPA(name);
        //noinspection UnnecessaryLocalVariable
        FamilyNameJPA familyNameJPASame = familyNameJPA;
        FamilyNameJPA familyNameJPAOther = new FamilyNameJPA(name);
        FamilyNameJPA familyNameJPADifferent = new FamilyNameJPA(otherFamilyName);
        FamilyNameJPA nullName = null;
        FamilyIdJPA familyIdJPA = new FamilyIdJPA(1);

        assertEquals(familyNameJPA, familyNameJPASame);
        assertSame(familyNameJPA, familyNameJPASame);
        assertEquals(familyNameJPA.hashCode(), familyNameJPASame.hashCode());
        assertEquals(familyNameJPA, familyNameJPAOther);
        assertNotSame(familyNameJPA, familyNameJPAOther);
        assertEquals(familyNameJPA.hashCode(), familyNameJPAOther.hashCode());
        assertNotEquals(familyNameJPA, familyNameJPADifferent);
        assertNotSame(familyNameJPA, familyNameJPADifferent);
        assertNotEquals(familyNameJPA.hashCode(), familyNameJPADifferent.hashCode());
        assertNotEquals(0, familyNameJPA.hashCode());
        assertNotEquals(familyNameJPA, name);
        assertNotEquals(familyNameJPA, nullName);
        assertNotEquals(familyNameJPA, familyIdJPA);
    }

    @Test
    void getFamilyName() {
        String expected = "Test Family Name";
        FamilyNameJPA familyNameJPA = new FamilyNameJPA(expected);

        String result = familyNameJPA.getFamilyName();

        assertEquals(expected, result);
    }

    @Test
    void noArgsConstructor() {
        FamilyNameJPA familyNameJPA = new FamilyNameJPA();

        assertNotNull(familyNameJPA);
    }
}