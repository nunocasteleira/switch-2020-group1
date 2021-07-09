package switchtwentytwenty.project.domain.domainservices;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.applicationservices.implservices.FamilyMemberService;
import switchtwentytwenty.project.applicationservices.implservices.RelationshipService;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.ObjectCanNotBeNullException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.shared.AccountId;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.family.RelationshipInputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Profile("FamilyDomainServiceItTest")
class FamilyDomainServiceItTest {
    @Autowired
    FamilyDomainService familyDomainService;
    @Autowired
    FamilyMemberService familyMemberService;
    @Autowired
    RelationshipService relationshipService;
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    IPersonRepositoryJPA personRepositoryJPA;
    @Autowired
    IFamilyRepositoryJPA familyRepositoryJPA;

    FamilyOutputDTO familyOutputDTO;
    Family family;

    String adminId1;
    String adminId2;
    PersonInputDTO personInputDTO1;
    PersonInputDTO personInputDTO2;

    @BeforeEach
    void setUpFamily() {
        String adminId = "micaela@gmail.com";
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("Micaela");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("Rua de Baixo");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("4774-123");
        familyInputDTO.setPhoneNumber("919999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("13/05/1990");
        familyInputDTO.setFamilyName("Passos");
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        family = familyRepository.getDatabaseSavedFamily(new FamilyId(familyOutputDTO.getFamilyId()));
    }

    @BeforeEach
    void setUpForRelationships(){
        adminId1 = "admin@gmail.com";
        adminId2 = "admin2@gmail.com";

        personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("António");
        personInputDTO1.setEmail("antonio@gmail.com");
        personInputDTO1.setStreet("Rua clara");
        personInputDTO1.setLocation("Porto");
        personInputDTO1.setPostCode("4000-000");
        personInputDTO1.setPhoneNumber("911111111");
        personInputDTO1.setVat("222333222");
        personInputDTO1.setBirthDate("05/03/2000");
        personInputDTO1.setAdminId(adminId1);

        personInputDTO2 = new PersonInputDTO();
        personInputDTO2.setName("Maria");
        personInputDTO2.setEmail("maria@gmail.com");
        personInputDTO2.setStreet("Rua escura");
        personInputDTO2.setLocation("Lisboa");
        personInputDTO2.setPostCode("5000-000");
        personInputDTO2.setPhoneNumber("912222222");
        personInputDTO2.setVat("212333444");
        personInputDTO2.setBirthDate("11/03/2000");
        personInputDTO2.setAdminId(adminId1);
    }

    @AfterEach
    void clean() {
        personRepositoryJPA.deleteAll();
        familyRepositoryJPA.deleteAll();
    }

    @Test
    void checkIfFamilyHasCashAccount_FamilyDoesNotHaveCashAccount() {
        AccountId accountId = new AccountId(0);
        familyDomainService.checkIfFamilyHasCashAccount(family);

        assertEquals(accountId, family.getCashAccountId());
    }

    @Test
    void checkIfFamilyHasCashAccount_FamilyAlreadyHasCashAccount() {
        AccountId accountId = new AccountId(29);
        family.setAccountId(accountId);

        assertThrows(DuplicateObjectException.class,
                () -> familyDomainService.checkIfFamilyHasCashAccount(family));
    }


    @Test
    void existsRelationship_True() {
        boolean result;

        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail(adminId1);
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO1 = familyMemberService.createFamily(familyInputDTO);
        Family family1 = familyRepository.getDatabaseSavedFamily(new FamilyId(familyOutputDTO1.getFamilyId()));

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                family1.getFamilyId().getFamilyId());
        AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                family1.getFamilyId().getFamilyId());

        // BIG RED FLAG, NEED TO UPDATE FAMILY MANUALLY AFTER EVERY CHANGE
        family1 = familyRepository.getDatabaseSavedFamily(family1.getFamilyId());

        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(person1Id.getEmail(), person2Id.getEmail(), "1");

        relationshipService.createRelationship(family1.getFamilyId().getFamilyId(), relationshipInputDTO);

        // BIG RED FLAG, NEED TO UPDATE FAMILY MANUALLY AFTER EVERY CHANGE
        family1 = familyRepository.getDatabaseSavedFamily(family1.getFamilyId());

        result = familyDomainService.existsRelationship(family1, new Email(person1Id.getEmail()),
                new Email(person2Id.getEmail()));

        assertTrue(result);

    }

    @Test
    void existsRelationship_NullPersonId() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail(adminId1);
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO1 = familyMemberService.createFamily(familyInputDTO);
        Family family1 = familyRepository.getDatabaseSavedFamily(new FamilyId(familyOutputDTO1.getFamilyId()));

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO1.getFamilyId());
        AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                familyOutputDTO1.getFamilyId());


        assertThrows(ObjectCanNotBeNullException.class,
                () -> familyDomainService.existsRelationship(family1, null,
                        new Email(person2Id.getEmail())));

        assertThrows(ObjectCanNotBeNullException.class,
                () -> familyDomainService.existsRelationship(family1, new Email(person1Id.getEmail()),
                        null));
    }

    @Test
    void existsRelationship_NotInTheFamily() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail(adminId1);
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO1 = familyMemberService.createFamily(familyInputDTO);
        Family family1 = familyRepository.getDatabaseSavedFamily(new FamilyId(familyOutputDTO1.getFamilyId()));

        FamilyInputDTO familyInputDTO2 = new FamilyInputDTO();
        familyInputDTO2.setName("Golias");
        familyInputDTO2.setEmail(adminId2);
        familyInputDTO2.setStreet("Golias St");
        familyInputDTO2.setLocation("Lisboa");
        familyInputDTO2.setPostCode("1234-321");
        familyInputDTO2.setPhoneNumber("961111111");
        familyInputDTO2.setVat("123456987");
        familyInputDTO2.setBirthDate("12/01/1998");
        familyInputDTO2.setFamilyName("Nunes");
        FamilyOutputDTO familyOutputDTO2 = familyMemberService.createFamily(familyInputDTO2);

        personInputDTO1.setAdminId(adminId2);
        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO2.getFamilyId());
        AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                familyOutputDTO1.getFamilyId());

        assertThrows(ObjectDoesNotExistException.class,
                () -> familyDomainService.existsRelationship(family1, new Email(person1Id.getEmail()),
                        new Email(person2Id.getEmail())));

        assertThrows(ObjectDoesNotExistException.class,
                () -> familyDomainService.existsRelationship(family1, new Email(person2Id.getEmail()),
                        new Email(person1Id.getEmail())));

    }
}