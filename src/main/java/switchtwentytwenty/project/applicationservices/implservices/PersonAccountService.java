package switchtwentytwenty.project.applicationservices.implservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import switchtwentytwenty.project.applicationservices.iservices.IPersonAccountService;
import switchtwentytwenty.project.assemblers.AccountDTOAssembler;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.account.PersonalBankAccount;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.account.*;
import switchtwentytwenty.project.dto.account.*;
import switchtwentytwenty.project.dto.person.FamilyMembersOutputDTO;
import switchtwentytwenty.project.dto.person.FamilyMemberMapper;
import switchtwentytwenty.project.dto.person.FamilyMembersOutputDTO;
import switchtwentytwenty.project.dto.transaction.TransferMapper;
import switchtwentytwenty.project.repositories.AccountRepository;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonAccountService implements IPersonAccountService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransferMapper transferMapper;
    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private AccountDTOAssembler accountDTOAssembler;

    /**
     * Method to create a new person bank account, save it in the repository and add it to the
     * person
     *
     * @param accountInputDTO account information
     * @return the account output
     */
    public AccountOutputDTO createBankAccount(AccountInputDTO accountInputDTO, String personId) {
        Person person = personRepository.getByEmail(new Email(personId));

        InitialAmountValue initialAmount = accountDTOAssembler.fromDTOToInitialAmountValue(accountInputDTO);
        Description description = accountDTOAssembler.fromDTOToAccountDescription(accountInputDTO);
        Provider provider = accountDTOAssembler.fromDTOToProvider(accountInputDTO);
        PersonalBankAccount personalBankAccount = new PersonalBankAccount(initialAmount, description, provider);

        PersonalBankAccount savedAccount = accountRepository.savePersonalBankAccount(personalBankAccount);
        AccountId accountId = savedAccount.getAccountId();

        addAccountToPerson(person, accountId);

        return accountDTOAssembler.bankAccountToOutputDTO(savedAccount);
    }

    /**
     * Method to add the Bank Account to the person
     *
     * @param person    the logged person
     * @param accountId the account id that we want to save
     */
    private void addAccountToPerson(Person person, AccountId accountId) {
        person.addAccountId(accountId);

        personRepository.savePerson(person);
    }


    /**
     * Method to create a personal cash account, by saving it in the repository and adding it to
     * person.
     *
     * @param accountInputDTO a DTO with the necessary data to create personal cash account.
     * @param personId        the id of the person we will create the personal account.
     * @return a DTO with the data of the created personal cash account.
     */
    public AccountOutputDTO createPersonalCashAccount(AccountInputDTO accountInputDTO, String personId) {
        Email personIdVo = new Email(personId);
        InitialAmountValue amount = accountDTOAssembler.fromDTOToInitialAmountValue(accountInputDTO);
        Description accountDescription = accountDTOAssembler.fromDTOToAccountDescription(accountInputDTO);

        CashAccount personalCashAccount = new CashAccount(amount, accountDescription);
        CashAccount savedCashAccount = accountRepository.saveCashAccount(personalCashAccount);
        AccountId accountId = savedCashAccount.getAccountId();

        Person person = personRepository.getByEmail(personIdVo);
        addAccountToPerson(person, accountId);

        return accountDTOAssembler.accountToOutputDTO(savedCashAccount);
    }

    /**
     * This method allows to obtain the list of all cash accounts of a specific family member.
     *
     * @param personId family member's email address.
     * @return DTO with the list of family member cash accounts'.
     */
    @Override
    public CashAccountsOutputDTO getCashAccounts(String personId) {
        Email aPersonId = new Email(personId);
        List<AccountId> accounts = personRepository.getCashAccounts(aPersonId);
        List<CashAccount> cashAccounts = accountRepository.getByAccountId(accounts);
        return transferMapper.toDTO(cashAccounts);
    }

    /**
     * Method to get List of accounts of this owner
     *
     * @param personId the id of the person we will create the personal account.
     * @return a DTO with the List of this user's accounts.
     */
    public AccountListDTO getMemberAccountList(String personId) {
        Email aPersonId = new Email(personId);

        Person aPerson = personRepository.getByEmail(new Email(personId));
        FamilyId aFamilyId = aPerson.getFamilyId();
        Family aFamily = familyRepository.getDatabaseSavedFamily(aFamilyId);
        AccountId accountId = aFamily.getCashAccountId();
        List<AccountId> familyAccount = new ArrayList<>();
        familyAccount.add(accountId);
        List<CashAccount> familyCashAccount = accountRepository.getByAccountId(familyAccount);

        List<AccountId> accounts = personRepository.getCashAccounts(aPersonId);
        List<CashAccount> cashAccounts = accountRepository.getByAccountId(accounts);
        cashAccounts.addAll(familyCashAccount);

        List<PersonalBankAccount> bankAccounts = accountRepository.getByBankAccountId(accounts);

        AccountListMapper accountListMapper = new AccountListMapper();


        return accountListMapper.toDTO(bankAccounts, cashAccounts);
    }

    @Override
    public FamilyMembersOutputDTO getFamilyMembersWithCashAccounts(String personId) {
        Email aPersonId = new Email(personId);
        Person aPerson = personRepository.getByEmail(aPersonId);
        FamilyId aFamilyId = aPerson.getFamilyId();
        List<Email> familyMembers = familyRepository.getFamilyMembers(aFamilyId);
        List<Person> familyMembersWithAccounts = personRepository.getFamilyMembersWithAccounts(familyMembers, aPerson);
        List<Person> familyMembersWithCashAccounts = accountRepository.getFamilyMembersWithCashAccounts(familyMembersWithAccounts);

        FamilyMemberMapper familyMemberMapper = new FamilyMemberMapper();
        return familyMemberMapper.toDTO(familyMembersWithCashAccounts);
    }
}