package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import switchtwentytwenty.project.domain.domainservices.FamilyDomainService;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.factories.FamilyFactory;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.dto.person.ProfileInformationDTO;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.PersonRepository;
import switchtwentytwenty.project.repositories.RoleRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Profile("FamilyMemberService")
class FamilyMemberServiceTest {

    @Mock
    PersonRepository personRepository;

    @Mock
    FamilyRepository familyRepository;

    @Mock
    FamilyFactory familyFactory;

    @Mock
    PasswordEncoder encoder;

    @Mock
    RoleRepository roleRepository;

    @Mock
    FamilyDomainService familyDomainService;

    @InjectMocks
    FamilyMemberService familyMemberService;

    @Test
    void createFamily_Successfully() {
        // arrange
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("António");
        familyInputDTO.setEmail("antonio@gmail.com");
        familyInputDTO.setStreet("Rua clara");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("4000-000");
        familyInputDTO.setPhoneNumber("911111111");
        familyInputDTO.setVat("222333222");
        familyInputDTO.setBirthDate("11/09/1999");
        familyInputDTO.setFamilyName("Silva");
        Family family = new Family.FamilyBuilder(new FamilyName("Silva"), new Email("antonio@gmail.com"))
                .withRegistrationDate()
                .withId()
                .build();
        Person administrator = new Person.Builder(new Email("admin@gmail.com")).build();

        when(familyFactory.getFamily()).thenReturn(family);
        when(familyRepository.save(family)).thenReturn(family);
        when(familyFactory.getAdministrator()).thenReturn(administrator);
        when(personRepository.addPerson(isA(Person.class))).thenReturn(administrator);
        when(encoder.encode(familyInputDTO.getPassword())).thenReturn("password");
        when(roleRepository.getByName(ERole.ROLE_FAMILY_ADMIN)).thenReturn(new Role(ERole.ROLE_FAMILY_ADMIN));
        doNothing().when(familyRepository).addFamilyMember(isA(Email.class), isA(FamilyId.class));

        // act
        FamilyOutputDTO result = familyMemberService.createFamily(familyInputDTO);

        //assert
        assertNotNull(result);
    }

