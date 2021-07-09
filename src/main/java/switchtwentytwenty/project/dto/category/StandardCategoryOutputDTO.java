package switchtwentytwenty.project.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class StandardCategoryOutputDTO extends RepresentationModel<StandardCategoryOutputDTO> {

    @Getter
    private Object categoryId;

    @Getter
    private String categoryName;

    @Getter
    private Object parentCategoryId;

    @Getter
    private long idDatabase;

    /**
     * Constructor for the categoryOutputDTO object without the parentCategoryId.
     *
     * @param categoryId   the id of the category.
     * @param categoryName the name of the category.
     */
    public StandardCategoryOutputDTO(Object categoryId, String categoryName, long idDatabase) {
        this.categoryId = categoryId;
        this.categoryName = categoryName.toUpperCase();
        this.idDatabase = idDatabase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StandardCategoryOutputDTO that = (StandardCategoryOutputDTO) o;
        return categoryId.equals(that.categoryId) && categoryName.equalsIgnoreCase(that.categoryName) && Objects.equals(parentCategoryId, that.parentCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), categoryId, categoryName, parentCategoryId, idDatabase);
    }
}
