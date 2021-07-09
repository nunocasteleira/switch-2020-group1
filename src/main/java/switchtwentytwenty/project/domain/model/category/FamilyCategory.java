package switchtwentytwenty.project.domain.model.category;

import switchtwentytwenty.project.domain.model.interfaces.Entity;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.domain.model.shared.FamilyId;

import java.util.Objects;

public class FamilyCategory extends BaseCategory implements Entity {
    FamilyId familyId;

    public FamilyCategory(CategoryName name, FamilyId familyId) {
        this.name = name;
        this.familyId = familyId;
        this.id = new CategoryId(hashCode());
    }

    public FamilyCategory(CategoryName name, CategoryId parentCategoryId, FamilyId familyId) {
        this.name = name;
        this.parentId = parentCategoryId;
        this.familyId = familyId;
        this.id = new CategoryId(hashCode());
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
        FamilyCategory that = (FamilyCategory) o;
        return familyId.equals(that.familyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), familyId);
    }

    /**
     * Method to obtain the family id of the category.
     *
     * @return an instance of the familyId.
     */
    public FamilyId getFamilyId() {
        return familyId;
    }

}
