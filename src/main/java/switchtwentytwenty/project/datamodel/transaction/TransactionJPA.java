package switchtwentytwenty.project.datamodel.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import switchtwentytwenty.project.datamodel.shared.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
public abstract class TransactionJPA {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    @Getter
    @Embedded
    private OriginAccountIdJPA accountIdValue;
    @Getter
    @Embedded
    private AmountJPA amount;
    @Getter
    @Embedded
    private DescriptionJPA transactionDescription;
    @Getter
    @Embedded
    private TransactionDateJPA date;
    @Getter
    @Embedded
    private CategoryIdJPA categoryId;
    @Getter
    private boolean isPayment;

    protected TransactionJPA(OriginAccountIdJPA accountIdJPA, AmountJPA amountJPA, DescriptionJPA descriptionJPA, TransactionDateJPA transactionDateJPA, CategoryIdJPA categoryIdJPA, boolean isPayment){
        this.accountIdValue = accountIdJPA;
        this.amount = amountJPA;
        this.transactionDescription = descriptionJPA;
        this.date = transactionDateJPA;
        this.categoryId = categoryIdJPA;
        this.isPayment = isPayment;
    }


    @Override
    public boolean equals(Object o) {
        TransactionJPA that = (TransactionJPA) o;
        return transactionId == that.transactionId && isPayment == that.isPayment && Objects.equals(accountIdValue, that.accountIdValue) && Objects.equals(amount, that.amount) && Objects.equals(transactionDescription, that.transactionDescription) && Objects.equals(date, that.date) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountIdValue, amount, transactionDescription, date, categoryId, isPayment);
    }
}
