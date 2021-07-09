package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.category.BaseCategory;
import switchtwentytwenty.project.domain.model.category.FamilyCategory;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.domain.model.shared.FamilyId;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FamilyCategoryTreeMapperTest {

    FamilyCategoryTreeMapper mapper = new FamilyCategoryTreeMapper();

    @Test
    void getFamilyCategoryTreeDTO() {
        FamilyId familyId = new FamilyId(1);
        List<BaseCategory> familyCategories = new ArrayList<>();
        StandardCategory categoryOne = new StandardCategory(new CategoryName("categoryOne"));
        familyCategories.add(categoryOne);
        FamilyCategory categoryTwo = new FamilyCategory(new CategoryName("categoryTwo"), familyId);
        familyCategories.add(categoryTwo);

        CategoryDTO standardCategoryDTO = new CategoryDTO(categoryOne.getId().getId(), categoryOne.getName().toString(), categoryOne.getIdDatabase());
        CategoryDTO familyCategoryDTO = new CategoryDTO(categoryTwo.getId().getId(), categoryTwo.getName().toString(), categoryTwo.getIdDatabase());
        standardCategoryDTO.setChildCategories(new ArrayList<>());
        familyCategoryDTO.setChildCategories(new ArrayList<>());
        List<CategoryDTO> familyCategoryDTOList = new ArrayList<>();
        familyCategoryDTOList.add(standardCategoryDTO);
        familyCategoryDTOList.add(familyCategoryDTO);
        CategoriesTreeDTO expected = new CategoriesTreeDTO(familyCategoryDTOList);

        CategoriesTreeDTO result = mapper.getFamilyCategoryTreeDTO(familyCategories);

        assertNotNull(result);
        assertEquals(expected, result);
    }
}