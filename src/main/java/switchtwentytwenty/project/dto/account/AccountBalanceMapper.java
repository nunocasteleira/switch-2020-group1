package switchtwentytwenty.project.dto.account;


import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.shared.Balance;

public class AccountBalanceMapper {

    public AccountBalanceDTO toDTO(Balance accountBalance) {

        if (accountBalance == null) {
            throw new ObjectDoesNotExistException("Balance is invalid");
        }
        return new AccountBalanceDTO(accountBalance);
    }
}
