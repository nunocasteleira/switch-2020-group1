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
public class OriginAccountIdJPA implements Serializable {
    private static final long serialVersionUID = 0;
    @Getter
    private long accountIdNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof switchtwentytwenty.project.datamodel.shared.OriginAccountIdJPA)) {
            return false;
        }
        switchtwentytwenty.project.datamodel.shared.OriginAccountIdJPA that = (switchtwentytwenty.project.datamodel.shared.OriginAccountIdJPA) o;
        return accountIdNumber == that.accountIdNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountIdNumber);
    }
}
