package switchtwentytwenty.project.datamodel.transaction;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.transaction.TransferVOs;

import static org.junit.jupiter.api.Assertions.*;

class TransferJPATest {

    @Test
    void testNoArgsConstructor() {
        TransferJPA result = new TransferJPA();

        assertNotNull(result);
    }

    @Test
    void testGetter() {
        //arrange
        long transactionId = 0;
        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(3);
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(4);
        AmountJPA amountJPA = new AmountJPA(25.78, 3);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Electricity bill");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("12-09-2010 14:00");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);

        //act
        TransferJPA transferJPA = new TransferJPA(accountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);
        boolean isPayment = transferJPA.isPayment();

        //assert
        assertNotNull(transferJPA);
        assertEquals(transactionId, transferJPA.getTransactionId());
        assertEquals(accountIdJPA, transferJPA.getAccountIdValue());
        assertEquals(destinationAccountIdJPA, transferJPA.getDestinationAccountIdJPA());
        assertEquals(amountJPA, transferJPA.getAmount());
        assertEquals(descriptionJPA, transferJPA.getTransactionDescription());
        assertEquals(transactionDateJPA, transferJPA.getDate());
        assertEquals(categoryIdJPA, transferJPA.getCategoryId());
        assertFalse(isPayment);
    }

    @Test
    void testEquals_test1_DifferentObjects() {
        //arrange
        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(3);
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(4);
        AmountJPA amountJPA = new AmountJPA(25.78, 3);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Electricity bill");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("12-09-2010 14:00");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);

        //act
        TransferJPA transferJPA = new TransferJPA(accountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);
        TransferJPA otherTransferJPA = new TransferJPA(accountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);

        //assert
        assertNotSame(transferJPA, otherTransferJPA);
        assertEquals(transferJPA, otherTransferJPA);
    }

    @Test
    void testEquals_test2_SameObjects() {
        //arrange
        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(3);
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(4);
        AmountJPA amountJPA = new AmountJPA(25.78, 3);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Electricity bill");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("12-09-2010 14:00");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);

        //act
        TransferJPA transferJPA = new TransferJPA(accountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);
        TransferJPA otherTransferJPA = transferJPA;

        //assert
        assertSame(transferJPA, otherTransferJPA);
        assertEquals(transferJPA, otherTransferJPA);
    }

    @Test
    void testEquals_test3_DifferentObjectsType() {
        //arrange
        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(3);
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(4);
        AmountJPA amountJPA = new AmountJPA(25.78, 3);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Electricity bill");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("12-09-2010 14:00");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);

        //act
        TransferJPA transferJPA = new TransferJPA(accountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);
        AccountId otherOriginId = new AccountId(accountIdJPA.getAccountIdNumber());

        //assert
        assertNotEquals(transferJPA, otherOriginId);
    }

    @Test
    void testEquals_test4_NotEqualObjects() {
        //arrange
        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(3);
        OriginAccountIdJPA otherAccountIdJPA = new OriginAccountIdJPA(5);
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(4);
        AmountJPA amountJPA = new AmountJPA(25.78, 3);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Electricity bill");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("12-09-2010 14:00");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);

        //act
        TransferJPA transferJPA = new TransferJPA(accountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);
        TransferJPA otherTransferJPA = new TransferJPA(otherAccountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);

        // assert
        assertNotEquals(transferJPA, otherTransferJPA);
    }

    @Test
    void testHashCode() {
        //arrange
        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(3);
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(4);
        AmountJPA amountJPA = new AmountJPA(25.78, 3);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Electricity bill");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("12-09-2010 14:00");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);

        //act
        TransferJPA transferJPA = new TransferJPA(accountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);
        TransferJPA otherTransferJPA = new TransferJPA(accountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA);

        //assert
        assertNotSame(transferJPA, otherTransferJPA);
        assertEquals(transferJPA.hashCode(), otherTransferJPA.hashCode());
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