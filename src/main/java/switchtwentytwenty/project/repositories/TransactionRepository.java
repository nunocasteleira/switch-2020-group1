package switchtwentytwenty.project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import switchtwentytwenty.project.datamodel.assembler.TransactionDomainDataAssembler;
import switchtwentytwenty.project.datamodel.shared.OriginAccountIdJPA;
import switchtwentytwenty.project.datamodel.transaction.PaymentJPA;
import switchtwentytwenty.project.datamodel.transaction.TransactionJPA;
import switchtwentytwenty.project.datamodel.transaction.TransferJPA;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.domain.model.transaction.Payment;
import switchtwentytwenty.project.domain.model.transaction.Transaction;
import switchtwentytwenty.project.domain.model.transaction.Transfer;
import switchtwentytwenty.project.repositories.irepositories.ITransactionRepositoryJPA;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {
    @Autowired
    ITransactionRepositoryJPA iTransactionRepositoryJPA;
    @Autowired
    TransactionDomainDataAssembler transactionDomainDataAssembler;

    public Transaction save(Payment aPayment) {
        TransactionJPA aPaymentJPA = transactionDomainDataAssembler.toData(aPayment);
        TransactionJPA savedPaymentJPA = iTransactionRepositoryJPA.save(aPaymentJPA);
        return toDomainPayment(savedPaymentJPA);
    }

    private Payment toDomainPayment(TransactionJPA savedTransactionJPA) {
        TransactionId transactionId = new TransactionId(savedTransactionJPA.getTransactionId());
        AccountId accountId = new AccountId(savedTransactionJPA.getAccountIdValue().getAccountIdNumber());
        Currency currency = Currency.convertNumberToCurrency(savedTransactionJPA.getAmount().getCurrency());
        TransactionAmount amount = new TransactionAmount(savedTransactionJPA.getAmount().getAmount(), currency);
        Description description = new Description(savedTransactionJPA.getTransactionDescription().getDescription());
        TransactionDate date = new TransactionDate(savedTransactionJPA.getDate().getDate());
        CategoryId categoryId = new CategoryId(savedTransactionJPA.getCategoryId().getId());
        Description destinationEntity = new Description(((PaymentJPA) savedTransactionJPA).getDestinationEntityJPA().getDestinationEntity());

        return new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withCategoryId(categoryId)
                .withDestinationEntity(destinationEntity)
                .withTransactionId(transactionId)
                .build();
    }

    public Transaction save(Transfer aTransfer) {
        TransactionJPA aTransferJPA = transactionDomainDataAssembler.toData(aTransfer);
        TransactionJPA savedTransferJPA = iTransactionRepositoryJPA.save(aTransferJPA);
        return toDomainTransfer(savedTransferJPA);
    }

    private Transfer toDomainTransfer(TransactionJPA savedTransactionJPA) {
        AccountId originAccountId = new AccountId(savedTransactionJPA.getAccountIdValue().getAccountIdNumber());
        AccountId destinationAccountId = new AccountId(((TransferJPA) savedTransactionJPA).getDestinationAccountIdJPA().getDestinationAccountIdNumber());
        Currency currency = Currency.convertNumberToCurrency(savedTransactionJPA.getAmount().getCurrency());
        TransactionAmount amount = new TransactionAmount(savedTransactionJPA.getAmount().getAmount(), currency);
        TransactionId transactionId = new TransactionId(savedTransactionJPA.getTransactionId());
        Description description = new Description(savedTransactionJPA.getTransactionDescription().getDescription());
        TransactionDate date = new TransactionDate(savedTransactionJPA.getDate().getDate());
        CategoryId categoryId = new CategoryId(savedTransactionJPA.getCategoryId().getId());

        return new Transfer.TransferBuilder(originAccountId, destinationAccountId, amount)
                .withTransactionId(transactionId)
                .withDescription(description)
                .withTransactionDate(date)
                .withCategoryId(categoryId)
                .build();
    }

    public List<Transaction> findAllByAccountId(AccountId accountId) {
        OriginAccountIdJPA originAccountIdJPA = new OriginAccountIdJPA(accountId.getAccountIdNumber());
        List<TransactionJPA> transactionsJPA = iTransactionRepositoryJPA.findAllByAccountId(originAccountIdJPA);
        return toDomain(transactionsJPA);
    }

    public List<Transaction> toDomain(List<TransactionJPA> transactionsJPA) {
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionJPA transactionJPA : transactionsJPA) {
            if (transactionJPA.isPayment()) {
                Payment aPayment = toDomainPayment(transactionJPA);
                transactions.add(aPayment);
            } else if (!transactionJPA.isPayment()) {
                Transfer aTransfer = toDomainTransfer(transactionJPA);
                transactions.add(aTransfer);
            }
        }
        return transactions;
    }
}