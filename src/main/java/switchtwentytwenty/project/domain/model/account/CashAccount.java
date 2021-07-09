package switchtwentytwenty.project.domain.model.account;

import switchtwentytwenty.project.domain.model.interfaces.Entity;
import switchtwentytwenty.project.domain.model.shared.Description;
import switchtwentytwenty.project.domain.model.shared.AccountId;
import switchtwentytwenty.project.domain.model.shared.InitialAmountValue;

public class CashAccount extends Account implements Entity {
    /**
     * Constructor method for CashAccount object.
     *
     * @param initialAmountValue initial cash value
     */
    public CashAccount(InitialAmountValue initialAmountValue, Description accountDescription) {
        this.accountId = new AccountId(0);
        this.initialAmountValue = initialAmountValue;
        this.accountDescription = accountDescription;
    }

    /**
     * Method to verify if CashAccount has a given Id.
     * @param id id to compare
     * @return true if account has that id, false if not
     */
    @Override
    public boolean hasId(AccountId id) {
        return this.accountId.equals(id);
    }
}
