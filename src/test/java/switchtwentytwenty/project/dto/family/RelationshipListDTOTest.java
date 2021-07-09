package switchtwentytwenty.project.dto.family;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.Relationship;
import switchtwentytwenty.project.domain.model.shared.RelationshipType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipListDTOTest {

    @Test
    void testEquals() {
        String mainUserId = "main@user.com";
        String mainUserName = "Main";
        String otherUserId = "other@user.com";
        String otherUserName = "Other";

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(mainUserId);
        Email email2 = new Email(otherUserId);

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);

        RelationshipOutputDTO relationshipOutputDTO =  relationshipMapper.toDTO(relationship1, mainUserName, otherUserName);

        List<RelationshipOutputDTO> relationshipListDTOList = new ArrayList<>();
        relationshipListDTOList.add(relationshipOutputDTO);
        RelationshipListDTO listDTO = new RelationshipListDTO(relationshipListDTOList);
        RelationshipListDTO listDTOSame = listDTO;
        RelationshipListDTO listDTOOther = new RelationshipListDTO(relationshipListDTOList);
        RelationshipListDTO listDTODifferent = new RelationshipListDTO(new ArrayList<>());

        assertSame(listDTO, listDTOSame);
        assertEquals(listDTO, listDTOSame);
        assertEquals(listDTO.getRelationshipList(), listDTOSame.getRelationshipList());
        assertEquals(listDTO.hashCode(), listDTOSame.hashCode());
        assertEquals(listDTO, listDTOOther);
        assertEquals(listDTO.getRelationshipList(), listDTOOther.getRelationshipList());
        assertEquals(listDTO.hashCode(), listDTOOther.hashCode());
        assertNotEquals(listDTO, listDTODifferent);
        assertNotEquals(listDTO.getRelationshipList(), listDTODifferent.getRelationshipList());
        assertNotEquals(listDTO.hashCode(), listDTODifferent.hashCode());
        assertNotEquals(null, listDTODifferent);
        assertNotSame(listDTO, listDTOOther);
        assertNotEquals(listDTO, relationshipOutputDTO);
        assertNotEquals(listDTO, email1);
        assertNotEquals(listDTO, listDTODifferent);
        assertNotEquals(null, listDTO);
        assertFalse(listDTO.equals(listDTODifferent));
        assertNotEquals(0, listDTO.hashCode());
        assertFalse(email1.equals(listDTO));
    }

    @Test
    void getRelationshipList() {
        String mainUserId = "main@user.com";
        String mainUserName = "Main";
        String otherUserId = "other@user.com";
        String otherUserName = "Other";

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(mainUserId);
        Email email2 = new Email(otherUserId);

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);

        RelationshipOutputDTO relationshipOutputDTO =  relationshipMapper.toDTO(relationship1, mainUserName, otherUserName);

        List<RelationshipOutputDTO> expected = new ArrayList<>();
        expected.add(relationshipOutputDTO);
        RelationshipListDTO listDTO = new RelationshipListDTO(expected);

        List<RelationshipOutputDTO> result = listDTO.getRelationshipList();

        assertEquals(expected, result);
    }
}