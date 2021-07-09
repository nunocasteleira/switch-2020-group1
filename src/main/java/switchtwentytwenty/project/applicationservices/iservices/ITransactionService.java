package switchtwentytwenty.project.applicationservices.iservices;

import switchtwentytwenty.project.dto.account.AccountBalanceDTO;
import switchtwentytwenty.project.dto.transaction.*;

public interface ITransactionService {
    TransactionOutputDTO registerPayment(PaymentInputDTO paymentInputDTO, long accountId);

    AccountBalanceDTO getAccountBalance(long accountId);

    TransactionOutputDTO transfer(TransferInputDTO transferInputDTO, long originAccountId);

    TransactionListDTO getAccountTransactionsBetweenDates(long accountId, DateRangeDTO dateRangeDTO);
}