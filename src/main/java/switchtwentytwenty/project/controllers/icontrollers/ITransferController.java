package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import switchtwentytwenty.project.dto.transaction.TransferInputDTO;

public interface ITransferController {
    ResponseEntity<Object> transfer(@RequestBody TransferInputDTO transferInputDTO, @PathVariable long originAccountId);
}