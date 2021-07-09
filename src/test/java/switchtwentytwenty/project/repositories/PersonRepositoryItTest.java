package switchtwentytwenty.project.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import switchtwentytwenty.project.applicationservices.implservices.FamilyMemberService;
import switchtwentytwenty.project.applicationservices.implservices.PersonService;
import switchtwentytwenty.project.datamodel.person.PersonJPA;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PersonRepositoryItTest {
    @Autowired
    FamilyMemberService familyMemberService;
    @Autowired
    PersonService personService;
    @Autowired
    IPersonRepositoryJPA iPersonRepositoryJPA;
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    IFamilyRepositoryJPA familyRepositoryJPA;

    @BeforeEach
    void clean() {
        iPersonRepositoryJPA.deleteAll();
        familyRepositoryJPA.deleteAll();
    }

    @Test
    void personIsCreatedSuccessfully() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail("something@gmail.com");
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Martins");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("something@gmail.com");
        Set<Role> roles = new HashSet<>();
        Role familyMember = new Role(ERole.ROLE_FAMILY_MEMBER);
        roles.add(familyMember);
        AddFamilyMemberDTO expected = new AddFamilyMemberDTO(personInputDTO.getEmail(), personInputDTO.getName(), personInputDTO.getStreet(), personInputDTO.getLocation(), personInputDTO.getPostCode(), personInputDTO.getBirthDate(), personInputDTO.getVat(), familyOutputDTO.getFamilyId(), personInputDTO.getPhoneNumber(), roles);
        int sizeExpected = 2;

        AddFamilyMemberDTO result = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        long sizeResult = iPersonRepositoryJPA.count();

        assertNotNull(result);
        assertEquals(expected, result);
        assertEquals(sizeExpected, sizeResult);
    }

    @Test
    void createAdministrator_Successfully() {
        // arrange
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail("admin@gmail.com");
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        PersonJPA expected = new PersonJPA(new EmailJPA(familyInputDTO.getEmail()), new PersonNameJPA(familyInputDTO.getName()),
                new AddressJPA(familyInputDTO.getStreet(), familyInputDTO.getLocation(), familyInputDTO.getPostCode()),
                new BirthDateJPA(familyInputDTO.getBirthDate()), new PersonVatJPA(familyInputDTO.getVat()), 0);
        iPersonRepositoryJPA.save(expected);
        int sizeExpected = 1;

        // act
        PersonJPA result = personRepository.fromOptionalToPersonJPA(new EmailJPA(familyInputDTO.getEmail()));
        long sizeResult = iPersonRepositoryJPA.count();

        assertEquals(expected, result);
        assertEquals(sizeExpected, sizeResult);
    }

    @Test
    void ensureValidEmailIsAdded() {
        //Arrange
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
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        String expectedName = "Fabio";
        personInputDTO.setName(expectedName);
        personInputDTO.setVat("227475987");
        String expectedEmail = "fabio@gmail.com";
        personInputDTO.setEmail(expectedEmail);
        personInputDTO.setPhoneNumber("917899987");
        personInputDTO.setLocation("Porto");
        personInputDTO.setStreet("Rua das Flores");
        personInputDTO.setPostCode("4444-333");
        String expectedBirthDate = "09/11/1988";
        personInputDTO.setBirthDate(expectedBirthDate);
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO personId = familyMemberService.addFamilyMember(personInputDTO,
                familyOutputDTO.getFamilyId());
        String validEmail = "joao@gmail.com";
        int expected = 2;
        personService.addEmail(personId.getEmail(), validEmail);

        //Act
        long result = iPersonRepositoryJPA.count();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void ensureThatOptionalToPersonIsThrowingException() {
        EmailJPA emailJPA = new EmailJPA("antonio@gmail.com");

        assertThrows(ObjectDoesNotExistException.class, () ->
                personRepository.fromOptionalToPersonJPA(emailJPA));
    }

    @Test
    void getPersonByEmailSuccessfully() {
        //Arrange
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail("something@gmail.com");
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Martins");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("something@gmail.com");
        familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        Email email = new Email("antonio@gmail.com");

        //Act
        Person person = personRepository.getByEmail(email);

        //Assert
        assertNotNull(person);
    }

    @Test
    void getPersonByEmailThrowsExceptionWhenPersonDoesNotExist() {
        //Arrange
        Email email = new Email("antonio@gmail.com");

        //Assert
        assertThrows(ObjectDoesNotExistException.class, () ->
                personRepository.getByEmail(email));
    }

    @Test
    void getEmailList() {
        //Arrange
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
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

        PersonInputDTO personInputDTO = new PersonInputDTO();
        String expectedName = "Fabio";
        personInputDTO.setName(expectedName);
        personInputDTO.setVat("227475987");
        String expectedEmail = "fabio@gmail.com";
        personInputDTO.setEmail(expectedEmail);
        personInputDTO.setPhoneNumber("917899987");
        personInputDTO.setLocation("Porto");
        personInputDTO.setStreet("Rua das Flores");
        personInputDTO.setPostCode("4444-333");
        String expectedBirthDate = "09/11/1988";
        personInputDTO.setBirthDate(expectedBirthDate);
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO personId = familyMemberService.addFamilyMember(personInputDTO,
                familyOutputDTO.getFamilyId());
        String validEmail = "joao@gmail.com";
        personService.addEmail(personId.getEmail(), validEmail);
        List<Email> mockList = new ArrayList<>();
        mockList.add(new Email(validEmail));


        //Act

        List<Email> emailList = personRepository.getEmailList(new Email(expectedEmail));

        //Assert
        assertNotNull(emailList);
        assertEquals(emailList.get(0), mockList.get(0));
        assertEquals(emailList.size(), mockList.size());
        assertNotSame(emailList, mockList);

    }
}