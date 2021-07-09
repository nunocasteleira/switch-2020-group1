package switchtwentytwenty.project.datamodel.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class AmountJPA implements Serializable {
    private static final long serialVersionUID = 0;
    @Getter
    private double amount;
    @Getter
    private int currency;

    /**
     * constructor method for InitialAmountValueJPA object.
     *
     * @param initialAmount initial money amount
     */
    public AmountJPA(double initialAmount, int currency) {
        this.amount = initialAmount;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmountJPA)) {
            return false;
        }
        AmountJPA that = (AmountJPA) o;
        return Double.compare(that.amount, amount) == 0 && currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
