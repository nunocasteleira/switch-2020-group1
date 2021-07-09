package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.category.CategoryInputDTO;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryInputDTOTest {


    @Test
    void ensureCategoryDTOIsWorkingProperly() {
        String categoryName = "Electronics";
        int parentId = new Random().nextInt();

        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setParentId(parentId);
        inputDTO.setName(categoryName);

        assertNotNull(inputDTO);
        assertEquals(parentId, inputDTO.getParentId());
        assertEquals(categoryName, inputDTO.getName());
    }

}
