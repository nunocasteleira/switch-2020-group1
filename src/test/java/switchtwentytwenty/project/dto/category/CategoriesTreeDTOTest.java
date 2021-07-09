package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.dto.category.CategoriesTreeDTO;
import switchtwentytwenty.project.dto.category.CategoryDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriesTreeDTOTest {

    @Test
    void noArgsConstructor() {
        CategoriesTreeDTO standardCategoriesTreeDTO = new CategoriesTreeDTO();

        assertNotNull(standardCategoriesTreeDTO);
    }

    @Test
    void allArgsConstructorAndGetStandardCategoriesDTO() {
        CategoryDTO subStandardCategoryDTO = new CategoryDTO(1, "SubCategory", 1);
        ArrayList<CategoryDTO> arrayList = new ArrayList<>();
        arrayList.add(subStandardCategoryDTO);

        CategoriesTreeDTO standardCategoriesTreeDTO = new CategoriesTreeDTO(arrayList);

        List<CategoryDTO> expected = arrayList;
        List<CategoryDTO> result = standardCategoriesTreeDTO.getCategoriesDTO();

        assertNotNull(standardCategoriesTreeDTO);
        assertEquals(expected, result);
    }

    @Test
    void setAndGetStandardCategoriesDTO() {
        CategoryDTO subStandardCategoryDTO = new CategoryDTO(1, "SubCategory", 1);
        ArrayList<CategoryDTO> arrayList = new ArrayList<>();
        arrayList.add(subStandardCategoryDTO);

        CategoriesTreeDTO standardCategoriesTreeDTO = new CategoriesTreeDTO();
        standardCategoriesTreeDTO.setCategoriesDTO(arrayList);

        List<CategoryDTO> expected = arrayList;
        List<CategoryDTO> result = standardCategoriesTreeDTO.getCategoriesDTO();

        assertNotNull(standardCategoriesTreeDTO);
        assertEquals(expected, result);
    }

    @Test
    void testEquals() {
        CategoryDTO subStandardCategoryDTO = new CategoryDTO(1, "SubCategory", 1);
        ArrayList<CategoryDTO> arrayList = new ArrayList<>();
        arrayList.add(subStandardCategoryDTO);

        CategoryDTO subStandardCategoryDTO2 = new CategoryDTO(2, "SubCategory2", 2);
        ArrayList<CategoryDTO> arrayList2 = new ArrayList<>();
        arrayList.add(subStandardCategoryDTO2);


        CategoriesTreeDTO standardCategoriesTreeDTO = new CategoriesTreeDTO(arrayList);
        CategoriesTreeDTO standardCategoriesTreeDTOEmpty = new CategoriesTreeDTO();
        CategoriesTreeDTO standardCategoriesTreeDTOSame = standardCategoriesTreeDTO;
        CategoriesTreeDTO standardCategoriesTreeDTOSameEmpty = standardCategoriesTreeDTOEmpty;
        CategoriesTreeDTO standardCategoriesTreeOther = new CategoriesTreeDTO(arrayList);
        CategoriesTreeDTO standardCategoriesTreeDTODifferent = new CategoriesTreeDTO(arrayList2);

        assertEquals(standardCategoriesTreeDTO, standardCategoriesTreeDTOSame);
        assertSame(standardCategoriesTreeDTO, standardCategoriesTreeDTOSame);
        assertEquals(standardCategoriesTreeDTOEmpty, standardCategoriesTreeDTOSameEmpty);
        assertSame(standardCategoriesTreeDTOEmpty, standardCategoriesTreeDTOSameEmpty);
        assertEquals(standardCategoriesTreeDTO.hashCode(), standardCategoriesTreeDTOSame.hashCode());
        assertEquals(standardCategoriesTreeDTO, standardCategoriesTreeOther);
        assertNotSame(standardCategoriesTreeDTO, standardCategoriesTreeOther);
        assertEquals(standardCategoriesTreeDTO.hashCode(), standardCategoriesTreeOther.hashCode());
        assertNotEquals(0, standardCategoriesTreeDTO.hashCode());
        assertNotEquals(standardCategoriesTreeDTO, standardCategoriesTreeDTODifferent);
        assertNotEquals(null, standardCategoriesTreeDTO);
        assertNotEquals(subStandardCategoryDTO, standardCategoriesTreeDTO);
        assertFalse(standardCategoriesTreeDTO.equals(null));
        assertFalse(standardCategoriesTreeDTO.equals(new FamilyId(1)));
    }
}
