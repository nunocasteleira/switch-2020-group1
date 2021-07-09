package switchtwentytwenty.project.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.datamodel.shared.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Profile("TransactionRepositoryItTest")
class TransactionRepositoryItTest {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ITransactionRepositoryJPA iTransactionRepositoryJPA;

    @AfterEach
    void clean() {
        iTransactionRepositoryJPA.deleteAll();
    }

    @Test
    void savePaymentSuccessfully() {
        //Arrange
        AccountId accountId = new AccountId(1);
        TransactionAmount amount = new TransactionAmount(-35.00, Currency.EUR);
        Description description = new Description("Electricity bill");
        TransactionDate date = new TransactionDate("12/09/2010 15:00");
        CategoryId categoryId = new CategoryId(2);
        Description destinationEntity = new Description("EDP");
        TransactionId transactionId = new TransactionId(3);

        Payment aPayment = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withCategoryId(categoryId)
                .withDestinationEntity(destinationEntity)
                .withTransactionId()
                .build();

        Transaction expectedTransaction = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withCategoryId(categoryId)
                .withDestinationEntity(destinationEntity)
                .withTransactionId()
                .build();

        //Act
        Transaction resultTransaction = transactionRepository.save(aPayment);

        //Assert
        assertNotNull(resultTransaction);
        assertEquals(expectedTransaction.getAccountId(), resultTransaction.getAccountId());
        assertEquals(expectedTransaction.getDescription(), resultTransaction.getDescription());
        assertEquals(expectedTransaction.getCategoryId(), resultTransaction.getCategoryId());
    }

    @Test
    void transfer_Successfully() {
        //arrange
        AccountId originAccountId = new AccountId(1);
        AccountId destinationAccountId = new AccountId(2);
        TransactionAmount amount = new TransactionAmount(-35.00, Currency.EUR);
        Description description = new Description("Electricity bill");
        TransactionDate date = new TransactionDate("12/09/2010 15:00");
        CategoryId categoryId = new CategoryId(2);

        Transfer aTransfer = new Transfer.TransferBuilder(originAccountId, destinationAccountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withCategoryId(categoryId)
                .withTransactionId()
                .build();

        Transaction expected = new Transfer.TransferBuilder(originAccountId, destinationAccountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withCategoryId(categoryId)
                .withTransactionId()
                .build();

        //act
        Transaction result = transactionRepository.save(aTransfer);

        //assert
        assertNotNull(result);
        assertEquals(expected.getAccountId(), result.getAccountId());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getCategoryId(), result.getCategoryId());
    }

    @Test
    void convertTransactionsJPAIntoTransactions() {
        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(1);
        AmountJPA amountJPA = new AmountJPA(-234.56, 2);
        AmountJPA amountJPATwo = new AmountJPA(-33.33, 3);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Description");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("22/02/2000 12:12");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);
        CategoryIdJPA categoryIdJPATwo = new CategoryIdJPA(4);
        DestinationEntityJPA destinationEntityJPA = new DestinationEntityJPA("Shop");
        List<TransactionJPA> transactionsJPA = new ArrayList<>();
        PaymentJPA paymentJPAOne = new PaymentJPA(accountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA, destinationEntityJPA);
        PaymentJPA paymentJPATwo = new PaymentJPA(accountIdJPA, amountJPATwo, descriptionJPA, transactionDateJPA, categoryIdJPATwo, destinationEntityJPA);
        transactionsJPA.add(paymentJPAOne);
        transactionsJPA.add(paymentJPATwo);

        AccountId accountId = new AccountId(1);
        TransactionAmount amount = new TransactionAmount(-234.56, Currency.USD);
        TransactionAmount amountTwo = new TransactionAmount(-33.33, Currency.GBP);
        Description description = new Description("Description");
        TransactionDate transactionDate = new TransactionDate("22/02/2000 12:12");
        CategoryId categoryId = new CategoryId(2);
        CategoryId categoryIdTwo = new CategoryId(4);
        Description destinationEntity = new Description("Shop");
        List<Transaction> expectedTransactions = new ArrayList<>();
        Payment paymentOne = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(transactionDate)
                .withCategoryId(categoryId)
                .withDestinationEntity(destinationEntity)
                .withTransactionId()
                .build();
        Payment paymentTwo = new Payment.Builder(accountId, amountTwo)
                .withDescription(description)
                .withTransactionDate(transactionDate)
                .withCategoryId(categoryIdTwo)
                .withDestinationEntity(destinationEntity)
                .withTransactionId()
                .build();
        expectedTransactions.add(paymentOne);
        expectedTransactions.add(paymentTwo);

        List<Transaction> resultTransactions = transactionRepository.toDomain(transactionsJPA);

        assertNotNull(resultTransactions);
        assertEquals(expectedTransactions, resultTransactions);
    }

    @Test
    void convertTransactionsJPAIntoTransactions_Transfer() {
        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(1);
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(2);
        AmountJPA amountJPA = new AmountJPA(-234.56, 2);
        AmountJPA amountJPATwo = new AmountJPA(-33.33, 3);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Description");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("22/02/2000 12:12");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);
        CategoryIdJPA categoryIdJPATwo = new CategoryIdJPA(4);

        List<TransactionJPA> transactionsJPA = new ArrayList<>();
        TransferJPA transferJPAOne = new TransferJPA(accountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);
        TransferJPA transferJPATwo = new TransferJPA(accountIdJPA, destinationAccountIdJPA, amountJPATwo, descriptionJPA, transactionDateJPA, categoryIdJPATwo);
        transactionsJPA.add(transferJPAOne);
        transactionsJPA.add(transferJPATwo);

        AccountId accountId = new AccountId(1);
        AccountId destinationAccountId = new AccountId(2);
        TransactionAmount amount = new TransactionAmount(-234.56, Currency.USD);
        TransactionAmount amountTwo = new TransactionAmount(-33.33, Currency.GBP);
        Description description = new Description("Description");
        TransactionDate transactionDate = new TransactionDate("22/02/2000 12:12");
        CategoryId categoryId = new CategoryId(2);
        CategoryId categoryIdTwo = new CategoryId(4);
        List<Transaction> expected = new ArrayList<>();
        Transfer transferOne = new Transfer.TransferBuilder(accountId, destinationAccountId, amount)
                .withDescription(description)
                .withTransactionDate(transactionDate)
                .withCategoryId(categoryId)
                .withTransactionId()
                .build();
        Transfer transferTwo = new Transfer.TransferBuilder(accountId, destinationAccountId, amountTwo)
                .withDescription(description)
                .withTransactionDate(transactionDate)
                .withCategoryId(categoryIdTwo)
                .withTransactionId()
                .build();
        expected.add(transferOne);
        expected.add(transferTwo);

        List<Transaction> result = transactionRepository.toDomain(transactionsJPA);

        assertNotNull(result);
        assertEquals(expected, result);
    }
}