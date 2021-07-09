package switchtwentytwenty.project.datamodel.shared;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class FamilyIdJPA implements Serializable {
    private static final long serialVersionUID = 0;

    @Getter
    private long familyId;

    /**
     * Constructor for the FamilyId object.
     *
     * @param id hashCode of the family.
     */
    public FamilyIdJPA(long id) {
        this.familyId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilyIdJPA that = (FamilyIdJPA) o;
        return familyId == that.familyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(familyId);
    }
}
