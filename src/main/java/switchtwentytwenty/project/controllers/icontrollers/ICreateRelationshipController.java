package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.dto.family.RelationshipInputDTO;

public interface ICreateRelationshipController {

    ResponseEntity<Object> createRelationship(RelationshipInputDTO relationshipInputDTO, long familyId);
}
