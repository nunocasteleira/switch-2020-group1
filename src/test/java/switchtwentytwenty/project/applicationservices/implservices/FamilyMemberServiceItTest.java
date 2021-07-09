package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.dto.person.ProfileInformationDTO;
import switchtwentytwenty.project.repositories.PersonRepository;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Profile("FamilyMemberServiceTest")
class FamilyMemberServiceItTest {

    @Autowired
    IFamilyRepositoryJPA familyRepository;

    @Autowired
    IPersonRepositoryJPA personRepository;

    @Autowired
    FamilyMemberService familyMemberService;

    @Autowired
    PersonRepository notCrudPersonRepository;

    FamilyInputDTO familyInputDTO;

    @BeforeEach
    void init() {
        familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("António");
        familyInputDTO.setEmail("notpassing@gmail.com");
        familyInputDTO.setStreet("Rua clara");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("4000-000");
        familyInputDTO.setPhoneNumber("911111111");
        familyInputDTO.setVat("222333222");
        familyInputDTO.setBirthDate("11/09/1999");
        familyInputDTO.setFamilyName("Silva");
    }

    @AfterEach
    void clean() {
        familyRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    void createFamily_Successfully() {
        long expectedFamilies = 1;

        //act
        FamilyOutputDTO result = familyMemberService.createFamily(familyInputDTO);
        long resultFamilies = familyRepository.count();

        //assert
        assertNotNull(result);
        assertEquals(expectedFamilies, resultFamilies);
    }

    @Test
    void createFamilyWithNullInput() {
        familyInputDTO = null;

        assertThrows(NullPointerException.class, () -> familyMemberService.createFamily(familyInputDTO));
    }

    @Test
    void addFamilyMemberSuccessfully() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Miguel");
        personInputDTO.setEmail("bla@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911122111");
        personInputDTO.setVat("222355222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("notpassing@gmail.com");
        String expected = personInputDTO.getEmail();

        AddFamilyMemberDTO result = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        assertNotNull(result);
        assertEquals(expected, result.getEmail());
    }

    @Test
    void addTwoFamilyMemberSuccessfully() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Miguel");
        personInputDTO.setEmail("ble@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911122111");
        personInputDTO.setVat("222355222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("notpassing@gmail.com");
        PersonInputDTO personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("Patrícia");
        personInputDTO1.setEmail("bleble@gmail.com");
        personInputDTO1.setStreet("Rua clara");
        personInputDTO1.setLocation("Porto");
        personInputDTO1.setPostCode("4000-000");
        personInputDTO1.setPhoneNumber("911442111");
        personInputDTO1.setVat("222555222");
        personInputDTO1.setBirthDate("11/09/1970");
        personInputDTO1.setAdminId("notpassing@gmail.com");
        String expected = personInputDTO.getEmail();
        String expected1 = personInputDTO1.getEmail();
        int numberOfPeopleExpected = 3;

        AddFamilyMemberDTO result = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO result1 = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());
        long numberOfPeople = personRepository.count();

        assertNotNull(result);
        assertEquals(expected, result.getEmail());
        assertEquals(expected1, result1.getEmail());
        assertEquals(numberOfPeopleExpected, numberOfPeople);
    }

    @Test
    void addFamilyMemberSuccessfullyWithOutPhoneNumber() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Miguel");
        personInputDTO.setEmail("man@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setVat("222355222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("notpassing@gmail.com");
        String expected = personInputDTO.getEmail();

        AddFamilyMemberDTO result = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        assertNotNull(result);
        assertEquals(expected, result.getEmail());
    }


