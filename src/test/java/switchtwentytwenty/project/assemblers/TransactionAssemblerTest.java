package switchtwentytwenty.project.assemblers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.domain.model.transaction.Payment;
import switchtwentytwenty.project.domain.model.transaction.Transfer;
import switchtwentytwenty.project.dto.transaction.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TransactionAssemblerTest {
    @Autowired
    TransactionAssembler transactionAssembler;

    @Test
    void transformPrimitivesIntoValueObjectsSuccessfully() {
        //Arrange
        long accountId = 1;
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 1, "Electricity bill", "12/09/2010 15:00", "EDP", 2);
        AccountId accountIdVO = new AccountId(accountId);
        TransactionAmount amountVO = new TransactionAmount(-20, Currency.EUR);
        Description descriptionVO = new Description("Electricity bill");
        TransactionDate transactionDateVO = new TransactionDate("12/09/2010 15:00");
        Description destinationEntityVO = new Description("EDP");
        CategoryId categoryIdVO = new CategoryId(2);
        PaymentVOs expectedPaymentVOs = new PaymentVOs(accountIdVO, amountVO, descriptionVO, transactionDateVO, destinationEntityVO, categoryIdVO);

        //Act
        PaymentVOs resultPaymentVOs = transactionAssembler.toDomain(paymentInputDTO, accountId);

        //Assert
        assertNotNull(resultPaymentVOs);
        assertEquals(expectedPaymentVOs, resultPaymentVOs);
    }

    @Test
    void transformPaymentIntoOutputDTOSuccessfully() {
        //Arrange
        AccountId accountIdVO = new AccountId(1);
        TransactionAmount amountVO = new TransactionAmount(-20, Currency.EUR);
        Description descriptionVO = new Description("Electricity bill");
        TransactionDate transactionDateVO = new TransactionDate("12/09/2010 15:00");
        Description destinationEntityVO = new Description("EDP");
        CategoryId categoryIdVO = new CategoryId(2);

        Payment payment = new Payment.Builder(accountIdVO, amountVO)
                .withDescription(descriptionVO)
                .withTransactionDate(transactionDateVO)
                .withDestinationEntity(destinationEntityVO)
                .withCategoryId(categoryIdVO)
                .withTransactionId()
                .build();

        CategoryName categoryName = new CategoryName("BILLS");
        Balance balance = new Balance(50.00);
        TransactionOutputDTO expectedOutputDTO = new TransactionOutputDTO(1, -20, "EUR", "Electricity bill", "12/09/2010 15:00", "EDP", 2, "BILLS", 50.00);

        //Act
        TransactionOutputDTO resultOutputDTO = transactionAssembler.toOutputDTO(payment, categoryName, balance);

        //Assert
        assertNotNull(resultOutputDTO);
        assertEquals(expectedOutputDTO, resultOutputDTO);
    }

    @Test
    void toDomain_Successfully() {
        //arrange
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 1, "Electricity bill", "12/09/2010 15:00", "EDP", 2);
        long accountId = 1;
        AccountId accountIdVO = new AccountId(accountId);
        AccountId destinationIdVO = new AccountId(2);
        TransactionAmount amountVO = new TransactionAmount(-20, Currency.EUR);
        Description descriptionVO = new Description("Electricity bill");
        TransactionDate transactionDateVO = new TransactionDate("12/09/2010 15:00");
        CategoryId categoryIdVO = new CategoryId("EDP");
        TransferVOs expected = new TransferVOs(accountIdVO, destinationIdVO, amountVO, descriptionVO, transactionDateVO, categoryIdVO);

        //act
        TransferVOs result = transactionAssembler.toDomain(transferInputDTO, accountId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void destinationAccountTransferToDomain_Successfully() {
        //arrange
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 1, "Electricity bill", "12/09/2010 15:00", "EDP", 2);
        long accountId = 1;
        AccountId accountIdVO = new AccountId(accountId);
        AccountId destinationIdVO = new AccountId(2);
        TransactionAmount amountVO = new TransactionAmount(20, Currency.EUR);
        Description descriptionVO = new Description("Electricity bill");
        TransactionDate transactionDateVO = new TransactionDate("12/09/2010 15:00");
        CategoryId categoryIdVO = new CategoryId("EDP");
        TransferVOs expected = new TransferVOs(destinationIdVO, accountIdVO, amountVO, descriptionVO, transactionDateVO, categoryIdVO);

        //act
        TransferVOs result = transactionAssembler.destinationAccountTransferToDomain(transferInputDTO, accountId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void toDomain_Successfully_toOutputDTO() {
        //arrange
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 1, "Electricity bill", "12/09/2010 15:00", "EDP", 2);
        AccountId accountIdVO = new AccountId(1);
        AccountId destinationIdVO = new AccountId(2);
        TransactionAmount amountVO = new TransactionAmount(-20, Currency.EUR);
        Description descriptionVO = new Description("Electricity bill");
        TransactionDate transactionDateVO = new TransactionDate("12/09/2010 15:00");
        CategoryId categoryIdVO = new CategoryId("EDP");
        Transfer transfer = new Transfer.TransferBuilder(accountIdVO, destinationIdVO, amountVO)
                .withDescription(descriptionVO)
                .withTransactionDate(transactionDateVO)
                .withCategoryId(categoryIdVO)
                .withTransactionId()
                .build();

        CategoryName categoryName = new CategoryName("BILLS");
        Balance balance = new Balance(50.00);
        TransactionOutputDTO expected = new TransactionOutputDTO(1, 2,  -20, "EUR", "Electricity bill", "12/09/2010 15:00", "EDP", "BILLS", 50.00);

        //act
        TransactionOutputDTO result = transactionAssembler.toOutputDTO(transfer, categoryName, balance);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }
}
