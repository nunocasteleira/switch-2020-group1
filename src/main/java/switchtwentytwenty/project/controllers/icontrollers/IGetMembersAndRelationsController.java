package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;

public interface IGetMembersAndRelationsController {
    ResponseEntity<Object> getListOfMembersAndTheirRelations(long familyId);
}
