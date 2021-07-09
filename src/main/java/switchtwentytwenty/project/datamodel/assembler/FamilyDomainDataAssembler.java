package switchtwentytwenty.project.datamodel.assembler;

import org.springframework.stereotype.Component;
import switchtwentytwenty.project.datamodel.family.FamilyJPA;
import switchtwentytwenty.project.datamodel.family.FamilyMembersJPA;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.family.FamilyOutputVOs;

import java.util.ArrayList;
import java.util.List;

@Component
public class FamilyDomainDataAssembler {

    private List<RelationshipJPA> relationshipsToJPA(FamilyJPA familyJPA,
                                                     List<Relationship> relationshipList) {
        List<RelationshipJPA> listToReturn = new ArrayList<>();
        for (Relationship relationship : relationshipList) {
            RelationshipIdJPA relationshipIdJPA = new RelationshipIdJPA(relationship.getRelationshipId().getId());
            EmailJPA mainUser = new EmailJPA(relationship.getMainUserId().getEmailAddress());
            int relationshipType = relationship.getFamilyRelationshipType().getNumericValue();
            EmailJPA otherUser = new EmailJPA(relationship.getOtherUserId().getEmailAddress());
            RelationshipJPA relationshipJPA =
                    new RelationshipJPA(relationshipIdJPA, mainUser, relationshipType, otherUser, familyJPA);
            listToReturn.add(relationshipJPA);
        }
        return listToReturn;
    }

    private List<Relationship> relationshipJPAtoDomain(
            List<RelationshipJPA> relationshipJPAList) {
        List<Relationship> relationships = new ArrayList<>();
        for (RelationshipJPA relationshipJPA : relationshipJPAList) {
            RelationshipId relationshipId = new RelationshipId((relationshipJPA.getRelationshipId().getId()));
            Email mainUser = new Email(relationshipJPA.getMainUser().getEmailAddress());
            Email otherUser = new Email(relationshipJPA.getOtherUser().getEmailAddress());
            int relationshipType = relationshipJPA.getRelationshipType();
            Relationship relationship = new Relationship(relationshipType, mainUser, otherUser);
            relationship.setRelationshipId(relationshipId);
            relationships.add(relationship);
        }
        return relationships;
    }

    public FamilyJPA toData(Family family) {
        long familyIdJPA = family.getFamilyId().getFamilyId();
        FamilyNameJPA familyNameJPA = new FamilyNameJPA(family.getFamilyName().toString());
        RegistrationDateJPA registrationDateJPA =
                new RegistrationDateJPA(family.getRegistrationDate().toString());
        EmailJPA adminId = new EmailJPA(family.getAdminId().getEmailAddress());
        long accountId = family.getCashAccountId().getAccountIdNumber();
        FamilyJPA familyJPA = new FamilyJPA(familyIdJPA, familyNameJPA, registrationDateJPA, adminId, accountId);
        List<RelationshipJPA> relationshipJPAList =
                relationshipsToJPA(familyJPA, family.getRelationshipsList());
        familyJPA.setFamilyRelationships(relationshipJPAList);
        return familyJPA;
    }

    private List<Email> familyMembersToDomain(
            List<FamilyMembersJPA> familyMembersJPAList) {
        List<Email> familyMembers = new ArrayList<>();
        for (FamilyMembersJPA familyMember : familyMembersJPAList) {
            EmailJPA emailJPA = familyMember.getPersonId();
            Email email = new Email(emailJPA.getEmailAddress());
            familyMembers.add(email);
        }
        return familyMembers;
    }

    public FamilyOutputVOs toDomainVOs(FamilyJPA familyJPA) {
        FamilyId familyId = new FamilyId(familyJPA.getFamilyId());
        FamilyName familyName = new FamilyName(familyJPA.getFamilyName().getFamilyName());
        RegistrationDate registrationDate =
                new RegistrationDate(familyJPA.getRegistrationDate().getDate());
        Email emailId = new Email(familyJPA.getAdminId().getEmailAddress());
        AccountId accountId = new AccountId(familyJPA.getAccountId());

        List<FamilyMembersJPA> familyMembersJPAList = familyJPA.getFamilyMembers();
        List<Email> familyMembers = familyMembersToDomain(familyMembersJPAList);
        List<RelationshipJPA> familyRelationshipsJPA = familyJPA.getFamilyRelationships();
        List<Relationship> familyRelationships = relationshipJPAtoDomain(familyRelationshipsJPA);
        return new FamilyOutputVOs(familyName, emailId, registrationDate, familyId, accountId, familyMembers, familyRelationships);
    }

}
