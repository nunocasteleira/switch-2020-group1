package switchtwentytwenty.project.dto.transaction;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionListDTO extends RepresentationModel<TransactionListDTO> {
    @Getter
    private final List<TransactionOutputDTO> transactionList;

    public TransactionListDTO(List<TransactionOutputDTO> transactionList) {
        this.transactionList = new ArrayList<>(transactionList);
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
        TransactionListDTO that = (TransactionListDTO) o;
        return Objects.equals(transactionList, that.transactionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transactionList);
    }
}
