package switchtwentytwenty.project.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class FamilyCategoryOutputDTO extends RepresentationModel<FamilyCategoryOutputDTO> {

    @Getter
    private Object categoryId;

    @Getter
    private String categoryName;

    @Getter
    private Object parentCategoryId;

    @Getter
    private long familyId;

    @Getter
    private long idDatabase;

    /**
     * Constructor for the familyCategoryOutputDTO object without the parentCategoryId.
     *
     * @param categoryId   the id of the category.
     * @param categoryName the name of the category.
     * @param familyId     the family id to which the category belongs.
     * @param idDatabase   the database id of the category.
     */
    public FamilyCategoryOutputDTO(Object categoryId, String categoryName, long familyId, long idDatabase) {
        this.categoryId = categoryId;
        this.categoryName = categoryName.toUpperCase();
        this.familyId = familyId;
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
        FamilyCategoryOutputDTO that = (FamilyCategoryOutputDTO) o;
        return categoryId.equals(that.categoryId) && Objects.equals(parentCategoryId, that.parentCategoryId) && familyId == that.familyId && idDatabase == that.idDatabase && categoryName.equalsIgnoreCase(that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName, parentCategoryId, familyId, idDatabase);
    }
}