    @Test
    void addFamilyMemberWithRepeatedEmail() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Miguel");
        personInputDTO.setEmail("notpassing@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setVat("222355222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("notpassing@gmail.com");

        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId()));
    }

    @Test
    void addFamilyMemberWithNonExistentFamily() {
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Miguel");
        personInputDTO.setEmail("mopa@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911122111");
        personInputDTO.setVat("222355222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("notpassing@gmail.com");

        assertThrows(ObjectDoesNotExistException.class,
                () -> familyMemberService.addFamilyMember(personInputDTO, 1));
    }

    @Test
    void getProfileInformationDTOFromAnExistingPerson() {
        //Arrange
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail("notdavidadmin@gmail.com");
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Martins");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

        PersonInputDTO personInputDTO = new PersonInputDTO();
        String expectedName = "Fabio";
        personInputDTO.setName(expectedName);
        personInputDTO.setVat("227475987");
        String expectedEmail = "notfabio@gmail.com";
        personInputDTO.setEmail(expectedEmail);
        personInputDTO.setPhoneNumber("917899987");
        personInputDTO.setLocation("Porto");
        personInputDTO.setStreet("Rua das Flores");
        personInputDTO.setPostCode("4444-333");
        String expectedBirthDate = "09/11/1988";
        personInputDTO.setBirthDate(expectedBirthDate);
        personInputDTO.setAdminId("notdavidadmin@gmail.com");
        familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        //Act
        ProfileInformationDTO profileInformationDTO = familyMemberService.getProfileInformation("notfabio@gmail.com", "notfabio@gmail.com");
        String actualName = profileInformationDTO.getName();
        String actualEmailAddress = profileInformationDTO.getMainEmailAddress();
        String actualBirthdate = profileInformationDTO.getBirthDate();

        //Assert
        assertNotNull(profileInformationDTO);
        assertEquals(expectedBirthDate, actualBirthdate);
        assertEquals(expectedEmail, actualEmailAddress);
        assertEquals(expectedName, actualName);
    }

    @Test
    void getProfileInformationDTOFromAnNonExistingPerson() {
        //Assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> familyMemberService.getProfileInformation("gervasio@hotmail.com", "gervasio@hotmail.com"));
    }

    @Test
    void getProfileInformationDTOFromTwoExistingPersons() {
        //Arrange
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail("otherdavidAdmin@gmail.com");
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Martins");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

        PersonInputDTO firstPersonInputDTO = new PersonInputDTO();
        String expectedNameFirstPerson = "Fabio";
        firstPersonInputDTO.setName(expectedNameFirstPerson);
        firstPersonInputDTO.setVat("227475987");
        firstPersonInputDTO.setEmail("otherfabio@gmail.com");
        firstPersonInputDTO.setPhoneNumber("917899987");
        firstPersonInputDTO.setLocation("Porto");
        firstPersonInputDTO.setStreet("Rua das Flores");
        firstPersonInputDTO.setPostCode("4444-333");
        firstPersonInputDTO.setBirthDate("09/11/1988");
        firstPersonInputDTO.setAdminId("otherdavidAdmin@gmail.com");
        familyMemberService.addFamilyMember(firstPersonInputDTO, familyOutputDTO.getFamilyId());

        ProfileInformationDTO firstProfileInformationDTO =
                familyMemberService.getProfileInformation("otherfabio@gmail.com", "otherfabio@gmail.com");

        PersonInputDTO secondPersonInputDTO = new PersonInputDTO();
        String expectedNameSecondPerson = "Gervasio";
        secondPersonInputDTO.setName(expectedNameSecondPerson);
        secondPersonInputDTO.setVat("227475777");
        secondPersonInputDTO.setEmail("gervasio@gmail.com");
        secondPersonInputDTO.setPhoneNumber("917899987");
        secondPersonInputDTO.setLocation("Porto");
        secondPersonInputDTO.setStreet("Rua das Flores");
        secondPersonInputDTO.setPostCode("4444-333");
        secondPersonInputDTO.setBirthDate("09/11/1988");
        secondPersonInputDTO.setAdminId("otherdavidAdmin@gmail.com");
        familyMemberService.addFamilyMember(secondPersonInputDTO, familyOutputDTO.getFamilyId());

        ProfileInformationDTO secondProfileInformationDTO =
                familyMemberService.getProfileInformation("gervasio@gmail.com", "gervasio@gmail.com");

        String firstPersonName = firstProfileInformationDTO.getName();
        String secondPersonName = secondProfileInformationDTO.getName();

        //Assert
        assertNotNull(firstProfileInformationDTO);
        assertNotNull(secondProfileInformationDTO);
        assertNotEquals(firstPersonName, secondPersonName);
    }

    @Test
    void ensureFamilyAdminGetsProperRoles() {
        familyMemberService.createFamily(familyInputDTO);
        Person admin = notCrudPersonRepository.getByEmail(new Email("notpassing@gmail.com"));

        Set<Role> expected = new HashSet<>();
        expected.add(new Role(ERole.ROLE_FAMILY_MEMBER));
        expected.add(new Role(ERole.ROLE_FAMILY_ADMIN));

        Set<Role> result = admin.getRoles();

        assertEquals(expected, result);
    }

    @Test
    void ensureFamilyMemberGetsProperRoles() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Miguel");
        String memberEmail = "bla@gmail.com";
        personInputDTO.setEmail(memberEmail);
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911122111");
        personInputDTO.setVat("222355222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("notpassing@gmail.com");

        familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        Person admin = notCrudPersonRepository.getByEmail(new Email(memberEmail));

        Set<Role> expected = new HashSet<>();
        expected.add(new Role(ERole.ROLE_FAMILY_MEMBER));

        Set<Role> result = admin.getRoles();

        assertEquals(expected, result);
    }

}