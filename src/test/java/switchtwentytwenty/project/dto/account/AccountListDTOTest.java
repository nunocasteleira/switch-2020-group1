package switchtwentytwenty.project.dto.account;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.account.PersonalBankAccount;
import switchtwentytwenty.project.domain.model.shared.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AccountListDTOTest {

    @Test
    void getAccountList() {

        //Arrange
        List<CashAccount> cashAccountList = new ArrayList<>();

        AccountId accountId = new AccountId(0);
        switchtwentytwenty.project.domain.model.shared.Currency EUR = switchtwentytwenty.project.domain.model.shared.Currency.EUR;
        InitialAmountValue initialAmountValue = new InitialAmountValue(50.30, EUR);
        Description accountDescription = new Description("Family cash account.");
        CashAccount account = new CashAccount(initialAmountValue, accountDescription);

        cashAccountList.add(account);

        List<PersonalBankAccount> bankAccountList = new ArrayList<>();

        InitialAmountValue initialAmountValue1 = new InitialAmountValue(100, Currency.EUR);
        Description accountDescription1 = new Description("my bank account");
        Provider provider = new Provider("Bank");

        PersonalBankAccount bankAccount = new PersonalBankAccount(initialAmountValue1, accountDescription1, provider);

        bankAccountList.add(bankAccount);


        //Act
        AccountListDTO accountListDTO = new AccountListDTO(bankAccountList, cashAccountList);
        List<PersonalBankAccount> actualBank = accountListDTO.getPersonalBankAccount();
        List<CashAccount> actualCash = accountListDTO.getCashAccountList();
        AccountListDTO accountListDTO1 = new AccountListDTO(actualBank, actualCash);



        //Assert
        assertNotNull(accountListDTO);
        assertEquals(accountListDTO, accountListDTO1);
    }

    @Test
    void testEquals() {
        //Arrange

        //Arrange
        List<CashAccount> cashAccountList = new ArrayList<>();
        InitialAmountValue initialAmountValue = new InitialAmountValue(50.30, Currency.EUR);
        Description accountDescription = new Description("Family cash account.");
        CashAccount account = new CashAccount(initialAmountValue, accountDescription);
        cashAccountList.add(account);

        List<PersonalBankAccount> bankAccountList = new ArrayList<>();
        InitialAmountValue initialAmountValue1 = new InitialAmountValue(100, Currency.EUR);
        Description accountDescription1 = new Description("my bank account");
        Provider provider = new Provider("Bank");
        PersonalBankAccount bankAccount = new PersonalBankAccount(initialAmountValue1, accountDescription1, provider);
        bankAccountList.add(bankAccount);

        List<CashAccount> cashAccountList1 = new ArrayList<>();
        InitialAmountValue initialAmountValue2 = new InitialAmountValue(12.30, Currency.EUR);
        Description accountDescription2 = new Description("Family cash account.");
        CashAccount account1 = new CashAccount(initialAmountValue2, accountDescription2);
        cashAccountList1.add(account1);

        List<PersonalBankAccount> bankAccountList1 = new ArrayList<>();
        InitialAmountValue initialAmountValue3 = new InitialAmountValue(12, Currency.EUR);
        Description accountDescription3 = new Description("my bank account");
        Provider provider1 = new Provider("Bank");
        PersonalBankAccount bankAccount1 = new PersonalBankAccount(initialAmountValue3, accountDescription3, provider1);
        bankAccountList1.add(bankAccount1);


        //Act
        AccountListDTO accountListDTO = new AccountListDTO(bankAccountList, cashAccountList);
        AccountListDTO accountListDTOSame = accountListDTO;
        AccountListDTO accountListDTOOther = new AccountListDTO(bankAccountList, cashAccountList);
        AccountListDTO accountListDTODiffBank = new AccountListDTO(bankAccountList1, cashAccountList);
        AccountListDTO accountListDTODiffCash = new AccountListDTO(bankAccountList, cashAccountList1);


        //Assert
        assertNotNull(accountListDTO);
        assertEquals(accountListDTO, accountListDTOSame);
        assertSame(accountListDTO, accountListDTOSame);
        assertEquals(accountListDTO.hashCode(), accountListDTOSame.hashCode());
        assertNotEquals(0, accountListDTO.hashCode());
        assertEquals(accountListDTO, accountListDTOOther);
        assertNotSame(accountListDTO, accountListDTOOther);
        assertEquals(accountListDTO.hashCode(), accountListDTOOther.hashCode());
        assertFalse(accountListDTO.equals(bankAccountList1));
        assertFalse(accountListDTO.equals(cashAccountList1));
        assertTrue(accountListDTO.equals(accountListDTOOther));
        assertNotEquals(accountListDTO, bankAccountList);
        assertNotEquals(accountListDTO, cashAccountList);
        assertNotEquals(accountListDTO,bankAccountList1);
        assertNotEquals(accountListDTO,cashAccountList1);
        assertNotEquals(accountListDTO,accountListDTODiffBank);
        assertNotEquals(accountListDTO,accountListDTODiffCash);
        assertNotEquals(accountListDTO.hashCode(),accountListDTODiffBank.hashCode());
        assertNotEquals(accountListDTO.hashCode(),accountListDTODiffCash.hashCode());
    }
}
