package switchtwentytwenty.project.controllers.icontrollers;

import com.sun.istack.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import switchtwentytwenty.project.dto.transaction.PaymentInputDTO;

public interface IRegisterPaymentController {
    @PostMapping("/transactions")
    ResponseEntity<Object> registerPayment(@RequestBody @NotNull PaymentInputDTO paymentInputDTO, long accountId);
}
