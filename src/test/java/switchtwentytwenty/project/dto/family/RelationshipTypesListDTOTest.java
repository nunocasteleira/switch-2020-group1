package switchtwentytwenty.project.dto.family;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipTypesListDTOTest {

    @Test
    void ensureNoArgsConstructorIsWorking(){
        List<RelationshipTypeDTO> relationshipTypesList = new ArrayList<>();
        int value = 1;
        String type = "PARENT";
        RelationshipTypeDTO relationshipTypeDTO = new RelationshipTypeDTO(value, type);
        relationshipTypesList.add(relationshipTypeDTO);

        RelationshipTypesListDTO relationshipTypesListDTO = new RelationshipTypesListDTO();
        relationshipTypesListDTO.setRelationshipTypesList(relationshipTypesList);

        assertNotNull(relationshipTypesListDTO);
        assertEquals(relationshipTypesList, relationshipTypesListDTO.getRelationshipTypesList());
    }

    @Test
    void testEqualsAndHashCode(){
        List<RelationshipTypeDTO> relationshipTypesList = new ArrayList<>();
        int value = 1;
        String type = "PARENT";
        RelationshipTypeDTO relationshipTypeDTO = new RelationshipTypeDTO(value, type);
        relationshipTypesList.add(relationshipTypeDTO);
        List<RelationshipTypeDTO> relationshipTypesListOther = new ArrayList<>();
        int valueOther = 2;
        String typeOther = "CHILD";
        RelationshipTypeDTO relationshipTypeDTOOther = new RelationshipTypeDTO(valueOther, typeOther);
        relationshipTypesListOther.add(relationshipTypeDTOOther);


        RelationshipTypesListDTO relationshipTypesListDTO = new RelationshipTypesListDTO(relationshipTypesList);
        RelationshipTypesListDTO relationshipTypesListDTOSame = relationshipTypesListDTO;
        RelationshipTypesListDTO relationshipTypesListDTOEqual = new RelationshipTypesListDTO(relationshipTypesList);
        RelationshipTypesListDTO relationshipTypesListDTOOther = new RelationshipTypesListDTO(relationshipTypesListOther);

        assertEquals(relationshipTypesListDTO, relationshipTypesListDTOSame);
        assertSame(relationshipTypesListDTO, relationshipTypesListDTOSame);
        assertEquals(relationshipTypesListDTO, relationshipTypesListDTOEqual);
        assertNotSame(relationshipTypesListDTO, relationshipTypesListDTOEqual);
        assertEquals(relationshipTypesListDTO.getRelationshipTypesList(), relationshipTypesListDTOSame.getRelationshipTypesList());
        assertEquals(relationshipTypesListDTO.hashCode(), relationshipTypesListDTOSame.hashCode());
        assertEquals(relationshipTypesListDTO.getRelationshipTypesList(), relationshipTypesListDTOEqual.getRelationshipTypesList());
        assertEquals(relationshipTypesListDTO.hashCode(), relationshipTypesListDTOEqual.hashCode());
        assertNotEquals(relationshipTypesListDTO,relationshipTypesListDTOOther);
        assertNotEquals(relationshipTypesListDTO.getRelationshipTypesList(), relationshipTypesListDTOOther.getRelationshipTypesList());
        assertNotEquals(relationshipTypesListDTO.hashCode(), relationshipTypesListDTOOther.hashCode());
        assertNotEquals(0, relationshipTypesListDTO.hashCode());
        assertNotEquals(null, relationshipTypesListDTO);
        assertNotEquals(relationshipTypesListDTO, type);
        assertFalse(relationshipTypesListDTO.equals(relationshipTypesListDTOOther));
        assertTrue(relationshipTypesListDTO.equals(relationshipTypesListDTOEqual));
    }
}