package switchtwentytwenty.project.assemblers;

import org.springframework.stereotype.Service;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.domain.model.transaction.Payment;
import switchtwentytwenty.project.domain.model.transaction.Transfer;
import switchtwentytwenty.project.dto.transaction.*;

@Service
public class TransactionAssembler {

    public PaymentVOs toDomain(PaymentInputDTO paymentInputDTO, long accountId) {
        double amount = (paymentInputDTO.getAmount()) * -1;
        TransactionAmount anAmount = new TransactionAmount(amount, Currency.convertNumberToCurrency(paymentInputDTO.getCurrency()));
        AccountId anAccountId = new AccountId(accountId);
        Description aDescription = new Description(paymentInputDTO.getDescription());
        TransactionDate paymentDate = new TransactionDate(paymentInputDTO.getDate());
        Description aDestinationEntity = new Description(paymentInputDTO.getDestinationEntity());
        CategoryId aCategoryId = new CategoryId(paymentInputDTO.getCategoryId());

        return new PaymentVOs(anAccountId, anAmount, aDescription, paymentDate, aDestinationEntity, aCategoryId);
    }

    public TransactionOutputDTO toOutputDTO(Payment aPayment, CategoryName aCategoryName, Balance afterPaymentBalance) {
        long transactionId = aPayment.getTransactionId().getTransactionIdValue();
        long accountId = aPayment.getAccountId().getAccountIdNumber();
        double amount = aPayment.getAmount().getAmount();
        int currencyNumber = aPayment.getAmount().getCurrency().getCurrencyNumber();
        Currency currencyValue = Currency.convertNumberToCurrency(currencyNumber);
        String currency = currencyValue.toString();
        String description = aPayment.getDescription().getAccountDescription();
        String date = aPayment.getDate().toString();
        String destinationEntity = aPayment.getDestinationEntity().getAccountDescription();
        String categoryName = aCategoryName.toString();
        Object categoryId = aPayment.getCategoryId().getId();
        double balance = afterPaymentBalance.getAmount();

        return new TransactionOutputDTO(transactionId, accountId, amount, currency, description, date, destinationEntity, categoryId, categoryName, balance);
    }

    public TransferVOs toDomain(TransferInputDTO transferInputDTO, long accountId) {
        AccountId anOriginAccountId = new AccountId(accountId);
        AccountId aDestinationAccountId = new AccountId(transferInputDTO.getDestinationAccountId());
        double amount = (transferInputDTO.getAmount()) * -1;
        TransactionAmount anAmount = new TransactionAmount(amount, Currency.convertNumberToCurrency(transferInputDTO.getCurrency()));
        Description aDescription = new Description(transferInputDTO.getDescription());
        TransactionDate transferDate = new TransactionDate(transferInputDTO.getDate());
        CategoryId aCategoryId = new CategoryId(transferInputDTO.getCategoryId());

        return new TransferVOs(anOriginAccountId, aDestinationAccountId, anAmount, aDescription, transferDate, aCategoryId);
    }

    public TransferVOs destinationAccountTransferToDomain(TransferInputDTO transferInputDTO, long accountId) {
        AccountId anOriginAccountId = new AccountId(transferInputDTO.getDestinationAccountId());
        AccountId aDestinationAccountId = new AccountId(accountId);
        double amount = (transferInputDTO.getAmount());
        TransactionAmount anAmount = new TransactionAmount(amount, Currency.convertNumberToCurrency(transferInputDTO.getCurrency()));
        Description aDescription = new Description(transferInputDTO.getDescription());
        TransactionDate transferDate = new TransactionDate(transferInputDTO.getDate());
        CategoryId aCategoryId = new CategoryId(transferInputDTO.getCategoryId());

        return new TransferVOs(anOriginAccountId, aDestinationAccountId, anAmount, aDescription, transferDate, aCategoryId);
    }

    public TransactionOutputDTO toOutputDTO(Transfer aTransfer, CategoryName aCategoryName, Balance afterTransferBalance) {
        long transactionId = aTransfer.getTransactionId().getTransactionIdValue();
        long originAccountId = aTransfer.getAccountId().getAccountIdNumber();
        long destinationAccountId = aTransfer.getDestinationAccountId().getAccountIdNumber();
        double amount = aTransfer.getAmount().getAmount();
        int currencyNumber = aTransfer.getAmount().getCurrency().getCurrencyNumber();
        Currency currencyValue = Currency.convertNumberToCurrency(currencyNumber);
        String currency = currencyValue.toString();
        String description = aTransfer.getDescription().getAccountDescription();
        String date = aTransfer.getDate().toString();
        String categoryName = aCategoryName.toString();
        Object categoryId = aTransfer.getCategoryId().getId();
        double balance = afterTransferBalance.getAmount();

        return new TransactionOutputDTO(transactionId, originAccountId, destinationAccountId, amount, currency, description, date, categoryId, categoryName, balance);
    }
}
