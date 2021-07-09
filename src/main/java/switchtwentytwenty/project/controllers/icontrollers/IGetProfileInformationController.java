package switchtwentytwenty.project.controllers.icontrollers;

import io.jsonwebtoken.Jwt;
import org.springframework.http.ResponseEntity;

public interface IGetProfileInformationController {
    ResponseEntity<Object> getProfileInformation(String personId);
}
