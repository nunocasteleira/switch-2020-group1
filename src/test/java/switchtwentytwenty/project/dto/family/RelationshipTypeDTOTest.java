package switchtwentytwenty.project.dto.family;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipTypeDTOTest {

    @Test
    void ensureNoArgsConstructorIsWorking(){
        int value = 1;
        String type = "MOTHER";
        RelationshipTypeDTO relationshipTypeDTO = new RelationshipTypeDTO();
        relationshipTypeDTO.setNumericValue(value);
        relationshipTypeDTO.setRelationshipType(type);

        assertNotNull(relationshipTypeDTO);
        assertEquals(value, relationshipTypeDTO.getNumericValue());
        assertEquals(type, relationshipTypeDTO.getRelationshipType());
    }

    @Test
    void testEqualsAndHashCode(){
        int value = 1;
        int valueOther = 2;
        String type = "MOTHER";
        String typeOther = "FATHER";
        RelationshipTypeDTO relationshipTypeDTO = new RelationshipTypeDTO(value, type);
        RelationshipTypeDTO relationshipTypeDTOSame = relationshipTypeDTO;
        RelationshipTypeDTO relationshipTypeDTOEqual = new RelationshipTypeDTO(value, type);
        RelationshipTypeDTO relationshipTypeDTOOther = new RelationshipTypeDTO(valueOther, typeOther);

        assertEquals(relationshipTypeDTO, relationshipTypeDTOSame);
        assertSame(relationshipTypeDTO, relationshipTypeDTOSame);
        assertEquals(relationshipTypeDTO, relationshipTypeDTOEqual);
        assertNotSame(relationshipTypeDTO, relationshipTypeDTOEqual);
        assertEquals(relationshipTypeDTO.getNumericValue(), relationshipTypeDTOSame.getNumericValue());
        assertEquals(relationshipTypeDTO.getRelationshipType(), relationshipTypeDTOSame.getRelationshipType());
        assertEquals(relationshipTypeDTO.hashCode(), relationshipTypeDTOSame.hashCode());
        assertEquals(relationshipTypeDTO.getNumericValue(), relationshipTypeDTOEqual.getNumericValue());
        assertEquals(relationshipTypeDTO.getRelationshipType(), relationshipTypeDTOEqual.getRelationshipType());
        assertEquals(relationshipTypeDTO.hashCode(), relationshipTypeDTOEqual.hashCode());
        assertNotEquals(relationshipTypeDTO,relationshipTypeDTOOther);
        assertNotEquals(relationshipTypeDTO.getNumericValue(), relationshipTypeDTOOther.getNumericValue());
        assertNotEquals(relationshipTypeDTO.getRelationshipType(), relationshipTypeDTOOther.getRelationshipType());
        assertNotEquals(relationshipTypeDTO.hashCode(), relationshipTypeDTOOther.hashCode());
        assertNotEquals(0, relationshipTypeDTO.hashCode());
        assertNotEquals(0, relationshipTypeDTO.getNumericValue());
        assertNotEquals(null, relationshipTypeDTO);
        assertNotEquals(relationshipTypeDTO, value);
        assertFalse(relationshipTypeDTO.equals(relationshipTypeDTOOther));
        assertFalse(relationshipTypeDTO.getRelationshipType().equals(relationshipTypeDTOOther.getRelationshipType()));
        assertFalse(relationshipTypeDTO.getNumericValue() == relationshipTypeDTOOther.getNumericValue());
        assertTrue(relationshipTypeDTO.equals(relationshipTypeDTOEqual));
        assertTrue(relationshipTypeDTO.getRelationshipType().equals(relationshipTypeDTOEqual.getRelationshipType()));
        assertTrue(relationshipTypeDTO.getNumericValue() == relationshipTypeDTOEqual.getNumericValue());
    }
}