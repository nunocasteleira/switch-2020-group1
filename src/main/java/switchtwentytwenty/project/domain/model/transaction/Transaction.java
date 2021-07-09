package switchtwentytwenty.project.domain.model.transaction;

import lombok.Getter;
import lombok.Setter;
import switchtwentytwenty.project.domain.model.interfaces.AggregateRoot;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.Objects;

public abstract class Transaction implements AggregateRoot<TransactionId> {
    @Getter
    @Setter
    protected TransactionId transactionId;
    @Getter
    protected AccountId accountId;
    @Getter
    protected TransactionAmount amount;
    @Getter
    protected Description description;
    @Getter
    protected TransactionDate date;
    @Getter
    protected CategoryId categoryId;
    @Getter
    protected boolean isPayment;

    public boolean isAfter(TransactionDate date) {
        return this.date.isAfter(date);
    }

    public boolean isBefore(TransactionDate date) {
        return this.date.isBefore(date);
    }

    @Override
    public boolean equals(Object o) {
        Transaction that = (Transaction) o;
        return isPayment == that.isPayment && Objects.equals(transactionId, that.transactionId) && Objects.equals(accountId, that.accountId) && Objects.equals(amount, that.amount) && Objects.equals(description, that.description) && Objects.equals(date, that.date) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountId, amount, description, date, categoryId, isPayment);
    }
}
