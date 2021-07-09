package switchtwentytwenty.project.dto.category;

import switchtwentytwenty.project.domain.model.category.BaseCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FamilyCategoryTreeMapper {

    public CategoriesTreeDTO getFamilyCategoryTreeDTO(List<BaseCategory> unorderedCategoryList){
        List<CategoryDTO> familyCategoryTree = mapFamilyCategoriesTree(unorderedCategoryList, null);
        return new CategoriesTreeDTO(familyCategoryTree);
    }

    private List<CategoryDTO> mapFamilyCategoriesTree(List<BaseCategory> unorderedCategoryList, CategoryId parentId) {
        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        List<BaseCategory> filteredCategories = unorderedCategoryList.stream().filter(s -> s.getParentId() != parentId).collect(Collectors.toList());

        for (BaseCategory baseCategory : unorderedCategoryList) {
            CategoryId categoryIdVO = baseCategory.getId();
            CategoryId parentCategoryIdVO = baseCategory.getParentId();
            if (isSameCategory(parentCategoryIdVO, parentId)) {
                List<CategoryDTO> childCategories = mapFamilyCategoriesTree(filteredCategories, categoryIdVO);
                CategoryDTO standardCategoryDTO = toDTO(baseCategory);
                standardCategoryDTO.setChildCategories(childCategories);
                categoryDTOs.add(standardCategoryDTO);
            }
        }
        return categoryDTOs;
    }

    public CategoryDTO toDTO(BaseCategory category) {
        Object categoryId = category.getId().getId();
        String categoryName = category.getName().toString();
        long idDatabase = category.getIdDatabase();

        if (category.getParentId() == null) {
            return new CategoryDTO(categoryId, categoryName, idDatabase);
        } else {
            Object categoryParentId = category.getParentId().getId();
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
