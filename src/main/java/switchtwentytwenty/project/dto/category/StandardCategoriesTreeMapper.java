package switchtwentytwenty.project.dto.category;

import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StandardCategoriesTreeMapper {

    public CategoriesTreeDTO getStandardCategoriesTreeDTO(List<StandardCategory> standardCategories) {
        List<CategoryDTO> standardCategoryDTOs = mapStandardCategoriesTree(standardCategories, null);
        return new CategoriesTreeDTO(standardCategoryDTOs);
    }

    private List<CategoryDTO> mapStandardCategoriesTree(List<StandardCategory> standardCategories, CategoryId parentId) {
        List<CategoryDTO> standardCategoryDTOs = new ArrayList<>();

        List<StandardCategory> filteredCategories = standardCategories.stream().filter(s -> s.getParentId() != parentId).collect(Collectors.toList());

        for (StandardCategory standardCategory : standardCategories) {
            CategoryId categoryIdVO = standardCategory.getId();
            CategoryId parentCategoryIdVO = standardCategory.getParentId();
            if (isSameCategory(parentCategoryIdVO, parentId)) {
                List<CategoryDTO> childCategories = mapStandardCategoriesTree(filteredCategories, categoryIdVO);
                CategoryDTO standardCategoryDTO = toDTO(standardCategory);
                standardCategoryDTO.setChildCategories(childCategories);
                standardCategoryDTOs.add(standardCategoryDTO);
            }
        }
        return standardCategoryDTOs;
    }

    public CategoryDTO toDTO(StandardCategory standardCategory) {
        Object categoryId = standardCategory.getId().getId();
        String categoryName = standardCategory.getName().toString();
        long idDatabase = standardCategory.getIdDatabase();

        if (standardCategory.getParentId() == null) {
            return new CategoryDTO(categoryId, categoryName, idDatabase);
        } else {
            Object categoryParentId = standardCategory.getParentId().getId();
            return new CategoryDTO(categoryId, categoryName, categoryParentId, idDatabase);
        }
    }

    private boolean isSameCategory(CategoryId categoryIdOne, CategoryId categoryIdTwo) {
        if (categoryIdOne == null && categoryIdTwo == null) {
            return true;
        }

        return categoryIdOne != null && categoryIdTwo != null && categoryIdOne.getId().equals(categoryIdTwo.getId());
    }
}
