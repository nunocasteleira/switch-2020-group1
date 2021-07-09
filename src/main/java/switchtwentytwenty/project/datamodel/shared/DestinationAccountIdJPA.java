package switchtwentytwenty.project.datamodel.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DestinationAccountIdJPA implements Serializable {
    private static final long serialVersionUID = 0;

    @Getter
    private long destinationAccountIdNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DestinationAccountIdJPA that = (DestinationAccountIdJPA) o;
        return destinationAccountIdNumber == that.destinationAccountIdNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinationAccountIdNumber);
    }
}