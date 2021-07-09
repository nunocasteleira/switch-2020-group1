package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.category.CategoryInputDTO;
import switchtwentytwenty.project.dto.category.FamilyCategoryOutputDTO;
import switchtwentytwenty.project.dto.category.StandardCategoryOutputDTO;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StandardCategoryOutputDTOTest {

    @Test
    void ensureCategoryOutputDTOIsWorkingProperly() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);

        //assert
        assertNotNull(outputDTO);
    }

    @Test
    void ensureNoArgsConstructorIsWorkingProperly() {
        //assert
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO();

        //act + assert
        assertNotNull(outputDTO);
    }

    @Test
    void ensureAllArgsConstructorIsWorkingProperly() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int parentId = new Random().nextInt();

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, parentId);

        //assert
        assertNotNull(outputDTO);
    }

    @Test
    void ensureOutputDTOIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);
        StandardCategoryOutputDTO result;

        //act
        result = new StandardCategoryOutputDTO(id, name, idDatabase);

        //assert
        assertEquals(outputDTO, result);
    }

    @Test
    void ensureOutputDTOAllArgsIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int parentId = new Random().nextInt();
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, parentId);
        StandardCategoryOutputDTO result;

        //act
        result = new StandardCategoryOutputDTO(id, name, parentId);

        //assert
        assertEquals(outputDTO, result);
    }


    @Test
    void ensureCategoryIdIsNotEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);
        StandardCategoryOutputDTO result;

        //act
        result = new StandardCategoryOutputDTO();

        //assert
        assertFalse(outputDTO.equals(result));
    }


    @Test
    void ensureCategoryOutputDTOIsNotEqualToCategoryInputDTO() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        long idDatabase = new Random().nextLong();

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);

        //assert
        assertNotEquals(outputDTO, inputDTO);
    }

    @Test
    void ensureOutputDTOIsEqualTrue() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);

        //assert
        assertTrue(outputDTO.equals(outputDTO));
    }

    @Test
    void ensureHashCodeEquals() {
        //arrange
        String name = "Groceries";
        int id = new Random().nextInt();
        int sameId = id;
        String sameName = "GROCERIES";
        long idDatabase = new Random().nextLong();

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);
        StandardCategoryOutputDTO sameOutputDTO = new StandardCategoryOutputDTO(sameId, sameName, idDatabase);

        //assert
        assertEquals(outputDTO.hashCode(), sameOutputDTO.hashCode());
    }

    @Test
    void ensureHashCodeNotEquals() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int otherId = new Random().nextInt();
        long idDatabase = new Random().nextLong();


        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);
        StandardCategoryOutputDTO otherOutputDTO = new StandardCategoryOutputDTO(otherId, name, idDatabase);

        //assert
        assertNotEquals(outputDTO.hashCode(), otherOutputDTO.hashCode());
    }


    @Test
    void ensureCategoryNameEquals() {
        //arrange
        String name = "Groceries";
        int id = new Random().nextInt();
        int sameId = id;
        String sameName = "GROCERIES";
        long idDatabase = new Random().nextLong();

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);
        StandardCategoryOutputDTO sameOutputDTO = new StandardCategoryOutputDTO(sameId, sameName, idDatabase);

        //assert
        assertEquals(outputDTO.getCategoryName(), sameOutputDTO.getCategoryName());
        assertTrue(outputDTO.getCategoryName().equals(sameOutputDTO.getCategoryName()));
    }

    @Test
    void ensureGetIdIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        Object result;

        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);

        //act
        result = outputDTO.getCategoryId();

        //assert
        assertEquals(id, result);
    }

    @Test
    void ensureGetIdIsNotEqual() {
        //arrange
        int id = new Random().nextInt();
        int randomId = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        Object result;

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(randomId, name, idDatabase);
        result = outputDTO.getCategoryId();

        //assert
        assertNotEquals(id, result);
    }

    @Test
    void ensureCategoryOutputDTONotEqualsToNull() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);
        StandardCategoryOutputDTO outputDTONull = null;

        //assert
        assertNotEquals(outputDTO, outputDTONull);
    }

    @Test
    void ensureGetCategoryNameIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        String result;

        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, idDatabase);

        //act
        result = outputDTO.getCategoryName();

        //assert
        assertEquals(name.toUpperCase(), result);
    }

    @Test
    void ensureGetCategoryNameNotEqual() {
        //arrange
        String name = "Groceries";
        int randomId = new Random().nextInt();
        String otherName = "Shopping";
        long idDatabase = new Random().nextLong();
        String result;

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(randomId, name, idDatabase);
        result = outputDTO.getCategoryName();

        //assert
        assertNotEquals(otherName, result);
    }


    @Test
    void ensureGetParentIdIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int parentId = new Random().nextInt();
        long idDatabase = new Random().nextLong();
        Object result;

        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, parentId, idDatabase);

        //act
        result = outputDTO.getParentCategoryId();

        //assert
        assertEquals(parentId, result);
    }

    @Test
    void ensureGetParentIdIsNotEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int parentId = new Random().nextInt();
        int randomParentId = new Random().nextInt();
        long idDatabase = new Random().nextLong();
        Object result;

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, parentId, idDatabase);
        result = outputDTO.getCategoryId();

        //assert
        assertNotEquals(randomParentId, result);
    }

    @Test
    void ensureGetIdDatabaseIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int parentId = new Random().nextInt();
        long idDatabase = new Random().nextLong();
        long result;

        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, parentId, idDatabase);

        //act
        result = outputDTO.getIdDatabase();

        //assert
        assertEquals(idDatabase, result);
    }

    @Test
    void ensureGetIdDatabaseIsNotEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int parentId = new Random().nextInt();
        long randomLong = new Random().nextLong();
        long idDatabase = new Random().nextLong();
        long result;

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, parentId, idDatabase);
        result = outputDTO.getIdDatabase();

        //assert
        assertNotEquals(randomLong, result);
    }


    @Test
    void equalsAndHashCode() {
        //arrange
        //id
        int id = new Random().nextInt();
        int otherId = new Random().nextInt();

        //name
        String name = "Groceries";
        String otherName = "Vegetables";

        //parentId
        int parentId = new Random().nextInt();
        int otherParentId = new Random().nextInt();

        //idDatabase
        long idDatabase = new Random().nextLong();
        long otherIdDatabase = new Random().nextLong();

        long familyId = 123456789;

        //act
        StandardCategoryOutputDTO outputDTO = new StandardCategoryOutputDTO(id, name, parentId, idDatabase);
        StandardCategoryOutputDTO outputDTOEqual = outputDTO;
        StandardCategoryOutputDTO outputDTONull = null;
        StandardCategoryOutputDTO outputDTOSame = new StandardCategoryOutputDTO(id, name, parentId, idDatabase);
        StandardCategoryOutputDTO outputDTODifferentId = new StandardCategoryOutputDTO(otherId, name, parentId, idDatabase);
        StandardCategoryOutputDTO outputDTODifferentName = new StandardCategoryOutputDTO(id, otherName, parentId, idDatabase);
        StandardCategoryOutputDTO outputDTODifferentParent = new StandardCategoryOutputDTO(id, name, otherParentId, idDatabase);
        StandardCategoryOutputDTO outputDTODifferentIdDatabase = new StandardCategoryOutputDTO(id, name, otherParentId, otherIdDatabase);
        FamilyCategoryOutputDTO familyCategoryOutputDTO = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);

        //assert
        assertEquals(outputDTO, outputDTOSame);
        assertNotSame(outputDTO, outputDTOSame);
        assertEquals(outputDTO, outputDTOEqual);
        assertEquals(outputDTO.hashCode(), outputDTOSame.hashCode());
        assertNotEquals(outputDTO, outputDTONull);
        assertFalse(outputDTO.equals(id));
        assertFalse(outputDTO.equals(name));
        assertFalse(outputDTO.equals(parentId));
        assertFalse(outputDTO.equals(idDatabase));
        assertNotEquals(outputDTO, outputDTODifferentId);
        assertNotEquals(outputDTO, outputDTODifferentName);
        assertNotEquals(outputDTO, outputDTODifferentParent);
        assertNotEquals(outputDTO, outputDTODifferentIdDatabase);
        assertFalse(outputDTO.equals(outputDTODifferentId));
        assertFalse(outputDTO.equals(outputDTODifferentName));
        assertFalse(outputDTO.equals(outputDTODifferentParent));
        assertFalse(outputDTO.equals(outputDTODifferentIdDatabase));
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentId.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentName.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentParent.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentIdDatabase.hashCode());
        assertNotEquals(0, outputDTO.hashCode());
        assertNotEquals(outputDTO, otherName);
        assertNotEquals(outputDTO, otherIdDatabase);
        assertNotEquals(outputDTO, familyCategoryOutputDTO);
        assertFalse(outputDTO.equals(familyCategoryOutputDTO));
    }

}
