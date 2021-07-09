package switchtwentytwenty.project.assemblers;

import org.springframework.stereotype.Component;
import switchtwentytwenty.project.domain.exceptions.InvalidRelationshipType;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.RelationshipType;
import switchtwentytwenty.project.dto.family.RelationshipInputDTO;

@Component
public class RelationshipAssembler {

    public Email createMainUserEmail(RelationshipInputDTO relationshipInputDTO) {
        String mainUserIdString = relationshipInputDTO.getMainUserId();
        return new Email(mainUserIdString);
    }

    public Email createOtherUserEmail(RelationshipInputDTO relationshipInputDTO) {
        String otherUserIdString = relationshipInputDTO.getOtherUserId();
        return new Email(otherUserIdString);
    }

    /**
     * Method to instantiate RelationshipType value object given relationshipInputDTO.
     * @param relationshipInputDTO relationshipInputDTO object with relationship information
     * @return RelationshipType value object
     */
    public RelationshipType createRelationshipType(RelationshipInputDTO relationshipInputDTO) {
        String relationshipTypeString = relationshipInputDTO.getRelationshipType();
        if (relationshipTypeString == null) {
            throw new InvalidRelationshipType("Relationship type cannot be null.");
        }
        int relationshipTypeInt = Integer.parseInt(relationshipTypeString);
        return RelationshipType.valueOf(relationshipTypeInt);
    }

    /**
     * Method to instantiate RelationshipType value object given the relationship type value number.
     * @param relationshipType relationship type value number (integer)
     * @return RelationshipType value object
     */
    public RelationshipType instantiateRelationshipType(int relationshipType) {
        return RelationshipType.valueOf(relationshipType);
    }
}
