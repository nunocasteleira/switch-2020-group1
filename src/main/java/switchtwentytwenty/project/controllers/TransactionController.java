package switchtwentytwenty.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import switchtwentytwenty.project.applicationservices.iservices.ITransactionService;
import switchtwentytwenty.project.controllers.icontrollers.IGetAccountTransactionsBetweenDates;
import switchtwentytwenty.project.controllers.icontrollers.IRegisterPaymentController;
import switchtwentytwenty.project.controllers.icontrollers.ITransferController;
import switchtwentytwenty.project.dto.transaction.*;

import java.time.DateTimeException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class TransactionController implements IRegisterPaymentController, ITransferController, IGetAccountTransactionsBetweenDates {
    @Autowired
    private ITransactionService transactionService;

    /**
     * This method registers a payment from an account
     * @param paymentInputDTO DTO containing payment information
     * @param accountId Id of the account the payment is registered from
     * @return DTO with payment information, hateoas links and http status
     */
    @Override
    @PostMapping("/transactions/{accountId}/payments")
    public ResponseEntity<Object> registerPayment(@RequestBody PaymentInputDTO paymentInputDTO, @PathVariable long accountId) {
        TransactionOutputDTO transactionOutputDTO;
        try {
            transactionOutputDTO = transactionService.registerPayment(paymentInputDTO, accountId);
            long transactionId = transactionOutputDTO.getTransactionId();
            Link selfLink =
                    linkTo(TransactionController.class).slash("transactions").slash(transactionId).withRel("paymentInformation");
            Link accountOptions = linkTo(AccountController.class).slash("accounts").slash("accountId").withRel("accountOptions");
            transactionOutputDTO.add(selfLink, accountOptions);
            return new ResponseEntity<>(transactionOutputDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException | DateTimeException | NullPointerException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PostMapping("/transactions/{originAccountId}/transfers")
    public ResponseEntity<Object> transfer(@RequestBody TransferInputDTO transferInputDTO, @PathVariable long originAccountId) {
        try {
            TransactionOutputDTO transferOutputDTO = transactionService.transfer(transferInputDTO, originAccountId);
            long transactionId = transferOutputDTO.getTransactionId();
            Link selfLink = linkTo(TransactionController.class).slash("transactions").slash(transactionId).withRel("transferInformation");
            Link accountOptions = linkTo(AccountController.class).slash("accounts").slash("accountId").withRel("accountOptions");
            transferOutputDTO.add(selfLink, accountOptions);
            return new ResponseEntity<>(transferOutputDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException | DateTimeException | NullPointerException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @GetMapping("/transactions/{accountId}")
    public ResponseEntity<Object> getAccountTransactionsBetweenDates(@PathVariable("accountId") long accountId, @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate) {
        DateRangeDTO dateRangeDTO = new DateRangeDTO(startDate, endDate);
        try {
            TransactionListDTO transactionListDTO = transactionService.getAccountTransactionsBetweenDates(accountId, dateRangeDTO);
            Link selfLink = linkTo(TransactionController.class).slash("transactions").slash(accountId).withRel("transactions");
            Link accountOptions = linkTo(AccountController.class).slash("accounts").slash(accountId).withRel("accountOptions");
            transactionListDTO.add(selfLink, accountOptions);
            return new ResponseEntity<>(transactionListDTO, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
