package switchtwentytwenty.project.datamodel.category;

import lombok.NoArgsConstructor;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryNameJPA;
import switchtwentytwenty.project.datamodel.shared.ParentCategoryIdJPA;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class StandardCategoryJPA extends BaseCategoryJPA {
    /**
     * Constructor of a standard category JPA non-root, i.e, a child category JPA that has a parent
     * category JPA.
     *
     * @param categoryId id of the category
     * @param name       name of the category
     * @param parentId   id of the parent category
     */
    public StandardCategoryJPA(CategoryIdJPA categoryId, CategoryNameJPA name, ParentCategoryIdJPA parentId) {
        super(categoryId, name, parentId, true);
    }

    /**
     * Constructor of a root standard category JPA, i.e, a parent category JPA.
     *
     * @param categoryId id of the category
     * @param name       name of the category
     */
    public StandardCategoryJPA(CategoryIdJPA categoryId, CategoryNameJPA name) {
        super(categoryId, name, true);
    }
}
