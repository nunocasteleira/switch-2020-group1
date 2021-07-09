package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.dto.category.CategoriesTreeDTO;
import switchtwentytwenty.project.dto.category.CategoryDTO;
import switchtwentytwenty.project.dto.category.StandardCategoriesTreeMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StandardCategoriesTreeMapperTest {

    StandardCategoriesTreeMapper mapper = new StandardCategoriesTreeMapper();

    @Test
    void mapStandardCategoriesTree_Correctly() {
        List<StandardCategory> standardCategories = new ArrayList<>();
        StandardCategory categoryOne = new StandardCategory(new CategoryName("categoryOne"));
        standardCategories.add(categoryOne);

        CategoryDTO standardCategoryDTO = new CategoryDTO(categoryOne.getId().getId(), categoryOne.getName().toString(), categoryOne.getIdDatabase());
        standardCategoryDTO.setChildCategories(new ArrayList<>());
        List<CategoryDTO> standardCategoryDTOList = new ArrayList<>();
        standardCategoryDTOList.add(standardCategoryDTO);
        CategoriesTreeDTO expected = new CategoriesTreeDTO(standardCategoryDTOList);

        CategoriesTreeDTO result = mapper.getStandardCategoriesTreeDTO(standardCategories);

        assertNotNull(result);
        assertEquals(expected, result);
    }
}
