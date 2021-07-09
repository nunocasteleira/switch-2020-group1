package switchtwentytwenty.project.datamodel.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import switchtwentytwenty.project.datamodel.shared.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "Transfers")
public class TransferJPA extends TransactionJPA {
    @Getter
    DestinationAccountIdJPA destinationAccountIdJPA;

    public TransferJPA(OriginAccountIdJPA originAccountIdJPA, DestinationAccountIdJPA destinationAccountIdJPA, AmountJPA amountJPA, DescriptionJPA descriptionJPA, TransactionDateJPA transactionDateJPA, CategoryIdJPA categoryIdJPA) {
        super(originAccountIdJPA, amountJPA, descriptionJPA, transactionDateJPA, categoryIdJPA, false);
        this.destinationAccountIdJPA = destinationAccountIdJPA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TransferJPA that = (TransferJPA) o;
        return Objects.equals(destinationAccountIdJPA, that.destinationAccountIdJPA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), destinationAccountIdJPA);
    }
}