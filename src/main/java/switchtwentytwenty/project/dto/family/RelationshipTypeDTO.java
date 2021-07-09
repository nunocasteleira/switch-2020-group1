package switchtwentytwenty.project.dto.family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class RelationshipTypeDTO {
    @Getter
    @Setter
    private int numericValue;
    @Getter
    @Setter
    private String relationshipType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelationshipTypeDTO)) {
            return false;
        }
        RelationshipTypeDTO that = (RelationshipTypeDTO) o;
        return numericValue == that.numericValue && relationshipType.equals(that.relationshipType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numericValue, relationshipType);
    }
}
