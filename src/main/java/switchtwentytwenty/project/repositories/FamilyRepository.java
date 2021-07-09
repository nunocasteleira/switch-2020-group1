package switchtwentytwenty.project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import switchtwentytwenty.project.datamodel.assembler.FamilyDomainDataAssembler;
import switchtwentytwenty.project.datamodel.family.FamilyJPA;
import switchtwentytwenty.project.datamodel.shared.EmailJPA;
import switchtwentytwenty.project.datamodel.shared.RelationshipIdJPA;
import switchtwentytwenty.project.datamodel.shared.RelationshipJPA;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.family.FamilyOutputVOs;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional //To Lazy load Relationships
public class FamilyRepository {
    @Autowired
    IFamilyRepositoryJPA iFamilyRepositoryJPA;
    @Autowired
    private FamilyDomainDataAssembler familyDomainDataAssembler;

    /**
     * This method allows to save a familyJPA in the repository of families.
     *
     * @param family a family object we want to save.
     * @return saved family.
     */
    public Family save(Family family) {
        FamilyJPA familyJPA = familyDomainDataAssembler.toData(family);
        FamilyJPA savedFamily = iFamilyRepositoryJPA.save(familyJPA);
        FamilyId databaseFamilyId = new FamilyId(savedFamily.getFamilyId());
        return getDatabaseSavedFamily(databaseFamilyId);
    }

    public boolean existsById(FamilyId familyId) {
        return iFamilyRepositoryJPA.existsById(familyId.getFamilyId());
    }

    /**
     * This method finds an instance of FamilyJPA from the database and converts it to a
     * corresponding instance of Family
     *
     * @param familyId id of the FamilyJPA we want to obtain
     * @return instance of Family
     */
    public Family getDatabaseSavedFamily(FamilyId familyId) {
        FamilyJPA familyJPA = fromOptionalToJPA(familyId);
        FamilyOutputVOs familyOutputVOs = familyDomainDataAssembler.toDomainVOs(familyJPA);
        Family family = new Family.FamilyBuilder(familyOutputVOs.getFamilyName(), familyOutputVOs.getEmail())
                .withRegistrationDate(familyOutputVOs.getRegistrationDate())
                .withId(familyOutputVOs.getFamilyId())
                .build();
        family.setFamilyId(familyOutputVOs.getFamilyId());
        family.setAccountId(familyOutputVOs.getAccountId());
        family.setFamilyMembers(familyOutputVOs.getFamilyMembers());
        family.setFamilyRelationships(familyOutputVOs.getFamilyRelationships());
        return family;
    }

    /**
     * This method allows to find an instance of FamilyJPA from the database.
     *
     * @param familyId id of the family object we want to obtain.
     * @return instance of familyJPA.
     */
    private FamilyJPA fromOptionalToJPA(FamilyId familyId) {
        long databaseFamilyId = familyId.getFamilyId();
        Optional<FamilyJPA> familyJPAOptional = iFamilyRepositoryJPA.findById(databaseFamilyId);
        if (familyJPAOptional.isPresent()) {
            return familyJPAOptional.get();
        }
        throw new ObjectDoesNotExistException("Family does not exist.");
    }

    /**
     * This method allows to obtain the list of all family members existing in specific family.
     *
     * @param familyId id of the family.
     * @return list of family members id's (email).
     */
    public List<Email> getFamilyMembers(FamilyId familyId) {
        Family family = getDatabaseSavedFamily(familyId);
        return family.getFamilyMembers();
    }

    /**
     * This method allows to add the family member to the family and save it again.
     *
     * @param email    an email object that belongs to the family member that we want to add to the
     *                 family.
     * @param familyId id of the family we want to obtain to add the family member.
     */
    public void addFamilyMember(Email email, FamilyId familyId) {
        FamilyJPA familyJPA = fromOptionalToJPA(familyId);
        EmailJPA emailJPA = new EmailJPA(email.getEmailAddress());
        familyJPA.addFamilyMember(emailJPA);
        iFamilyRepositoryJPA.save(familyJPA);
    }

    /**
     * This method saves the relationship in a designated family, found by by their familyId
     *
     * @param relationship the relationship to be saved
     * @param familyId     the id of the requested family
     * @return a Relationship object with the above properties
     */
    public Relationship addRelationship(Relationship relationship, FamilyId familyId) {
        Family family = getDatabaseSavedFamily(familyId);
        family.addRelationship(relationship);
        iFamilyRepositoryJPA.save(familyDomainDataAssembler.toData(family));
        return relationship;
    }

    /**
     * This method gets the relationship list of a designated family, by their familyId
     *
     * @param familyId the id of the requested family
     * @return a list of relationships existing in the designated family
     */
    public List<Relationship> getRelationshipsList(FamilyId familyId) {
        Family family = getDatabaseSavedFamily(familyId);
        return family.getRelationshipsList();
    }

    /**
     * Method to save the Family with associated Cash Account Id in the repository.
     *
     * @param family family with a valid cash account id
     */
    public void saveFamilyWithAccount(Family family) {
        AccountId accountId = family.getCashAccountId();
        FamilyJPA familyJPA = fromOptionalToJPA(family.getFamilyId());
        familyJPA.setAccountId(accountId.getAccountIdNumber());
        iFamilyRepositoryJPA.save(familyJPA);
    }

    /**
     * Method to change the relationship in a family.
     *
     * @param familyId            - id of the family
     * @param relationshipId      - id of the relationship
     * @param newRelationshipType - RelationshipType object
     * @return changed relationship object
     */
    public Relationship changeRelationshipAndSave(long familyId, int relationshipId, RelationshipType newRelationshipType) {
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyJPA uneditedFamilyJPA = fromOptionalToJPA(familyIdVO);
        RelationshipIdJPA relationshipIdJPA = new RelationshipIdJPA(relationshipId);

        RelationshipJPA relationshipToBeChanged = uneditedFamilyJPA.getRelationshipJPAById(relationshipIdJPA);

        relationshipToBeChanged.setRelationshipType(newRelationshipType.getNumericValue());

        iFamilyRepositoryJPA.save(uneditedFamilyJPA);

        Family editedFamily = getDatabaseSavedFamily(familyIdVO);

        return editedFamily.getRelationshipById(relationshipId);
    }
}