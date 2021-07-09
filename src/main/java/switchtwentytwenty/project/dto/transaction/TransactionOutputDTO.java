package switchtwentytwenty.project.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionOutputDTO extends RepresentationModel<TransactionOutputDTO> {
    @Getter
    @Setter
    private long transactionId;
    @Getter
    @Setter
    private long accountId;
    @Getter
    @Setter
    private long destinationAccountId;
    @Getter
    @Setter
    private double amount;
    @Getter
    @Setter
    private String currency;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String date;
    @Getter
    @Setter
    private String destinationEntity;
    @Getter
    @Setter
    private Object categoryId;
    @Getter
    @Setter
    private String categoryName;
    @Getter
    @Setter
    private double balance;

    public TransactionOutputDTO(long accountId, double amount, String currency, String description, String date, String destinationEntity, Object categoryId, String categoryName, double balance) {
        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.date = date;
        this.destinationEntity = destinationEntity;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.balance = balance;
    }

    public TransactionOutputDTO(long transactionId, long accountId, double amount, String currency, String description, String date, String destinationEntity, Object categoryId, String categoryName, double balance) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.date = date;
        this.destinationEntity = destinationEntity;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.balance = balance;
    }

    public TransactionOutputDTO(long originAccountId, long destinationAccountId, double amount, String currency, String description, String date, Object categoryId, String categoryName, double balance) {
        this.accountId = originAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.balance = balance;
    }

    public TransactionOutputDTO(long transactionId, long originAccountId, long destinationAccountId, double amount, String currency, String description, String date, Object categoryId, String categoryName, double balance) {
        this.transactionId = transactionId;
        this.accountId = originAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.balance = balance;
    }

    public TransactionOutputDTO(long transactionId, double amount, String currency, String description, String date, String categoryName) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.date = date;
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionOutputDTO that = (TransactionOutputDTO) o;
        return accountId == that.accountId && destinationAccountId == that.destinationAccountId && Double.compare(that.amount, amount) == 0 && Double.compare(that.balance, balance) == 0 && currency.equals(that.currency) && description.equals(that.description) && categoryName.equals(that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transactionId, accountId, destinationAccountId, amount, currency, description, date, destinationEntity, categoryId, categoryName, balance);
    }
}