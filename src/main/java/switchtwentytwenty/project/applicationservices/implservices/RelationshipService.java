package switchtwentytwenty.project.applicationservices.implservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import switchtwentytwenty.project.applicationservices.iservices.IRelationshipService;
import switchtwentytwenty.project.assemblers.RelationshipAssembler;
import switchtwentytwenty.project.domain.domainservices.FamilyDomainService;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.family.*;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RelationshipService implements IRelationshipService {
    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RelationshipAssembler relationshipAssembler;
    @Autowired
    private FamilyDomainService familyDomainService;
    @Autowired
    private RelationshipMapper relationshipMapper;

    /**
     * Gets list of existing relationship types
     *
     * @return list of relationship types
     */
    @Override
    public List<RelationshipType> getRelationshipTypesList() {
        return Arrays.asList(RelationshipType.values());
    }

    //Delete this method after rearrange tests
    /**
     * Gets list of existing family members
     *
     * @return list of family members id's
     */
    @Override
    public List<Email> getFamilyMembers(FamilyId familyId) {
        return familyRepository.getFamilyMembers(familyId);
    }

    /**
     * @param familyId             id of the family
     * @param relationshipInputDTO input relationship DTO containing all of the relationship
     *                             informations
     * @return list of family members by their id
     */
    @Override
    public RelationshipOutputDTO createRelationship(long familyId, @NonNull RelationshipInputDTO relationshipInputDTO) {
        boolean result;

        RelationshipType relationshipType = relationshipAssembler.createRelationshipType(relationshipInputDTO);
        Email mainUserId = relationshipAssembler.createMainUserEmail(relationshipInputDTO);
        Email otherUserId = relationshipAssembler.createOtherUserEmail(relationshipInputDTO);

        if (relationshipInputDTO.getMainUserId().equals(relationshipInputDTO.getOtherUserId())) {
            throw new DuplicateObjectException("A person can't have a relationship with themselves.");
        }

        String mainUserName = personRepository.getByEmail(mainUserId).getName().toString();
        String otherUserName = personRepository.getByEmail(otherUserId).getName().toString();

        Relationship relationship = new Relationship(relationshipType, mainUserId, otherUserId);

        FamilyId familyIdVO = new FamilyId(familyId);
        Family family = familyRepository.getDatabaseSavedFamily(familyIdVO);

        result = familyDomainService.existsRelationship(family, relationship.getMainUserId(), relationship.getOtherUserId());

        if (!result) {
            Relationship savedRelationship = familyRepository.addRelationship(relationship, familyIdVO);
            return relationshipMapper.toDTO(savedRelationship, mainUserName, otherUserName);

        } else {
            throw new DuplicateObjectException("The relationship already exists.");
        }
    }

    /**
     * This method gets the list of members and their relationships in the logged person's family as
     * a List of DTO
     *
     * @param familyId the family id those relationships are related to
     * @return FamilyRelationshipDTO List with the relationships
     */
    @Override
    public RelationshipListDTO getFamilyRelationshipList(long familyId) {
        FamilyId familyIdVO = new FamilyId(familyId);
        List<RelationshipOutputDTO> listOfRelationshipsDTO = new ArrayList<>();

        List<Relationship> listOfRelationships = familyRepository.getRelationshipsList(familyIdVO);
        RelationshipMapper relationshipMapper = new RelationshipMapper();

        for (Relationship relationship : listOfRelationships) {
            Email mainUserId = relationship.getMainUserId();
            Email otherUserId = relationship.getOtherUserId();

            PersonName mainUserName = getPersonName(mainUserId);
            PersonName otherUserName = getPersonName(otherUserId);

            RelationshipOutputDTO relationshipOutputDTO = relationshipMapper.toDTO(relationship,
                    mainUserName.toString(), otherUserName.toString());

            listOfRelationshipsDTO.add(relationshipOutputDTO);
        }
        return new RelationshipListDTO(listOfRelationshipsDTO);
    }

    private PersonName getPersonName(Email userId) {
        Person mainUser = personRepository.getByEmail(userId);
        return mainUser.getName();
    }

    /**
     * Method to change relationship in the family.
     * @param familyId - id of the family
     * @param relationshipId - id of the relationship
     * @return RelationshipOutputDTO object with changed relationship
     */
    @Override
    public RelationshipOutputDTO changeRelationship(long familyId, int relationshipId, int relationshipType){

        RelationshipType newRelationshipType = relationshipAssembler.instantiateRelationshipType(relationshipType);

        Relationship changedRelationship = familyRepository.changeRelationshipAndSave(familyId, relationshipId, newRelationshipType);

        Email mainUserId = changedRelationship.getMainUserId();
        Email otherUserId = changedRelationship.getOtherUserId();
        String mainUserName = getPersonName(mainUserId).toString();
        String otherUserName = getPersonName(otherUserId).toString();

        return relationshipMapper.toDTO(changedRelationship, mainUserName, otherUserName);

    }

    /**
     * Method to obtain family relationship types in a list.
     * @return RelationshipTypesListDTO object
     */
    @Override
    public RelationshipTypesListDTO getRelationshipTypesListDTO(){
        List<RelationshipType> relationshipTypes = getRelationshipTypesList();

        return relationshipMapper.relationshipTypesToDTO(relationshipTypes);
    }
}