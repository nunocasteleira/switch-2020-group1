package switchtwentytwenty.project.dto.family;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class RelationshipTypesListDTO extends RepresentationModel<RelationshipTypesListDTO> {
    @Getter
    @Setter
    private List<RelationshipTypeDTO> relationshipTypesList;

    /**
     * Constructor method for RelationshipTypesListDTO.
     * @param relationshipTypesList list of RelationshipTypeDTO.
     */
    public RelationshipTypesListDTO(List<RelationshipTypeDTO> relationshipTypesList) {
        this.relationshipTypesList =  new ArrayList<>(relationshipTypesList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelationshipTypesListDTO)) {
            return false;
        }
        RelationshipTypesListDTO that = (RelationshipTypesListDTO) o;
        return relationshipTypesList.equals(that.relationshipTypesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), relationshipTypesList);
    }
}
