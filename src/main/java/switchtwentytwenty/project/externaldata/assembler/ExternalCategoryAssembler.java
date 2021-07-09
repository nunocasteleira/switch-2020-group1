package switchtwentytwenty.project.externaldata.assembler;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.dto.category.ExternalCategoryDTO;

@NoArgsConstructor
@Component
public class ExternalCategoryAssembler {

    /**
     * This method allows to convert a string to a CategoryId object.
     *
     * @param externalCategoryDTO a dto of strings (categoryId, name and parentId).
     * @return the converted CategoryId object.
     */
    public  CategoryId fromPrimitiveToCategoryId(ExternalCategoryDTO externalCategoryDTO) {
        return new CategoryId(externalCategoryDTO.getCategoryId());
    }

    /**
     * This method allows to convert a string to a CategoryName object.
     *
     * @param externalCategoryDTO a dto of strings (categoryId, name and parentId).
     * @return the converted CategoryName object.
     */
    public  CategoryName fromPrimitiveToCategoryName(ExternalCategoryDTO externalCategoryDTO) {
        return new CategoryName(externalCategoryDTO.getName());
    }

    /**
     * This method allows to convert a string to a CategoryId object.
     *
     * @param externalCategoryDTO a dto of strings (categoryId, name and parentId).
     * @return the converted CategoryId object.
     */
    public  CategoryId fromPrimitiveToParentId(ExternalCategoryDTO externalCategoryDTO) {
        CategoryId parentId;
        String parentIdString = externalCategoryDTO.getParentId();
        if (parentIdString.equals("null")) {
            parentId = null;
        } else {
            parentId = new CategoryId(parentIdString);
        }
        return parentId;
    }
}