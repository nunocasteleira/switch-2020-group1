package switchtwentytwenty.project.datamodel.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class TransactionDateJPA implements Serializable {
    private static final long serialVersionUID = 0;
    @Getter
    private String date;

    public TransactionDateJPA(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDateJPA)) {
            return false;
        }
        TransactionDateJPA that = (TransactionDateJPA) o;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

}
