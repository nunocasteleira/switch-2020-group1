package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.family.Family;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FamilyIdTest {

    @Test
    void createFamilyId_Successfully() {
        int expected = new Random().nextInt();
        FamilyId familyId = new FamilyId(expected);

        long result = familyId.getFamilyId();

        assertEquals(expected, result);
    }

    @Test
    void testEquals_test1_DifferentObjects() {
        int id = new Random().nextInt();
        FamilyId familyId = new FamilyId(id);
        FamilyId otherFamilyId = new FamilyId(id);

        assertNotSame(familyId, otherFamilyId);
        assertEquals(familyId, otherFamilyId);
    }

    @Test
    void testEquals_test2_SameObjects() {
        int id = new Random().nextInt();
        FamilyId familyId = new FamilyId(id);
        //noinspection UnnecessaryLocalVariable
        FamilyId otherFamilyId = familyId;

        assertSame(familyId, otherFamilyId);
        assertEquals(familyId, otherFamilyId);
    }

    @Test
    void testEquals_test3_DifferentObjectsType() {
        int id = new Random().nextInt();
        String emailAddress = "tito@gmail.com";
        FamilyId familyId = new FamilyId(id);
        Email email = new Email(emailAddress);

        assertNotEquals(familyId, email);
    }

    @Test
    void testEquals_test4_NotEqualObjects() {
        int id = new Random().nextInt();
        int otherId = new Random().nextInt() + 1;
        FamilyId familyId = new FamilyId(id);
        FamilyId otherFamilyId = new FamilyId(otherId);

        assertNotEquals(familyId, otherFamilyId);
    }

    @Test
    void ensureHashCodeIsWorking() {
        int id = new Random().nextInt();
        FamilyId familyId = new FamilyId(id);
        //noinspection UnnecessaryLocalVariable
        FamilyId secondFamilyId = familyId;
        int otherId = new Random().nextInt() + 1;
        FamilyId differentFamilyId = new FamilyId(otherId);
        boolean result;
        int result1 = familyId.hashCode();
        int result2 = differentFamilyId.hashCode();

        result = result1 == result2;

        assertEquals(familyId.hashCode(), familyId.hashCode());
        assertNotEquals(secondFamilyId.hashCode(), differentFamilyId.hashCode());
        assertFalse(result);
    }

    @Test
    void testToString() {
        int initialValue = new Random().nextInt();
        FamilyId familyId = new FamilyId(initialValue);
        String result;
        String expected;

        expected = String.valueOf(initialValue);
        result = familyId.toString();

        assertEquals(expected, result);
    }

    @Test
    void testEqualsNull() {
        FamilyId familyId = new FamilyId(1);
        FamilyId familyIdNull = null;
        Email personId = new Email("something@gmail.com");

        assertNotEquals(familyId, familyIdNull);
        assertNotEquals(familyId, personId);
    }
}