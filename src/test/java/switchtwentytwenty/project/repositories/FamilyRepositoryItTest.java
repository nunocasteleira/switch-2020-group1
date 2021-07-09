package switchtwentytwenty.project.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import switchtwentytwenty.project.applicationservices.implservices.FamilyMemberService;
import switchtwentytwenty.project.applicationservices.implservices.RelationshipService;
import switchtwentytwenty.project.datamodel.assembler.FamilyDomainDataAssembler;
import switchtwentytwenty.project.datamodel.assembler.PersonDomainDataAssembler;
import switchtwentytwenty.project.datamodel.family.FamilyJPA;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.family.RelationshipInputDTO;
import switchtwentytwenty.project.dto.family.RelationshipOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FamilyRepositoryItTest {

    Email adminId;

    Person person1;
    Person person2;
    Person person3;

    FamilyId familyId;
    Family family;

    Email email1;
    Email email2;
    Email email3;

    PersonInputDTO personInputDTO1;
    PersonInputDTO personInputDTO2;
    PersonInputDTO personInputDTO3;

    @Autowired
    FamilyMemberService familyMemberService;
    @Autowired
    RelationshipService relationshipService;
    @Autowired
    IFamilyRepositoryJPA iFamilyRepositoryJPA;
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    PersonDomainDataAssembler assembler;
    @Autowired
    IPersonRepositoryJPA iPersonRepositoryJPA;
    @Autowired
    FamilyDomainDataAssembler familyDomainDataAssembler;


    @BeforeEach
    void initialize() {
        adminId = new Email("admin@gmail.com");

        FamilyName familyName = new FamilyName("Silva");
        family = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();
        familyId = family.getFamilyId();

        email1 = new Email("antonio@gmail.com");
        person1 = new Person.Builder(email1)
                .withName(new PersonName("António"))
                .withAddress(new Address("Rua clara", "Porto", "4000-000"))
                .withBirthDate(new BirthDate("05/03/2000"))
                .withVat(new PersonVat("222333222"))
                .withFamilyId(familyId)
                .build();

        email2 = new Email("maria@gmail.com");
        person2 = new Person.Builder(email2)
                .withName(new PersonName("Maria"))
                .withAddress(new Address("Rua escura", "Lisboa", "5000-000"))
                .withBirthDate(new BirthDate("11/03/2000"))
                .withVat(new PersonVat("212333444"))
                .withFamilyId(familyId)
                .build();

        email3 = new Email("zedebraga@gmail.com");
        person3 = new Person.Builder(email3)
                .withName(new PersonName("Ze"))
                .withAddress(new Address("Rua cansada", "Braga", "3000-000"))
                .withBirthDate(new BirthDate("18/07/2003"))
                .withVat(new PersonVat("253333445"))
                .withFamilyId(familyId)
                .build();

        personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("António");
        personInputDTO1.setEmail("antonio@gmail.com");
        personInputDTO1.setStreet("Rua clara");
        personInputDTO1.setLocation("Porto");
        personInputDTO1.setPostCode("4000-000");
        personInputDTO1.setPhoneNumber("911111111");
        personInputDTO1.setVat("222333222");
        personInputDTO1.setBirthDate("05/03/2000");
        personInputDTO1.setAdminId("admin@gmail.com");

        personInputDTO2 = new PersonInputDTO();
        personInputDTO2.setName("Maria");
        personInputDTO2.setEmail("maria@gmail.com");
        personInputDTO2.setStreet("Rua escura");
        personInputDTO2.setLocation("Lisboa");
        personInputDTO2.setPostCode("5000-000");
        personInputDTO2.setPhoneNumber("912222222");
        personInputDTO2.setVat("212333444");
        personInputDTO2.setBirthDate("11/03/2000");
        personInputDTO2.setAdminId("admin@gmail.com");

        personInputDTO3 = new PersonInputDTO();
        personInputDTO3.setName("Ze");
        personInputDTO3.setEmail("zedebraga@gmail.com");
        personInputDTO3.setStreet("Rua cansada");
        personInputDTO3.setLocation("Braga");
        personInputDTO3.setPostCode("3000-000");
        personInputDTO3.setPhoneNumber("913333333");
        personInputDTO3.setVat("253333445");
        personInputDTO3.setBirthDate("18/07/2003");
        personInputDTO3.setAdminId("admin@gmail.com");

    }

    @AfterEach
    void clean() {
        iPersonRepositoryJPA.deleteAll();
        iFamilyRepositoryJPA.deleteAll();
    }

    @Test
    void createFamily_Successfully() {
        // arrange
        FamilyName familyName = new FamilyName("Silva");
        Email adminId = new Email("admin@gmail.com");
        int expected = 1;
        Family expectedFamily = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();
        FamilyJPA familyJPA = familyDomainDataAssembler.toData(expectedFamily);

        // act
        iFamilyRepositoryJPA.save(familyJPA);

        FamilyId familyIdResult = new FamilyId(familyJPA.getFamilyId());
        Family result = familyRepository.getDatabaseSavedFamily(familyIdResult);

        // assert
        assertEquals(expected, iFamilyRepositoryJPA.count());
        assertEquals(expectedFamily, result);
    }


    @Test
    void getFamilyMembersList_Successfully() {
        FamilyJPA familyJPA = familyDomainDataAssembler.toData(family);
        FamilyJPA savedFamily = iFamilyRepositoryJPA.save(familyJPA);
        FamilyId familyId = new FamilyId(savedFamily.getFamilyId());
        familyMemberService.addFamilyMember(personInputDTO1, familyId.getFamilyId());
        familyMemberService.addFamilyMember(personInputDTO2, familyId.getFamilyId());
        int expected = 2;

        int result = familyRepository.getFamilyMembers(familyId).size();

        assertEquals(expected, result);
        assertNotNull(result);
    }

    @Test
    void getFamilyMembersList_Exception_FamilyIdNull() {
        assertThrows(NullPointerException.class,
                () -> familyRepository.getFamilyMembers(null).size());
    }

    @Test
    void ensureExceptionIsThrow() {
        FamilyId familyId = new FamilyId(hashCode());

        assertThrows(ObjectDoesNotExistException.class,
                () -> familyRepository.getDatabaseSavedFamily(familyId));
    }

    @Test
    void ensureExceptionIsThrowWhenAddMemberToANonExistingFamily() {
        FamilyId familyId = new FamilyId(hashCode());
        Email email = new Email("some@gmail.com");

        assertThrows(ObjectDoesNotExistException.class,
                () -> familyRepository.addFamilyMember(email, familyId));
    }

    @Test
    void saveFamilyWithCashAccount_Successfully() {
        FamilyJPA familyJPA = familyDomainDataAssembler.toData(family);
        family.setAccountId(new AccountId(800));
        FamilyJPA savedFamily = iFamilyRepositoryJPA.save(familyJPA);
        FamilyId familyId = new FamilyId(savedFamily.getFamilyId());
        family.setFamilyId(familyId);

        familyRepository.saveFamilyWithAccount(family);

        Family resultFamily = familyRepository.getDatabaseSavedFamily(familyId);

        assertEquals(family.getCashAccountId(), resultFamily.getCashAccountId());
    }

    @Test
    void changeRelationship_Successfully() {
        FamilyJPA familyJPA = familyDomainDataAssembler.toData(family);
        FamilyJPA savedFamily = iFamilyRepositoryJPA.save(familyJPA);
        long familyId = savedFamily.getFamilyId();
        familyMemberService.addFamilyMember(personInputDTO1, familyId);
        familyMemberService.addFamilyMember(personInputDTO2, familyId);
        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(email1.getEmailAddress(),email2.getEmailAddress(), "1" );
        RelationshipOutputDTO createdRelationship = relationshipService.createRelationship(familyId, relationshipInputDTO);
        int relationshipId = createdRelationship.getRelationshipId();

        RelationshipType newRelationshipType = RelationshipType.valueOf(2);
        Relationship expectedRelationship = new Relationship(newRelationshipType, email1, email2);

        Relationship result = familyRepository.changeRelationshipAndSave(familyId, relationshipId, newRelationshipType);

        assertEquals(expectedRelationship, result);
        assertEquals(expectedRelationship.getMainUserId(), result.getMainUserId());
        assertEquals(expectedRelationship.getOtherUserId(), result.getOtherUserId());
        assertEquals(expectedRelationship.getFamilyRelationshipType(), result.getFamilyRelationshipType());
    }

    @Test
    void failToChangeRelationship_FamilyDoesNotExist() {
        long nonexistentFamilyId = 0;
        RelationshipType aRelationshipType = RelationshipType.valueOf(2);

        assertThrows(ObjectDoesNotExistException.class, () -> familyRepository.changeRelationshipAndSave(nonexistentFamilyId, 1, aRelationshipType));
    }

    @Test
    void failToChangeRelationship_RelationshipDoesNotExist() {
        FamilyJPA familyJPA = familyDomainDataAssembler.toData(family);
        FamilyJPA savedFamily = iFamilyRepositoryJPA.save(familyJPA);
        long familyId = savedFamily.getFamilyId();
        int nonexistentRelationshipId = 0;
        RelationshipType aRelationshipType = RelationshipType.valueOf(2);

        assertThrows(ObjectDoesNotExistException.class, () -> familyRepository.changeRelationshipAndSave(familyId, nonexistentRelationshipId, aRelationshipType));
    }
}