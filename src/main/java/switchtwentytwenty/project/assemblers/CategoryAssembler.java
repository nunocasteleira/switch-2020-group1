package switchtwentytwenty.project.assemblers;

import org.springframework.stereotype.Component;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.dto.category.CategoryInputDTO;

@Component
public class CategoryAssembler {

    /**
     * Method to get the categoryName from the input DTO.
     *
     * @param inputDTO an input DTO with all the necessary category data.
     * @return a categoryName
     */
    public CategoryName fromDTOToCategoryName(CategoryInputDTO inputDTO) {
        return new CategoryName(inputDTO.getName());
    }

    /**
     * Method to get the categoryId of the parent category from the input DTO.
     *
     * @param inputDTO an input DTO with all the necessary category data.
     * @return a categoryId from the parent category.
     */
    public CategoryId fromDTOToCategoryParent(CategoryInputDTO inputDTO) {
        return new CategoryId(inputDTO.getParentId());
    }

}


