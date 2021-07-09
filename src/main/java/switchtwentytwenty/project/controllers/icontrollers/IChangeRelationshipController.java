package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IChangeRelationshipController {
    ResponseEntity<Object> changeRelationship(@PathVariable("familyId") long familyId, @PathVariable("relationshipId") int relationshipId, int relationshipType);

}
