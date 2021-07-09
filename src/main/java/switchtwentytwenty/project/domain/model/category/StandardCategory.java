package switchtwentytwenty.project.domain.model.category;

import switchtwentytwenty.project.domain.model.interfaces.Entity;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;

public class StandardCategory extends BaseCategory implements Entity {

    /**
     * Constructor of a root standard category, i.e, a parent category.
     *
     * @param name name of the category
     */
    public StandardCategory(CategoryName name) {
        this.name = name;
        this.isStandard = true;
        this.id = new CategoryId(hashCode());
    }


    /**
     * Constructor of a standard category non-root, i.e, a child category.
     *
     * @param name     name of the category
     * @param parentId id of the parent category
     */
    public StandardCategory(CategoryName name, CategoryId parentId) {
        this.name = name;
        this.parentId = parentId;
        this.isStandard = true;
        this.id = new CategoryId(hashCode());
    }

    /**
     * Constructor method for root External Standard Category.
     *
     * @param categoryId id of the category.
     * @param name       name of the category.
     */
    public StandardCategory(CategoryId categoryId, CategoryName name) {
        this.name = name;
        this.isStandard = true;
        this.id = categoryId;
    }

    /**
     * Constructor of a non-root External Standard Category (child category).
     *
     * @param categoryId id of the category.
     * @param name       name of the category.
     * @param parentId   id of the parent category.
     */
    public StandardCategory(CategoryId categoryId, CategoryName name, CategoryId parentId) {
        this.name = name;
        this.parentId = parentId;
        this.isStandard = true;
        this.id = categoryId;
    }
}
