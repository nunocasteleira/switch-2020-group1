package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import switchtwentytwenty.project.dto.account.AccountInputDTO;

public interface ICreatePersonalCashAccountController {

    ResponseEntity<Object> createPersonalCashAccount(@RequestBody AccountInputDTO cashAccountInputDTO, @PathVariable("personId") String personId);

}
