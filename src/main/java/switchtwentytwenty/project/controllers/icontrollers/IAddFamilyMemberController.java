package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.dto.person.PersonInputDTO;

public interface IAddFamilyMemberController {
    ResponseEntity<Object> addFamilyMember(PersonInputDTO personInputDTO, long familyId);
}
