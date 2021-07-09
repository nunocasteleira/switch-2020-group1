package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;

public interface IGetFamilyMembersWithCashAccounts {
    ResponseEntity<Object> getFamilyMembersWithCashAccounts(String personId);
}
