package switchtwentytwenty.project.datamodel.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryNameJPA;
import switchtwentytwenty.project.datamodel.shared.ParentCategoryIdJPA;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class FamilyCategoryJPA extends BaseCategoryJPA {
    @Getter
    long familyId;

    /**
     * Constructor of a family category JPA non-root, i.e, a child category JPA that has a parent
     * category JPA.
     *
     * @param categoryId id of the category
     * @param name       name of the category
     * @param parentId   id of the parent category
     * @param familyId   id of the family that owns the category
     */
    public FamilyCategoryJPA(CategoryIdJPA categoryId, CategoryNameJPA name, ParentCategoryIdJPA parentId, long familyId) {
        super(categoryId, name, parentId, false);
        this.familyId = familyId;
    }

    /**
     * Constructor of a family category JPA non-root, i.e, a child category JPA that has a parent
     * category JPA.
     *
     * @param categoryId id of the category
     * @param name       name of the category
     * @param familyId   id of the family that owns the category
     */
    public FamilyCategoryJPA(CategoryIdJPA categoryId, CategoryNameJPA name, long familyId) {
        super(categoryId, name, false);
        this.familyId = familyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FamilyCategoryJPA that = (FamilyCategoryJPA) o;
        return familyId == that.familyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), familyId);
    }
}
