package switchtwentytwenty.project.domain.model.transaction;

import lombok.Getter;
import switchtwentytwenty.project.domain.model.interfaces.Entity;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.Objects;

public class Transfer extends Transaction implements Entity {

    @Getter
    private final AccountId destinationAccountId;

    private Transfer(TransferBuilder builder) {
        this.accountId = builder.originAccountId;
        this.destinationAccountId = builder.destinationAccountId;
        this.amount = builder.amount;
        this.transactionId = builder.transactionId;
        this.description = builder.description;
        this.date = builder.date;
        this.categoryId = builder.categoryId;
    }

    @Override
    public boolean hasId(TransactionId id) {
        return this.transactionId == id;
    }

    public static class TransferBuilder {
        private final AccountId originAccountId;
        private final AccountId destinationAccountId;
        private final TransactionAmount amount;
        private TransactionId transactionId;
        private Description description;
        private TransactionDate date;
        private CategoryId categoryId;

        public TransferBuilder(AccountId originAccountId, AccountId destinationAccountId, TransactionAmount amount) {
            this.originAccountId = originAccountId;
            this.destinationAccountId = destinationAccountId;
            this.amount = amount;
        }

        public TransferBuilder withTransactionId(TransactionId transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public TransferBuilder withTransactionId() {
            this.transactionId = new TransactionId(0);
            return this;
        }

        public TransferBuilder withDescription(Description description) {
            this.description = description;
            return this;
        }

        public TransferBuilder withTransactionDate(TransactionDate transactionDate) {
            this.date = transactionDate;
            return this;
        }

        public TransferBuilder withCategoryId(CategoryId categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Transfer build() {
            return new Transfer(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transfer transfer = (Transfer) o;
        return Objects.equals(destinationAccountId, transfer.destinationAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), destinationAccountId);
    }
}