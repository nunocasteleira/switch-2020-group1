package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.exceptions.InvalidAmountException;

import static org.junit.jupiter.api.Assertions.*;

class TransactionAmountTest {

    @Test
    void createTransactionAmountSuccessfully() {
        Currency EUR = Currency.EUR;
        TransactionAmount result1 = new TransactionAmount(-10.50, EUR);
        TransactionAmount result2 = new TransactionAmount(-10.50, EUR);
        TransactionAmount result3 = new TransactionAmount(-8.0, EUR);

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);
        assertEquals(result1, result2);
        assertNotEquals(result1, result3);
        assertNotSame(result1, result2);
        assertNotSame(result1, result3);
    }


    @Test
    void createInitialAmountSuccessfully_AmountWithTwoDecimals() {
        Currency EUR = Currency.EUR;

        TransactionAmount result = new TransactionAmount(-1.10, EUR);

        assertNotNull(result);
    }

    @Test
    void createInitialAmountSuccessfully_AmountWithOneDecimal() {
        Currency EUR = Currency.EUR;

        TransactionAmount result = new TransactionAmount(-1.5, EUR);

        assertNotNull(result);
    }

    @Test
    void createInitialAmountSuccessfully_AmountWithNoDecimals() {
        Currency EUR = Currency.EUR;

        TransactionAmount result = new TransactionAmount(-5, EUR);

        assertNotNull(result);
    }

    @Test
    void createInitialAmountSuccessfully_AmountMoreDecimals() {
        Currency EUR = Currency.EUR;
        double amount = -5.0000000;

        TransactionAmount result = new TransactionAmount(amount, EUR);

        assertNotNull(result);
        assertEquals(-5, result.getAmount());
    }

    @Test
    void createInitialAmountSuccessfully_AmountWithThreeDecimalsButValid() {
        Currency EUR = Currency.EUR;
        double amount = -1.520;

        TransactionAmount result = new TransactionAmount(amount, EUR);

        assertNotNull(result);
        assertEquals(-1.52, result.getAmount());
    }

/*    @Test
    void failToCreateTransactionAmount_AmountZero() {
        Currency EUR = Currency.EUR;
        double transactionAmount = 0;

        assertThrows(InvalidAmountException.class, () -> new TransactionAmount(transactionAmount, EUR));
    }

    @Test
    void failToCreateInitialAmount_PositiveValue() {
        Currency EUR = Currency.EUR;
        assertThrows(InvalidAmountException.class, () -> new TransactionAmount(15.2, EUR));
    }*/

    @Test
    void failToCreateInitialAmount_InvalidAmountThreeDecimals() {
        Currency EUR = Currency.EUR;
        assertThrows(InvalidAmountException.class, () -> new TransactionAmount(-12.555, EUR));
    }

    @Test
    void failToCreateInitialAmount_InvalidAmountFourDecimals() {
        Currency EUR = Currency.EUR;
        assertThrows(InvalidAmountException.class, () -> new TransactionAmount(-12.1424, EUR));
    }

    @Test
    void testEquals() {
        double amount = -10;
        Currency EUR = Currency.EUR;
        Currency USD = Currency.USD;
        TransactionAmount transactionAmount = new TransactionAmount(amount, EUR);
        TransactionAmount transactionAmountSame = transactionAmount;
        TransactionAmount transactionAmountOther = new TransactionAmount(amount, EUR);
        TransactionAmount transactionAmountOtherCurrency = new TransactionAmount(amount, USD);
        TransactionAmount transactionAmountOtherAmount = new TransactionAmount(-20, EUR);

        assertEquals(transactionAmount, transactionAmountSame);
        assertSame(transactionAmount, transactionAmountSame);
        assertTrue(transactionAmount.equals(transactionAmountSame));
        assertTrue(transactionAmount.equals(transactionAmountOther));
        assertEquals(transactionAmount.hashCode(), transactionAmountSame.hashCode());
        assertEquals(transactionAmount.getAmount(), transactionAmountSame.getAmount());
        assertEquals(transactionAmount.getCurrency(), transactionAmountSame.getCurrency());
        assertEquals(transactionAmount, transactionAmountOther);
        assertNotSame(transactionAmount, transactionAmountOther);
        assertEquals(transactionAmount.hashCode(), transactionAmountOther.hashCode());
        assertEquals(transactionAmount.getAmount(), transactionAmountOther.getAmount());
        assertEquals(transactionAmount.getCurrency(), transactionAmountOther.getCurrency());
        assertFalse(transactionAmount.equals(transactionAmountOtherCurrency));
        assertFalse(transactionAmount.equals(transactionAmountOtherAmount));
        assertNotEquals(transactionAmount, amount);
        assertNotEquals(transactionAmount, EUR);
        assertNotEquals(null, transactionAmount);
        assertNotEquals(0, transactionAmount.hashCode());
        assertNotEquals(transactionAmount, transactionAmountOtherAmount);
        assertNotEquals(transactionAmount.hashCode(), transactionAmountOtherAmount.hashCode());
        assertEquals(transactionAmount.getCurrency(), transactionAmountOtherAmount.getCurrency());
        assertNotEquals(transactionAmount.getAmount(), transactionAmountOtherAmount.getAmount());
        assertNotEquals(transactionAmount, USD);
        assertNotEquals(transactionAmount.hashCode(), transactionAmountOtherCurrency.hashCode());
        assertEquals(transactionAmount.getAmount(), transactionAmountOtherCurrency.getAmount());
        assertNotEquals(transactionAmount.getCurrency(), transactionAmountOtherCurrency.getCurrency());
    }

    @Test
    void getAmount() {
        double expected = -10;
        Currency EUR = Currency.EUR;
        TransactionAmount transactionAmount = new TransactionAmount(expected, EUR);

        double result = transactionAmount.getAmount();

        assertEquals(expected, result);
    }

    @Test
    void getCurrency() {
        Currency expected = Currency.GBP;

        Currency GBP = Currency.convertNumberToCurrency(3);
        TransactionAmount transactionAmount = new TransactionAmount(-10, GBP);

        Currency result = transactionAmount.getCurrency();

        assertEquals(expected, result);
        assertEquals(expected.getCurrencyNumber(), result.getCurrencyNumber());
    }
}
