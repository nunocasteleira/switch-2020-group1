package switchtwentytwenty.project.dto.category;

import switchtwentytwenty.project.domain.model.category.FamilyCategory;
import switchtwentytwenty.project.domain.model.category.StandardCategory;

import java.util.ArrayList;
import java.util.List;

public class MergeStandardFamilyCategoriesListMapper {


    StandardCategoriesTreeMapper standardCategoriesTreeMapper = new StandardCategoriesTreeMapper();

    FamilyCategoryTreeMapper familyCategoryTreeMapper = new FamilyCategoryTreeMapper();

    public StandardCategoriesDTO mapMergedStandardFamilyCategoriesList(List<StandardCategory> standardCategories, List<FamilyCategory> familyCategories) {

        List<CategoryDTO> mergedCategoriesDTO = new ArrayList<>();
        for (StandardCategory aStandardCategory : standardCategories) {
            CategoryDTO aStandardCategoryDTO = standardCategoriesTreeMapper.toDTO(aStandardCategory);
            mergedCategoriesDTO.add(aStandardCategoryDTO);
        }

        for (FamilyCategory aFamilyCategory : familyCategories) {
            CategoryDTO aFamilyCategoryDTO = familyCategoryTreeMapper.toDTO(aFamilyCategory);
            mergedCategoriesDTO.add(aFamilyCategoryDTO);
        }
        return new StandardCategoriesDTO(mergedCategoriesDTO);
    }

}
