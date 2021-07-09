package switchtwentytwenty.project.assemblers;

import org.springframework.stereotype.Service;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.shared.Description;
import switchtwentytwenty.project.domain.model.shared.Currency;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.domain.model.shared.InitialAmountValue;
import switchtwentytwenty.project.domain.model.account.PersonalBankAccount;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.account.AccountInputDTO;

@Service
public class AccountDTOAssembler {
    // ------ Input DTO assemblers to Value Objects:
    /**
     * Method to extract family id value from input DTO and convert into value object Family Id
     * @param inputDTO input DTO with account information
     * @return FamilyId value object
     */
    public FamilyId fromDTOTOFamilyId(AccountInputDTO inputDTO){
        return new FamilyId(inputDTO.getFamilyId());
    }

    /**
     * Method to extract initial amount value from input DTO and convert into value object Initial Amount
     * @param inputDTO input DTO with account information
     * @return InitialAmountValue value object
     */
    public InitialAmountValue fromDTOToInitialAmountValue(AccountInputDTO inputDTO){
        double amount = inputDTO.getInitialAmount();
        Currency currency = Currency.convertNumberToCurrency(inputDTO.getCurrency());
        return new InitialAmountValue(amount, currency);
    }

    /**
     * Method to extract account description from input DTO and convert into value object AccountDescription.
     * @param inputDTO input DTO with account information
     * @return AccountDescription value object
     */
    public Description fromDTOToAccountDescription(AccountInputDTO inputDTO){
        String description = inputDTO.getDescription();
        return new Description(description);
    }

    /**
     * method to extract person id from input DTO and convert it to the Email object
     * @param inputDTO dto with all information
     * @return the email
     */
    public Provider fromDTOToProvider(AccountInputDTO inputDTO){
        return new Provider(inputDTO.getProvider());
    }



    // ------ Output DTO assemblers from Value Objects:
    /**
     * Method to convert Account  information into an output DTO
     * @return AccountOutputDTO object
     */
    public AccountOutputDTO accountToOutputDTO(CashAccount cashAccount){
        long accountId = cashAccount.getAccountId().getAccountIdNumber();
        double initialAmount = cashAccount.getInitialAmountValue().getAmount();
        String currency = cashAccount.getInitialAmountValue().getCurrency().name();
        String accountDescription = cashAccount.getAccountDescription().getAccountDescription();
        return new AccountOutputDTO(accountId, initialAmount, currency, accountDescription);
    }

    public AccountOutputDTO bankAccountToOutputDTO(PersonalBankAccount bankAccount){
        long accountId = bankAccount.getAccountId().getAccountIdNumber();
        double initialAmount = bankAccount.getInitialAmountValue().getAmount();
        String currency = bankAccount.getInitialAmountValue().getCurrency().name();
        String accountDescription = bankAccount.getAccountDescription().getAccountDescription();
        String provider = bankAccount.getProvider().toString();
        return new AccountOutputDTO(accountId, initialAmount, currency, accountDescription, provider);
    }
}
