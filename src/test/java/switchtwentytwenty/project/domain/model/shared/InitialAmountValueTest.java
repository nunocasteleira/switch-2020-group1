package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.exceptions.InvalidAmountException;

import static org.junit.jupiter.api.Assertions.*;

class InitialAmountValueTest {

    @Test
    void createInitialAmountSuccessfully() {
        Currency EUR = Currency.EUR;
        InitialAmountValue result1 = new InitialAmountValue(10.50, EUR);
        InitialAmountValue result2 = new InitialAmountValue(10.50, EUR);
        InitialAmountValue result3 = new InitialAmountValue(8.0, EUR);

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);
        assertEquals(result1, result2);
        assertNotEquals(result1, result3);
        assertNotSame(result1, result2);
        assertNotSame(result1, result3);
    }

    @Test
    void createInitialAmountSuccessfully_AmountZero() {
        Currency EUR = Currency.EUR;
        double initialAmount = 0;

        InitialAmountValue result = new InitialAmountValue(initialAmount, EUR);

        assertNotNull(result);
        assertEquals(initialAmount, result.getAmount());
        assertEquals(EUR, result.getCurrency());
    }
    @Test
    void createInitialAmountSuccessfully_AmountWithTwoDecimals() {
        Currency EUR = Currency.EUR;

        InitialAmountValue result = new InitialAmountValue(1.10, EUR);

        assertNotNull(result);
    }
    @Test
    void createInitialAmountSuccessfully_AmountWithOneDecimal() {
        Currency EUR = Currency.EUR;

        InitialAmountValue result = new InitialAmountValue(1.5, EUR);

        assertNotNull(result);
    }
    @Test
    void createInitialAmountSuccessfully_AmountWithNoDecimals() {
        Currency EUR = Currency.EUR;

        InitialAmountValue result = new InitialAmountValue(5, EUR);

        assertNotNull(result);
    }
    @Test
    void createInitialAmountSuccessfully_AmountMoreDecimals() {
        Currency EUR = Currency.EUR;
        double amount = 5.0000000;

        InitialAmountValue result = new InitialAmountValue(amount, EUR);

        assertNotNull(result);
        assertEquals(5, result.getAmount());
    }

    @Test
    void createInitialAmountSuccessfully_AmountWithThreeDecimalsButValid() {
        Currency EUR = Currency.EUR;
        double amount = 1.520;

        InitialAmountValue result = new InitialAmountValue(amount, EUR);

        assertNotNull(result);
        assertEquals(1.52, result.getAmount());
    }

    @Test
    void failToCreateInitialAmount_NegativeValue() {
        Currency EUR = Currency.EUR;
        assertThrows(InvalidAmountException.class, () -> new InitialAmountValue(-15.2, EUR));
    }

    @Test
    void failToCreateInitialAmount_InvalidAmountThreeDecimals() {
        Currency EUR = Currency.EUR;
        assertThrows(InvalidAmountException.class, () -> new InitialAmountValue(12.555, EUR));
    }

    @Test
    void failToCreateInitialAmount_InvalidAmountFourDecimals() {
        Currency EUR = Currency.EUR;
        assertThrows(InvalidAmountException.class, () -> new InitialAmountValue(12.1424, EUR));
    }

    @Test
    void testEquals() {
        double amount = 10;
        Currency EUR = Currency.EUR;
        Currency USD = Currency.USD;
        InitialAmountValue initialAmountValue = new InitialAmountValue(amount, EUR);
        InitialAmountValue initialAmountValueSame = initialAmountValue;
        InitialAmountValue initialAmountValueOther = new InitialAmountValue(amount, EUR);
        InitialAmountValue initialAmountValueOtherCurrency = new InitialAmountValue(amount, USD);
        InitialAmountValue initialAmountValueOtherAmount = new InitialAmountValue(20, EUR);

        assertEquals(initialAmountValue, initialAmountValueSame);
        assertSame(initialAmountValue, initialAmountValueSame);
        assertTrue(initialAmountValue.equals(initialAmountValueSame));
        assertTrue(initialAmountValue.equals(initialAmountValueOther));
        assertEquals(initialAmountValue.hashCode(), initialAmountValueSame.hashCode());
        assertEquals(initialAmountValue.getAmount(), initialAmountValueSame.getAmount());
        assertEquals(initialAmountValue.getCurrency(), initialAmountValueSame.getCurrency());
        assertEquals(initialAmountValue, initialAmountValueOther);
        assertNotSame(initialAmountValue, initialAmountValueOther);
        assertEquals(initialAmountValue.hashCode(), initialAmountValueOther.hashCode());
        assertEquals(initialAmountValue.getAmount(), initialAmountValueOther.getAmount());
        assertEquals(initialAmountValue.getCurrency(), initialAmountValueOther.getCurrency());
        assertFalse(initialAmountValue.equals(initialAmountValueOtherCurrency));
        assertFalse(initialAmountValue.equals(initialAmountValueOtherAmount));
        assertNotEquals(initialAmountValue, amount);
        assertNotEquals(initialAmountValue, EUR);
        assertNotEquals(null, initialAmountValue);
        assertNotEquals(0, initialAmountValue.hashCode());
        assertNotEquals(initialAmountValue, initialAmountValueOtherAmount);
        assertNotEquals(initialAmountValue.hashCode(), initialAmountValueOtherAmount.hashCode());
        assertEquals(initialAmountValue.getCurrency(), initialAmountValueOtherAmount.getCurrency());
        assertNotEquals(initialAmountValue.getAmount(), initialAmountValueOtherAmount.getAmount());
        assertNotEquals(initialAmountValue, USD);
        assertNotEquals(initialAmountValue.hashCode(), initialAmountValueOtherCurrency.hashCode());
        assertEquals(initialAmountValue.getAmount(), initialAmountValueOtherCurrency.getAmount());
        assertNotEquals(initialAmountValue.getCurrency(), initialAmountValueOtherCurrency.getCurrency());
    }

    @Test
    void getAmount() {
        double expected = 10;
        Currency EUR = Currency.EUR;
        InitialAmountValue initialAmountValue = new InitialAmountValue(expected, EUR);

        double result = initialAmountValue.getAmount();

        assertEquals(expected, result);
    }

    @Test
    void getCurrency() {
        Currency expected = Currency.GBP;

        Currency GBP = Currency.convertNumberToCurrency(3);
        InitialAmountValue initialAmountValue = new InitialAmountValue(10, GBP);

        Currency result = initialAmountValue.getCurrency();

        assertEquals(expected, result);
        assertEquals(expected.getCurrencyNumber(), result.getCurrencyNumber());
    }
}