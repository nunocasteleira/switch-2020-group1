package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IGetFamilyMembersController {
    ResponseEntity<Object> getFamilyMembers(@PathVariable("familyId") long familyId);
}
