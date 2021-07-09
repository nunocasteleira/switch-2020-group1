package switchtwentytwenty.project.dto.account;

import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.account.PersonalBankAccount;
import java.util.List;

public class AccountListMapper {

    public AccountListDTO toDTO(List<PersonalBankAccount> personalBankAccount,List <CashAccount> cashAccount) {

        if (personalBankAccount == null) {
            throw new ObjectDoesNotExistException("This account does not exist");
        }
        if (cashAccount == null) {
            throw new ObjectDoesNotExistException("This account does not exist");
        }

        return new AccountListDTO(personalBankAccount, cashAccount);
    }
}
