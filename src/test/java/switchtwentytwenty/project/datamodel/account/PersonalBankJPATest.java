package switchtwentytwenty.project.datamodel.account;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.shared.AmountJPA;
import switchtwentytwenty.project.datamodel.shared.DescriptionJPA;
import switchtwentytwenty.project.datamodel.shared.ProviderJPA;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBankJPATest {

    @Test
    void ensurePersonalBankIsCreatedAndGetterIsWorking() {
        AmountJPA initialAmountValue = new AmountJPA(10, 1);
        DescriptionJPA descriptionJPA = new DescriptionJPA("My bank");
        ProviderJPA providerJPA = new ProviderJPA("bank");

        PersonalBankJPA personalBankAccount = new PersonalBankJPA(initialAmountValue, descriptionJPA, providerJPA);

        assertNotNull(personalBankAccount);
        assertEquals(new ProviderJPA("bank"), personalBankAccount.getProvider());
    }

    @Test
    void ensureEqualsAndHash() {
        AmountJPA initialAmountValue = new AmountJPA(10, 1);
        DescriptionJPA descriptionJPA = new DescriptionJPA("My bank");
        ProviderJPA providerJPA = new ProviderJPA("bank");

        PersonalBankJPA personalBankAccount = new PersonalBankJPA(initialAmountValue, descriptionJPA, providerJPA);
        PersonalBankJPA same = personalBankAccount;
        PersonalBankJPA different = new PersonalBankJPA(initialAmountValue, descriptionJPA, new ProviderJPA("else"));
        CashAccountJPA anotherType = new CashAccountJPA(initialAmountValue, descriptionJPA);

        assertEquals(personalBankAccount, same);
        assertEquals(personalBankAccount.hashCode(), same.hashCode());
        assertNotEquals(personalBankAccount, different);
        assertNotEquals(personalBankAccount.hashCode(), different.hashCode());
        assertEquals(personalBankAccount.getProvider(), same.getProvider());
        assertNotEquals(personalBankAccount.getProvider(), different.getProvider());
        assertEquals(anotherType, personalBankAccount);
        assertNotEquals(initialAmountValue, personalBankAccount);
    }

}