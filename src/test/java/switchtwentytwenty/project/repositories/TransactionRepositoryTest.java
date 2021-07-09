package switchtwentytwenty.project.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.datamodel.assembler.TransactionDomainDataAssembler;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.datamodel.transaction.PaymentJPA;
import switchtwentytwenty.project.datamodel.transaction.TransactionJPA;
import switchtwentytwenty.project.datamodel.transaction.TransferJPA;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.domain.model.transaction.Payment;
import switchtwentytwenty.project.domain.model.transaction.Transaction;
import switchtwentytwenty.project.domain.model.transaction.Transfer;
import switchtwentytwenty.project.repositories.irepositories.ITransactionRepositoryJPA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("TransactionRepositoryTest")
class TransactionRepositoryTest {

    @Mock
    TransactionDomainDataAssembler transactionDomainDataAssembler;
    @Mock
    ITransactionRepositoryJPA iTransactionRepositoryJPA;
    @InjectMocks
    TransactionRepository transactionRepository;

    @Test
    void savePaymentSuccessfully() {
        //Arrange
        AccountId accountId = new AccountId(1);
        TransactionAmount amount = new TransactionAmount(-35.00, Currency.EUR);
        Description description = new Description("Electricity bill");
        TransactionDate date = new TransactionDate("12/09/2010 15:00");
        CategoryId categoryId = new CategoryId(2);
        Description destinationEntity = new Description("EDP");

        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(1);
        AmountJPA amountJPA = new AmountJPA(-35.00, 1);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Electricity bill");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("12/09/2010 15:00");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);
        DestinationEntityJPA destinationEntityJPA = new DestinationEntityJPA("EDP");

        Payment aPayment = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withCategoryId(categoryId)
                .withDestinationEntity(destinationEntity)
                .withTransactionId()
                .build();

        TransactionJPA transactionJPA = new PaymentJPA(accountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA, destinationEntityJPA);

        Transaction expectedTransaction = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withCategoryId(categoryId)
                .withDestinationEntity(destinationEntity)
                .withTransactionId()
                .build();

        when(transactionDomainDataAssembler.toData(aPayment)).thenReturn((PaymentJPA) transactionJPA);
        when(iTransactionRepositoryJPA.save(transactionJPA)).thenReturn(transactionJPA);

        //Act
        Transaction resultTransaction = transactionRepository.save(aPayment);

        //Assert
        assertNotNull(resultTransaction);
        assertEquals(expectedTransaction.getAccountId(), resultTransaction.getAccountId());
        assertEquals(expectedTransaction.getDescription(), resultTransaction.getDescription());
        assertEquals(expectedTransaction.getCategoryId(), resultTransaction.getCategoryId());
    }

    @Test
    void save_Successfully() {
        //arrange
        AccountId originAccountId = new AccountId(1);
        AccountId destinationAccountId = new AccountId(2);
        TransactionAmount amount = new TransactionAmount(-35.00, Currency.EUR);
        Description description = new Description("Electricity bill");
        TransactionDate date = new TransactionDate("12/09/2010 15:00");
        CategoryId categoryId = new CategoryId(2);

        OriginAccountIdJPA originAccountIdJPA = new OriginAccountIdJPA(1);
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(2);
        AmountJPA amountJPA = new AmountJPA(-35.00, 1);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Electricity bill");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("12/09/2010 15:00");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);

        Transfer aTransfer = new Transfer.TransferBuilder(originAccountId, destinationAccountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withCategoryId(categoryId)
                .withTransactionId()
                .build();

        TransactionJPA transactionJPA = new TransferJPA(originAccountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);

        Transaction expected = new Transfer.TransferBuilder(originAccountId, destinationAccountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withCategoryId(categoryId)
                .withTransactionId()
                .build();

        when(transactionDomainDataAssembler.toData(aTransfer)).thenReturn((TransferJPA) transactionJPA);
        when(iTransactionRepositoryJPA.save(transactionJPA)).thenReturn(transactionJPA);

        //Act
        Transaction result = transactionRepository.save(aTransfer);

        //Assert
        assertNotNull(result);
        assertEquals(expected.getAccountId(), result.getAccountId());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getCategoryId(), result.getCategoryId());
    }
}