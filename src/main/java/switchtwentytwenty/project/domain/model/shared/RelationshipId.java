package switchtwentytwenty.project.domain.model.shared;

import switchtwentytwenty.project.domain.model.interfaces.Id;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.util.Objects;

public class RelationshipId implements ValueObject, Id {

    private final int id;

    /**
     * Method constructor for the categoryId object
     *
     * @param id id
     */
    public RelationshipId(int id) {
        this.id = id;
    }


    /**
     * Method to get the id.
     *
     * @return id of the category
     */
    public int getId() {
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
        RelationshipId that = (RelationshipId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

