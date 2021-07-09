package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.dto.account.AccountInputDTO;

public interface ICreateFamilyCashAccountController {

    ResponseEntity<Object> createFamilyCashAccount(AccountInputDTO inputDTO);
}
