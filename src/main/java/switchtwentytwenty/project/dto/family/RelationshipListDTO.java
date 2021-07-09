package switchtwentytwenty.project.dto.family;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RelationshipListDTO extends RepresentationModel<RelationshipListDTO> {
    @Getter
    private final List<RelationshipOutputDTO> relationshipList;

    public RelationshipListDTO(List<RelationshipOutputDTO> relationshipList) {
        this.relationshipList = new ArrayList<>(relationshipList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelationshipListDTO that = (RelationshipListDTO) o;
        return Objects.equals(relationshipList, that.relationshipList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), relationshipList);
    }
}
