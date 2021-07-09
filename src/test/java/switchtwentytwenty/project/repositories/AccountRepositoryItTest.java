package switchtwentytwenty.project.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import switchtwentytwenty.project.applicationservices.implservices.FamilyAccountService;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.shared.Description;
import switchtwentytwenty.project.domain.model.shared.Currency;
import switchtwentytwenty.project.domain.model.shared.InitialAmountValue;
import switchtwentytwenty.project.repositories.irepositories.IAccountRepositoryJPA;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountRepositoryItTest {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    IAccountRepositoryJPA accountRepositoryJPA;
    @Autowired
    FamilyAccountService familyAccountService;

    @Test
    void saveFamilyCashAccountSuccessfully() {
        double amount = 10;
        Currency EUR = Currency.EUR;
        InitialAmountValue initialAmountValue = new InitialAmountValue(amount, EUR);
        String description = "Family cash account.";
        Description accountDescription = new Description(description);
        CashAccount cashAccount = new CashAccount(initialAmountValue, accountDescription);
        long accountIdBeforeSave = 0;


        CashAccount savedFamilyCashAccount = accountRepository.saveCashAccount(cashAccount);

        assertEquals(accountIdBeforeSave, cashAccount.getAccountId().getAccountIdNumber());
        assertEquals(initialAmountValue, savedFamilyCashAccount.getInitialAmountValue());
        assertEquals(amount, savedFamilyCashAccount.getInitialAmountValue().getAmount());
        assertEquals(EUR, savedFamilyCashAccount.getInitialAmountValue().getCurrency());
        assertEquals(accountDescription, savedFamilyCashAccount.getAccountDescription());
        assertEquals(description, savedFamilyCashAccount.getAccountDescription().getAccountDescription());
    }
}