package switchtwentytwenty.project.datamodel.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import switchtwentytwenty.project.datamodel.shared.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "Payments")
public class PaymentJPA extends TransactionJPA {
    @Getter
    private DestinationEntityJPA destinationEntityJPA;

    public PaymentJPA(OriginAccountIdJPA accountIdJPA, AmountJPA amountJPA, DescriptionJPA descriptionJPA, TransactionDateJPA transactionDateJPA, CategoryIdJPA categoryIdJPA, DestinationEntityJPA destinationEntityJPA) {
        super(accountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA, true);
        this.destinationEntityJPA = destinationEntityJPA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentJPA)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PaymentJPA that = (PaymentJPA) o;
        return Objects.equals(destinationEntityJPA, that.destinationEntityJPA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), destinationEntityJPA);
    }
}
