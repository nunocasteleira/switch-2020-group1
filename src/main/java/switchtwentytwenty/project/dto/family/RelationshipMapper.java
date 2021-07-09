package switchtwentytwenty.project.dto.family;

import org.springframework.stereotype.Component;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.Relationship;
import switchtwentytwenty.project.domain.model.shared.RelationshipType;

import java.util.ArrayList;
import java.util.List;

@Component
public class RelationshipMapper {
    public RelationshipOutputDTO toDTO(Relationship relationship, String mainUserName, String otherUserName) {
        String relationshipType = relationship.getFamilyRelationshipType().toString();
        Email mainUserId = relationship.getMainUserId();
        Email otherUserId = relationship.getOtherUserId();

        String mainUserEmailAddress = mainUserId.getEmailAddress();
        String otherUserEmailAddress = otherUserId.getEmailAddress();

        RelationshipUserDTO relationshipUserDTOMain = new RelationshipUserDTO(mainUserName, mainUserEmailAddress);
        RelationshipUserDTO relationshipUserDTOOther = new RelationshipUserDTO(otherUserName, otherUserEmailAddress);

        int relationshipId = relationship.getRelationshipId().getId();

        return new RelationshipOutputDTO(relationshipUserDTOMain, relationshipType, relationshipUserDTOOther, relationshipId);
    }

    /**
     * Method to convert a list of RelationshipType objects into a RelationshipTypesListDTO.
     * @param relationshipTypes list of RelationshipType domain objects
     * @return RelationshipTypesListDTO object
     */
    public RelationshipTypesListDTO relationshipTypesToDTO (List<RelationshipType> relationshipTypes){
        List<RelationshipTypeDTO> relationshipTypesList = new ArrayList<>();

        for (RelationshipType relationshipType:relationshipTypes) {
            RelationshipTypeDTO relationshipTypeDTO = new RelationshipTypeDTO(relationshipType.getNumericValue(), relationshipType.toString());
            relationshipTypesList.add(relationshipTypeDTO);
        }
        return new RelationshipTypesListDTO(relationshipTypesList);
    }
}
