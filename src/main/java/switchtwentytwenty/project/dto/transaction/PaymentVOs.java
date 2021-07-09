package switchtwentytwenty.project.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class PaymentVOs {
    @Getter
    @Setter
    private AccountId accountId;
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
    private Description destinationEntity;
    @Getter
    @Setter
    private CategoryId categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentVOs)) {
            return false;
        }
        PaymentVOs that = (PaymentVOs) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(amount, that.amount) && Objects.equals(description, that.description) && Objects.equals(date, that.date) && Objects.equals(destinationEntity, that.destinationEntity) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, amount, description, date, destinationEntity, categoryId);
    }
}
