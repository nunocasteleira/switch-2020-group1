package switchtwentytwenty.project.dto.category;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

public class CategoryDTO {
    @Getter
    private Object id;
    @Getter
    private String name;
    @Getter
    private Object parentId;
    @Getter
    private final long idDatabase;
    @Getter
    @Setter
    private List<CategoryDTO> childCategories;

    public CategoryDTO(Object id, String name, Object parentId, long idDatabase) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.idDatabase = idDatabase;
    }

    public CategoryDTO(Object id, String name, long idDatabase) {
        this.id = id;
        this.name = name;
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
        CategoryDTO that = (CategoryDTO) o;
        return id.equals(that.id) && name.equals(that.name) && Objects.equals(parentId, that.parentId) && Objects.equals(childCategories, that.childCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentId, childCategories);
    }
}
