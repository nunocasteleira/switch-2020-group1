package switchtwentytwenty.project.datamodel.assembler;

import org.springframework.stereotype.Service;
import switchtwentytwenty.project.datamodel.account.AccountJPA;
import switchtwentytwenty.project.datamodel.account.CashAccountJPA;
import switchtwentytwenty.project.datamodel.account.PersonalBankJPA;
import switchtwentytwenty.project.datamodel.shared.AmountJPA;
import switchtwentytwenty.project.datamodel.shared.DescriptionJPA;
import switchtwentytwenty.project.datamodel.shared.ProviderJPA;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.account.PersonalBankAccount;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountDomainDataAssembler {

    /**
     * Method to convert CashAccount from domain to JPA.
     *
     * @param cashAccount Family Cash Account domain object
     * @return Family Cash Account JPA object
     */
    public CashAccountJPA cashAccountToData(CashAccount cashAccount) {
        double amount = cashAccount.getInitialAmountValue().getAmount();
        int currency = cashAccount.getInitialAmountValue().getCurrency().getCurrencyNumber();
        String accountDescription = cashAccount.getAccountDescription().getAccountDescription();

        AmountJPA initialAmountValueJPA =
                new AmountJPA(amount, currency);
        DescriptionJPA accountDescriptionJPA = new DescriptionJPA(accountDescription);

        return new CashAccountJPA(initialAmountValueJPA, accountDescriptionJPA);
    }

    /**
     * Method to convert PersonalBankAccount from domain to JPA
     *
     * @param personalBankAccount the domain account
     * @return the JPA of this domain object
     */
    public PersonalBankJPA personalBankToData(PersonalBankAccount personalBankAccount) {
        double amount = personalBankAccount.getInitialAmountValue().getAmount();
        int currency = personalBankAccount.getInitialAmountValue().getCurrency().getCurrencyNumber();
        String description = personalBankAccount.getAccountDescription().getAccountDescription();
        String provider = personalBankAccount.getProvider().getAccountProvider();

        AmountJPA initialAmountValueJPA =
                new AmountJPA(amount, currency);
        DescriptionJPA accountDescriptionJPA = new DescriptionJPA(description);
        ProviderJPA providerJPA = new ProviderJPA(provider);

        return new PersonalBankJPA(initialAmountValueJPA, accountDescriptionJPA, providerJPA);
    }

    /**
     * Method to convert object from account JPA to value object Initial Amount.
     *  @param accountJPA account JPA data model object

     * @return the domain object
     */
    public InitialAmountValue fromDataToInitialAmountValue(AccountJPA accountJPA) {
        Currency currency = Currency.convertNumberToCurrency(accountJPA.getInitialAmountValue().getCurrency());
        return new InitialAmountValue(accountJPA.getInitialAmountValue().getAmount(), currency);
    }

    /**
     * Method to convert object from account JPA to value object AccountDescription.
     * @param accountJPA account JPA data model object
     * @return the domain object
     */
    public Description fromDataToAccountDescription(AccountJPA accountJPA) {
        return new Description(accountJPA.getAccountDescription().getDescription());
    }

    /**
     * Method to convert cash jpa to domain object provider
     * @param provider the bank provider
     * @return the provider object domain
     */
    public Provider fromDataToProvider(String provider) {
        return new Provider(provider);
    }

    /**
     * This method allows to convert a list of CashAccountJPA to a list of CashAccount.
     * @param cashAccountsJPA the list of CashAccountJPA.
     * @return the list of CashAccount.
     */
    public List<CashAccount> cashAccountsJPAToDomain(List<CashAccountJPA> cashAccountsJPA) {
        List<CashAccount> cashAccounts = new ArrayList<>();
        for (CashAccountJPA cashAccountJPA : cashAccountsJPA) {
            CashAccount cashAccount = cashAccountJPAToDomain(cashAccountJPA);
            cashAccounts.add(cashAccount);
        }
        return cashAccounts;
    }

    /**
     * This method allows to convert a CashAccountJPA to a CashAccount.
     * @param cashAccountJPA cashAccount data object.
     * @return cashAccount domain object.
     */
    public CashAccount cashAccountJPAToDomain(CashAccountJPA cashAccountJPA) {
        long accountId = cashAccountJPA.getAccountId();
        AccountId originAccountId = new AccountId(accountId);
        double amount = cashAccountJPA.getInitialAmountValue().getAmount();
        Currency currency = Currency.convertNumberToCurrency(cashAccountJPA.getInitialAmountValue().getCurrency());
        String description = cashAccountJPA.getAccountDescription().getDescription();
        InitialAmountValue initialAmountValue = new InitialAmountValue(amount, currency);
        Description accountDescription = new Description(description);

        CashAccount cashAccount = new CashAccount(initialAmountValue, accountDescription);
        cashAccount.setAccountId(originAccountId);
        return cashAccount;
    }

    /**
     * This method allows to convert a list of PersonalBankJPA to a list of PersonalBankAccount.
     * @param bankAccountsJPA the list of PersonalBankJPA.
     * @return the list of PersonalBankAccount.
     */
    public List<PersonalBankAccount> bankAccountsJPAToDomain(List<PersonalBankJPA> bankAccountsJPA) {
        List<PersonalBankAccount> bankAccounts = new ArrayList<>();
        for (PersonalBankJPA personalBankJPA : bankAccountsJPA) {
            PersonalBankAccount bankAccount = bankAccountJPAToDomain(personalBankJPA);
            bankAccounts.add(bankAccount);
        }
        return bankAccounts;
    }

    /**
     * This method allows to convert a personalBankJPA to a personalBankAccount.
     * @param personalBankJPA bankAccount data object.
     * @return personalBankAccount domain object.
     */
    public PersonalBankAccount bankAccountJPAToDomain(PersonalBankJPA personalBankJPA) {
        long accountId = personalBankJPA.getAccountId();
        AccountId originAccountId = new AccountId(accountId);
        double amount = personalBankJPA.getInitialAmountValue().getAmount();
        Currency currency = Currency.convertNumberToCurrency(personalBankJPA.getInitialAmountValue().getCurrency());
        String description = personalBankJPA.getAccountDescription().getDescription();
        String provider = personalBankJPA.getProvider().getProvider();

        InitialAmountValue initialAmountValue = new InitialAmountValue(amount, currency);
        Description accountDescription = new Description(description);
        Provider accountProvider = new Provider(provider);

        PersonalBankAccount personalBankAccount = new PersonalBankAccount(initialAmountValue, accountDescription, accountProvider);
        personalBankAccount.setAccountId(originAccountId);
        return personalBankAccount;
    }
}