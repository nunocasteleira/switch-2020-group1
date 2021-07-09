package switchtwentytwenty.project.repositories;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.datamodel.account.CashAccountJPA;
import switchtwentytwenty.project.datamodel.assembler.AccountDomainDataAssembler;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.repositories.irepositories.IAccountRepositoryJPA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@Profile("AccountRepositoryTest")
public class AccountRepositoryTest {
    @Mock
    IAccountRepositoryJPA iAccountRepositoryJPA;
    @Mock
    AccountDomainDataAssembler accountDomainDataAssembler;
    @Mock
    CashAccountJPA cashAccountJPA;
    @Mock
    CashAccount cashAccount;
    @InjectMocks
    AccountRepository accountRepository;


    /*@Test
    void saveCashAccountSuccessfully() {
        Currency EUR = Currency.EUR;
        InitialAmountValue initialAmountValue = new InitialAmountValue(10.50, EUR);
        Description accountDescription = new Description("Family cash account.");
        CashAccount aCashAccount = new CashAccount(initialAmountValue, accountDescription);

        when(accountsDomainDataAssembler.cashAccountToData(aCashAccount)).thenReturn(cashAccountJPA);
        when(iAccountRepositoryJPA.save(isA(CashAccountJPA.class))).thenReturn(cashAccountJPA);
        when(accountsDomainDataAssembler.fromDataToInitialAmountValue(10.50, 1)).thenReturn(initialAmountValue);
        when(accountsDomainDataAssembler.fromDataToAccountDescription("Family cash account.")).thenReturn(accountDescription);
        lenient().doNothing().when(cashAccount).setAccountId(isA(AccountId.class));

        CashAccount result = accountRepository.saveCashAccount(aCashAccount);

        assertNotNull(result);
        assertEquals(0, aCashAccount.getAccountId(). getAccountIdNumber());
        assertEquals(0, result.getAccountId(). getAccountIdNumber());
    }*/

}
