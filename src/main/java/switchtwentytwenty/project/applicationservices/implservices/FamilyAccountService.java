package switchtwentytwenty.project.applicationservices.implservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import switchtwentytwenty.project.applicationservices.iservices.IFamilyAccountService;
import switchtwentytwenty.project.assemblers.AccountDTOAssembler;
import switchtwentytwenty.project.domain.domainservices.FamilyDomainService;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.shared.Description;
import switchtwentytwenty.project.domain.model.shared.AccountId;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.domain.model.shared.InitialAmountValue;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.repositories.AccountRepository;
import switchtwentytwenty.project.repositories.FamilyRepository;

@Service
public class FamilyAccountService implements IFamilyAccountService {
    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDTOAssembler accountDTOAssembler;
    @Autowired
    private FamilyDomainService familyDomainService;

    /**
     * Method to create a new family cash account, save it in the repository and add it to the
     * family
     * @param inputDTO family cash account input DTO with information
     * @return accountOutputDTO with account information
     */
    public AccountOutputDTO createFamilyCashAccount(AccountInputDTO inputDTO) {
        FamilyId familyId = accountDTOAssembler.fromDTOTOFamilyId(inputDTO);
        Family family = familyRepository.getDatabaseSavedFamily(familyId);

        familyDomainService.checkIfFamilyHasCashAccount(family);

        InitialAmountValue initialAmountValue = accountDTOAssembler.fromDTOToInitialAmountValue(inputDTO);
        Description accountDescription = accountDTOAssembler.fromDTOToAccountDescription(inputDTO);
        CashAccount cashAccount = new CashAccount(initialAmountValue, accountDescription);

        CashAccount savedCashAccount = accountRepository.saveCashAccount(cashAccount);
        AccountId newAccountId = savedCashAccount.getAccountId();

        addCashAccountToFamily(family, newAccountId);

        return accountDTOAssembler.accountToOutputDTO(savedCashAccount);
    }

    /**
     * Method to add the Cash Account Id to the Family.
     *
     * @param family        family object(domain)
     * @param cashAccountId Id of the cash account to be added
     */
    private void addCashAccountToFamily(Family family, AccountId cashAccountId) {
        family.setAccountId(cashAccountId);

        familyRepository.saveFamilyWithAccount(family);
    }
}
