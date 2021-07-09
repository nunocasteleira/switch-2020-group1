package switchtwentytwenty.project.dto.transaction;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.*;

import static org.junit.jupiter.api.Assertions.*;

class TransferVOsTest {

    @Test
    void testNoArgsConstructor() {
        TransferVOs transferVOs = new TransferVOs();

        assertNotNull(transferVOs);
    }

    @Test
    void testGetterAndSetter() {
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

        //act
        TransferVOs transferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);
        transferVOs.setOriginAccountId(originId);
        transferVOs.setDestinationAccountId(destinationId);
        transferVOs.setAmount(transactionAmount);
        transferVOs.setDescription(transactionDescription);
        transferVOs.setDate(transactionDate);
        transferVOs.setCategoryId(category);

        //assert
        assertNotNull(transferVOs);
        assertEquals(originId, transferVOs.getOriginAccountId());
        assertEquals(destinationId, transferVOs.getDestinationAccountId());
        assertEquals(transactionAmount, transferVOs.getAmount());
        assertEquals(transactionDescription, transferVOs.getDescription());
        assertEquals(transactionDate, transferVOs.getDate());
        assertEquals(category, transferVOs.getCategoryId());
    }

    @Test
    void testEquals_test1_DifferentObjects() {
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

        //act
        TransferVOs transferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);
        TransferVOs otherTransferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);

        //assert
        assertNotSame(transferVOs, otherTransferVOs);
        assertEquals(transferVOs, otherTransferVOs);
    }

    @Test
    void testEquals_test2_SameObjects() {
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

        //act
        TransferVOs transferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);
        TransferVOs otherTransferVOs = transferVOs;

        //assert
        assertSame(transferVOs, otherTransferVOs);
        assertEquals(transferVOs, otherTransferVOs);
    }

    @Test
    void testEquals_test3_DifferentObjectsType() {
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

        //act
        TransferVOs transferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);
        AccountId otherOriginId = new AccountId(originAccountId);

        //assert
        assertNotEquals(transferVOs, otherOriginId);
    }

    @Test
    void testEquals_test4_NotEqualObjects() {
        //arrange
        int originAccountId = 1;
        AccountId originId = new AccountId(originAccountId);
        int otherOriginAccountId = 3;
        AccountId otherOriginId = new AccountId(otherOriginAccountId);
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

        //act
        TransferVOs transferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);
        TransferVOs otherTransferVOs = new TransferVOs(otherOriginId, destinationId, transactionAmount, transactionDescription, transactionDate, category);

        // assert
        assertNotEquals(transferVOs, otherTransferVOs);
    }

    @Test
    void testHashCode() {
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

        //act
        TransferVOs transferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);
        TransferVOs otherTransferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);

        //assert
        assertNotSame(transferVOs, otherTransferVOs);
        assertEquals(transferVOs.hashCode(), otherTransferVOs.hashCode());
    }

    @Test
    void testHashCode_false() {
        //arrange
        int originAccountId = 1;
        AccountId originId = new AccountId(originAccountId);
        int otherOriginAccountId = 3;
        AccountId otherOriginId = new AccountId(otherOriginAccountId);
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

        //act
        TransferVOs transferVOs = new TransferVOs(originId, destinationId, transactionAmount, transactionDescription, transactionDate, category);
        TransferVOs otherTransferVOs = new TransferVOs(otherOriginId, destinationId, transactionAmount, transactionDescription, transactionDate, category);

        // assert
        assertNotEquals(transferVOs.hashCode(), otherTransferVOs.hashCode());
    }
}