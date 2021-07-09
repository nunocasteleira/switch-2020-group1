package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitialAmountValueJPATest {

    @Test
    void getAmount() {
        double expected = 100.00;
        AmountJPA initialAmountValueJPA = new AmountJPA(expected, 1);
        double result;

        result = initialAmountValueJPA.getAmount();

        assertEquals(expected, result);
    }
    @Test
    void getCurrency() {
        int expected = 4;
        AmountJPA initialAmountValueJPA = new AmountJPA(20, 4);

        int result = initialAmountValueJPA.getCurrency();

        assertEquals(expected, result);
    }

    @Test
    void testEquals() {
        double amount = 100.00;
        double differentAmount = 200.00;
        AmountJPA initialAmountValueJPA = new AmountJPA(amount, 1);
        AmountJPA initialAmountValueJPASame = initialAmountValueJPA;
        AmountJPA initialAmountValueJPAOther = new AmountJPA(amount, 1);
        AmountJPA initialAmountValueJPADifferentAmount = new AmountJPA(differentAmount, 1);
        AmountJPA initialAmountValueJPADifferentCurrency = new AmountJPA(amount, 2);

        assertEquals(initialAmountValueJPA, initialAmountValueJPASame);
        assertSame(initialAmountValueJPA, initialAmountValueJPASame);
        assertEquals(initialAmountValueJPA.hashCode(), initialAmountValueJPASame.hashCode());
        assertEquals(initialAmountValueJPA, initialAmountValueJPAOther);
        assertNotSame(initialAmountValueJPA, initialAmountValueJPAOther);
        assertEquals(initialAmountValueJPA.hashCode(), initialAmountValueJPAOther.hashCode());
        assertNotEquals(0, initialAmountValueJPA.hashCode());
        assertNotEquals(initialAmountValueJPA, initialAmountValueJPADifferentAmount);
        assertNotEquals(initialAmountValueJPA, amount);
        assertNotEquals(null, initialAmountValueJPA);
        assertFalse(initialAmountValueJPA.equals(null));
        assertNotEquals(initialAmountValueJPA, initialAmountValueJPADifferentCurrency);
        assertEquals(initialAmountValueJPA.getAmount(), initialAmountValueJPADifferentCurrency.getAmount());
        assertNotEquals(initialAmountValueJPA.getCurrency(), initialAmountValueJPADifferentCurrency.getCurrency());
        assertNotEquals(initialAmountValueJPA.hashCode(), initialAmountValueJPADifferentCurrency.hashCode());
        assertFalse(initialAmountValueJPA.equals(initialAmountValueJPADifferentCurrency));
    }

    @Test
    void testNoArgsConstructor() {
        AmountJPA initialAmountValueJPA = new AmountJPA();

        assertNotNull(initialAmountValueJPA);
    }
}