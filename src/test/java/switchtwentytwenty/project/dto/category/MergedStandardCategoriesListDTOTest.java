package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.category.CategoryDTO;
import switchtwentytwenty.project.dto.category.MergedStandardCategoriesListDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergedStandardCategoriesListDTOTest {

    @Test
    void getMergedStandardCategoriesListDTO_Successfully() {
        CategoryDTO aCategoryDto = new CategoryDTO(1, "House", 1);
        CategoryDTO anotherCategoryDto = new CategoryDTO(2, "Car", 2);
        CategoryDTO aCategoryWithParent = new CategoryDTO(3, "Water Bills", 1, 3);
        List<CategoryDTO> standardCategories = new ArrayList<>();
        standardCategories.add(aCategoryDto);
        standardCategories.add(anotherCategoryDto);
        standardCategories.add(aCategoryWithParent);

        MergedStandardCategoriesListDTO result = new MergedStandardCategoriesListDTO(standardCategories);

        assertNotNull(result);
        assertEquals(result.getStandardCategories(), standardCategories);
    }
    @Test
    void ensureNoArgsConstructorIsWorking() {
        CategoryDTO aCategoryDto = new CategoryDTO(1, "House", 1);
        CategoryDTO anotherCategoryDto = new CategoryDTO(2, "Car", 2);
        CategoryDTO aCategoryWithParent = new CategoryDTO(3, "Water Bills", 1, 3);
        List<CategoryDTO> standardCategories = new ArrayList<>();
        standardCategories.add(aCategoryDto);
        standardCategories.add(anotherCategoryDto);
        standardCategories.add(aCategoryWithParent);

        MergedStandardCategoriesListDTO result = new MergedStandardCategoriesListDTO();
        result.setStandardCategories(standardCategories);

        assertNotNull(result);
        assertEquals(result.getStandardCategories(), standardCategories);
    }

    @Test
    void testEqualsAndHashCode() {
        CategoryDTO aCategoryDto = new CategoryDTO(1, "House", 1);
        CategoryDTO anotherCategoryDto = new CategoryDTO(2, "Car", 2);
        CategoryDTO aCategoryWithParent = new CategoryDTO(3, "Water Bills", 1, 3);
        List<CategoryDTO> standardCategories = new ArrayList<>();
        standardCategories.add(aCategoryDto);
        standardCategories.add(anotherCategoryDto);
        standardCategories.add(aCategoryWithParent);
        List<CategoryDTO> differentList = new ArrayList<>();
        differentList.add(anotherCategoryDto);

        MergedStandardCategoriesListDTO mergedListDTO = new MergedStandardCategoriesListDTO(standardCategories);
        MergedStandardCategoriesListDTO mergedListDTOSame = mergedListDTO;
        MergedStandardCategoriesListDTO mergedListDTOEqual = new MergedStandardCategoriesListDTO(standardCategories);
        MergedStandardCategoriesListDTO mergedListDTODifferent = new MergedStandardCategoriesListDTO(differentList);

        assertEquals(mergedListDTO, mergedListDTOSame);
        assertEquals(mergedListDTO, mergedListDTOEqual);
        assertSame(mergedListDTO, mergedListDTOSame);
        assertNotSame(mergedListDTO, mergedListDTOEqual);
        assertEquals(mergedListDTO.getStandardCategories(), mergedListDTOSame.getStandardCategories());
        assertEquals(mergedListDTO.hashCode(), mergedListDTOSame.hashCode());
        assertEquals(mergedListDTO.getStandardCategories(), mergedListDTOEqual.getStandardCategories());
        assertEquals(mergedListDTO.hashCode(), mergedListDTOEqual.hashCode());
        assertNotEquals(mergedListDTO, mergedListDTODifferent);
        assertNotEquals(mergedListDTO.getStandardCategories(), mergedListDTODifferent.getStandardCategories());
        assertNotEquals(mergedListDTO.hashCode(), mergedListDTODifferent.hashCode());
        assertTrue(mergedListDTO.equals(mergedListDTOSame));
        assertTrue(mergedListDTO.equals(mergedListDTOEqual));
        assertFalse(mergedListDTO.equals(mergedListDTODifferent));
        assertFalse(mergedListDTO.equals(null));
        assertFalse(mergedListDTO.equals(aCategoryDto));
        assertNotEquals(0, mergedListDTO.hashCode());
        assertNotEquals(0, mergedListDTODifferent.hashCode());
    }
}