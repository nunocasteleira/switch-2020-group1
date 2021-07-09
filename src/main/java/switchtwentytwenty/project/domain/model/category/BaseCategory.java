package switchtwentytwenty.project.domain.model.category;

import lombok.Getter;
import lombok.Setter;
import switchtwentytwenty.project.domain.model.interfaces.AggregateRoot;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;

import java.util.Objects;

public abstract class BaseCategory implements AggregateRoot<CategoryId> {
    protected CategoryId id;
    protected CategoryName name;
    protected CategoryId parentId;
    @Getter
    protected boolean isStandard = false;
    @Getter
    @Setter
    protected long idDatabase;

    /**
     * Method to get the category id.
     *
     * @return id of the category
     */
    public CategoryId getId() {
        return this.id;
    }

    /**
     * Method to set the Id of a Category.
     *
     * @param id id of the category
     */
    public void setId(CategoryId id) {
        this.id = id;
    }

    /**
     * Method to get the name of a category.
     *
     * @return name of the category
     */
    public CategoryName getName() {
        return name;
    }

    /**
     * Method to check the category Id
     *
     * @param id id of the category
     * @return true if the id is equal
     */
    @Override
    public boolean hasId(CategoryId id) {
        return id.equals(this.id);
    }

    /**
     * Method to get parent Id.
     *
     * @return the parent id of this category.
     */
    public CategoryId getParentId() {
        return parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseCategory that = (BaseCategory) o;
        return Objects.equals(name, that.name) && Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentId, isStandard);
    }

}
