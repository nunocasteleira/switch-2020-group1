package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.person.EmailListDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.PersonRepository;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("PersonServiceTest")
class PersonServiceItTest {
    @Autowired
    PersonService personService;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    FamilyMemberService familyMemberService;
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    IPersonRepositoryJPA personRepositoryJPA;
    @Autowired
    IFamilyRepositoryJPA familyRepositoryJPA;

    @BeforeEach
    void initialize(){
        personRepositoryJPA.deleteAll();
        familyRepositoryJPA.deleteAll();
    }

    @Test
    void addEmailSuccessfully() {
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
        personInputDTO.setName("Miguel");
        personInputDTO.setEmail("miguel@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911122111");
        personInputDTO.setVat("222355222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        String email = "joao@gmail.com";
        AddFamilyMemberDTO id = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        EmailListDTO result = personService.addEmail(id.getEmail(), email);
        Email aPersonId = new Email("miguel@gmail.com");
        Person aPerson = personRepository.getByEmail(aPersonId);
        List<Email> expected = aPerson.getEmailAddresses();

        List<String> dtoList = new ArrayList<>();
        dtoList.add("joao@gmail.com");
        EmailListDTO expectedList = new EmailListDTO(dtoList);

        assertNotNull(result);
        assertEquals(expected.get(0).toString(), result.getEmailAddresses().get(0));
        assertEquals(expected.size(), result.getEmailAddresses().size());
        assertEquals(expectedList, result);
    }


    @Test
    void addRepeatedEmailUnsuccessfully() {
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
        personInputDTO.setName("Miguel");
        personInputDTO.setEmail("miguel@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911122111");
        personInputDTO.setVat("222355222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        String email = "antonio@gmail.com";
        AddFamilyMemberDTO id = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        assertThrows(IllegalArgumentException.class, () -> personService.addEmail(id.getEmail(),
                email));
    }


    @Test
    void removeEmailSuccessfully() {
        // Arrange
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
        familyMemberService.createFamily(familyInputDTO);

        String adminEmail = "antonio@gmail.com";
        String adminEmail2 = "antonio2@gmail.com";
        String adminEmail3 = "antonio3@gmail.com";

        personService.addEmail(adminEmail, adminEmail2);
        personService.addEmail(adminEmail, adminEmail3);

        // Act

        EmailListDTO result = personService.removeEmail(adminEmail, adminEmail3);

        Email aPersonId = new Email("antonio@gmail.com");
        Person aPerson = personRepository.getByEmail(aPersonId);
        List<Email> expected = aPerson.getEmailAddresses();

        List<String> dtoList = new ArrayList<>();
        dtoList.add("antonio2@gmail.com");
        EmailListDTO expectedList = new EmailListDTO(dtoList);

        // Equals

        assertNotNull(result);
        assertEquals(expected.get(0).toString(), result.getEmailAddresses().get(0));
        assertEquals(expectedList, result);
    }


    @Test
    void removePrimaryEmailUnsuccessfully() {
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
        String email = "antonio@gmail.com";
        familyMemberService.createFamily(familyInputDTO);

        assertThrows(IllegalArgumentException.class, () -> personService.removeEmail(email,
                email));
    }

    @Test
    void tryToRemoveEmailToANonExistentPerson() {

        assertThrows(ObjectDoesNotExistException.class, () -> personService.removeEmail("tete@gmail.com",
                "tina@gmail.com"));
    }
    /*@Test
    void tryToRemoveUnexistentEmail() {
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
        String email = "antonio@gmail.com";
        familyMemberService.createFamily(familyInputDTO);

        assertThrows(ObjectDoesNotExistException.class, () -> personService.removeEmail("antonio@gmail.com",
                "anEmail@sapo.pt"));
    }*/
}
