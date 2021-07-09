package switchtwentytwenty.project.datamodel.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class FamilyNameJPA implements Serializable {
    private static final long serialVersionUID = 0;
    @Getter
    private String familyName;

    public FamilyNameJPA(String name) {
        this.familyName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilyNameJPA that = (FamilyNameJPA) o;
        return Objects.equals(familyName, that.familyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(familyName);
    }
}
