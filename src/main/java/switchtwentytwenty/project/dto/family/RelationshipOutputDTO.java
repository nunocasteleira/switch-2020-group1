package switchtwentytwenty.project.dto.family;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class RelationshipOutputDTO extends RepresentationModel<RelationshipOutputDTO> {
    @Getter
    private final RelationshipUserDTO mainUser;
    @Getter
    private final String relationshipType;
    @Getter
    private final RelationshipUserDTO otherUser;
    @Getter
    private final int relationshipId;

    /**
     * Constructor method for the Family Relationships Data Transfer Object, receiving the names of
     * the family members and the relationship type between them.
     *
     * @param mainUser         family member name and ID whose relation is from him/her towards
     *                         another person
     * @param relationshipType description of the relationship type between two family members
     * @param otherUser        family member name and ID who has family relationship with the main
     *                         person
     */
    public RelationshipOutputDTO(RelationshipUserDTO mainUser, String relationshipType, RelationshipUserDTO otherUser, int relationshipId) {
        this.mainUser = mainUser;
        this.relationshipType = relationshipType;
        this.otherUser = otherUser;
        this.relationshipId = relationshipId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelationshipOutputDTO that = (RelationshipOutputDTO) o;
        return Objects.equals(mainUser, that.mainUser)
                && Objects.equals(relationshipType, that.relationshipType)
                && Objects.equals(otherUser, that.otherUser)
                && relationshipId == that.relationshipId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mainUser, relationshipType, otherUser, relationshipId);
    }
}

