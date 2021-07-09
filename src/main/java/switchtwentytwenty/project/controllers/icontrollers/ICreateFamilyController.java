package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;

public interface ICreateFamilyController {

    ResponseEntity<Object> createFamily(FamilyInputDTO familyInputDTO);
}
