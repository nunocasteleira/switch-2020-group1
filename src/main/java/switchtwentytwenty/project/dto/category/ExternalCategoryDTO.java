package switchtwentytwenty.project.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
public class ExternalCategoryDTO {
    @Getter
    String categoryId;
    @Getter
    String name;
    @Getter
    String parentId;

    public static ExternalCategoryDTO toExternalCategoryDTO(String categoryId, String name, String parentId, String link) {
        ExternalCategoryDTO dto;
        String fullQualifiedName = link + "/" + categoryId;

        if(!parentId.equals("null")){
            String fullQualifiedParentName = link + "/" + parentId;
            dto = new ExternalCategoryDTO(fullQualifiedName, name, fullQualifiedParentName);
        }
        else {
            dto = new ExternalCategoryDTO(fullQualifiedName, name, parentId);
        }
        return dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExternalCategoryDTO)) {
            return false;
        }
        ExternalCategoryDTO that = (ExternalCategoryDTO) o;
        return categoryId.equals(that.categoryId) && name.equals(that.name) && parentId.equals(that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, parentId);
    }
}
