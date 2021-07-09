package switchtwentytwenty.project.datamodel.transaction;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.domain.model.shared.Description;

import static org.junit.jupiter.api.Assertions.*;

class PaymentJPATest {

    @Test
    void testNoArgsConstructor() {
        PaymentJPA paymentJPA = new PaymentJPA();

        assertNotNull(paymentJPA);
    }

    @Test
    void getValueObjectsJPA() {
        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(3);
        AmountJPA amountJPA = new AmountJPA(25.78, 3);
        DescriptionJPA descriptionJPA = new DescriptionJPA("Electricity bill");
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("12-09-2010 14:00");
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(2);
        DestinationEntityJPA destinationEntityJPA = new DestinationEntityJPA("EDP");
        PaymentJPA paymentJPA = new PaymentJPA(accountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA, destinationEntityJPA);

        OriginAccountIdJPA resultAccountIdJPA = paymentJPA.getAccountIdValue();
        AmountJPA resultAmountJPA = paymentJPA.getAmount();
        DescriptionJPA resultDescriptionJPA = paymentJPA.getTransactionDescription();
        TransactionDateJPA resultTransactionDateJPA = paymentJPA.getDate();
        CategoryIdJPA resultCategoryIdJPA = paymentJPA.getCategoryId();
        DestinationEntityJPA resultDestinationEntityJPA = paymentJPA.getDestinationEntityJPA();
        long transactionId = paymentJPA.getTransactionId();
        boolean isPayment = paymentJPA.isPayment();

        assertEquals(accountIdJPA, resultAccountIdJPA);
        assertEquals(amountJPA, resultAmountJPA);
        assertEquals(descriptionJPA, resultDescriptionJPA);
        assertEquals(transactionDateJPA, resultTransactionDateJPA);
        assertEquals(categoryIdJPA, resultCategoryIdJPA);
        assertEquals(destinationEntityJPA, resultDestinationEntityJPA);
        assertNotSame(transactionId, resultAccountIdJPA);
        assertTrue(isPayment);
    }

    @Test
    void testEquals() {
        OriginAccountIdJPA accountIdJPA1 = new OriginAccountIdJPA(3);
        AmountJPA amountJPA1 = new AmountJPA(25.78, 3);
        DescriptionJPA descriptionJPA1 = new DescriptionJPA("Electricity bill");
        TransactionDateJPA transactionDateJPA1 = new TransactionDateJPA("12-09-2010 14:00");
        CategoryIdJPA categoryIdJPA1 = new CategoryIdJPA(2);
        DestinationEntityJPA destinationEntityJPA1 = new DestinationEntityJPA("EDP");
        PaymentJPA paymentJPA1 = new PaymentJPA(accountIdJPA1, amountJPA1, descriptionJPA1, transactionDateJPA1, categoryIdJPA1, destinationEntityJPA1);

        OriginAccountIdJPA accountIdJPA2 = new OriginAccountIdJPA(1);
        AmountJPA amountJPA2 = new AmountJPA(14.98, 1);
        DescriptionJPA descriptionJPA2 = new DescriptionJPA("Water bill");
        TransactionDateJPA transactionDateJPA2 = new TransactionDateJPA("13-08-2009 16:00");
        CategoryIdJPA categoryIdJPA2 = new CategoryIdJPA(4);
        DestinationEntityJPA destinationEntityJPA2 = new DestinationEntityJPA("Indaqua");
        PaymentJPA paymentJPA2 = new PaymentJPA(accountIdJPA2, amountJPA2, descriptionJPA2, transactionDateJPA2, categoryIdJPA2, destinationEntityJPA2);
        //noinspection UnnecessaryLocalVariable
        PaymentJPA paymentJPA1a = paymentJPA1;
        PaymentJPA paymentJPA1b = new PaymentJPA(accountIdJPA1, amountJPA1, descriptionJPA1, transactionDateJPA1, categoryIdJPA1, destinationEntityJPA1);

        TransactionJPA transactionJPA = new PaymentJPA(accountIdJPA1, amountJPA1, descriptionJPA1, transactionDateJPA1, categoryIdJPA1, destinationEntityJPA1);
        TransactionJPA transactionJPA1 = new PaymentJPA(accountIdJPA2, amountJPA2, descriptionJPA2, transactionDateJPA1, categoryIdJPA1, destinationEntityJPA1);

        assertEquals(paymentJPA1.getCategoryId(), paymentJPA1b.getCategoryId());
        assertEquals(paymentJPA1.isPayment(), paymentJPA1b.isPayment());
        assertEquals(paymentJPA1.getAccountIdValue(), paymentJPA1b.getAccountIdValue());
        assertEquals(paymentJPA1.getAmount(), paymentJPA1b.getAmount());
        assertEquals(paymentJPA1.getDate(), paymentJPA1b.getDate());
        assertEquals(paymentJPA1.getTransactionDescription(), paymentJPA1b.getTransactionDescription());
        assertEquals(paymentJPA1.getTransactionId(), paymentJPA1b.getTransactionId());
        assertEquals(paymentJPA1, paymentJPA1a);
        assertEquals(paymentJPA1, paymentJPA1b);
        assertSame(paymentJPA1, paymentJPA1a);
        assertNotSame(paymentJPA1, paymentJPA1b);
        assertEquals(paymentJPA1.hashCode(), paymentJPA1a.hashCode());
        assertEquals(transactionJPA.hashCode(), paymentJPA1.hashCode());
        assertNotEquals(transactionJPA.hashCode(), paymentJPA2.hashCode());
        assertEquals(paymentJPA1.getDestinationEntityJPA(), paymentJPA1a.getDestinationEntityJPA());
        assertEquals(paymentJPA1.hashCode(), paymentJPA1b.hashCode());
        assertEquals(paymentJPA1.getDestinationEntityJPA(), paymentJPA1b.getDestinationEntityJPA());
        assertNotEquals(paymentJPA1, paymentJPA2);
        assertNotEquals(transactionJPA1, transactionDateJPA2);
        assertNotEquals(transactionJPA.getAccountIdValue(), transactionJPA1.getAccountIdValue());
        assertNotEquals(paymentJPA1.hashCode(), paymentJPA2.hashCode());
        assertNotEquals(paymentJPA1.getDestinationEntityJPA(), paymentJPA2.getDestinationEntityJPA());
        assertNotEquals(paymentJPA1a.getDestinationEntityJPA(), paymentJPA2.getDestinationEntityJPA());
        assertNotEquals(paymentJPA1b.getDestinationEntityJPA(), paymentJPA2.getDestinationEntityJPA());
        assertNotEquals(0, paymentJPA1.hashCode());
        assertNotEquals(null, paymentJPA1);
        assertFalse(paymentJPA1.equals(null));
        assertFalse(paymentJPA1.equals(paymentJPA2));
        assertTrue(paymentJPA1.equals(paymentJPA1b));
    }
}
