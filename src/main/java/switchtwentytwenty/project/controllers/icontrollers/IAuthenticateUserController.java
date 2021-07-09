package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import switchtwentytwenty.project.dto.authentication.LoginRequestDTO;

public interface IAuthenticateUserController {
    @PostMapping("/signin")
    ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest);
}
