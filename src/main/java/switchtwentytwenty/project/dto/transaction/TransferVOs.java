package switchtwentytwenty.project.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class TransferVOs {
    @Getter
    @Setter
    private AccountId originAccountId;
    @Getter
    @Setter
    private AccountId destinationAccountId;
    @Getter
    @Setter
    private TransactionAmount amount;
    @Getter
    @Setter
    private Description description;
    @Getter
    @Setter
    private TransactionDate date;
    @Getter
    @Setter
    private CategoryId categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransferVOs that = (TransferVOs) o;
        return Objects.equals(originAccountId, that.originAccountId) && Objects.equals(destinationAccountId, that.destinationAccountId) && Objects.equals(amount, that.amount) && Objects.equals(description, that.description) && Objects.equals(date, that.date) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originAccountId, destinationAccountId, amount, description, date, categoryId);
    }
}