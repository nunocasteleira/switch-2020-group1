package switchtwentytwenty.project.domain.model.transaction;

import lombok.Getter;
import switchtwentytwenty.project.domain.model.interfaces.Entity;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.Objects;

public class Payment extends Transaction implements Entity {

    @Getter
    private final Description destinationEntity;

    private Payment(Builder builder) {
        this.transactionId = builder.transactionId;
        this.accountId = builder.accountId;
        this.amount = builder.amount;
        this.description = builder.description;
        this.date = builder.date;
        this.destinationEntity = builder.destinationEntity;
        this.categoryId = builder.categoryId;
    }

    @Override
    public boolean hasId(TransactionId id) {
        return this.transactionId == id;
    }

    public static class Builder {
        private final AccountId accountId;
        private final TransactionAmount amount;
        private TransactionId transactionId;
        private Description description;
        private TransactionDate date;
        private CategoryId categoryId;
        private Description destinationEntity;


        public Builder(AccountId accountId, TransactionAmount amount) {
            this.accountId = accountId;
            this.amount = amount;
        }

        public Builder withTransactionId(TransactionId transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder withTransactionId() {
            this.transactionId = new TransactionId(0);
            return this;
        }

        public Builder withDescription(Description description) {
            this.description = description;
            return this;
        }

        public Builder withTransactionDate(TransactionDate transactionDate) {
            this.date = transactionDate;
            return this;
        }

        public Builder withCategoryId(CategoryId categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder withDestinationEntity(Description destinationEntity) {
            this.destinationEntity = destinationEntity;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Payment payment = (Payment) o;
        return Objects.equals(destinationEntity, payment.destinationEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), destinationEntity);
    }
}