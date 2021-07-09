package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.category.ExternalCategoryDTO;

import static org.junit.jupiter.api.Assertions.*;

class ExternalCategoryDTOTest {

    @Test
    void testMethodToExternalCategoryDTO_withParentId() {
        String categoryId = "aCategoryId";
        String name = "aCategoryName";
        String parentId = "aParentId";
        String link = "a/fake/link/to";
        String expectedCategoryId = "a/fake/link/to/aCategoryId";
        String expectedParentId = "a/fake/link/to/aParentId";

        ExternalCategoryDTO result = ExternalCategoryDTO.toExternalCategoryDTO(categoryId, name, parentId, link);

        assertNotNull(result);
        assertEquals(result.getCategoryId(), expectedCategoryId);
        assertEquals(result.getName(), name);
        assertEquals(result.getParentId(), expectedParentId);
    }
    @Test
    void testMethodToExternalCategoryDTO_withoutParent() {
        String categoryId = "aCategoryId";
        String name = "aCategoryName";
        String parentId = "null";
        String link = "a/fake/link/to";
        String expectedCategoryId = "a/fake/link/to/aCategoryId";

        ExternalCategoryDTO result = ExternalCategoryDTO.toExternalCategoryDTO(categoryId, name, parentId, link);

        assertNotNull(result);
        assertEquals(result.getCategoryId(), expectedCategoryId);
        assertEquals(result.getName(), name);
        assertEquals(result.getParentId(), parentId);
    }

    @Test
    void testEqualsAndHashCode() {
        String categoryId = "aCategoryId";
        String anotherCategoryId = "anotherCategoryId";
        String name = "aCategoryName";
        String anotherName = "anotherCategoryName";
        String parentId = "null";
        String anotherParentId = "notNull";

        ExternalCategoryDTO externalCategoryDTO = new ExternalCategoryDTO(categoryId, name, parentId);
        ExternalCategoryDTO externalCategoryDTOSame = externalCategoryDTO;
        ExternalCategoryDTO externalCategoryDTOEquals = new ExternalCategoryDTO(categoryId, name, parentId);
        ExternalCategoryDTO externalCategoryDTODifferentId = new ExternalCategoryDTO(anotherCategoryId, name, parentId);
        ExternalCategoryDTO externalCategoryDTODifferentName = new ExternalCategoryDTO(categoryId, anotherName, parentId);
        ExternalCategoryDTO externalCategoryDTODifferentParent = new ExternalCategoryDTO(categoryId, name, anotherParentId);

        assertNotNull(externalCategoryDTO);
        assertEquals(externalCategoryDTO, externalCategoryDTOSame);
        assertEquals(externalCategoryDTO, externalCategoryDTOEquals);
        assertSame(externalCategoryDTO, externalCategoryDTOSame);
        assertNotSame(externalCategoryDTO, externalCategoryDTOEquals);
        assertEquals(externalCategoryDTO.getCategoryId(), externalCategoryDTOSame.getCategoryId());
        assertEquals(externalCategoryDTO.getName(), externalCategoryDTOSame.getName());
        assertEquals(externalCategoryDTO.getParentId(), externalCategoryDTOSame.getParentId());
        assertEquals(externalCategoryDTO.hashCode(), externalCategoryDTOSame.hashCode());
        assertEquals(externalCategoryDTO.hashCode(), externalCategoryDTOEquals.hashCode());
        assertNotEquals(externalCategoryDTO, externalCategoryDTODifferentId);
        assertNotEquals(externalCategoryDTO, externalCategoryDTODifferentName);
        assertNotEquals(externalCategoryDTO, externalCategoryDTODifferentParent);
        assertNotEquals(externalCategoryDTO.hashCode(), externalCategoryDTODifferentId.hashCode());
        assertNotEquals(externalCategoryDTO.hashCode(), externalCategoryDTODifferentName.hashCode());
        assertNotEquals(externalCategoryDTO.hashCode(), externalCategoryDTODifferentParent.hashCode());
        assertNotEquals(externalCategoryDTO.getCategoryId(), externalCategoryDTODifferentId.getCategoryId());
        assertEquals(externalCategoryDTO.getName(), externalCategoryDTODifferentId.getName());
        assertEquals(externalCategoryDTO.getParentId(), externalCategoryDTODifferentId.getParentId());
        assertNotEquals(externalCategoryDTO.getName(), externalCategoryDTODifferentName.getName());
        assertNotEquals(externalCategoryDTO.getParentId(), externalCategoryDTODifferentParent.getCategoryId());
        assertTrue(externalCategoryDTO.equals(externalCategoryDTOSame));
        assertTrue(externalCategoryDTO.equals(externalCategoryDTOEquals));
        assertFalse(externalCategoryDTO.equals(externalCategoryDTODifferentId));
        assertFalse(externalCategoryDTO.equals(externalCategoryDTODifferentName));
        assertFalse(externalCategoryDTO.equals(externalCategoryDTODifferentParent));
        assertFalse(externalCategoryDTO.equals(null));
        assertFalse(externalCategoryDTO.equals(categoryId));
        assertNotEquals(0, externalCategoryDTO.hashCode());
        assertNotEquals(0, externalCategoryDTODifferentId.hashCode());
    }
}