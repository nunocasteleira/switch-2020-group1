package switchtwentytwenty.project.dto.family;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.Relationship;
import switchtwentytwenty.project.domain.model.shared.RelationshipType;
import switchtwentytwenty.project.dto.family.RelationshipMapper;
import switchtwentytwenty.project.dto.family.RelationshipOutputDTO;
import switchtwentytwenty.project.dto.family.RelationshipUserDTO;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipOutputDTOTest {

    @Test
    void testEquals() {
        String mainUserId = "main@user.com";
        String mainUserName = "Main";
        String otherUserId = "other@user.com";
        String otherUserName = "Other";
        String thirdUserId = "other@user.com";
        String thirdUserName = "Other";


        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(mainUserId);
        Email email2 = new Email(otherUserId);
        Email email3 = new Email(thirdUserId);

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);
        Relationship relationship2 = new Relationship(RelationshipType.valueOf("CHILD"), email2, email1);
        Relationship relationship3 = new Relationship(RelationshipType.valueOf("COUSIN"), email1, email2);
        Relationship relationship4 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email3);

        RelationshipOutputDTO relationshipOutputDTO = relationshipMapper.toDTO(relationship1, mainUserName, otherUserName);
        //noinspection UnnecessaryLocalVariable
        RelationshipOutputDTO relationshipOutputDTOSame = relationshipOutputDTO;

        RelationshipOutputDTO relationshipOutputDTOOther = relationshipMapper.toDTO(relationship1, mainUserName, otherUserName);

        RelationshipOutputDTO relationshipOutputDTODifferent = relationshipMapper.toDTO(relationship2, otherUserName, mainUserName);

        RelationshipOutputDTO relationshipOutputDTODifferentType = relationshipMapper.toDTO(relationship3, mainUserName, otherUserName);

        RelationshipOutputDTO relationshipOutputDTODifferentOther = relationshipMapper.toDTO(relationship4, otherUserName, thirdUserName);


        String otherObject = "other object";

        assertSame(relationshipOutputDTO, relationshipOutputDTOSame);
        assertEquals(relationshipOutputDTO, relationshipOutputDTOSame);
        assertEquals(relationshipOutputDTO.hashCode(), relationshipOutputDTOSame.hashCode());
        assertEquals(relationshipOutputDTO, relationshipOutputDTOOther);
        assertEquals(relationshipOutputDTO.hashCode(), relationshipOutputDTOOther.hashCode());
        assertNotSame(relationshipOutputDTO, relationshipOutputDTOOther);
        assertNotEquals(relationshipOutputDTO, otherObject);
        assertNotEquals(relationshipOutputDTO, relationshipOutputDTODifferent);
        assertNotEquals(relationshipOutputDTO, relationshipOutputDTODifferentOther);
        assertNotEquals(relationshipOutputDTO, relationshipOutputDTODifferentType);
        assertNotEquals(0, relationshipOutputDTO.hashCode());
        assertNotEquals(null, relationshipOutputDTO);
        assertFalse(relationshipOutputDTOSame.equals(null));
        assertFalse(relationshipOutputDTO.getOtherUser().equals(relationshipOutputDTODifferent.getOtherUser()));
        assertTrue(relationshipOutputDTO.getOtherUser().equals(relationshipOutputDTOSame.getOtherUser()));
        assertNotEquals(relationshipOutputDTO.getRelationshipId(), relationshipOutputDTODifferent.getRelationshipId());
        assertEquals(relationshipOutputDTO.getRelationshipId(), relationshipOutputDTOSame.getRelationshipId());
        assertNotEquals(relationshipOutputDTO.getMainUser(), relationshipOutputDTODifferent.getMainUser());
        assertNotEquals(relationshipOutputDTO.getOtherUser(), relationshipOutputDTODifferent.getOtherUser());
        assertFalse(relationshipOutputDTO.getMainUser().equals(relationshipOutputDTODifferent.getMainUser()));
        assertFalse(relationshipOutputDTO.getOtherUser().equals(relationshipOutputDTODifferent.getOtherUser()));
        assertEquals(relationshipOutputDTO.getOtherUser(), relationshipOutputDTOSame.getOtherUser());
        assertNotEquals(relationshipOutputDTO.getOtherUser(), relationshipOutputDTOSame);
        assertFalse(relationshipOutputDTO.getOtherUser().equals(relationshipOutputDTOSame));
    }

    @Test
    void getMainUser() {
        String mainUserId = "main@user.com";
        String mainUserName = "Main";
        String otherUserId = "other@user.com";
        String otherUserName = "Other";
        RelationshipUserDTO expected = new RelationshipUserDTO(mainUserName, mainUserId);

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(mainUserId);
        Email email2 = new Email(otherUserId);

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);

        RelationshipOutputDTO relationshipOutputDTO = relationshipMapper.toDTO(relationship1, mainUserName, otherUserName);


        RelationshipUserDTO result = relationshipOutputDTO.getMainUser();

        assertEquals(expected, result);
    }

    @Test
    void getRelationshipType() {
        String mainUserId = "main@user.com";
        String mainUserName = "Main";
        String expected = "CHILD";
        String otherUserId = "other@user.com";
        String otherUserName = "Other";

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(mainUserId);
        Email email2 = new Email(otherUserId);

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);

        RelationshipOutputDTO relationshipOutputDTO = relationshipMapper.toDTO(relationship1, mainUserName, otherUserName);

        String result = relationshipOutputDTO.getRelationshipType();

        assertEquals(expected, result);
    }

    @Test
    void getOtherUser() {
        String mainUserId = "main@user.com";
        String mainUserName = "Main";
        String otherUserId = "other@user.com";
        String otherUserName = "Other";
        RelationshipUserDTO expected = new RelationshipUserDTO(otherUserName, otherUserId);

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(mainUserId);
        Email email2 = new Email(otherUserId);

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);

        RelationshipOutputDTO relationshipOutputDTO = relationshipMapper.toDTO(relationship1, mainUserName, otherUserName);

        RelationshipUserDTO result = relationshipOutputDTO.getOtherUser();

        assertEquals(expected, result);
        assertNotNull(relationshipOutputDTO.getRelationshipId());
        assertNotEquals(20L, relationshipOutputDTO.getRelationshipId());
        assertNotNull(relationshipOutputDTO.getRelationshipId());
    }

    @Test
    void ensureGetter() {
        String mainUserId = "main@user.com";
        String mainUserName = "Main";
        String otherUserId = "other@user.com";
        String otherUserName = "Other";
        RelationshipUserDTO first = new RelationshipUserDTO(mainUserName, mainUserId);
        RelationshipUserDTO second = new RelationshipUserDTO(otherUserName, otherUserId);
        RelationshipOutputDTO relationshipOutputDTO = new RelationshipOutputDTO(first, "CHILD", second, 1);

        assertEquals(1, relationshipOutputDTO.getRelationshipId());
    }
}