    @Test
    void createFamily_WhenAdminAlreadyExists() {
        // arrange
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("António");
        familyInputDTO.setEmail("antonio@gmail.com");
        familyInputDTO.setStreet("Rua clara");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("4000-000");
        familyInputDTO.setPhoneNumber("911111111");
        familyInputDTO.setVat("222333222");
        familyInputDTO.setBirthDate("11/09/1999");
        familyInputDTO.setFamilyName("Silva");
        Family family = new Family.FamilyBuilder(new FamilyName("Silva"), new Email("antonio@gmail.com"))
                .withRegistrationDate()
                .withId()
                .build();
        Person administrator = new Person.Builder(new Email("admin@gmail.com")).build();

        when(familyFactory.getFamily()).thenReturn(family);
        when(familyRepository.save(family)).thenReturn(family);
        when(familyFactory.getAdministrator()).thenReturn(administrator);
        when(personRepository.addPerson(isA(Person.class))).thenReturn(administrator);
        when(encoder.encode(familyInputDTO.getPassword())).thenReturn("password");
        when(roleRepository.getByName(ERole.ROLE_FAMILY_ADMIN)).thenReturn(new Role(ERole.ROLE_FAMILY_ADMIN));
        doThrow(InvalidDataAccessApiUsageException.class).when(familyRepository).addFamilyMember(isA(Email.class), isA(FamilyId.class));

        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> familyMemberService.createFamily(familyInputDTO));
    }

    @Test
    void addFamilyMemberSuccessfully() {
        FamilyId familyId = new FamilyId(hashCode());
        PersonInputDTO adminInputDTO = new PersonInputDTO();
        adminInputDTO.setName("António");
        adminInputDTO.setEmail("antonio@gmail.com");
        adminInputDTO.setStreet("Rua clara");
        adminInputDTO.setLocation("Porto");
        adminInputDTO.setPostCode("4000-000");
        adminInputDTO.setPhoneNumber("911111111");
        adminInputDTO.setVat("222333222");
        adminInputDTO.setBirthDate("11/09/1999");
        adminInputDTO.setAdminId("antonio@gmail.com");
        String expected = "antonio@gmail.com";
        //when(personRepository.checkIfThePersonExists(adminInputDTO)).thenReturn(false);

        when(encoder.encode(adminInputDTO.getPassword())).thenReturn("password");
        when(roleRepository.getByName(ERole.ROLE_FAMILY_MEMBER)).thenReturn(new Role(ERole.ROLE_FAMILY_MEMBER));

        AddFamilyMemberDTO result = familyMemberService.addFamilyMember(adminInputDTO, familyId.getFamilyId());

        assertEquals(expected, result.getEmail());
    }

    @Test
    void addTwoFamilyMemberSuccessfully() {
        FamilyId familyId = new FamilyId(hashCode());
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Miguel");
        personInputDTO.setEmail("miguel@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911122111");
        personInputDTO.setVat("222355222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        String expected = "miguel@gmail.com";
        PersonInputDTO personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("Patrícia");
        personInputDTO1.setEmail("patricia@gmail.com");
        personInputDTO1.setStreet("Rua clara");
        personInputDTO1.setLocation("Porto");
        personInputDTO1.setPostCode("4000-000");
        personInputDTO1.setPhoneNumber("911442111");
        personInputDTO1.setVat("222555222");
        personInputDTO1.setBirthDate("11/09/1970");
        personInputDTO1.setAdminId("antonio@gmail.com");
        String expected1 = personInputDTO1.getEmail();
        //when(personRepository.checkIfThePersonExists(isA(PersonInputDTO.class))).thenReturn(false);

        when(encoder.encode(personInputDTO.getPassword())).thenReturn("password");
        when(encoder.encode(personInputDTO1.getPassword())).thenReturn("password");
        when(roleRepository.getByName(ERole.ROLE_FAMILY_MEMBER)).thenReturn(new Role(ERole.ROLE_FAMILY_MEMBER));

        AddFamilyMemberDTO result = familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId());
        AddFamilyMemberDTO result1 = familyMemberService.addFamilyMember(personInputDTO1,
                familyId.getFamilyId());

        assertNotNull(result);
        assertEquals(expected, result.getEmail());
        assertEquals(expected1, result1.getEmail());
    }

    @Test
    void addFamilyMemberSuccessfullyWithOutPhoneNumber() {
        FamilyId familyId = new FamilyId(hashCode());
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Miguel");
        personInputDTO.setEmail("miguel@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setVat("222355222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");

        String expected = personInputDTO.getEmail();
        //when(personRepository.checkIfThePersonExists(personInputDTO)).thenReturn(false);

        when(encoder.encode(personInputDTO.getPassword())).thenReturn("password");
        when(roleRepository.getByName(ERole.ROLE_FAMILY_MEMBER)).thenReturn(new Role(ERole.ROLE_FAMILY_MEMBER));

        AddFamilyMemberDTO result = familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId());

        assertNotNull(result);
        assertEquals(expected, result.getEmail());
    }

    @Test
    void addFamilyMemberWithRepeatedEmail() {
        FamilyId familyId = new FamilyId(hashCode());
        PersonInputDTO adminInputDTO = new PersonInputDTO();
        adminInputDTO.setName("António");
        adminInputDTO.setEmail("antonio@gmail.com");
        adminInputDTO.setStreet("Rua clara");
        adminInputDTO.setLocation("Porto");
        adminInputDTO.setPostCode("4000-000");
        adminInputDTO.setPhoneNumber("911111111");
        adminInputDTO.setVat("222333222");
        adminInputDTO.setBirthDate("11/09/1999");
        adminInputDTO.setAdminId("antonio@gmail.com");
        when(personRepository.addPerson(isA(Person.class))).thenThrow(InvalidDataAccessApiUsageException.class);
        when(encoder.encode(adminInputDTO.getPassword())).thenReturn("password");
        when(roleRepository.getByName(ERole.ROLE_FAMILY_MEMBER)).thenReturn(new Role(ERole.ROLE_FAMILY_MEMBER));

        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> familyMemberService.addFamilyMember(adminInputDTO, familyId.getFamilyId()));
    }

    @Test
    void getProfileInformationDTOFromAnNonExistingPerson() {
        //Arrange
        Email personId = new Email("gervasio@hotmail.com");
        when(personRepository.getByEmail(personId)).thenThrow(ObjectDoesNotExistException.class);

        //Assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> familyMemberService.getProfileInformation("gervasio@hotmail.com", "gervasio@hotmail.com"));
    }

    @Test
    void getProfileInformationDTOFromAnExistingPerson() {
        //Arrange
        String expectedName = "Fabio";
        String expectedEmail = "fabio@gmail.com";
        String expectedBirthDate = "09/11/1988";
        int expectedFamilyId = 0;
        Email personId = new Email(expectedEmail);
        Address address = new Address(
                "street",
                "location",
                "1234-123"
        );

        Person person = new Person.Builder(personId)
                .withName(new PersonName(expectedName))
                .withBirthDate(new BirthDate(expectedBirthDate))
                .withVat(new PersonVat("123456789"))
                .withAddress(address)
                .withFamilyId(new FamilyId(0))
                .build();

        FamilyName familyName = new FamilyName("Maia");
        FamilyId familyId = new FamilyId(0);
        Family family = new Family.FamilyBuilder(familyName, personId).withRegistrationDate().withId(familyId).build();

        when(personRepository.getByEmail(personId)).thenReturn(person);
        when(familyRepository.getDatabaseSavedFamily(familyId)).thenReturn(family);

        //Act
        ProfileInformationDTO profileInformationDTO = familyMemberService.getProfileInformation("fabio@gmail.com", "fabio@gmail.com");
        String actualName = profileInformationDTO.getName();
        String actualEmailAddress = profileInformationDTO.getMainEmailAddress();
        String actualBirthdate = profileInformationDTO.getBirthDate();
        long actualFamilyId = profileInformationDTO.getFamilyId();
        //Assert
        assertNotNull(profileInformationDTO);
        assertEquals(expectedBirthDate, actualBirthdate);
        assertEquals(expectedEmail, actualEmailAddress);
        assertEquals(expectedName, actualName);
        assertEquals(expectedFamilyId, actualFamilyId);
    }


    @Test
    void getProfileInformationDTOFromTwoExistingPersons() {
        //Arrange
        String expectedFirstPersonName = "Fabio";
        String expectedFirstEmailAddress = "fabio@gmail.com";
        Email firstPersonId = new Email(expectedFirstEmailAddress);
        String expectedFirstPersonBirthDate = "09/11/1988";

        String expectedSecondPersonName = "Gervasio";
        String expectedSecondEmailAddress = "gervasio@gmail.com";
        Email secondPersonId = new Email(expectedSecondEmailAddress);
        String expectedSecondPersonBirthDate = "23/04/2010";

        Address address = new Address(
                "street",
                "location",
                "1234-123"
        );

        Person firstPerson = new Person.Builder(firstPersonId)
                .withName(new PersonName(expectedFirstPersonName))
                .withAddress(address)
                .withBirthDate(new BirthDate(expectedFirstPersonBirthDate))
                .withVat(new PersonVat("123456789"))
                .withFamilyId(new FamilyId(0))
                .build();

        Person secondPerson = new Person.Builder(secondPersonId)
                .withName(new PersonName(expectedSecondPersonName))
                .withAddress(address)
                .withBirthDate(new BirthDate(expectedSecondPersonBirthDate))
                .withVat(new PersonVat("213456789"))
                .withFamilyId(new FamilyId(0))
                .build();

        FamilyName familyName = new FamilyName("Maia");
        FamilyId familyId = new FamilyId(0);
        Family family = new Family.FamilyBuilder(familyName, firstPersonId).withRegistrationDate().withId(familyId).build();

        when(personRepository.getByEmail(firstPersonId)).thenReturn(firstPerson);
        when(personRepository.getByEmail(secondPersonId)).thenReturn(secondPerson);
        when(familyRepository.getDatabaseSavedFamily(familyId)).thenReturn(family);

        //Act
        ProfileInformationDTO firstProfileInformationDTO =
                familyMemberService.getProfileInformation("fabio@gmail.com", "fabio@gmail.com");
        ProfileInformationDTO secondProfileInformationDTO =
                familyMemberService.getProfileInformation("gervasio@gmail.com", "gervasio@gmail.com");

        String firstPersonName = firstProfileInformationDTO.getName();
        String firstEmailAddress = firstProfileInformationDTO.getMainEmailAddress();
        String firstBirthdate = firstProfileInformationDTO.getBirthDate();

        String secondPersonName = secondProfileInformationDTO.getName();
        String secondEmailAddress = secondProfileInformationDTO.getMainEmailAddress();
        String secondBirthdate = secondProfileInformationDTO.getBirthDate();

        //Assert
        assertNotNull(firstProfileInformationDTO);
        assertNotNull(secondProfileInformationDTO);

        assertNotEquals(firstPersonName, secondPersonName);

        assertEquals(expectedFirstPersonName, firstPersonName);
        assertEquals(expectedFirstEmailAddress, firstEmailAddress);
        assertEquals(expectedFirstPersonBirthDate, firstBirthdate);

        assertEquals(expectedSecondPersonName, secondPersonName);
        assertEquals(expectedSecondEmailAddress, secondEmailAddress);
        assertEquals(expectedSecondPersonBirthDate, secondBirthdate);
    }
}