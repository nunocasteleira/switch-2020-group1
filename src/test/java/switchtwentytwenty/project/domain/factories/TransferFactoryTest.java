package switchtwentytwenty.project.domain.factories;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.domain.model.transaction.Transfer;
import switchtwentytwenty.project.dto.transaction.TransferVOs;

import static org.junit.jupiter.api.Assertions.*;

class TransferFactoryTest {

    @Test
    void testNoArgsConstructor() {
        TransferFactory transferFactory = new TransferFactory();

        assertNotNull(transferFactory);
    }

    @Test
    void buildTransfer_Successfully() {
        //arrange
        int originAccountId = 1;
        AccountId originId = new AccountId(originAccountId);
        int destinationAccountId = 2;
        AccountId destinationId = new AccountId(destinationAccountId);
        double amount = 20;
        int currency = 2;
        TransactionAmount transactionAmount = new TransactionAmount(amount, Currency.convertNumberToCurrency(currency));
        String description = "Jantar Mexicano";
        Description transactionDescription = new Description(description);
        String date = "12/12/1212 12:12";
        TransactionDate transactionDate = new TransactionDate(date);
        int categoryId = 123456;
        CategoryId category = new CategoryId(categoryId);
        TransferVOs transferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);

        //act
        Transfer transfer = TransferFactory.buildTransfer(transferVOs);

        assertNotNull(transfer);
    }

    @Test
    void otherBuildTransfer_Successfully() {
        //arrange
        int originAccountId = 1;
        AccountId originId = new AccountId(originAccountId);
        int destinationAccountId = 2;
        AccountId destinationId = new AccountId(destinationAccountId);
        double amount = 20;
        int currency = 2;
        TransactionAmount transactionAmount = new TransactionAmount(amount, Currency.convertNumberToCurrency(currency));
        String description = "Jantar Mexicano";
        Description transactionDescription = new Description(description);
        String date = "12/12/1212 12:12";
        TransactionDate transactionDate = new TransactionDate(date);
        int categoryId = 123456;
        CategoryId category = new CategoryId(categoryId);
        TransferVOs transferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);

        Transfer expected = new Transfer.TransferBuilder(originId, destinationId, transactionAmount)
                .withDescription(transactionDescription)
                .withTransactionDate(transactionDate)
                .withCategoryId(category)
                .withTransactionId()
                .build();

        Transfer result = TransferFactory.buildTransfer(transferVOs);

        assertEquals(expected.getAmount(), result.getAmount());
        assertEquals(expected.getAccountId(), result.getAccountId());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getDate(), result.getDate());
        assertEquals(expected.getCategoryId(), result.getCategoryId());
        assertNotEquals(description.hashCode(), result.hashCode());
    }


}