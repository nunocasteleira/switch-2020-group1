package switchtwentytwenty.project.datamodel.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryNameJPA;
import switchtwentytwenty.project.datamodel.shared.ParentCategoryIdJPA;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
public class BaseCategoryJPA {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Getter
    @AttributeOverride(name = "id", column = @Column(name = "category_id"))
    private CategoryIdJPA categoryId;
    @Embedded
    @Getter
    private CategoryNameJPA name;
    @Getter
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "parent_id"))
    private ParentCategoryIdJPA parentId;
    @Getter
    private boolean isStandard;

    /**
     * Constructor of a base category JPA non-root, i.e, a child category JPA that has a parent
     * category JPA.
     *
     * @param categoryId id of the category
     * @param name       name of the category
     * @param parentId   id of the parent category
     * @param isStandard if it is a Standard category
     */
    public BaseCategoryJPA(CategoryIdJPA categoryId, CategoryNameJPA name, ParentCategoryIdJPA parentId, boolean isStandard) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
        this.isStandard = isStandard;
    }

    /**
     * Constructor of a root base category JPA, i.e, a parent category JPA.
     *
     * @param categoryId id of the category
     * @param name       name of the category
     * @param isStandard if it is a Standard category
     */
    public BaseCategoryJPA(CategoryIdJPA categoryId, CategoryNameJPA name, boolean isStandard) {
        this.categoryId = categoryId;
        this.name = name;
        this.isStandard = isStandard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseCategoryJPA that = (BaseCategoryJPA) o;
        return id == that.id && isStandard == that.isStandard && categoryId.equals(that.categoryId) && name.equals(that.name) && Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, name, parentId, isStandard);
    }

}
