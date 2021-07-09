package switchtwentytwenty.project.domain.model.account;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.exceptions.InvalidAmountException;
import switchtwentytwenty.project.domain.model.shared.Description;
import switchtwentytwenty.project.domain.model.shared.AccountId;
import switchtwentytwenty.project.domain.model.shared.Currency;
import switchtwentytwenty.project.domain.model.shared.InitialAmountValue;

import static org.junit.jupiter.api.Assertions.*;

class CashAccountTest {

    @Test
    void createFamilyCashAccountSuccessfully() {
        AccountId accountId = new AccountId(0);
        Currency EUR = Currency.EUR;
        InitialAmountValue initialAmountValue = new InitialAmountValue(50.30, EUR);
        Description accountDescription = new Description("Family cash account.");
        CashAccount account = new CashAccount(initialAmountValue, accountDescription);
        assertNotNull(account);
        assertTrue(account.hasId(accountId));
        assertEquals(accountId, account.getAccountId());
        assertEquals(initialAmountValue, account.getInitialAmountValue());
    }

    @Test
    void createFamilyCashAccountSuccessfully_WithInitialAmountZero() {
        Currency EUR = Currency.EUR;
        InitialAmountValue initialAmountValue = new InitialAmountValue(0, EUR);
        Description accountDescription = new Description("Family cash account.");
        CashAccount account = new CashAccount(initialAmountValue, accountDescription);
        assertNotNull(account);
    }

    @Test
    void failToCreateFamilyCashAccount_WithNegativeInitialAmount() {
        Currency EUR = Currency.EUR;
        Description accountDescription = new Description("Family cash account.");
        assertThrows(InvalidAmountException.class, () -> new CashAccount(new InitialAmountValue(-30, EUR),accountDescription));
    }

    @Test
    void hasIdTest() {
        double initialAmount = 50.30;
        AccountId accountIdOther = new AccountId(50);
        Currency EUR = Currency.EUR;
        Description accountDescription = new Description("Family cash account.");
        CashAccount account = new CashAccount(new InitialAmountValue(initialAmount, EUR),accountDescription);

        boolean result = account.hasId(accountIdOther);

        assertFalse(result);
    }

    @Test
    void ensureAccountsAreEquals() {
        Currency EUR = Currency.EUR;
        InitialAmountValue initialAmountValue = new InitialAmountValue(50.30, EUR);
        Description accountDescription1 = new Description("Family cash account.");
        Description accountDescription2 = new Description("The account.");
        CashAccount account1 = new CashAccount(initialAmountValue, accountDescription1);
        CashAccount account2 = new CashAccount(initialAmountValue, accountDescription1);
        CashAccount account3 = account1;
        CashAccount differentID = new CashAccount(new InitialAmountValue(50.30, EUR), accountDescription1);
        differentID.setAccountId(new AccountId(20));
        CashAccount differentAmount = new CashAccount(new InitialAmountValue(10, EUR),accountDescription1);
        CashAccount differentDescription = new CashAccount(initialAmountValue, accountDescription2);

        assertEquals(account1, account2);
        assertEquals(account1, account3);
        assertSame(account1, account3);
        assertNotSame(account1, account2);
        assertFalse(account1.equals(initialAmountValue));
        assertNotEquals(35, account1);
        assertEquals(account1.hashCode(), account2.hashCode());
        assertEquals(account1.hashCode(), account3.hashCode());
        assertNotEquals(0, account1.hashCode());
        assertTrue(account1.equals(account1));
        assertNotSame(account1, differentID);
        assertNotEquals(account1, differentID);
        assertFalse(account1.equals(differentID));
        assertFalse(account1.equals(differentAmount));
        assertFalse(account1.equals(differentDescription));
        assertFalse(account1.equals(null));
        assertNotEquals(account1, accountDescription1);
        assertNotSame(account1, differentAmount);
        assertNotEquals(account1, differentAmount);
        assertNotEquals(account1.hashCode(), differentAmount.hashCode());
        assertNotEquals(account1.getInitialAmountValue(), differentAmount.getInitialAmountValue());
        assertNotEquals(account1, differentDescription);
        assertNotEquals(account1.hashCode(), differentDescription.hashCode());
        assertNotEquals(account1.getAccountDescription(), differentDescription.getAccountDescription());
    }
}