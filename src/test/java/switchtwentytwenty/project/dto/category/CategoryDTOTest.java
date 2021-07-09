package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.category.CategoryDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDTOTest {

    @Test
    void notNullConstructor() {
        int id = 0;
        String name = "Name";
        int parentId = 5;
        long idDatabase = 1;

        CategoryDTO standardCategoryDTO1 = new CategoryDTO(id, name, idDatabase);
        CategoryDTO standardCategoryDTO2 = new CategoryDTO(id, name, parentId, idDatabase);

        assertNotNull(standardCategoryDTO1);
        assertNotNull(standardCategoryDTO2);
    }

    @Test
    void getId() {
        int expected = 0;

        int id = 0;
        String name = "Name";
        long idDatabase = 1;

        CategoryDTO standardCategoryDTO = new CategoryDTO(id, name, idDatabase);

        Object result = standardCategoryDTO.getId();

        assertEquals(expected, result);
    }

    @Test
    void getName() {
        String expected = "Name";

        int id = 0;
        String name = "Name";
        long idDatabase = 1;

        CategoryDTO standardCategoryDTO = new CategoryDTO(id, name, idDatabase);

        String result = standardCategoryDTO.getName();

        assertEquals(expected, result);
    }

    @Test
    void getParentId() {
        int expected = 5;

        int id = 0;
        String name = "Name";
        int parentId = 5;
        long idDatabase = 1;

        CategoryDTO standardCategoryDTO = new CategoryDTO(id, name, parentId, idDatabase);

        Object result = standardCategoryDTO.getParentId();

        assertEquals(expected, result);
    }

    @Test
    void getIdDatabase() {
        int expected = 1;

        int id = 0;
        String name = "Name";
        long idDatabase = 1;

        CategoryDTO standardCategoryDTO = new CategoryDTO(id, name, idDatabase);

        long result = standardCategoryDTO.getIdDatabase();

        assertEquals(expected, result);
    }

    @Test
    void setAndGetChildCategories() {
        CategoryDTO subStandardCategoryDTO = new CategoryDTO(1, "SubCategory", 2);
        ArrayList<CategoryDTO> arrayList = new ArrayList<>();
        arrayList.add(subStandardCategoryDTO);

        List<CategoryDTO> expected = arrayList;

        int id = 0;
        String name = "Name";
        long idDatabase = 1;

        CategoryDTO standardCategoryDTO = new CategoryDTO(id, name, idDatabase);

        List<CategoryDTO> standardCategoryDTOList = new ArrayList<>();
        standardCategoryDTOList.add(subStandardCategoryDTO);

        standardCategoryDTO.setChildCategories(standardCategoryDTOList);

        List<CategoryDTO> result = standardCategoryDTO.getChildCategories();

        assertEquals(expected, result);
    }

    @Test
    void testEquals() {
        int id1 = 0;
        String name1 = "Name";
        int parentId1 = 5;
        long idDatabase1 = 1;

        int id2 = 1;
        String name2 = "Name2";
        int parentId2 = 6;
        long idDatabase2 = 2;


        CategoryDTO standardCategoryDTO = new CategoryDTO(id1, name1, parentId1, idDatabase1);
        CategoryDTO standardCategoryDTOSame = standardCategoryDTO;
        CategoryDTO standardCategoryDTOOther = new CategoryDTO(id1, name1, parentId1, idDatabase1);
        CategoryDTO standardCategoryDTODifferent = new CategoryDTO(id2, name2, parentId2, idDatabase2);
        CategoryDTO standardCategoryDTODifferentId = new CategoryDTO(id2, name1, parentId1, idDatabase1);
        CategoryDTO standardCategoryDTODifferentName = new CategoryDTO(id1, name2, parentId1, idDatabase1);
        CategoryDTO standardCategoryDTODifferentParentId = new CategoryDTO(id1, name1, parentId2, idDatabase1);
        CategoryDTO standardCategoryDTODifferentIdDatabase = new CategoryDTO(id1, name1, parentId1, idDatabase2);

        assertEquals(standardCategoryDTO, standardCategoryDTOSame);
        assertSame(standardCategoryDTO, standardCategoryDTOSame);
        assertEquals(standardCategoryDTO.hashCode(), standardCategoryDTOSame.hashCode());
        assertEquals(standardCategoryDTO, standardCategoryDTOOther);
        assertNotSame(standardCategoryDTO, standardCategoryDTOOther);
        assertEquals(standardCategoryDTO.hashCode(), standardCategoryDTOOther.hashCode());
        assertNotEquals(0, standardCategoryDTO.hashCode());
        assertNotEquals(standardCategoryDTO, name1);
        assertNotEquals(standardCategoryDTO, standardCategoryDTODifferent);
        assertNotEquals(standardCategoryDTO, standardCategoryDTODifferentId);
        assertNotEquals(standardCategoryDTO, standardCategoryDTODifferentName);
        assertNotEquals(standardCategoryDTO, standardCategoryDTODifferentParentId);
        assertNotEquals(null, standardCategoryDTO);
        assertFalse(standardCategoryDTO.equals(id1));
        assertFalse(standardCategoryDTO.equals(name1));
        assertFalse(standardCategoryDTO.equals(parentId1));
        assertFalse(standardCategoryDTO.equals(idDatabase1));
        assertNotEquals(standardCategoryDTO, standardCategoryDTODifferent);
        assertFalse(standardCategoryDTO.equals(standardCategoryDTODifferentId));
        assertFalse(standardCategoryDTO.equals(standardCategoryDTODifferentName));
        assertFalse(standardCategoryDTO.equals(standardCategoryDTODifferentParentId));
        assertFalse(standardCategoryDTO.equals(standardCategoryDTODifferent));
        assertEquals(standardCategoryDTO.hashCode(), standardCategoryDTOSame.hashCode());
        assertNotEquals(standardCategoryDTO, name2);
        assertNotEquals(standardCategoryDTO, idDatabase2);
        assertNotEquals(standardCategoryDTO, id2);
        assertNotEquals(standardCategoryDTO.hashCode(), standardCategoryDTODifferentId.hashCode());
        assertNotEquals(standardCategoryDTO.hashCode(), standardCategoryDTODifferentName.hashCode());
        assertNotEquals(standardCategoryDTO.hashCode(), standardCategoryDTODifferentParentId.hashCode());
        assertNotEquals(standardCategoryDTO.hashCode(), standardCategoryDTODifferent.hashCode());
        assertNotEquals(standardCategoryDTO, standardCategoryDTODifferent);
    }

}
