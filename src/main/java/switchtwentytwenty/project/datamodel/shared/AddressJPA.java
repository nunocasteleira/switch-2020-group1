package switchtwentytwenty.project.datamodel.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class AddressJPA implements Serializable {
    private static final long serialVersionUID = 0;

    @Getter
    private String street;
    @Getter
    private String location;
    @Getter
    private String postalCode;

    public AddressJPA(String street, String location, String postalCode) {
        this.street = street;
        this.postalCode = postalCode;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressJPA that = (AddressJPA) o;
        return Objects.equals(street, that.street) && Objects.equals(location, that.location) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, location, postalCode);
    }
}
