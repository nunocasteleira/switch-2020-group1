package switchtwentytwenty.project.dto.category;

import switchtwentytwenty.project.domain.model.category.StandardCategory;

import java.util.ArrayList;
import java.util.List;

public class MergeCategoriesListMapper {

    StandardCategoriesTreeMapper standardCategoriesTreeMapper = new StandardCategoriesTreeMapper();

    public MergedStandardCategoriesListDTO mapMergedStandardCategoriesList(List<StandardCategory> standardCategories, List<StandardCategory> externalStandardCategories) {
        List<StandardCategory> mergedCategories = new ArrayList<>();
        mergedCategories.addAll(standardCategories);
        mergedCategories.addAll(externalStandardCategories);
        List<CategoryDTO> mergedCategoriesDTO = new ArrayList<>();
        for (StandardCategory aStandardCategory : mergedCategories) {
            CategoryDTO aStandardCategoryDTO = standardCategoriesTreeMapper.toDTO(aStandardCategory);
            mergedCategoriesDTO.add(aStandardCategoryDTO);
        }
        return new MergedStandardCategoriesListDTO(mergedCategoriesDTO);
    }
}