package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import switchtwentytwenty.project.dto.account.AccountInputDTO;

public interface IPersonalBankAccountController {

    ResponseEntity<Object> createPersonalBankAccount(AccountInputDTO inputDTO, @PathVariable("personId") String personId);
}
