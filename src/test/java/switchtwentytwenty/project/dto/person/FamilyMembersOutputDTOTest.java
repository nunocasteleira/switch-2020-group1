package switchtwentytwenty.project.dto.person;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.transaction.TransferVOs;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FamilyMembersOutputDTOTest {

    @Test
    void testNoArgsConstructor() {
        FamilyMembersOutputDTO familyMembersOutputDTO = new FamilyMembersOutputDTO();

        assertNotNull(familyMembersOutputDTO);
    }

    @Test
    void testGetterAndSetter() {
        //arrange
        List<FamilyMemberOutputDTO> familyMembers = new ArrayList<>();

        //act
        FamilyMembersOutputDTO familyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);
        familyMembersOutputDTO.setFamilyMembers(familyMembers);

        //assert
        assertNotNull(familyMembersOutputDTO);
        assertEquals(familyMembers, familyMembersOutputDTO.getFamilyMembers());
    }

    @Test
    void testEquals_test1_DifferentObjects() {
        //arrange
        List<FamilyMemberOutputDTO> familyMembers = new ArrayList<>();

        //act
        FamilyMembersOutputDTO familyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);
        FamilyMembersOutputDTO otherFamilyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);

        //assert
        assertNotSame(familyMembersOutputDTO, otherFamilyMembersOutputDTO);
        assertEquals(familyMembersOutputDTO, otherFamilyMembersOutputDTO);
    }

    @Test
    void testEquals_test2_SameObjects() {
        //arrange
        List<FamilyMemberOutputDTO> familyMembers = new ArrayList<>();

        //act
        FamilyMembersOutputDTO familyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);
        FamilyMembersOutputDTO otherFamilyMembersOutputDTO = familyMembersOutputDTO;

        //assert
        assertSame(familyMembersOutputDTO, otherFamilyMembersOutputDTO);
        assertEquals(familyMembersOutputDTO, otherFamilyMembersOutputDTO);
    }

    @Test
    void testEquals_test3_DifferentObjectsType() {
        //arrange
        List<FamilyMemberOutputDTO> familyMembers = new ArrayList<>();
        int originAccountId = 1;

        //act
        FamilyMembersOutputDTO familyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);
        AccountId otherOriginId = new AccountId(originAccountId);

        //assert
        assertNotEquals(familyMembersOutputDTO, otherOriginId);
    }

    @Test
    void testEquals_test4_NotEqualObjects() {
        //arrange
        List<FamilyMemberOutputDTO> familyMembers = new ArrayList<>();
        FamilyMemberOutputDTO familyMemberOutputDTO = new FamilyMemberOutputDTO();
        List<FamilyMemberOutputDTO> otherFamilyMembers = new ArrayList<>();
        otherFamilyMembers.add(familyMemberOutputDTO);

        //act
        FamilyMembersOutputDTO familyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);
        FamilyMembersOutputDTO otherFamilyMembersOutputDTO = new FamilyMembersOutputDTO(otherFamilyMembers);

        //assert
        assertNotEquals(familyMembersOutputDTO, otherFamilyMembersOutputDTO);
    }

    @Test
    void testHashCode() {
        //arrange
        List<FamilyMemberOutputDTO> familyMembers = new ArrayList<>();

        //act
        FamilyMembersOutputDTO familyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);
        FamilyMembersOutputDTO otherFamilyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);

        //assert
        assertNotSame(familyMembersOutputDTO, otherFamilyMembersOutputDTO);
        assertEquals(familyMembersOutputDTO.hashCode(), otherFamilyMembersOutputDTO.hashCode());
    }

    @Test
    void testHashCode_false() {
        //arrange
        List<FamilyMemberOutputDTO> familyMembers = new ArrayList<>();
        FamilyMemberOutputDTO familyMemberOutputDTO = new FamilyMemberOutputDTO();
        List<FamilyMemberOutputDTO> otherFamilyMembers = new ArrayList<>();
        otherFamilyMembers.add(familyMemberOutputDTO);

        //act
        FamilyMembersOutputDTO familyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);
        FamilyMembersOutputDTO otherFamilyMembersOutputDTO = new FamilyMembersOutputDTO(otherFamilyMembers);

        //assert
        assertNotEquals(familyMembersOutputDTO.hashCode(), otherFamilyMembersOutputDTO.hashCode());
    }
}