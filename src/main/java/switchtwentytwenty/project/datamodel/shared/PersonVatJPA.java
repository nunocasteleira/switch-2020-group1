package switchtwentytwenty.project.datamodel.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class PersonVatJPA implements Serializable {
    private static final long serialVersionUID = 0;

    @Getter
    private String vat;

    public PersonVatJPA(String vat) {
        this.vat = vat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonVatJPA that = (PersonVatJPA) o;
        return Objects.equals(vat, that.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vat);
    }
}
