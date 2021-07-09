package switchtwentytwenty.project.datamodel.family;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FamilyJPATest {
    long familyId;
    FamilyNameJPA familyName;
    RegistrationDateJPA registrationDate;
    EmailJPA adminId;
    long accountId;

    @BeforeEach
    void setUp() {
        familyId = 0;
        familyName = new FamilyNameJPA();
        registrationDate = new RegistrationDateJPA();
        adminId = new EmailJPA();
        accountId = 0;
    }

    @Test
    void ensureAllArgs() {
        FamilyJPA familyJPA = new FamilyJPA(familyId, familyName, registrationDate, adminId, accountId);

        assertNotNull(familyJPA);
        assertEquals(familyId, familyJPA.getFamilyId());
        assertEquals(familyName, familyJPA.getFamilyName());
        assertEquals(registrationDate, familyJPA.getRegistrationDate());
        assertEquals(adminId, familyJPA.getAdminId());
        assertEquals(accountId, familyJPA.getAccountId());
    }

    @Test
    void ensureNoArgs() {
        FamilyJPA familyJPA = new FamilyJPA();

        assertNotNull(familyJPA);
    }

    @Test
    void addFamilyMember() {
        FamilyJPA familyJPA = new FamilyJPA(familyId, familyName, registrationDate, adminId, accountId);
        EmailJPA newMember = new EmailJPA();
        familyJPA.addFamilyMember(newMember);
        List<FamilyMembersJPA> expected = new ArrayList<>();
        FamilyMembersJPA familyMembersJPA = new FamilyMembersJPA(familyJPA, newMember);
        expected.add(familyMembersJPA);

        List<FamilyMembersJPA> result = familyJPA.getFamilyMembers();

        assertEquals(expected, result);
    }

    @Test
    void setAndGetAccountId() {
        FamilyJPA familyJPA = new FamilyJPA();

        familyJPA.setAccountId(accountId);

        assertEquals(accountId, familyJPA.getAccountId());
    }

    @Test
    void setAndGetFamilyId() {
        FamilyJPA familyJPA = new FamilyJPA();

        familyJPA.setFamilyId(1L);

        assertEquals(1L, familyJPA.getFamilyId());
    }

    @Test
    void setFamilyRelationships() {
        RelationshipIdJPA relationshipIdJPA = new RelationshipIdJPA(505);
        FamilyJPA familyJPA = new FamilyJPA(familyId, familyName, registrationDate, adminId, accountId);
        EmailJPA mainUser = new EmailJPA();
        EmailJPA otherUser = new EmailJPA();
        int relationshipType = 1;
        RelationshipJPA relationshipJPA = new RelationshipJPA(relationshipIdJPA, mainUser, relationshipType,
                otherUser, familyJPA);
        List<RelationshipJPA> expected = new ArrayList<>();
        expected.add(relationshipJPA);

        familyJPA.setFamilyRelationships(expected);
        List<RelationshipJPA> result = familyJPA.getFamilyRelationships();

        assertEquals(expected, result);
    }

    @Test
    void getRelationshipJPAByIdSuccessfully() {
        RelationshipIdJPA relationshipIdJPA = new RelationshipIdJPA(1);
        FamilyJPA familyJPA = new FamilyJPA(familyId, familyName, registrationDate, adminId, accountId);
        EmailJPA mainUser = new EmailJPA();
        EmailJPA otherUser = new EmailJPA();
        int relationshipType = 1;
        RelationshipJPA relationshipJPA = new RelationshipJPA(relationshipIdJPA, mainUser, relationshipType,
                otherUser, familyJPA);
        List<RelationshipJPA> relationshipJPAList = new ArrayList<>();
        relationshipJPAList.add(relationshipJPA);
        familyJPA.setFamilyRelationships(relationshipJPAList);

        RelationshipJPA result = familyJPA.getRelationshipJPAById(relationshipIdJPA);

        assertEquals(relationshipJPA, result);
    }

    @Test
    void failToGetRelationshipJPAById() {
        RelationshipIdJPA relationshipIdJPA = new RelationshipIdJPA(0);
        FamilyJPA familyJPA = new FamilyJPA(familyId, familyName, registrationDate, adminId, accountId);

        assertThrows(ObjectDoesNotExistException.class, () -> familyJPA.getRelationshipJPAById(relationshipIdJPA));
    }
}