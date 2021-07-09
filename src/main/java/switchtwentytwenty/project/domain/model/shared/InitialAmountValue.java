package switchtwentytwenty.project.domain.model.shared;

import lombok.Getter;
import switchtwentytwenty.project.domain.exceptions.InvalidAmountException;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.math.BigDecimal;
import java.util.Objects;

public class InitialAmountValue implements ValueObject {
    @Getter
    private final double amount;
    @Getter
    private final Currency currency;

    /**
     * constructor method for InitialAmountValue object.
     *
     * @param initialAmount initial money value
     */
    public InitialAmountValue(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
        validateAmount();
    }

    /**
     * Method to validate the initial amount.
     */
    private void validateAmount() {
        if (this.amount < 0) {
            throw new InvalidAmountException("The initial cash amount cannot be negative.");
        }
        if (BigDecimal.valueOf(this.amount).scale() >= 3) {
            throw new InvalidAmountException("The amount can only have up to 2 decimals.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InitialAmountValue)) {
            return false;
        }
        InitialAmountValue that = (InitialAmountValue) o;
        return Double.compare(that.amount, amount) == 0 && currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}

