package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;

public interface IAddEmailController {
    ResponseEntity<Object> addEmail(String id, String email);
}
