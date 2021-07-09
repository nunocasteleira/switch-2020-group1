package switchtwentytwenty.project.domain.model.shared;

import lombok.Getter;
import switchtwentytwenty.project.domain.exceptions.InvalidAmountException;

import java.math.BigDecimal;
import java.util.Objects;

public class Balance {
    @Getter
    private final double amount;

    /**
     * Constructor method for the Balance object
     *
     * @param amount
     */
    public Balance(double amount) {
        this.amount = amount;
        validateBalance();
    }

    /**
     * Method to validate the balance.
     */
    private void validateBalance() {
        if (BigDecimal.valueOf(this.amount).scale() >= 3) {
            throw new InvalidAmountException("The amount can only have up to 2 decimals.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Balance)) {
            return false;
        }
        Balance balance = (Balance) o;
        return Double.compare(balance.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
