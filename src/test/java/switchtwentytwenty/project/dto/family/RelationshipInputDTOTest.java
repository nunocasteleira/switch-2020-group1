package switchtwentytwenty.project.dto.family;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.family.RelationshipInputDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RelationshipInputDTOTest {

    @Test
    void RelationshipDTO_StartedWithEmptyConstructor_IsCorrect() {
        String mainUserId = "antonio@gmail.com";
        String otherUserId = "ricardo@gmail.com";
        String relationshipType = "4";

        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO();
        relationshipInputDTO.setMainUserId(mainUserId);
        relationshipInputDTO.setOtherUserId(otherUserId);
        relationshipInputDTO.setRelationshipType(relationshipType);

        assertNotNull(relationshipInputDTO);
        assertEquals(mainUserId, relationshipInputDTO.getMainUserId());
        assertEquals(otherUserId, relationshipInputDTO.getOtherUserId());
        assertEquals(relationshipType, relationshipInputDTO.getRelationshipType());
    }

    @Test
    void RelationshipDTO_StartedWithAllArgsConstructor_IsCorrect() {
        String mainUserId = "antonio@gmail.com";
        String otherUserId = "ricardo@gmail.com";
        String relationshipType = "4";

        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(mainUserId, otherUserId, relationshipType);

        assertNotNull(relationshipInputDTO);
        assertEquals(mainUserId, relationshipInputDTO.getMainUserId());
        assertEquals(otherUserId, relationshipInputDTO.getOtherUserId());
        assertEquals(relationshipType, relationshipInputDTO.getRelationshipType());
    }

}
