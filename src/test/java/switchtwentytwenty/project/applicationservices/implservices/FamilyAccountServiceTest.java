package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import switchtwentytwenty.project.assemblers.AccountDTOAssembler;
import switchtwentytwenty.project.domain.domainservices.FamilyDomainService;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.shared.AccountId;
import switchtwentytwenty.project.domain.model.shared.Currency;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.domain.model.shared.InitialAmountValue;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.repositories.AccountRepository;
import switchtwentytwenty.project.repositories.FamilyRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FamilyAccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    FamilyRepository familyRepository;
    @Mock
    Family family;
    @Mock
    CashAccount cashAccount;
    @Mock
    AccountDTOAssembler accountDTOAssembler;
    @Mock
    FamilyDomainService familyDomainService;
    @InjectMocks
    FamilyAccountService familyAccountService;

    @Test
    void createFamilyCashAccount_Successfully() {
        double initialAmount = 30;
        int intFamilyId = hashCode();
        FamilyId familyId = new FamilyId(intFamilyId);
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(intFamilyId, initialAmount, 1,description);

        when(accountDTOAssembler.fromDTOTOFamilyId(inputDTO)).thenReturn(familyId);
        when(familyRepository.getDatabaseSavedFamily(familyId)).thenReturn(family);
        doNothing().when(familyDomainService).checkIfFamilyHasCashAccount(family);
        when(accountDTOAssembler.fromDTOToInitialAmountValue(inputDTO)).thenReturn(new InitialAmountValue(initialAmount, Currency.EUR));
        when(accountRepository.saveCashAccount(isA(CashAccount.class))).thenReturn(cashAccount);
        when(cashAccount.getAccountId()).thenReturn(new AccountId(10));
        doNothing().when(family).setAccountId(isA(AccountId.class));
        doNothing().when(familyRepository).saveFamilyWithAccount(family);
        AccountOutputDTO returnedOutput = new AccountOutputDTO(10, initialAmount, "EUR", description);
        when(accountDTOAssembler.accountToOutputDTO(cashAccount)).thenReturn(returnedOutput);

        AccountOutputDTO result = familyAccountService.createFamilyCashAccount(inputDTO);

        assertNotNull(result);
    }

    @Test
    void failToCreateFamilyCashAccount_WhenFamilyAlreadyHasCashAccount() {
        int intFamilyId = hashCode();
        double initialAmount = 30;
        FamilyId familyId = new FamilyId(intFamilyId);
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(intFamilyId, initialAmount, 1, description);
        when(accountDTOAssembler.fromDTOTOFamilyId(inputDTO)).thenReturn(familyId);
        when(familyRepository.getDatabaseSavedFamily(familyId)).thenReturn(family);

        doThrow(new DuplicateObjectException("")).when(familyDomainService).checkIfFamilyHasCashAccount(family);

        assertThrows(DuplicateObjectException.class,
                () -> familyAccountService.createFamilyCashAccount(inputDTO));
    }
}