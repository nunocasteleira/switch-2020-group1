package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.category.CategoryInputDTO;
import switchtwentytwenty.project.dto.category.FamilyCategoryOutputDTO;
import switchtwentytwenty.project.dto.category.StandardCategoryOutputDTO;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FamilyCategoryOutputDTOTest {

    @Test
    void ensureFamilyCategoryOutputDTONotNull() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        long familyId = 123456789;

        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);

        //assert
        assertNotNull(outputDTO);
    }

    @Test
    void ensureNoArgsConstructorNotNull() {
        //assert
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO();

        //act + assert
        assertNotNull(outputDTO);
    }

    @Test
    void ensureAllArgsConstructorNotNull() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int parentId = new Random().nextInt();
        long familyId = 123456789;
        long idDatabase = new Random().nextLong();

        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);

        //assert
        assertNotNull(outputDTO);
    }

    @Test
    void ensureFamilyCategoryOutputDTOAllArgsIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int parentId = new Random().nextInt();
        long familyId = 123456789;
        long idDatabase = new Random().nextLong();
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);
        FamilyCategoryOutputDTO result;

        //act
        result = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);

        //assert
        assertEquals(outputDTO, result);
    }

    @Test
    void ensureFamilyCategoryOutputDTOIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        long familyId = 123456789;
        FamilyCategoryOutputDTO result;

        FamilyCategoryOutputDTO expected = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);


        //act
        result = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);

        //assert
        assertEquals(expected, result);
    }


    @Test
    void ensureCategoryIdIsNotEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        long familyId = 123456789;
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);
        FamilyCategoryOutputDTO result;

        //act
        result = new FamilyCategoryOutputDTO();

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
        long familyId = 123456789;

        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);

        //assert
        assertNotEquals(outputDTO, inputDTO);
    }

    @Test
    void ensureOutputDTOIsEqualTrue() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        long familyId = 123456789;
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);

        //assert
        assertTrue(outputDTO.equals(outputDTO));
    }

    @Test
    void ensureHashCodeEquals() {
        //arrange
        String name = "Groceries";
        int id = new Random().nextInt();
        int sameId = id;
        String sameName = "Groceries";
        long idDatabase = new Random().nextLong();
        long familyId = 123456789;

        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);
        FamilyCategoryOutputDTO sameOutputDTO = new FamilyCategoryOutputDTO(sameId, sameName, familyId, idDatabase);

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
        long familyId = 123456789;


        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);
        FamilyCategoryOutputDTO otherOutputDTO = new FamilyCategoryOutputDTO(otherId, name, familyId, idDatabase);

        //assert
        assertNotEquals(outputDTO.hashCode(), otherOutputDTO.hashCode());
    }

    @Test
    void ensureGetIdIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        long familyId = 123456789;
        Object result;

        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);

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
        long familyId = 123456789;
        Object result;

        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(randomId, name, familyId, idDatabase);
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
        long familyId = 123456789;

        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);
        FamilyCategoryOutputDTO outputDTONull = null;

        //assert
        assertNotEquals(outputDTO, outputDTONull);
    }

    @Test
    void ensureGetCategoryNameIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        long idDatabase = new Random().nextLong();
        long familyId = 123456789;
        String result;

        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, familyId, idDatabase);

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
        long familyId = 123456789;
        String result;

        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(randomId, name, familyId, idDatabase);
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
        long familyId = 123456789;
        Object result;

        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);

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
        long familyId = 123456789;
        Object result;

        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);
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
        long familyId = 123456789;
        long result;

        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);

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
        long familyId = 123456789;
        long result;

        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);
        result = outputDTO.getIdDatabase();

        //assert
        assertNotEquals(randomLong, result);
    }

    @Test
    void ensureGetFamilyIdIsEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int parentId = new Random().nextInt();
        long idDatabase = new Random().nextLong();
        long familyId = 123456789;
        long result;

        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);

        //act
        result = outputDTO.getFamilyId();

        //assert
        assertEquals(familyId, result);
    }

    @Test
    void ensureGetFamilyIdIsNotEqual() {
        //arrange
        int id = new Random().nextInt();
        String name = "Groceries";
        int parentId = new Random().nextInt();
        long unexpected = new Random().nextLong();
        long idDatabase = new Random().nextLong();
        long familyId = 123456789;
        long result;

        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);
        result = outputDTO.getFamilyId();

        //assert
        assertNotEquals(unexpected, result);
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

        //familyId
        long familyId = 123456789;
        long otherFamilyId = 987654321;

        //idDatabase
        long idDatabase = new Random().nextLong();
        long otherIdDatabase = new Random().nextLong();


        //act
        FamilyCategoryOutputDTO outputDTO = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);
        FamilyCategoryOutputDTO outputDTOEqual = outputDTO;
        FamilyCategoryOutputDTO outputDTONull = null;
        FamilyCategoryOutputDTO outputDTOSame = new FamilyCategoryOutputDTO(id, name, parentId, familyId, idDatabase);
        FamilyCategoryOutputDTO outputDTODifferentId = new FamilyCategoryOutputDTO(otherId, name, parentId, familyId, idDatabase);
        FamilyCategoryOutputDTO outputDTODifferentName = new FamilyCategoryOutputDTO(id, otherName, parentId, familyId, idDatabase);
        FamilyCategoryOutputDTO outputDTODifferentParent = new FamilyCategoryOutputDTO(id, name, otherParentId, familyId, idDatabase);
        FamilyCategoryOutputDTO outputDTODifferentFamilyId = new FamilyCategoryOutputDTO(id, name, parentId, otherFamilyId, idDatabase);
        FamilyCategoryOutputDTO outputDTODifferentIdDatabase = new FamilyCategoryOutputDTO(id, name, parentId, familyId, otherIdDatabase);
        StandardCategoryOutputDTO otherObject = new StandardCategoryOutputDTO(id, name, idDatabase);
        FamilyCategoryOutputDTO outputDTODifferent = new FamilyCategoryOutputDTO(otherId, otherName, otherFamilyId, otherIdDatabase);

        //assert
        assertEquals(outputDTO, outputDTOSame);
        assertNotSame(outputDTO, outputDTOSame);
        assertEquals(outputDTO, outputDTOEqual);
        assertEquals(outputDTO.hashCode(), outputDTOSame.hashCode());
        assertNotEquals(outputDTO, outputDTONull);
        assertFalse(outputDTO.equals(id));
        assertFalse(outputDTO.equals(name));
        assertFalse(outputDTO.equals(parentId));
        assertFalse(outputDTO.equals(familyId));
        assertFalse(outputDTO.equals(idDatabase));
        assertNotEquals(outputDTO, outputDTODifferentId);
        assertNotEquals(outputDTO, outputDTODifferentName);
        assertNotEquals(outputDTO, outputDTODifferentParent);
        assertNotEquals(outputDTO, outputDTODifferentFamilyId);
        assertNotEquals(outputDTO, outputDTODifferentIdDatabase);
        assertNotEquals(outputDTO, outputDTODifferent);
        assertFalse(outputDTO.equals(outputDTODifferentId));
        assertFalse(outputDTO.equals(outputDTODifferentName));
        assertFalse(outputDTO.equals(outputDTODifferentParent));
        assertFalse(outputDTO.equals(outputDTODifferentFamilyId));
        assertFalse(outputDTO.equals(outputDTODifferentIdDatabase));
        assertFalse(outputDTO.equals(outputDTODifferent));
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentId.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentName.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentParent.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentFamilyId.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentIdDatabase.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferent.hashCode());
        assertNotEquals(0, outputDTO.hashCode());
        assertNotEquals(outputDTO, otherName);
        assertNotEquals(outputDTO, otherIdDatabase);
        assertNotEquals(outputDTO, otherObject);
        assertNotEquals(outputDTO, outputDTODifferent);
    }


}
