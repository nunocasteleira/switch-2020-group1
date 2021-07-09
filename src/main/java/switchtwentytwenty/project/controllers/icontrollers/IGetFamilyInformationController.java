package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;

public interface IGetFamilyInformationController {
    ResponseEntity<Object> getFamilyInformation(long familyId);
}
