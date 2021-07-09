package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;

public interface IGetListOfMemberAccounts {
    ResponseEntity<Object> getAccountList(String personId);
}
