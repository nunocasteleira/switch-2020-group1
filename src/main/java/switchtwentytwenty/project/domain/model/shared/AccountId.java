package switchtwentytwenty.project.domain.model.shared;

import lombok.Getter;
import switchtwentytwenty.project.domain.model.interfaces.Id;

import java.util.Objects;

public class AccountId implements Id {
    @Getter
    private final long accountIdNumber;

    /**
     * Constructor method for AccountId object.
     *
     * @param accountId account id - integer number
     */
    public AccountId(long accountId) {
        this.accountIdNumber = accountId;
        validateAccountId();
    }

    /**
     * Method to validate the account id.
     */
    private void validateAccountId() {
        if (accountIdNumber < 0) {
            throw new IllegalArgumentException("Invalid account Id");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountId)) {
            return false;
        }
        AccountId accountId1 = (AccountId) o;
        return accountIdNumber == accountId1.accountIdNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountIdNumber);
    }
}
