package switchtwentytwenty.project.datamodel.account;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.shared.DescriptionJPA;
import switchtwentytwenty.project.datamodel.shared.AmountJPA;

import static org.junit.jupiter.api.Assertions.*;

class CashAccountJPATest {

    @Test
    void createCashAccountJPA() {
        long expectedAccountId = 0;
        AmountJPA initialAmountValueJPA = new AmountJPA(50, 1);
        DescriptionJPA accountDescriptionJPA = new DescriptionJPA("Family cash account.");
        CashAccountJPA cashAccountJPA = new CashAccountJPA(initialAmountValueJPA, accountDescriptionJPA);

        assertNotNull(cashAccountJPA);
        assertEquals(expectedAccountId, cashAccountJPA.getAccountId());
        assertNotEquals(-20, cashAccountJPA.getAccountId());
        assertNotEquals(500, cashAccountJPA.getAccountId());
        assertEquals(initialAmountValueJPA, cashAccountJPA.getInitialAmountValue());
        assertEquals(initialAmountValueJPA.getAmount(), cashAccountJPA.getInitialAmountValue().getAmount());
        assertEquals(initialAmountValueJPA.getCurrency(), cashAccountJPA.getInitialAmountValue().getCurrency());
        assertEquals(accountDescriptionJPA, cashAccountJPA.getAccountDescription());
        assertEquals(expectedAccountId, cashAccountJPA.getAccountId());
        assertNotNull(cashAccountJPA.getAccountId());
    }

    @Test
    void test_EqualsAndHashcode() {
        AmountJPA initialAmountValueJPA1 = new AmountJPA(50, 1);
        AmountJPA initialAmountValueJPA2 = new AmountJPA(10, 1);
        AmountJPA initialAmountValueJPA3 = new AmountJPA(50, 3);
        DescriptionJPA accountDescriptionJPA1 = new DescriptionJPA("Family cash account.");
        DescriptionJPA accountDescriptionJPA2 = new DescriptionJPA("The cash account.");

        CashAccountJPA cashAccountJPA1 = new CashAccountJPA(initialAmountValueJPA1, accountDescriptionJPA1);
        CashAccountJPA cashAccountJPA1b = cashAccountJPA1;
        CashAccountJPA cashAccountJPA2 = new CashAccountJPA(initialAmountValueJPA1, accountDescriptionJPA1);
        CashAccountJPA cashAccountJPADifferentAmount = new CashAccountJPA(initialAmountValueJPA2, accountDescriptionJPA1);
        CashAccountJPA cashAccountJPADifferentCurrency = new CashAccountJPA(initialAmountValueJPA3, accountDescriptionJPA1);
        CashAccountJPA cashAccountJPADifferentDescription = new CashAccountJPA(initialAmountValueJPA1, accountDescriptionJPA2);

        assertNotNull(cashAccountJPA1);
        assertEquals(cashAccountJPA1, cashAccountJPA2);
        assertEquals(cashAccountJPA1, cashAccountJPA1b);
        assertNotSame(cashAccountJPA1, cashAccountJPA2);
        assertEquals(cashAccountJPA1.getAccountId(), cashAccountJPA1b.getAccountId());
        assertEquals(cashAccountJPA1.getInitialAmountValue(), cashAccountJPA1b.getInitialAmountValue());
        assertEquals(cashAccountJPA1.getInitialAmountValue().getAmount(), cashAccountJPA1b.getInitialAmountValue().getAmount());
        assertEquals(cashAccountJPA1.getInitialAmountValue().getCurrency(), cashAccountJPA1b.getInitialAmountValue().getCurrency());
        assertEquals(cashAccountJPA1.getAccountDescription(), cashAccountJPA1b.getAccountDescription());
        assertEquals(cashAccountJPA1.hashCode(), cashAccountJPA2.hashCode());
        assertTrue(cashAccountJPA1.equals(cashAccountJPA2));
        assertEquals(cashAccountJPA1.getAccountId(), cashAccountJPA2.getAccountId());
        assertEquals(cashAccountJPA1.getInitialAmountValue(), cashAccountJPA2.getInitialAmountValue());
        assertEquals(cashAccountJPA1.getInitialAmountValue().getAmount(), cashAccountJPA2.getInitialAmountValue().getAmount());
        assertEquals(cashAccountJPA1.getInitialAmountValue().getCurrency(), cashAccountJPA2.getInitialAmountValue().getCurrency());
        assertEquals(cashAccountJPA1.getAccountDescription(), cashAccountJPA2.getAccountDescription());
        assertNotEquals(null, cashAccountJPA1b);
        assertNotEquals(cashAccountJPA1, cashAccountJPADifferentAmount);
        assertNotEquals(cashAccountJPA1.hashCode(), cashAccountJPADifferentAmount.hashCode());
        assertNotEquals(cashAccountJPA1.getInitialAmountValue().getAmount(), cashAccountJPADifferentAmount.getInitialAmountValue().getAmount());
        assertFalse(cashAccountJPA1.equals(cashAccountJPADifferentAmount));
        assertNotEquals(cashAccountJPA1, cashAccountJPADifferentCurrency);
        assertNotEquals(cashAccountJPA1.hashCode(), cashAccountJPADifferentCurrency.hashCode());
        assertNotEquals(cashAccountJPA1.getInitialAmountValue().getCurrency(), cashAccountJPADifferentCurrency.getInitialAmountValue().getCurrency());
        assertFalse(cashAccountJPA1.equals(cashAccountJPADifferentCurrency));
        assertNotEquals(cashAccountJPA1, cashAccountJPADifferentDescription);
        assertNotEquals(cashAccountJPA1.hashCode(), cashAccountJPADifferentDescription.hashCode());
        assertNotEquals(cashAccountJPA1.getAccountDescription(), cashAccountJPADifferentDescription.getAccountDescription());
        assertFalse(cashAccountJPA1.equals(cashAccountJPADifferentDescription));
        assertFalse(cashAccountJPA1.equals(null));
        assertFalse(cashAccountJPA1.equals(initialAmountValueJPA1));
        assertFalse(cashAccountJPA1.equals(accountDescriptionJPA1));
        assertNotEquals(0, cashAccountJPADifferentAmount.hashCode());
        assertNotEquals(0, cashAccountJPADifferentCurrency.hashCode());
        assertNotEquals(0, cashAccountJPADifferentDescription.hashCode());
        assertEquals(0, cashAccountJPA1.getAccountId());
    }

    @Test
    void testNoArgsConstructor() {
        CashAccountJPA cashAccountJPA = new CashAccountJPA();

        assertNotNull(cashAccountJPA);
    }

}