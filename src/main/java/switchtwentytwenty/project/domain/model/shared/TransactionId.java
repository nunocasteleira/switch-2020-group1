package switchtwentytwenty.project.domain.model.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.util.Objects;

@AllArgsConstructor
public class TransactionId implements ValueObject {
    @Getter
    private final long transactionIdValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionId)) {
            return false;
        }
        TransactionId that = (TransactionId) o;
        return transactionIdValue == that.transactionIdValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionIdValue);
    }
}
