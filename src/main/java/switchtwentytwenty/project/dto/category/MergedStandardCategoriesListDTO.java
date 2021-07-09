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
public class MergedStandardCategoriesListDTO extends RepresentationModel<MergedStandardCategoriesListDTO> {

    @Getter
    @Setter
    private List<CategoryDTO> standardCategories;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MergedStandardCategoriesListDTO that = (MergedStandardCategoriesListDTO) o;
        return Objects.equals(standardCategories, that.standardCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), standardCategories);
    }
}
