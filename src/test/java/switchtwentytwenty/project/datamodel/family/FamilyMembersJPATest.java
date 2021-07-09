package switchtwentytwenty.project.datamodel.family;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.shared.EmailJPA;

import static org.junit.jupiter.api.Assertions.*;

class FamilyMembersJPATest {

    @Test
    void validateAllArgsConstructor() {
        FamilyJPA familyJPA = new FamilyJPA();
        EmailJPA emailJPA = new EmailJPA();

        FamilyMembersJPA familyMembersJPA = new FamilyMembersJPA(familyJPA, emailJPA);

        assertNotNull(familyMembersJPA);
        assertEquals(familyMembersJPA.getPersonId(), emailJPA);
    }

    @Test
    void ensureNoArgs() {
        FamilyMembersJPA familyMembersJPA = new FamilyMembersJPA();
        assertNotNull(familyMembersJPA);
    }

    @Test
    void validateFamilyMemberId() {
        FamilyJPA familyJPA = new FamilyJPA();
        EmailJPA emailJPA = new EmailJPA();
        FamilyMembersJPA familyMembersJPA = new FamilyMembersJPA(familyJPA, emailJPA);
        FamilyMembersJPA.FamilyPersonIdJPA expected =
                new FamilyMembersJPA.FamilyPersonIdJPA(familyJPA, emailJPA);

        FamilyMembersJPA.FamilyPersonIdJPA result = familyMembersJPA.getIdFamilyPerson();

        assertEquals(expected, result);
    }

    @Test
    void improveConstructorCoverage() {
        FamilyJPA familyJPA = new FamilyJPA();
        EmailJPA emailJPA = new EmailJPA();
        FamilyMembersJPA.FamilyPersonIdJPA expected =
                new FamilyMembersJPA.FamilyPersonIdJPA(familyJPA, emailJPA);

        FamilyMembersJPA familyMembersJPA = new FamilyMembersJPA(expected);

        assertNotNull(familyMembersJPA);
        assertEquals(expected, familyMembersJPA.getIdFamilyPerson());
    }

    @Test
    void equalsAndHashTest() {
        FamilyJPA familyJPA = new FamilyJPA();
        EmailJPA emailJPA = new EmailJPA();
        FamilyJPA otherFamilyJPA = new FamilyJPA();
        EmailJPA otherEmailJPA = new EmailJPA();

        FamilyMembersJPA familyMembersJPA = new FamilyMembersJPA(familyJPA, emailJPA);
        //noinspection UnnecessaryLocalVariable
        FamilyMembersJPA familyMembersJPASame = familyMembersJPA;
        FamilyMembersJPA familyMembersJPAOther = new FamilyMembersJPA(familyJPA, emailJPA);
        FamilyMembersJPA familyMembersJPADifferent = new FamilyMembersJPA(otherFamilyJPA,
                otherEmailJPA);

        assertEquals(familyMembersJPA, familyMembersJPASame);
        assertSame(familyMembersJPA, familyMembersJPASame);
        assertEquals(familyMembersJPA.hashCode(), familyMembersJPASame.hashCode());
        assertEquals(familyMembersJPA, familyMembersJPAOther);
        assertNotSame(familyMembersJPA, familyMembersJPAOther);
        assertEquals(familyMembersJPA.hashCode(), familyMembersJPAOther.hashCode());
        assertNotEquals(0, familyMembersJPA.hashCode());
        assertNotEquals(null, familyMembersJPA );
        assertNotEquals(familyMembersJPA, familyJPA);
        assertNotEquals(familyMembersJPA, familyMembersJPADifferent);
        assertNotEquals(familyMembersJPA.getIdFamilyPerson(), familyMembersJPADifferent.getIdFamilyPerson());
    }

    @Test
    void equalsAndHashTestInnerClass() {
        FamilyJPA familyJPA = new FamilyJPA();
        EmailJPA emailJPA = new EmailJPA();
        FamilyJPA otherFamilyJPA = new FamilyJPA();
        EmailJPA otherEmailJPA = new EmailJPA();

        FamilyMembersJPA.FamilyPersonIdJPA noArgs = new FamilyMembersJPA.FamilyPersonIdJPA();
        FamilyMembersJPA.FamilyPersonIdJPA familyPersonIdJPA =
                new FamilyMembersJPA.FamilyPersonIdJPA(familyJPA, emailJPA);
        //noinspection UnnecessaryLocalVariable
        FamilyMembersJPA.FamilyPersonIdJPA familyPersonIdJPASame = familyPersonIdJPA;
        FamilyMembersJPA.FamilyPersonIdJPA familyPersonIdJPAOther = new FamilyMembersJPA.FamilyPersonIdJPA(familyJPA, emailJPA);
        FamilyMembersJPA.FamilyPersonIdJPA familyPersonIdJPADifferent = new FamilyMembersJPA.FamilyPersonIdJPA(otherFamilyJPA,
                otherEmailJPA);

        assertEquals(familyPersonIdJPA, familyPersonIdJPASame);
        assertSame(familyPersonIdJPA, familyPersonIdJPASame);
        assertEquals(familyPersonIdJPA.hashCode(), familyPersonIdJPASame.hashCode());
        assertEquals(familyPersonIdJPA, familyPersonIdJPAOther);
        assertNotSame(familyPersonIdJPA, familyPersonIdJPAOther);
        assertEquals(familyPersonIdJPA.hashCode(), familyPersonIdJPAOther.hashCode());
        assertNotEquals(0, familyPersonIdJPA.hashCode());
        assertNotEquals(familyPersonIdJPA, familyJPA);
        assertNotEquals(familyPersonIdJPA, familyPersonIdJPADifferent);
        assertNotEquals(familyPersonIdJPA.hashCode(), familyPersonIdJPADifferent.hashCode());
        assertFalse(familyPersonIdJPA.equals(familyPersonIdJPADifferent));
        assertNotNull(noArgs);
        assertNotEquals(null, familyPersonIdJPA);
    }
}