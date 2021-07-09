package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.category.CategoryDTO;
import switchtwentytwenty.project.dto.category.StandardCategoriesDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StandardCategoriesDTOTest {

    @Test
    void getStandardCategoriesList() {

        //Arrange
        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        CategoryDTO aCategoryDto = new CategoryDTO(1, "House", 1);
        CategoryDTO anotherCategoryDto = new CategoryDTO(2, "Car", 2);

        categoryDTOs.add(aCategoryDto);
        categoryDTOs.add(anotherCategoryDto);

        //Act
        StandardCategoriesDTO result = new StandardCategoriesDTO(categoryDTOs);


        //Assert
        assertNotNull(result);
        assertEquals(result.getCategoryDTOs(), categoryDTOs);
    }

    @Test
    void ensureAllArgsConstructorIsWorking() {
        CategoryDTO aCategoryDto = new CategoryDTO(1, "House", 1);
        CategoryDTO anotherCategoryDto = new CategoryDTO(2, "Car", 2);
        List<CategoryDTO> standardCategories = new ArrayList<>();
        standardCategories.add(aCategoryDto);
        standardCategories.add(anotherCategoryDto);

        StandardCategoriesDTO result = new StandardCategoriesDTO(standardCategories);
        result.setCategoryDTOs(standardCategories);

        assertNotNull(result);
        assertEquals(result.getCategoryDTOs(), standardCategories);
    }

    @Test
    void testEquals() {
        CategoryDTO aCategoryDto = new CategoryDTO(1, "House", 1);
        CategoryDTO anotherCategoryDto = new CategoryDTO(2, "Car", 2);
        List<CategoryDTO> standardCategories = new ArrayList<>();
        standardCategories.add(aCategoryDto);
        standardCategories.add(anotherCategoryDto);
        List<CategoryDTO> differentList = new ArrayList<>();
        differentList.add(anotherCategoryDto);

        StandardCategoriesDTO standardCategoriesDTO = new StandardCategoriesDTO(standardCategories);
        StandardCategoriesDTO standardCategoriesDTOSame = standardCategoriesDTO;
        StandardCategoriesDTO standardCategoriesDTOEqual = new StandardCategoriesDTO(standardCategories);
        StandardCategoriesDTO standardCategoriesDTODifferent = new StandardCategoriesDTO(differentList);

        assertEquals(standardCategoriesDTO, standardCategoriesDTOSame);
        assertEquals(standardCategoriesDTO, standardCategoriesDTOEqual);
        assertSame(standardCategoriesDTO, standardCategoriesDTOSame);
        assertNotSame(standardCategoriesDTO, standardCategoriesDTOEqual);
        assertEquals(standardCategoriesDTO.getCategoryDTOs(), standardCategoriesDTOSame.getCategoryDTOs());
        assertEquals(standardCategoriesDTO.hashCode(), standardCategoriesDTOSame.hashCode());
        assertEquals(standardCategoriesDTO.getCategoryDTOs(), standardCategoriesDTOEqual.getCategoryDTOs());
        assertEquals(standardCategoriesDTO.hashCode(), standardCategoriesDTOEqual.hashCode());
        assertNotEquals(standardCategoriesDTO, standardCategoriesDTODifferent);
        assertNotEquals(standardCategoriesDTO.getCategoryDTOs(), standardCategoriesDTODifferent.getCategoryDTOs());
        assertNotEquals(standardCategoriesDTO.hashCode(), standardCategoriesDTODifferent.hashCode());
        assertTrue(standardCategoriesDTO.equals(standardCategoriesDTOSame));
        assertTrue(standardCategoriesDTO.equals(standardCategoriesDTOEqual));
        assertFalse(standardCategoriesDTO.equals(standardCategoriesDTODifferent));
        assertFalse(standardCategoriesDTO.equals(null));
        assertFalse(standardCategoriesDTO.equals(aCategoryDto));
        assertNotEquals(0, standardCategoriesDTO.hashCode());
        assertNotEquals(0, standardCategoriesDTODifferent.hashCode());
    }
}
