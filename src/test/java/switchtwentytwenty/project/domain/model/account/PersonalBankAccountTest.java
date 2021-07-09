package switchtwentytwenty.project.domain.model.account;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBankAccountTest {

    @Test
    void ensureAccountIsCreated() {
        InitialAmountValue initialAmountValue = new InitialAmountValue(100, Currency.EUR);
        Description accountDescription = new Description("my bank account");
        Provider provider = new Provider("Bank");

        PersonalBankAccount result = new PersonalBankAccount(initialAmountValue, accountDescription, provider);

        assertNotNull(result);
    }

    @Test
    void ensureAccountIsNotCreatedWithNullAmount() {
        InitialAmountValue initialAmountValue = null;
        Description accountDescription = new Description("my bank account");
        Provider provider = new Provider("Bank");

        assertThrows(IllegalArgumentException.class, () -> new PersonalBankAccount(initialAmountValue, accountDescription, provider));
    }

    @Test
    void ensureAccountIsNotCreatedWithNullDescription() {
        InitialAmountValue initialAmountValue = new InitialAmountValue(100, Currency.EUR);
        Description accountDescription = null;
        Provider provider = new Provider("Bank");

        assertThrows(IllegalArgumentException.class, () -> new PersonalBankAccount(initialAmountValue, accountDescription, provider));
    }

    @Test
    void ensureAccountIsNotCreatedWithNullProvider() {
        InitialAmountValue initialAmountValue = new InitialAmountValue(100, Currency.EUR);
        Description accountDescription = new Description("my bank account");
        Provider provider = null;

        assertThrows(IllegalArgumentException.class, () -> new PersonalBankAccount(initialAmountValue, accountDescription, provider));
    }

    @Test
    void ensureEquals() {
        InitialAmountValue initialAmountValue = new InitialAmountValue(100, Currency.EUR);
        Description accountDescription = new Description("my bank account");
        Provider provider = new Provider("Bank");

        PersonalBankAccount result = new PersonalBankAccount(initialAmountValue, accountDescription, provider);
        PersonalBankAccount equal = result;
        PersonalBankAccount result2 = new PersonalBankAccount(initialAmountValue, accountDescription, new Provider("Else"));
        PersonalBankAccount result3 = new PersonalBankAccount(initialAmountValue, new Description("description"), provider);
        PersonalBankAccount result4 = new PersonalBankAccount(new InitialAmountValue(9, Currency.EUR), accountDescription, provider);

        assertEquals(result, equal);
        assertEquals(result.hashCode(), equal.hashCode());
        assertNotEquals(result, result3);
        assertNotEquals(result, result4);
        assertNotEquals(null, result);
        assertNotEquals(result.hashCode(), result3.hashCode());
        assertEquals(result.getProvider(), equal.getProvider());
        assertFalse(result.hasId(new AccountId(12412L)));
        assertNotEquals(result.getProvider(), result2.getProvider());
    }
}