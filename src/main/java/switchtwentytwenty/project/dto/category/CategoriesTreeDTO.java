package switchtwentytwenty.project.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class CategoriesTreeDTO extends RepresentationModel<CategoriesTreeDTO> {
    @Getter
    @Setter
    private List<CategoryDTO> categoriesDTO;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CategoriesTreeDTO that = (CategoriesTreeDTO) o;
        return Objects.equals(categoriesDTO, that.categoriesDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), categoriesDTO);
    }
}
