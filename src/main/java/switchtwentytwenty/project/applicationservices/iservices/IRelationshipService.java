package switchtwentytwenty.project.applicationservices.iservices;

import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.domain.model.shared.RelationshipType;
import switchtwentytwenty.project.dto.family.RelationshipInputDTO;
import switchtwentytwenty.project.dto.family.RelationshipListDTO;
import switchtwentytwenty.project.dto.family.RelationshipOutputDTO;
import switchtwentytwenty.project.dto.family.RelationshipTypesListDTO;

import java.util.List;

public interface IRelationshipService {
    List<RelationshipType> getRelationshipTypesList();

    //Delete this method after rearrange tests
    List<Email> getFamilyMembers(FamilyId familyId);

    RelationshipOutputDTO createRelationship(long familyId, RelationshipInputDTO relationshipInputDTO);

    RelationshipListDTO getFamilyRelationshipList(long familyId);

    RelationshipOutputDTO changeRelationship(long familyId, int relationshipId, int relationshipType);

    RelationshipTypesListDTO getRelationshipTypesListDTO();
}
