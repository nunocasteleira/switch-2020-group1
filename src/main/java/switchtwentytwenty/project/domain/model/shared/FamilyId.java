package switchtwentytwenty.project.domain.model.shared;

import switchtwentytwenty.project.domain.model.interfaces.Id;

import java.util.Objects;

public class FamilyId implements Id {
    private final long id;

    /**
     * Constructor for the FamilyId object.
     *
     * @param id hashCode of the family.
     */
    public FamilyId(long id) {
        this.id = id;
    }

    /**
     * This method allows to obtain the id of the family.
     *
     * @return id of the family.
     */
    public long getFamilyId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilyId familyId = (FamilyId) o;
        return id == familyId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
