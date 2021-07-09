package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface IGetAccountTransactionsBetweenDates {
    @GetMapping("/accounts/{accountId}/transactions")
    ResponseEntity<Object> getAccountTransactionsBetweenDates(@PathVariable("accountId") long accountId, @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate);
}
