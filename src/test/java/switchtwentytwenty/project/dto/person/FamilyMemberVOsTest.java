package switchtwentytwenty.project.dto.person;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FamilyMemberVOsTest {

    @Test
    void testAllArgsConstructor() {
        Email email = new Email("maria@gmail.com");
        PersonName personName = new PersonName("Maria");
        Address address = new Address("Rua Clara", "Porto", "4400-000");
        BirthDate birthDate = new BirthDate("25/12/2000");
        PersonVat personVat = new PersonVat("249111111");
        FamilyId familyId = new FamilyId(0);
        PhoneNumber phoneNumber = new PhoneNumber("919999999");
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneNumber);
        List<Email> emailAddresses = new ArrayList<>();
        emailAddresses.add(email);

        FamilyMemberVOs familyMemberVOs = new FamilyMemberVOs(email, personName, address, birthDate, personVat, familyId, phoneNumbers, emailAddresses);

        assertNotNull(familyMemberVOs);
        assertEquals(familyMemberVOs, familyMemberVOs);
        assertEquals(email, familyMemberVOs.getEmail());
        assertEquals(personName, familyMemberVOs.getPersonName());
        assertEquals(address, familyMemberVOs.getAddress());
        assertEquals(birthDate, familyMemberVOs.getBirthDate());
        assertEquals(personVat, familyMemberVOs.getPersonVat());
        assertEquals(familyId, familyMemberVOs.getFamilyId());
        assertEquals(phoneNumbers, familyMemberVOs.getPhoneNumbers());
        assertEquals(emailAddresses, familyMemberVOs.getEmailAddresses());
    }

    @Test
    void testGetterAndSetter() {
        // arrange
        Email email = new Email("maria@gmail.com");
        PersonName personName = new PersonName("Maria");
        Address address = new Address("Rua Clara", "Porto", "4400-000");
        BirthDate birthDate = new BirthDate("25/12/2000");
        PersonVat personVat = new PersonVat("249111111");
        FamilyId familyId = new FamilyId(0);
        PhoneNumber phoneNumber = new PhoneNumber("919999999");
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneNumber);
        List<Email> emailAddresses = new ArrayList<>();
        emailAddresses.add(email);

        // act
        FamilyMemberVOs familyMemberVOs = new FamilyMemberVOs(email, personName, address, birthDate, personVat, familyId, phoneNumbers);
        familyMemberVOs.setPhoneNumbers(phoneNumbers);
        familyMemberVOs.setEmailAddresses(emailAddresses);
        familyMemberVOs.setPassword("password");

        // assert
        assertNotNull(familyMemberVOs);
        assertEquals(phoneNumbers, familyMemberVOs.getPhoneNumbers());
        assertEquals(emailAddresses, familyMemberVOs.getEmailAddresses());
        assertEquals("password", familyMemberVOs.getPassword());
    }

    @Test
    void testRoles() {
        // arrange
        Email email = new Email("maria@gmail.com");
        PersonName personName = new PersonName("Maria");
        Address address = new Address("Rua Clara", "Porto", "4400-000");
        BirthDate birthDate = new BirthDate("25/12/2000");
        PersonVat personVat = new PersonVat("249111111");
        FamilyId familyId = new FamilyId(0);
        PhoneNumber phoneNumber = new PhoneNumber("919999999");
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneNumber);
        List<Email> emailAddresses = new ArrayList<>();
        emailAddresses.add(email);
        Set<Role> roleSet = new HashSet<>();
        Role roleFamilyAdmin = new Role(ERole.ROLE_FAMILY_ADMIN);
        roleSet.add(roleFamilyAdmin);
        Set<Role> emptyRoleSet = Collections.EMPTY_SET;

        // act
        FamilyMemberVOs familyMemberVOs = new FamilyMemberVOs(email, personName, address, birthDate, personVat, familyId, phoneNumbers);
        familyMemberVOs.setPhoneNumbers(phoneNumbers);
        familyMemberVOs.setEmailAddresses(emailAddresses);
        familyMemberVOs.setRoles(roleSet);

        // assert
        assertNotNull(familyMemberVOs);
        assertEquals(roleSet, familyMemberVOs.getRoles());
        assertNotEquals(roleSet, emptyRoleSet);
    }
}