package switchtwentytwenty.project.dto.account;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.account.PersonalBankAccount;
import switchtwentytwenty.project.domain.model.shared.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountListMapperTest {

    AccountListMapper mapper = new AccountListMapper();

    @Test
    void toDTOTest() {
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

        //Act
        AccountListDTO accountListDTO = mapper.toDTO(bankAccountList, cashAccountList);

        //Assert
        assertNotNull(accountListDTO);
    }

    @Test
    void toDTONullCashAccountTest() {

        List<PersonalBankAccount> bankAccountList = new ArrayList<>();
        InitialAmountValue initialAmountValue1 = new InitialAmountValue(100, Currency.EUR);
        Description accountDescription1 = new Description("my bank account");
        Provider provider = new Provider("Bank");
        PersonalBankAccount bankAccount = new PersonalBankAccount(initialAmountValue1, accountDescription1, provider);
        bankAccountList.add(bankAccount);

        //Assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> mapper.toDTO(bankAccountList, null));
    }

    @Test
    void toDTONullBankAccountTest() {

        List<CashAccount> cashAccountList = new ArrayList<>();
        InitialAmountValue initialAmountValue = new InitialAmountValue(50.30, Currency.EUR);
        Description accountDescription = new Description("Family cash account.");
        CashAccount account = new CashAccount(initialAmountValue, accountDescription);
        cashAccountList.add(account);

        //Assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> mapper.toDTO( null, cashAccountList));
    }
}
