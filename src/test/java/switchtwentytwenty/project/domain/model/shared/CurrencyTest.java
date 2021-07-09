package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    @Test
    void convertNumberToCurrency_EUR() {
        int currencyNumber = 1;
        Currency expected = Currency.EUR;

        Currency result = Currency.convertNumberToCurrency(currencyNumber);

        assertEquals(expected, result);
    }

    @Test
    void convertNumberToCurrency_USD() {
        int currencyNumber = 2;
        Currency expected = Currency.USD;

        Currency result = Currency.convertNumberToCurrency(currencyNumber);

        assertEquals(expected, result);
    }

    @Test
    void convertNumberToCurrency_GBP() {
        int currencyNumber = 3;
        Currency expected = Currency.GBP;

        Currency result = Currency.convertNumberToCurrency(currencyNumber);

        assertEquals(expected, result);
    }

    @Test
    void convertNumberToCurrency_BRL() {
        int currencyNumber = 4;
        Currency expected = Currency.BRL;

        Currency result = Currency.convertNumberToCurrency(currencyNumber);

        assertEquals(expected, result);
    }

    @Test
    void convertNumberToCurrency_ValueZero() {
        int currencyNumber = 0;

        assertThrows(IllegalArgumentException.class,
                () -> Currency.convertNumberToCurrency(currencyNumber));
    }

    @Test
    void convertNumberToCurrency_NonExistent() {
        int currencyNumber = 5;

        assertThrows(IllegalArgumentException.class,
                () -> Currency.convertNumberToCurrency(currencyNumber));
    }

}