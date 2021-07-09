package switchtwentytwenty.project.externaldata.assembler;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.dto.category.ExternalCategoryDTO;

import static org.junit.jupiter.api.Assertions.*;

class ExternalCategoryAssemblerTest {
    ExternalCategoryAssembler assembler = new ExternalCategoryAssembler();

    @Test
    void assembler_fromPrimitiveToCategoryId_Successfully() {
        //arrange
        String categoryId = "1";
        String name = "groceries";
        String parentId = "null";
        ExternalCategoryDTO externalCategoryDTO = new ExternalCategoryDTO(categoryId, name, parentId);
        CategoryId expected = new CategoryId(externalCategoryDTO.getCategoryId());

        //act
        CategoryId result = assembler.fromPrimitiveToCategoryId(externalCategoryDTO);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void assembler_fromPrimitiveToCategoryName_Successfully() {
        //arrange
        String categoryId = "1";
        String name = "groceries";
        String parentId = "null";
        ExternalCategoryDTO externalCategoryDTO = new ExternalCategoryDTO(categoryId, name, parentId);
        CategoryName expected = new CategoryName(externalCategoryDTO.getName());

        //act
        CategoryName result = assembler.fromPrimitiveToCategoryName(externalCategoryDTO);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void assembler_fromPrimitiveToParentId_SuccessfullyNull() {
        //arrange
        String categoryId = "1";
        String name = "groceries";
        String parentId = "null";
        ExternalCategoryDTO externalCategoryDTO = new ExternalCategoryDTO(categoryId, name, parentId);
        CategoryId expected = null;

        //act
        CategoryId result = assembler.fromPrimitiveToParentId(externalCategoryDTO);

        //assert
        assertNull(result);
        assertEquals(expected, result);
    }

    @Test
    void assembler_fromPrimitiveToParentId_SuccessfullyNotNull() {
        //arrange
        String categoryId = "1";
        String name = "groceries";
        String parentId = "2";
        ExternalCategoryDTO externalCategoryDTO = new ExternalCategoryDTO(categoryId, name, parentId);
        CategoryId expected = new CategoryId(externalCategoryDTO.getParentId());

        //act
        CategoryId result = assembler.fromPrimitiveToParentId(externalCategoryDTO);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void ensureNoArgsConstructorIsWorking(){
        ExternalCategoryAssembler assembler = new ExternalCategoryAssembler();
        assertNotNull(assembler);
    }
}