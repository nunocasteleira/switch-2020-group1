package switchtwentytwenty.project.dto.family;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.dto.person.FamilyMembersOutputDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FamilyMembersOutputDTOTest {
/*
    @Test
    void testNoArgsConstructor() {
        FamilyMembersOutputDTO result = new FamilyMembersOutputDTO();

        assertNotNull(result);
    }

    @Test
    void testGetterAndSetter() {
        //arrange
        List<Email> familyMembers = new ArrayList<>();
        Email email = new Email("admin@gmail.com");
        familyMembers.add(email);

        //act
        FamilyMembersOutputDTO result = new FamilyMembersOutputDTO(familyMembers);
        result.setFamilyMembers(familyMembers);

        //assert
        assertNotNull(result);
        assertEquals(familyMembers, result.getFamilyMembers());
    }

    @Test
    void testEquals_test1_DifferentObjects() {
        //arrange
        List<Email> familyMembers = new ArrayList<>();
        Email email = new Email("admin@gmail.com");
        familyMembers.add(email);

        //act
        FamilyMembersOutputDTO result = new FamilyMembersOutputDTO(familyMembers);
        FamilyMembersOutputDTO otherResult = new FamilyMembersOutputDTO(familyMembers);

        //assert
        assertNotSame(result, otherResult);
        assertEquals(result, otherResult);
    }

    @Test
    void testEquals_test2_SameObjects() {
        //arrange
        List<Email> familyMembers = new ArrayList<>();
        Email email = new Email("admin@gmail.com");
        familyMembers.add(email);

        //act
        FamilyMembersOutputDTO result = new FamilyMembersOutputDTO(familyMembers);
        FamilyMembersOutputDTO otherResult = result;

        //assert
        assertSame(result, otherResult);
        assertEquals(result, otherResult);
    }

    @Test
    void testEquals_test3_DifferentObjectsType() {
        //arrange
        List<Email> familyMembers = new ArrayList<>();
        Email email = new Email("admin@gmail.com");
        familyMembers.add(email);

        //act
        FamilyMembersOutputDTO result = new FamilyMembersOutputDTO(familyMembers);

        //assert
        assertNotEquals(result, email);
    }

    @Test
    void testEquals_test4_NotEqualObjects() {
        //arrange
        List<Email> familyMembers = new ArrayList<>();
        Email email = new Email("admin@gmail.com");
        familyMembers.add(email);
        List<Email> otherFamilyMembers = new ArrayList<>();

        //act
        FamilyMembersOutputDTO result = new FamilyMembersOutputDTO(familyMembers);
        FamilyMembersOutputDTO otherResult = new FamilyMembersOutputDTO(otherFamilyMembers);

        //assert
        assertNotEquals(result, otherResult);
    }

    @Test
    void testHashCode() {
        //arrange
        List<Email> familyMembers = new ArrayList<>();
        Email email = new Email("admin@gmail.com");
        familyMembers.add(email);

        //act
        FamilyMembersOutputDTO result = new FamilyMembersOutputDTO(familyMembers);
        FamilyMembersOutputDTO otherResult = new FamilyMembersOutputDTO(familyMembers);

        //assert
        assertNotSame(result, otherResult);
        assertEquals(result.hashCode(), otherResult.hashCode());
    }

    @Test
    void testHashCode_false() {
        //arrange
        List<Email> familyMembers = new ArrayList<>();
        Email email = new Email("admin@gmail.com");
        familyMembers.add(email);
        List<Email> otherFamilyMembers = new ArrayList<>();

        //act
        FamilyMembersOutputDTO result = new FamilyMembersOutputDTO(familyMembers);
        FamilyMembersOutputDTO otherResult = new FamilyMembersOutputDTO(otherFamilyMembers);

        //assert
        assertNotEquals(result.hashCode(), otherResult.hashCode());
    }*/
}