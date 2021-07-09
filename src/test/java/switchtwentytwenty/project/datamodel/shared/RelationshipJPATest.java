package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.family.FamilyJPA;
import switchtwentytwenty.project.domain.model.shared.Email;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipJPATest {
    EmailJPA mainUserJPA;
    int relationshipType;
    EmailJPA otherUserJPA;
    FamilyJPA familyJPA;
    RelationshipJPA relationshipJPA;
    RelationshipIdJPA relationshipIdJPA;

    @BeforeEach
    void beforeEach() {
        relationshipIdJPA = new RelationshipIdJPA(505);
        String mainUserEmail = "main@user.com";
        relationshipType = 1;
        String otherUserEmail = "other@user.com";
        int familyIdInt = 0;
        String familyNameString = "TestFamily";
        String registrationDateString = "12/12/2021";
        FamilyIdJPA familyIdJPA = new FamilyIdJPA(familyIdInt);
        FamilyNameJPA familyNameJPA = new FamilyNameJPA(familyNameString);
        RegistrationDateJPA registrationDateJPA = new RegistrationDateJPA(registrationDateString);
        Email mainUser = new Email(mainUserEmail);
        mainUserJPA = new EmailJPA(mainUser.getEmailAddress());
        familyJPA = new FamilyJPA(1L, familyNameJPA,
                registrationDateJPA, mainUserJPA, 1L);

        Email otherUser = new Email(otherUserEmail);
        otherUserJPA = new EmailJPA(otherUser.getEmailAddress());
    }

    @Test
    void assertAllArgsConstructor() {
        RelationshipJPA relationshipJPA = new RelationshipJPA(relationshipIdJPA, mainUserJPA, relationshipType,
                otherUserJPA, familyJPA);

        assertNotNull(relationshipJPA);
    }

    @Test
    void equalsAndHashCode() {
        relationshipJPA = new RelationshipJPA(relationshipIdJPA, mainUserJPA, relationshipType,
                otherUserJPA, familyJPA);
        RelationshipJPA relationshipJPASame = relationshipJPA;
        RelationshipJPA relationshipJPAOther = new RelationshipJPA(relationshipIdJPA, mainUserJPA, relationshipType,
                otherUserJPA, familyJPA);
        RelationshipJPA relationshipJPADifferent = new RelationshipJPA(relationshipIdJPA, otherUserJPA,
                relationshipType + 1,
                mainUserJPA, familyJPA);
        RelationshipJPA nullRelationship = null;
        PersonNameJPA personNameJPA = new PersonNameJPA("some");

        assertEquals(relationshipJPA, relationshipJPASame);
        assertSame(relationshipJPA, relationshipJPASame);
        assertEquals(relationshipJPA.hashCode(), relationshipJPASame.hashCode());
        assertEquals(relationshipJPA.getRelationshipId(), relationshipJPASame.getRelationshipId());
        assertEquals(relationshipJPA.getRelationshipType(), relationshipJPASame.getRelationshipType());
        assertEquals(relationshipJPA.getMainUser(), relationshipJPASame.getMainUser());
        assertEquals(relationshipJPA.getOtherUser(), relationshipJPASame.getOtherUser());
        assertEquals(relationshipJPA, relationshipJPAOther);
        assertNotSame(relationshipJPA, relationshipJPAOther);
        assertEquals(relationshipJPA.hashCode(), relationshipJPAOther.hashCode());
        assertNotEquals(0, relationshipJPA.hashCode());
        assertNotEquals(relationshipJPA, mainUserJPA);
        assertNotEquals(relationshipJPA, relationshipJPADifferent);
        assertNotEquals(relationshipJPA.getRelationshipType(), relationshipJPADifferent.getRelationshipType());
        assertNotEquals(nullRelationship, relationshipJPA);
        assertNotEquals(relationshipJPA, personNameJPA);
        assertNotEquals(null, relationshipJPA);
        assertFalse(relationshipJPA.equals(relationshipJPADifferent));
        assertFalse(relationshipJPA.equals(otherUserJPA));
        assertTrue(relationshipJPA.equals(relationshipJPASame));
    }

    @Test
    void getMainUserTest() {
        relationshipJPA = new RelationshipJPA(relationshipIdJPA, mainUserJPA, relationshipType,
                otherUserJPA, familyJPA);

        EmailJPA result = relationshipJPA.getMainUser();

        assertEquals(mainUserJPA, result);
    }

    @Test
    void getOtherUserTest() {
        relationshipJPA = new RelationshipJPA(relationshipIdJPA,mainUserJPA, relationshipType,
                otherUserJPA, familyJPA);

        EmailJPA result = relationshipJPA.getOtherUser();

        assertEquals(otherUserJPA, result);
    }

    @Test
    void getRelationshipTypeTest() {
        relationshipJPA = new RelationshipJPA(relationshipIdJPA, mainUserJPA, relationshipType, otherUserJPA, familyJPA);

        int result = relationshipJPA.getRelationshipType();

        assertEquals(relationshipType, result);
        assertNotEquals(0, result);
    }

    @Test
    void getRelationshipIdTest() {
        relationshipJPA = new RelationshipJPA(relationshipIdJPA, mainUserJPA, relationshipType, otherUserJPA, familyJPA);

        RelationshipIdJPA result = relationshipJPA.getRelationshipId();

        assertEquals(relationshipIdJPA, result);
        assertNotEquals(0, result.hashCode());
    }

    @Test
    void noArgsConstructor() {
        RelationshipJPA relationshipJPA = new RelationshipJPA();
        relationshipJPA.setRelationshipType(relationshipType);

        assertNotNull(relationshipJPA);
        assertEquals(relationshipType, relationshipJPA.getRelationshipType());
    }
}