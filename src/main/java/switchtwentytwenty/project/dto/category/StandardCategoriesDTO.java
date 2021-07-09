package switchtwentytwenty.project.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class StandardCategoriesDTO extends RepresentationModel<StandardCategoriesDTO> {

    @Getter
    @Setter
    private List<CategoryDTO> categoryDTOs;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StandardCategoriesDTO)) {
            return false;
        }
        StandardCategoriesDTO that = (StandardCategoriesDTO) o;
        return Objects.equals(categoryDTOs, that.categoryDTOs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryDTOs);
    }
}
