package switchtwentytwenty.project.domain.model.account;

import lombok.Getter;
import lombok.Setter;
import switchtwentytwenty.project.domain.model.interfaces.AggregateRoot;
import switchtwentytwenty.project.domain.model.shared.Description;
import switchtwentytwenty.project.domain.model.shared.AccountId;
import switchtwentytwenty.project.domain.model.shared.InitialAmountValue;

import java.util.Objects;

public abstract class Account implements AggregateRoot<AccountId> {
    @Getter
    @Setter
    protected AccountId accountId;
    @Getter
    protected InitialAmountValue initialAmountValue;
    @Getter
    protected Description accountDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return accountId.equals(account.accountId) && initialAmountValue.equals(account.initialAmountValue) && accountDescription.equals(account.accountDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, initialAmountValue, accountDescription);
    }
}
