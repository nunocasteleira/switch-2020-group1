package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;

public interface IRemoveEmailController {
    ResponseEntity<Object> removeEmail(String personId, String email);
}
