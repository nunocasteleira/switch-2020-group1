package switchtwentytwenty.project.dto.family;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.FamilyId;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddFamilyMemberDTOTest {

    @Test
    void verifyNoArgsConstructor() {
        AddFamilyMemberDTO addFamilyMemberDTO = new AddFamilyMemberDTO();

        assertNotNull(addFamilyMemberDTO);
    }

    @Test
    void verifyAllArgsConstructor() {
        String email = "antonia@gmail.com";
        long databaseId = 123456789;
        String name = "Antónia";
        String street = "Rua da Amargura";
        String location = "Alentejo";
        String postCode = "4589-689";
        String birthdate = "12/01/1983";
        String vat = "223456789";
        long familyId = 12398547;
        String phoneNumbers = "918765456";
        Set<Role> roles = new HashSet<>();
        Role familyMember = new Role(ERole.ROLE_FAMILY_MEMBER);
        roles.add(familyMember);

        AddFamilyMemberDTO addFamilyMemberDTO = new AddFamilyMemberDTO(email, databaseId, name, street, location, postCode, birthdate, vat, familyId, phoneNumbers, roles);

        assertNotNull(addFamilyMemberDTO);
    }

    @Test
    void verifyConstructor() {
        String email = "antonia@gmail.com";
        String name = "Antónia";
        String street = "Rua da Amargura";
        String location = "Alentejo";
        String postCode = "4589-689";
        String birthdate = "12/01/1983";
        String vat = "223456789";
        long familyId = 12398547;
        String phoneNumbers = "918765456";
        Set<Role> roles = new HashSet<>();
        Role familyMember = new Role(ERole.ROLE_FAMILY_MEMBER);
        roles.add(familyMember);

        AddFamilyMemberDTO addFamilyMemberDTO = new AddFamilyMemberDTO(email, name, street, location, postCode, birthdate, vat, familyId, phoneNumbers, roles);

        assertNotNull(addFamilyMemberDTO);
    }

    @Test
    void verifyGetterAndSetter() {
        String email = "familyMember@gmail.com";
        long databaseId = 1;
        String name = "Member";
        String street = "Rua do Member";
        String location = "Member";
        String postCode = "4433-002";
        String birthDate = "20/05/1996";
        String vat = "222333666";
        int familyId = hashCode();
        String phoneNumber = "914649666";
        AddFamilyMemberDTO addFamilyMemberDTO = new AddFamilyMemberDTO();
        AddFamilyMemberDTO equalFamilyMemberDTO = addFamilyMemberDTO;
        AddFamilyMemberDTO different = new AddFamilyMemberDTO();

        addFamilyMemberDTO.setEmail(email);
        addFamilyMemberDTO.setDatabaseId(databaseId);
        addFamilyMemberDTO.setName(name);
        addFamilyMemberDTO.setStreet(street);
        addFamilyMemberDTO.setLocation(location);
        addFamilyMemberDTO.setPostCode(postCode);
        addFamilyMemberDTO.setFamilyId(familyId);
        addFamilyMemberDTO.setBirthdate(birthDate);
        addFamilyMemberDTO.setVat(vat);
        addFamilyMemberDTO.setPhoneNumbers(phoneNumber);


        //other information to AddFamilyMember
        String otherEmail = "antonia@gmail.com";
        String otherName = "Antónia";
        String otherStreet = "Rua da Amargura";
        String otherLocation = "Alentejo";
        String otherPostCode = "4589-689";
        String otherBirthdate = "12/01/1983";
        String otherVat = "223456789";
        long otherFamilyId = 12398547;
        String otherPhoneNumbers = "918765456";
        long otherIdDatabase = 129745689;
        Set<Role> roles = new HashSet<>();
        Role familyMember = new Role(ERole.ROLE_FAMILY_MEMBER);
        roles.add(familyMember);

        AddFamilyMemberDTO otherFamilyMember = new AddFamilyMemberDTO(otherEmail, otherName, otherStreet, otherLocation, otherPostCode, otherBirthdate, otherVat, otherFamilyId, otherPhoneNumbers, roles);

        assertNotNull(addFamilyMemberDTO);
        assertEquals(email, addFamilyMemberDTO.getEmail());
        assertEquals(databaseId, addFamilyMemberDTO.getDatabaseId());
        assertEquals(name, addFamilyMemberDTO.getName());
        assertEquals(street, addFamilyMemberDTO.getStreet());
        assertEquals(location, addFamilyMemberDTO.getLocation());
        assertEquals(postCode, addFamilyMemberDTO.getPostCode());
        assertEquals(birthDate, addFamilyMemberDTO.getBirthdate());
        assertEquals(familyId, addFamilyMemberDTO.getFamilyId());
        assertEquals(vat, addFamilyMemberDTO.getVat());
        assertEquals(phoneNumber, addFamilyMemberDTO.getPhoneNumbers());
        assertEquals(addFamilyMemberDTO, equalFamilyMemberDTO);
        assertNotEquals(addFamilyMemberDTO, different);
        assertEquals(addFamilyMemberDTO.hashCode(), equalFamilyMemberDTO.hashCode());
        assertNotEquals(addFamilyMemberDTO.hashCode(), different.hashCode());
        assertNotEquals(null, addFamilyMemberDTO);
        assertNotEquals(email, addFamilyMemberDTO);
        assertFalse(addFamilyMemberDTO.equals(null));
        assertFalse(addFamilyMemberDTO.equals(new FamilyId(12)));
        assertNotEquals(addFamilyMemberDTO, otherFamilyMember);
        assertNotSame(addFamilyMemberDTO, otherFamilyMember);
        assertEquals(email, addFamilyMemberDTO.getEmail());
        assertNotEquals(otherIdDatabase, addFamilyMemberDTO.getDatabaseId());
        assertNotEquals(otherName, addFamilyMemberDTO.getName());
        assertNotEquals(otherStreet, addFamilyMemberDTO.getStreet());
        assertNotEquals(otherLocation, addFamilyMemberDTO.getLocation());
        assertNotEquals(otherPostCode, addFamilyMemberDTO.getPostCode());
        assertNotEquals(otherBirthdate, addFamilyMemberDTO.getBirthdate());
        assertNotEquals(otherFamilyId, addFamilyMemberDTO.getFamilyId());
        assertNotEquals(otherVat, addFamilyMemberDTO.getVat());
        assertNotEquals(otherPhoneNumbers, addFamilyMemberDTO.getPhoneNumbers());
        assertNotEquals(otherFamilyId, otherFamilyMember);
        assertFalse(otherFamilyMember.equals(otherName));
        assertTrue(otherFamilyMember.equals(otherFamilyMember));

    }
}