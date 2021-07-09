package switchtwentytwenty.project.dto.category;

import org.springframework.stereotype.Component;
import switchtwentytwenty.project.domain.model.category.FamilyCategory;
import switchtwentytwenty.project.domain.model.category.StandardCategory;

@Component
public class CategoryMapper {

    /**
     * Method to obtain a categoryOutputDTO.
     *
     * @param standardCategory a standard category object.
     * @return a categoryOutputDTO
     */
    public StandardCategoryOutputDTO standardCategoryToDTO(StandardCategory standardCategory) {
        Object categoryId = standardCategory.getId().getId();
        String categoryName = standardCategory.getName().toString();
        long idDatabase = standardCategory.getIdDatabase();

        if (standardCategory.getParentId() == null) {
            return new StandardCategoryOutputDTO(categoryId, categoryName, idDatabase);
        } else {
            Object categoryParentId = standardCategory.getParentId().getId();
            return new StandardCategoryOutputDTO(categoryId, categoryName, categoryParentId, idDatabase);
        }
    }


    /**
     * Method to obtain a categoryOutputDTO.
     *
     * @param familyCategory a standard category object.
     * @return a categoryOutputDTO
     */
    public FamilyCategoryOutputDTO familyCategoryToDTO(FamilyCategory familyCategory) {
        Object categoryId = familyCategory.getId().getId();
        String categoryName = familyCategory.getName().toString();
        long idDatabase = familyCategory.getIdDatabase();
        long familyId = familyCategory.getFamilyId().getFamilyId();

        if (familyCategory.getParentId() == null) {
            return new FamilyCategoryOutputDTO(categoryId, categoryName, familyId, idDatabase);
        } else {
            Object categoryParentId = familyCategory.getParentId().getId();
            return new FamilyCategoryOutputDTO(categoryId, categoryName, categoryParentId, familyId, idDatabase);
        }
    }
}
