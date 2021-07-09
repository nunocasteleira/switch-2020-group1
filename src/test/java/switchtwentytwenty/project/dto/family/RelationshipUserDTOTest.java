package switchtwentytwenty.project.dto.family;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.family.RelationshipUserDTO;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipUserDTOTest {

    @Test
    void testEquals() {
        String name = "Name";
        String id = "id";
        String otherName = "Other Name";
        String otherId = "Other id";
        RelationshipUserDTO relationshipUserDTO = new RelationshipUserDTO(name, id);
        RelationshipUserDTO relationshipUserDTOSame = relationshipUserDTO;
        RelationshipUserDTO relationshipUserDTOOther = new RelationshipUserDTO(name, id);
        RelationshipUserDTO relationshipUserDTODifferent = new RelationshipUserDTO(otherName,
                otherId);
        RelationshipUserDTO relationshipUserDTODifferentId = new RelationshipUserDTO(name,
                otherId);
        RelationshipUserDTO relationshipUserDTODifferentName = new RelationshipUserDTO(otherName,
                id);

        assertEquals(relationshipUserDTO, relationshipUserDTOSame);
        assertSame(relationshipUserDTO, relationshipUserDTOSame);
        assertEquals(relationshipUserDTO.hashCode(), relationshipUserDTOSame.hashCode());
        assertEquals(relationshipUserDTO, relationshipUserDTOOther);
        assertNotSame(relationshipUserDTO, relationshipUserDTOOther);
        assertEquals(relationshipUserDTO.hashCode(), relationshipUserDTOOther.hashCode());
        assertNotEquals(0, relationshipUserDTO.hashCode());
        assertNotEquals(relationshipUserDTO, name);
        assertNotEquals(relationshipUserDTO, relationshipUserDTODifferent);
        assertNotEquals(relationshipUserDTO, relationshipUserDTODifferentId);
        assertNotEquals(relationshipUserDTO, relationshipUserDTODifferentName);
        assertNotEquals(null, relationshipUserDTO);
        assertFalse(relationshipUserDTO.equals(null));
        assertNotEquals(name, relationshipUserDTO);
        assertFalse(name.equals(relationshipUserDTO));

    }

    @Test
    void getUserName() {
        String expected = "Name";
        String id = "id";
        RelationshipUserDTO relationshipUserDTO = new RelationshipUserDTO(expected, id);

        String result = relationshipUserDTO.getUserName();

        assertEquals(expected, result);
    }

    @Test
    void getUserId() {
        String name = "Name";
        String expected = "id";
        RelationshipUserDTO relationshipUserDTO = new RelationshipUserDTO(name, expected);

        String result = relationshipUserDTO.getUserId();

        assertEquals(expected, result);
    }
}