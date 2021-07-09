package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IGetCashAccountsController {
    ResponseEntity<Object> getCashAccounts(@PathVariable String personId);
}
