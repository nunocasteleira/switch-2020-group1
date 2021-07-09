package switchtwentytwenty.project.dto.family;

import lombok.Getter;

import java.util.Objects;

public class RelationshipUserDTO {
    @Getter
    private final String userName;
    @Getter
    private final String userId;

    /**
     * Constructor method for the User oriented to a Relationship. It encapsulates the Name and the
     * ID of a designated User.
     *
     * @param userName family member name
     * @param userId   family member ID
     */
    public RelationshipUserDTO(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelationshipUserDTO that = (RelationshipUserDTO) o;
        return Objects.equals(userName, that.userName) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userId);
    }
}
