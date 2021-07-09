package switchtwentytwenty.project.dto.family;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.domain.model.shared.FamilyName;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;

import static org.junit.jupiter.api.Assertions.*;

class FamilyOutputDTOTest {

    @Test
    void testNoArgsConstructor() {
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO();

        assertNotNull(familyOutputDTO);
    }

    @Test
    void testGetterAndSetter() {
        // arrange
        int familyId = 1;
        String familyName = "Maia";
        String adminId = "maria@gmail.com";
        String registrationDate = "25/05/2021";

        // act
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);
        familyOutputDTO.setFamilyId(familyId);
        familyOutputDTO.setFamilyName(familyName);

        // assert
        assertNotNull(familyOutputDTO);
        assertEquals(familyId, familyOutputDTO.getFamilyId());
        assertEquals(familyName, familyOutputDTO.getFamilyName());
    }

    @Test
    void testEquals_test1_DifferentObjects() {
        // arrange
        int familyId = 1;
        String familyName = "Maia";
        String adminId = "maria@gmail.com";
        String registrationDate = "25/05/2021";

        // act
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);
        FamilyOutputDTO otherFamilyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);

        // assert
        assertNotSame(familyOutputDTO, otherFamilyOutputDTO);
        assertEquals(familyOutputDTO, otherFamilyOutputDTO);
    }

    @Test
    void testEquals_test2_SameObjects() {
        // arrange
        int familyId = 1;
        String familyName = "Maia";
        String adminId = "maria@gmail.com";
        String registrationDate = "25/05/2021";

        // act
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);
        FamilyOutputDTO otherFamilyOutputDTO = familyOutputDTO;

        // assert
        assertSame(familyOutputDTO, otherFamilyOutputDTO);
        assertEquals(familyOutputDTO, otherFamilyOutputDTO);
    }

    @Test
    void testEquals_test3_DifferentObjectsType() {
        // arrange
        int familyId = 1;
        String familyName = "Maia";
        String adminId = "maria@gmail.com";
        String registrationDate = "25/05/2021";

        // act
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);
        FamilyName otherFamilyName = new FamilyName(familyName);

        // assert
        assertNotEquals(familyOutputDTO, otherFamilyName);
    }

    @Test
    void testEquals_test4_NotEqualObjects() {
        // arrange
        int familyId = 1;
        String familyName = "Maia";
        String otherFamilyName = "Oliveira";
        String adminId = "maria@gmail.com";
        String registrationDate = "25/05/2021";

        // act
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);
        FamilyOutputDTO otherFamilyOutputDTO = new FamilyOutputDTO(familyId, otherFamilyName, adminId, registrationDate);

        // assert
        assertNotEquals(familyOutputDTO, otherFamilyOutputDTO);
    }

    @Test
    void testHashCode() {
        // arrange
        int familyId = 1;
        String familyName = "Maia";
        String adminId = "maria@gmail.com";
        String registrationDate = "25/05/2021";

        // act
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);
        FamilyOutputDTO otherFamilyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);

        // assert
        assertNotSame(familyOutputDTO, otherFamilyOutputDTO);
        assertEquals(familyOutputDTO.hashCode(), otherFamilyOutputDTO.hashCode());
    }

    @Test
    void testHashCode_false() {
        // arrange
        int familyId = 1;
        String familyName = "Maia";
        String otherFamilyName = "Oliveira";
        String adminId = "maria@gmail.com";
        String registrationDate = "25/05/2021";

        // act
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);
        FamilyOutputDTO otherFamilyOutputDTO = new FamilyOutputDTO(familyId, otherFamilyName, adminId, registrationDate);

        // assert
        assertNotEquals(familyOutputDTO.hashCode(), otherFamilyOutputDTO.hashCode());
    }

    @Test
    void ensureGetterAndSetter() {
        //arrange
        int familyId = 1;
        String familyName = "Maia";
        String adminId = "maria@gmail.com";
        String registrationDate = "25/05/2021";

        //act
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);
        FamilyOutputDTO secondFamily = new FamilyOutputDTO();
        secondFamily.setAdminId(adminId);
        secondFamily.setRegistrationDate(registrationDate);

        //assert
        assertEquals(familyId, familyOutputDTO.getFamilyId());
        assertEquals(familyName, familyOutputDTO.getFamilyName());
        assertEquals(adminId, familyOutputDTO.getAdminId());
        assertEquals(registrationDate, familyOutputDTO.getRegistrationDate());
        assertEquals(familyOutputDTO.getAdminId(), secondFamily.getAdminId());
        assertEquals(familyOutputDTO.getRegistrationDate(), secondFamily.getRegistrationDate());
    }

    @Test
    void testEqualsAndHashCode() {
        //arrange
        int familyId = 1;
        String familyName = "Maia";
        String adminId = "maria@gmail.com";
        String registrationDate = "25/05/2021";

        //other information of Other Family
        int otherFamilyId = 2;
        String otherFamilyName = "Fonseca";
        String otherAdminId = "roberta@gmail.com";
        String otherRegistrationDate = "25/05/2020";

        //act
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);
        FamilyOutputDTO secondFamily = new FamilyOutputDTO();
        FamilyOutputDTO familyOutputDTOSame = familyOutputDTO;
        FamilyOutputDTO familyOutputDTOEquals = new FamilyOutputDTO(familyId, familyName, adminId, registrationDate);
        FamilyOutputDTO familyOutputDTODifferent = new FamilyOutputDTO(otherFamilyId, otherFamilyName, otherAdminId, otherRegistrationDate);
        FamilyOutputDTO familyOutputDTODifferentFamilyId = new FamilyOutputDTO(otherFamilyId, familyName, adminId, registrationDate);
        FamilyOutputDTO familyOutputDTODifferentAdminId = new FamilyOutputDTO(familyId, familyName, otherAdminId, registrationDate);
        FamilyOutputDTO familyOutputDTODifferentFamilyName = new FamilyOutputDTO(familyId, otherFamilyName, adminId, registrationDate);
        FamilyOutputDTO familyOutputDTODifferentRegistrationDate = new FamilyOutputDTO(familyId, familyName, adminId, otherRegistrationDate);

        //assert
        assertEquals(familyOutputDTO, familyOutputDTOSame);
        assertNotSame(familyOutputDTO, familyOutputDTOEquals);
        assertSame(familyOutputDTO, familyOutputDTOSame);
        assertNotEquals(familyOutputDTO, familyOutputDTODifferent);
        assertNotSame(familyOutputDTO, familyOutputDTODifferent);
        assertNotEquals(familyOutputDTO, secondFamily);
        assertNotSame(familyOutputDTO, secondFamily);
        assertEquals(familyOutputDTO.hashCode(), familyOutputDTOSame.hashCode());

        assertNotEquals(familyOutputDTO.hashCode(), familyOutputDTODifferentAdminId.hashCode());
        assertNotEquals(null, familyOutputDTO);
        assertNotEquals(otherFamilyId, familyOutputDTO);
        assertFalse(familyOutputDTO.equals(null));
        assertFalse(familyOutputDTO.equals(new FamilyId(12)));
        assertNotEquals(familyOutputDTO, familyOutputDTODifferentAdminId);
        assertNotEquals(familyOutputDTO, familyOutputDTODifferentFamilyName);
        assertNotEquals(familyOutputDTO, familyOutputDTODifferentRegistrationDate);
        assertFalse(familyOutputDTO.equals(familyOutputDTODifferentAdminId));
        assertFalse(familyOutputDTO.equals(familyOutputDTODifferentRegistrationDate));
        assertNotEquals(familyOutputDTO.hashCode(), familyOutputDTODifferent.hashCode());
        assertNotEquals(familyOutputDTO.hashCode(), familyOutputDTODifferentAdminId.hashCode());
        assertNotEquals(familyOutputDTO.hashCode(), familyOutputDTODifferentFamilyName.hashCode());
        assertNotEquals(familyOutputDTO.hashCode(), familyOutputDTODifferentRegistrationDate.hashCode());
        assertNotEquals(0, familyOutputDTO.hashCode());


    }


}