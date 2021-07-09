package switchtwentytwenty.project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import switchtwentytwenty.project.datamodel.account.CashAccountJPA;
import switchtwentytwenty.project.datamodel.account.PersonalBankJPA;
import switchtwentytwenty.project.datamodel.shared.AmountJPA;
import switchtwentytwenty.project.datamodel.assembler.AccountDomainDataAssembler;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.account.PersonalBankAccount;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.domain.model.shared.AccountId;
import switchtwentytwenty.project.domain.model.shared.Description;
import switchtwentytwenty.project.domain.model.shared.InitialAmountValue;
import switchtwentytwenty.project.domain.model.shared.Provider;
import switchtwentytwenty.project.repositories.irepositories.IAccountRepositoryJPA;

import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository {
    @Autowired
    IAccountRepositoryJPA iAccountRepositoryJPA;
    @Autowired
    AccountDomainDataAssembler accountDomainDataAssembler;

    /**
     * Method to save a Family Account in the Account repository and save the new account Id in the
     * domain model.
     *
     * @param cashAccount family cash account object
     */
    public CashAccount saveCashAccount(CashAccount cashAccount) {
        CashAccountJPA cashAccountJPA = accountDomainDataAssembler.cashAccountToData(cashAccount);
        iAccountRepositoryJPA.save(cashAccountJPA);

        AccountId newAccountId = new AccountId(cashAccountJPA.getAccountId());
        InitialAmountValue initialAmountValue = accountDomainDataAssembler.fromDataToInitialAmountValue(cashAccountJPA);
        Description accountDescription = accountDomainDataAssembler.fromDataToAccountDescription(cashAccountJPA);
        CashAccount savedCashAccount = new CashAccount(initialAmountValue, accountDescription);
        savedCashAccount.setAccountId(newAccountId);
        return savedCashAccount;
    }

    public PersonalBankAccount savePersonalBankAccount(PersonalBankAccount personalBankAccount) {
        PersonalBankJPA personalBankJPA = accountDomainDataAssembler.personalBankToData(personalBankAccount);
        iAccountRepositoryJPA.save(personalBankJPA);

        AccountId newAccountId = new AccountId(personalBankJPA.getAccountId());
        InitialAmountValue initialAmountValue = accountDomainDataAssembler.fromDataToInitialAmountValue(personalBankJPA);
        Description accountDescription = accountDomainDataAssembler.fromDataToAccountDescription(personalBankJPA);
        Provider provider = accountDomainDataAssembler.fromDataToProvider(personalBankAccount.getProvider().toString());
        PersonalBankAccount savedBankAccount = new PersonalBankAccount(initialAmountValue, accountDescription, provider);
        savedBankAccount.setAccountId(newAccountId);

        return savedBankAccount;
    }

    public void existsAccount(long accountId) {
        if (!iAccountRepositoryJPA.existsCashAccount(accountId) && !iAccountRepositoryJPA.existsBankAccount(accountId)) {
            throw new ObjectDoesNotExistException("The account does not exist.");
        }
    }

    /**
     * This method allows to obtain a list of CashAccount from an AccountId list.
     *
     * @param accounts a list of AccountId.
     * @return the list of CashAccount.
     */
    public List<CashAccount> getByAccountId(List<AccountId> accounts) {
        List<Long> longAccounts = fromAccountIdToLong(accounts);
        List<CashAccountJPA> cashAccountsJPA = new ArrayList<>();
        for (Long longAccountId : longAccounts) {
            Optional<CashAccountJPA> cashAccountJPAOptional = iAccountRepositoryJPA.findCashAccountById(longAccountId);
            cashAccountJPAOptional.ifPresent(cashAccountsJPA::add);
        }
        return accountDomainDataAssembler.cashAccountsJPAToDomain(cashAccountsJPA);
    }

    public List<PersonalBankAccount> getByBankAccountId(List<AccountId> accounts) {
        List<Long> longAccounts = fromAccountIdToLong(accounts);
        List<PersonalBankJPA> bankAccountsJPA = new ArrayList<>();
        for (Long longAccountId : longAccounts) {
            Optional<PersonalBankJPA> bankAccountJPAOptional = iAccountRepositoryJPA.findBankAccountById(longAccountId);
            bankAccountJPAOptional.ifPresent(bankAccountsJPA::add);
        }
        return accountDomainDataAssembler.bankAccountsJPAToDomain(bankAccountsJPA);
    }

    /**
     * This method allows to convert a list of AccountId into a list of long account Id.
     *
     * @param accounts a list of AccountId.
     * @return the list of long account Id.
     */
    private List<Long> fromAccountIdToLong(List<AccountId> accounts) {
        List<Long> longAccounts = new ArrayList<>();
        for (AccountId accountId : accounts) {
            long longAccountId = accountId.getAccountIdNumber();
            longAccounts.add(longAccountId);
        }
        return longAccounts;
    }

    //Método comentado para averiguar esta possibilidade de implementação:
    //ir buscar AccountJPA abstrata e converter em CashAccountJPA
    /**
     * This method allows to obtain a list of CashAccountJPA after converting AccountJPA into CashAccountJPA.
     * //@param accounts a list of AccountId.
     * @return the list of CashAccountJPA.
     */
/*    private List<CashAccountJPA> fromOptionalToJPA(List<AccountId> accounts) {
        List<Long> longAccounts = fromAccountIdToLong(accounts);

        List<AccountJPA> accountsJPA = new ArrayList<>();
        for (Long longAccountId : longAccounts) {
            Optional<AccountJPA> accountJPAOptional = iAccountRepositoryJPA.findById(longAccountId);
            accountJPAOptional.ifPresent(accountsJPA::add);
        }
        List<CashAccountJPA> cashAccountsJPA = new ArrayList<>();
        for (AccountJPA accountJPA : accountsJPA) {
            if (accountJPA.getClass() == CashAccountJPA.class) {
                cashAccountsJPA.add((CashAccountJPA)accountJPA);
            }
        }
        return cashAccountsJPA;
    }*/

    public InitialAmountValue getByAccountId(AccountId accountId) {
        long accountIdValue = accountId.getAccountIdNumber();
        Optional<CashAccountJPA> cashAccountJPAOptional = iAccountRepositoryJPA.findCashAccountById(accountIdValue);
        Optional<PersonalBankJPA> personalBankJPAOptional = iAccountRepositoryJPA.findBankAccountById(accountIdValue);
        AmountJPA amountJPA;
        InitialAmountValue initialAmount = null;

        if (cashAccountJPAOptional.isPresent()) {
            amountJPA = cashAccountJPAOptional.get().getInitialAmountValue();
            initialAmount = new InitialAmountValue(amountJPA.getAmount(), Currency.convertNumberToCurrency(amountJPA.getCurrency()));
        }
        if (personalBankJPAOptional.isPresent()) {
            amountJPA = personalBankJPAOptional.get().getInitialAmountValue();
            initialAmount = new InitialAmountValue(amountJPA.getAmount(), Currency.convertNumberToCurrency(amountJPA.getCurrency()));
        }
        return initialAmount;
    }

    public List<Person> getFamilyMembersWithCashAccounts(List<Person> familyMembersWithAccounts) {
        List<Person> familyMembersWithCashAccounts = new ArrayList<>();
        for (Person person : familyMembersWithAccounts) {
            for (AccountId accountId : person.getPersonalAccounts()) {
                iAccountRepositoryJPA.existsCashAccount(accountId.getAccountIdNumber());
            }
            familyMembersWithCashAccounts.add(person);
        }
        return familyMembersWithCashAccounts;
    }
}