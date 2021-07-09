package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.applicationservices.implservices.FamilyMemberService;
import switchtwentytwenty.project.applicationservices.implservices.RelationshipService;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.Relationship;
import switchtwentytwenty.project.domain.model.shared.RelationshipType;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.family.*;
import switchtwentytwenty.project.dto.person.FamilyMemberOutputDTO;
import switchtwentytwenty.project.dto.person.FamilyMembersOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.PersonRepository;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
@Profile("FamilyControllerTest")
class FamilyControllerItTest {
    @Autowired
    FamilyController controller;
    @Autowired
    RelationshipService relationshipService;
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    IPersonRepositoryJPA personRepositoryJPA;
    @Autowired
    FamilyMemberService familyMemberService;
    @Autowired
    IFamilyRepositoryJPA familyRepositoryJPA;

    FamilyInputDTO familyInputDTO;

    @BeforeEach
    void initialize() {
        familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail("admin@gmail.com");
        familyInputDTO.setStreet("Rua clara");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("4000-000");
        familyInputDTO.setPhoneNumber("911111111");
        familyInputDTO.setVat("222333555");
        familyInputDTO.setBirthDate("11/09/1999");
        familyInputDTO.setFamilyName("Silva");
    }

    @AfterEach
    void clean() {
        personRepositoryJPA.deleteAll();
        familyRepositoryJPA.deleteAll();
    }

    @Test
    void createFamily_Successfully() {
        //arrange
        FamilyOutputDTO expectedDTO = new FamilyOutputDTO();
        expectedDTO.setFamilyName("Silva");
        expectedDTO.setAdminId("admin@gmail.com");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String registrationDate = today.format(formatter);
        expectedDTO.setRegistrationDate(registrationDate);
        Link selfLink = linkTo(FamilyController.class).slash("1").withRel("1");
        expectedDTO.add(selfLink);

        //act
        ResponseEntity<Object> result = controller.createFamily(familyInputDTO);
        FamilyOutputDTO resultDTO = (FamilyOutputDTO) result.getBody();

        //assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedDTO, resultDTO);
    }

    @Test
    void createFamily_WithNonExistingFamily() {
        HttpStatus expected = HttpStatus.BAD_REQUEST;
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("toni@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");

        //act
        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO, 200);

        //assert
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureFamilyMemberIsAdded() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("toni@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId("admin@gmail.com");
        int expected = 2;
        Set<Role> roles = new HashSet<>();
        Role familyMember = new Role(ERole.ROLE_FAMILY_MEMBER);
        roles.add(familyMember);

        ResponseEntity<Object> objectResult = controller.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO resultDTO = (AddFamilyMemberDTO) objectResult.getBody();
        AddFamilyMemberDTO expectedDTO = new AddFamilyMemberDTO(personInputDTO.getEmail(), personInputDTO.getName(), personInputDTO.getStreet(), personInputDTO.getLocation(), personInputDTO.getPostCode(), personInputDTO.getBirthDate(), personInputDTO.getVat(), familyOutputDTO.getFamilyId(), personInputDTO.getPhoneNumber(), roles);
        Link selfLink = linkTo(methodOn(PersonController.class).getProfileInformation(personInputDTO.getEmail())).withRel(personInputDTO.getEmail());
        expectedDTO.add(selfLink);
        long result = personRepositoryJPA.count();

        assertEquals(expected, result);
        assertEquals(expectedDTO, resultDTO);
    }

    @Test
    void ensureDifferentAdminCannotAddMembers() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("toni@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId("tony@gmail.com");

        ResponseEntity<Object> objectResult = controller.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        assertEquals(HttpStatus.FORBIDDEN, objectResult.getStatusCode());
    }

//    @Test
//    void ensureFamilyMemberIsAddedToItsFamily() {
//        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
//        familyInputDTO.setName("António");
//        familyInputDTO.setEmail("antonio@gmail.com");
//        familyInputDTO.setStreet("Rua clara");
//        familyInputDTO.setLocation("Porto");
//        familyInputDTO.setPostCode("4000-000");
//        familyInputDTO.setPhoneNumber("911111111");
//        familyInputDTO.setVat("222333222");
//        familyInputDTO.setBirthDate("11/09/1999");
//        familyInputDTO.setFamilyName("Silva");
//
//        familyId = familyMemberService.createFamily(familyInputDTO);
//
//        PersonInputDTO personInputDTO = new PersonInputDTO();
//        personInputDTO.setName("António");
//        personInputDTO.setEmail("toni@gmail.com");
//        personInputDTO.setStreet("Rua clara");
//        personInputDTO.setLocation("Porto");
//        personInputDTO.setPostCode("4000-000");
//        personInputDTO.setPhoneNumber("911111111");
//        personInputDTO.setVat("222333222");
//        personInputDTO.setBirthDate("05/03/2000");
//
//        int expected = 2;
//
//        controller.addFamilyMember(personInputDTO, familyId);
//        Family family = familyRepository.getByFamilyIdJPA(familyId);
//
//        int result = family.getFamilyMemberList().size();
//
//        assertEquals(expected, result);
//    }

    @Test
    void ensureNotPossibleToAddTwoPersonsWithSameEmail() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId("admin@gmail.com");
        controller.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO,
                familyOutputDTO.getFamilyId());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void personIsNotCreatedWithNullName(String candidate) {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName(candidate);
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId("admin@gmail.com");

        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO,
                familyOutputDTO.getFamilyId());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void personIsNotCreatedWithNullEmail() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Antonio");
        personInputDTO.setEmail(null);
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId("admin@gmail.com");

        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void personIsNotCreatedWithNullStreet(String candidate) {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Antonio");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet(candidate);
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId("admin@gmail.com");

        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void personIsNotCreatedWithInvalidLocation(String candidate) {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Antonio");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation(candidate);
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId("admin@gmail.com");

        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void personIsNotCreatedWithInvalidPostCode(String candidate) {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Antonio");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("porto");
        personInputDTO.setPostCode(candidate);
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId("admin@gmail.com");

        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void personIsNotCreatedWithEmptyTelephone() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Antonio");
        personInputDTO.setEmail("toni@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("porto");
        personInputDTO.setPostCode("4400-000");
        personInputDTO.setPhoneNumber("");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId("admin@gmail.com");

        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void personIsNotCreatedWithInvalidVat(String candidate) {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Antonio");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("porto");
        personInputDTO.setPostCode("4400-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat(candidate);
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId("admin@gmail.com");

        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO,
                familyOutputDTO.getFamilyId());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"02/12/2022", "", "02-02-2000"})
    void personIsNotCreatedWithInvalidBirthDate(String candidate) {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Antonio");
        personInputDTO.setEmail("toni@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("porto");
        personInputDTO.setPostCode("4400-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate(candidate);
        personInputDTO.setAdminId("admin@gmail.com");

        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO,
                familyOutputDTO.getFamilyId());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void createRelationship_WithTwoPeopleThatAreTheSame() {
        ResponseEntity<Object> responseEntity;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        PersonInputDTO personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("António");
        personInputDTO1.setEmail("antonio@gmail.com");
        personInputDTO1.setStreet("Rua clara");
        personInputDTO1.setLocation("Porto");
        personInputDTO1.setPostCode("4000-000");
        personInputDTO1.setPhoneNumber("911111111");
        personInputDTO1.setVat("222333222");
        personInputDTO1.setBirthDate("05/03/2000");
        personInputDTO1.setAdminId("admin@gmail.com");

        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());

        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(person1Id.getEmail(), person1Id.getEmail(),
                "1");

        responseEntity = controller.createRelationship(relationshipInputDTO, familyIdInt);

        assertNotNull(responseEntity);
        assertEquals(expected, responseEntity.getStatusCode());
    }

    @Test
    void createRelationships_Successfully() {
        ResponseEntity<Object> responseEntity1;
        ResponseEntity<Object> responseEntity2;
        ResponseEntity<Object> responseEntity3;
        HttpStatus expected = HttpStatus.CREATED;

        PersonInputDTO personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("António");
        personInputDTO1.setEmail("antonio@gmail.com");
        personInputDTO1.setStreet("Rua clara");
        personInputDTO1.setLocation("Porto");
        personInputDTO1.setPostCode("4000-000");
        personInputDTO1.setPhoneNumber("911111111");
        personInputDTO1.setVat("222333222");
        personInputDTO1.setBirthDate("05/03/2000");
        personInputDTO1.setAdminId("admin@gmail.com");


        PersonInputDTO personInputDTO2 = new PersonInputDTO();
        personInputDTO2.setName("Maria");
        personInputDTO2.setEmail("maria@gmail.com");
        personInputDTO2.setStreet("Rua escura");
        personInputDTO2.setLocation("Lisboa");
        personInputDTO2.setPostCode("5000-000");
        personInputDTO2.setPhoneNumber("912222222");
        personInputDTO2.setVat("212333444");
        personInputDTO2.setBirthDate("11/03/2000");
        personInputDTO2.setAdminId("admin@gmail.com");


        PersonInputDTO personInputDTO3 = new PersonInputDTO();
        personInputDTO3.setName("Ze");
        personInputDTO3.setEmail("zedebraga@gmail.com");
        personInputDTO3.setStreet("Rua cansada");
        personInputDTO3.setLocation("Braga");
        personInputDTO3.setPostCode("3000-000");
        personInputDTO3.setPhoneNumber("913333333");
        personInputDTO3.setVat("253333445");
        personInputDTO3.setBirthDate("18/07/2003");
        personInputDTO3.setAdminId("admin@gmail.com");


        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO person3Id = familyMemberService.addFamilyMember(personInputDTO3,
                familyOutputDTO.getFamilyId());

        RelationshipInputDTO relationshipInputDTO1 =
                new RelationshipInputDTO(person1Id.getEmail(), person2Id.getEmail(),
                        "1");

        RelationshipInputDTO relationshipInputDTO2 =
                new RelationshipInputDTO(person2Id.getEmail(), person3Id.getEmail(),
                        "4");

        RelationshipInputDTO relationshipInputDTO3 =
                new RelationshipInputDTO(person3Id.getEmail(), person1Id.getEmail(),
                        "10");


        responseEntity1 = controller.createRelationship(relationshipInputDTO1, familyIdInt);
        responseEntity2 = controller.createRelationship(relationshipInputDTO2, familyIdInt);
        responseEntity3 = controller.createRelationship(relationshipInputDTO3, familyIdInt);

        assertNotNull(responseEntity1);
        assertEquals(expected, responseEntity1.getStatusCode());

        assertNotNull(responseEntity2);
        assertEquals(expected, responseEntity2.getStatusCode());

        assertNotNull(responseEntity3);
        assertEquals(expected, responseEntity3.getStatusCode());
    }

    @Test
    void createRelationships_NullUserId() {
        ResponseEntity<Object> responseEntity1;
        ResponseEntity<Object> responseEntity2;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        PersonInputDTO personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("António");
        personInputDTO1.setEmail("antonio@gmail.com");
        personInputDTO1.setStreet("Rua clara");
        personInputDTO1.setLocation("Porto");
        personInputDTO1.setPostCode("4000-000");
        personInputDTO1.setPhoneNumber("911111111");
        personInputDTO1.setVat("222333222");
        personInputDTO1.setBirthDate("05/03/2000");
        personInputDTO1.setAdminId("admin@gmail.com");


        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());

        RelationshipInputDTO relationshipInputDTO1 =
                new RelationshipInputDTO(null, person1Id.getEmail(),
                        "1");

        RelationshipInputDTO relationshipInputDTO2 =
                new RelationshipInputDTO(person1Id.getEmail(), null,
                        "4");

        responseEntity1 = controller.createRelationship(relationshipInputDTO1, familyIdInt);
        responseEntity2 = controller.createRelationship(relationshipInputDTO2, familyIdInt);

        assertNotNull(responseEntity1);
        assertEquals(expected, responseEntity1.getStatusCode());

        assertNotNull(responseEntity2);
        assertEquals(expected, responseEntity2.getStatusCode());

    }

    @Test
    void createRelationships_NullRelationshipType() {
        ResponseEntity<Object> responseEntity1;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        PersonInputDTO personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("António");
        personInputDTO1.setEmail("antonio@gmail.com");
        personInputDTO1.setStreet("Rua clara");
        personInputDTO1.setLocation("Porto");
        personInputDTO1.setPostCode("4000-000");
        personInputDTO1.setPhoneNumber("911111111");
        personInputDTO1.setVat("222333222");
        personInputDTO1.setBirthDate("05/03/2000");
        personInputDTO1.setAdminId("admin@gmail.com");

        PersonInputDTO personInputDTO2 = new PersonInputDTO();
        personInputDTO2.setName("Maria");
        personInputDTO2.setEmail("maria@gmail.com");
        personInputDTO2.setStreet("Rua escura");
        personInputDTO2.setLocation("Lisboa");
        personInputDTO2.setPostCode("5000-000");
        personInputDTO2.setPhoneNumber("912222222");
        personInputDTO2.setVat("212333444");
        personInputDTO2.setBirthDate("11/03/2000");
        personInputDTO2.setAdminId("admin@gmail.com");

        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                familyOutputDTO.getFamilyId());

        RelationshipInputDTO relationshipInputDTO1 =
                new RelationshipInputDTO(person1Id.getEmail(), person2Id.getEmail(),
                        null);

        responseEntity1 = controller.createRelationship(relationshipInputDTO1, familyIdInt);

        assertNotNull(responseEntity1);
        assertEquals(expected, responseEntity1.getStatusCode());

    }

    @Test
    void createRelationships_WrongFamilyId() {
        ResponseEntity<Object> responseEntity1;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        PersonInputDTO personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("António");
        personInputDTO1.setEmail("antonio@gmail.com");
        personInputDTO1.setStreet("Rua clara");
        personInputDTO1.setLocation("Porto");
        personInputDTO1.setPostCode("4000-000");
        personInputDTO1.setPhoneNumber("911111111");
        personInputDTO1.setVat("222333222");
        personInputDTO1.setBirthDate("05/03/2000");
        personInputDTO1.setAdminId("admin@gmail.com");

        PersonInputDTO personInputDTO2 = new PersonInputDTO();
        personInputDTO2.setName("Maria");
        personInputDTO2.setEmail("maria@gmail.com");
        personInputDTO2.setStreet("Rua escura");
        personInputDTO2.setLocation("Lisboa");
        personInputDTO2.setPostCode("5000-000");
        personInputDTO2.setPhoneNumber("912222222");
        personInputDTO2.setVat("212333444");
        personInputDTO2.setBirthDate("11/03/2000");
        personInputDTO2.setAdminId("admin2@gmail.com");


        FamilyInputDTO familyInputDTO2 = new FamilyInputDTO();
        familyInputDTO2.setName("Golias");
        familyInputDTO2.setEmail("admin2@gmail.com");
        familyInputDTO2.setStreet("Rua escura");
        familyInputDTO2.setLocation("Lisboa");
        familyInputDTO2.setPostCode("4300-000");
        familyInputDTO2.setPhoneNumber("919999999");
        familyInputDTO2.setVat("222333666");
        familyInputDTO2.setBirthDate("11/09/1998");
        familyInputDTO2.setFamilyName("Gomes");

        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        FamilyOutputDTO familyOutputDTO2 = familyMemberService.createFamily(familyInputDTO2);

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                familyOutputDTO2.getFamilyId());

        RelationshipInputDTO relationshipInputDTO1 =
                new RelationshipInputDTO(person1Id.getEmail(), person2Id.getEmail(),
                        "1");

        responseEntity1 = controller.createRelationship(relationshipInputDTO1, familyIdInt);

        assertNotNull(responseEntity1);
        assertEquals(expected, responseEntity1.getStatusCode());

    }

    //    DEPRECATED TEST, WILL PROBABLY BE USED IN THE FUTURE
//    @Test
//    void getRelationshipTypesList_Successfully() {
//        List<RelationshipType> relationshipTypesList =
//                controller.getRelationshipTypesList();
//
//        int expectedListSize = 10;
//        int actualListSize = relationshipTypesList.size();
//
//        assertNotNull(relationshipTypesList);
//        assertEquals(expectedListSize, actualListSize);
//    }

    //    DEPRECATED TEST, WILL PROBABLY BE USED IN THE FUTURE
//    @Test
//    void getFamilyMembersList_Successfully() {
//        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
//        familyInputDTO.setName("David");
//        familyInputDTO.setEmail(adminId);
//        familyInputDTO.setStreet("David St");
//        familyInputDTO.setLocation("Porto");
//        familyInputDTO.setPostCode("1234-123");
//        familyInputDTO.setPhoneNumber("969999999");
//        familyInputDTO.setVat("123456789");
//        familyInputDTO.setBirthDate("12/01/2000");
//        familyInputDTO.setFamilyName("Silva");
//        FamilyId familyId = familyMemberService.createFamily(familyInputDTO);
//
//        familyMemberService.addFamilyMember(personInputDTO1, familyId);
//        familyMemberService.addFamilyMember(personInputDTO2, familyId);
//        familyMemberService.addFamilyMember(personInputDTO3, familyId);
//
//        List<Email> actualPersonList = controller.getFamilyMembersList(familyId);
//
//        int expectedListSize = 3;
//        int actualListSize = actualPersonList.size();
//
//        assertEquals(expectedListSize, actualListSize);
//        assertNotNull(actualPersonList);
//    }

    @Test
    void getListOfMembersAndTheirRelationsIntegration() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();

        PersonInputDTO otherUserInputDTO = new PersonInputDTO();
        otherUserInputDTO.setEmail("maria@teste.com");
        otherUserInputDTO.setName("Maria");
        otherUserInputDTO.setVat("223456789");
        otherUserInputDTO.setStreet("Maria St");
        otherUserInputDTO.setLocation("Porto");
        otherUserInputDTO.setPostCode("1234-123");
        otherUserInputDTO.setBirthDate("10/01/2000");
        otherUserInputDTO.setPhoneNumber("919999999");
        otherUserInputDTO.setAdminId(familyOutputDTO.getAdminId());
        AddFamilyMemberDTO otherUserId = familyMemberService.addFamilyMember(otherUserInputDTO,
                familyId);
        HttpStatus expected = HttpStatus.OK;

        List<RelationshipOutputDTO> dtoList = new ArrayList<>();

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(familyInputDTO.getEmail());
        Email email2 = new Email(otherUserInputDTO.getEmail());

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);

        dtoList.add(relationshipMapper.toDTO(relationship1, familyInputDTO.getName(), otherUserInputDTO.getName()));
        RelationshipListDTO expectedList = new RelationshipListDTO(dtoList);

        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(familyInputDTO.getEmail(), otherUserId.getEmail(), "4");

        relationshipService.createRelationship(familyId, relationshipInputDTO);

        ResponseEntity<Object> result =
                controller.getListOfMembersAndTheirRelations(familyId);

        assertEquals(expected, result.getStatusCode());
        assertEquals(expectedList, result.getBody());
    }

    @Test
    void getEmptyList() {
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();

        List<RelationshipOutputDTO> dtoList = new ArrayList<>();
        RelationshipListDTO expectedList = new RelationshipListDTO(dtoList);

        HttpStatus expected = HttpStatus.OK;

        ResponseEntity<Object> result =
                controller.getListOfMembersAndTheirRelations(familyId);

        assertEquals(expected, result.getStatusCode());
        assertEquals(expectedList, result.getBody());
    }

    @Test
    void tryToRetrieveRelationshipFromNonExistingFamily() {
        ResponseEntity<Object> responseEntity;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        responseEntity = controller.getListOfMembersAndTheirRelations(0);

        assertNotNull(responseEntity);
        assertEquals(expected, responseEntity.getStatusCode());
    }

/*    @Test
    void getFamilyMembers_Successfully() {
        //arrange
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();

        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        personInputDTO.setAdminId(familyOutputDTO.getAdminId());
        AddFamilyMemberDTO addFamilyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        FamilyMembersOutputDTO expectedDTO = new FamilyMembersOutputDTO();
        List<Email> familyMembers = new ArrayList<>();
        familyMembers.add(new Email(familyOutputDTO.getAdminId()));
        familyMembers.add(new Email(addFamilyMemberDTO.getEmail()));
        expectedDTO.setFamilyMembers(familyMembers);

        HttpStatus expected = HttpStatus.OK;

        //act
        ResponseEntity<Object> result = controller.getFamilyMembers(familyId);
        FamilyMembersOutputDTO resultDTO = (FamilyMembersOutputDTO) result.getBody();

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
        assertEquals(expectedDTO, resultDTO);
    }

    @Test
    void getFamilyMembers_OnlyAdministrator() {
        //arrange
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();

        FamilyMembersOutputDTO expectedDTO = new FamilyMembersOutputDTO();
        List<Email> familyMembers = new ArrayList<>();
        familyMembers.add(new Email(familyOutputDTO.getAdminId()));
        expectedDTO.setFamilyMembers(familyMembers);

        HttpStatus expected = HttpStatus.OK;

        //act
        ResponseEntity<Object> result = controller.getFamilyMembers(familyId);
        FamilyMembersOutputDTO resultDTO = (FamilyMembersOutputDTO) result.getBody();

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
        assertEquals(expectedDTO, resultDTO);
    }*/

    @Test
    void changeRelationshipSuccessfully_FromOneToTwo(){
        // Add a member to family and create relationship:
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();
        PersonInputDTO otherUserInputDTO = new PersonInputDTO();
        otherUserInputDTO.setEmail("maria@teste.com");
        otherUserInputDTO.setName("Maria");
        otherUserInputDTO.setVat("223456789");
        otherUserInputDTO.setStreet("Maria St");
        otherUserInputDTO.setLocation("Porto");
        otherUserInputDTO.setPostCode("1234-123");
        otherUserInputDTO.setBirthDate("10/01/2000");
        otherUserInputDTO.setPhoneNumber("919999999");
        otherUserInputDTO.setAdminId(familyOutputDTO.getAdminId());
        AddFamilyMemberDTO otherUserId = familyMemberService.addFamilyMember(otherUserInputDTO,
                familyId);
        RelationshipInputDTO relationshipInputDTO =
                new RelationshipInputDTO(familyInputDTO.getEmail(), otherUserId.getEmail(),
                        "1");
        RelationshipOutputDTO relationshipOutputDTO = relationshipService.createRelationship(familyId, relationshipInputDTO);

        int relationshipId = relationshipOutputDTO.getRelationshipId();

        //arrange
        RelationshipUserDTO mainUser = new RelationshipUserDTO(familyInputDTO.getName(), familyInputDTO.getEmail());
        RelationshipUserDTO otherUser = new RelationshipUserDTO(otherUserInputDTO.getName(), otherUserInputDTO.getEmail());
        RelationshipOutputDTO expectedOutput = new RelationshipOutputDTO(mainUser, "PARTNER", otherUser, relationshipId);

        int newRelationshipType = 2;
        ResponseEntity<Object> result = controller.changeRelationship(familyId, relationshipId, newRelationshipType);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedOutput, result.getBody());
    }

    @Test
    void failToChangeRelationship_WhenRelationshipIdDoesNotExist(){
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();
        String expectedMessage = "The relationship does not exist in the family.";

        int newRelationshipType = 5;
        ResponseEntity<Object> result = controller.changeRelationship(familyId, 10, newRelationshipType);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(expectedMessage, result.getBody());
    }

    @Test
    void failToChangeRelationship_WhenFamilyIdDoesNotExist(){
        String expectedMessage = "Family does not exist.";

        int newRelationshipType = 5;
        ResponseEntity<Object> result = controller.changeRelationship(500, 1, newRelationshipType);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(expectedMessage, result.getBody());
    }

    @Test
    void failToChangeRelationship_InvalidRelationshipType(){
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();
        String expectedMessage = "Invalid relationship type.";

        int newRelationshipType = -5;
        ResponseEntity<Object> result = controller.changeRelationship(familyId, 1, newRelationshipType);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(expectedMessage, result.getBody());
    }

    @Test
    void getRelationshipTypesSuccessfully(){
        List<RelationshipTypeDTO> DTOList = new ArrayList<>();
        RelationshipTypeDTO relationshipType1 = new RelationshipTypeDTO(1,"SPOUSE");
        RelationshipTypeDTO relationshipType2 = new RelationshipTypeDTO(2,"PARTNER");
        RelationshipTypeDTO relationshipType3 = new RelationshipTypeDTO(3,"PARENT");
        RelationshipTypeDTO relationshipType4 = new RelationshipTypeDTO(4,"CHILD");
        RelationshipTypeDTO relationshipType5 = new RelationshipTypeDTO(5,"SIBLING");
        RelationshipTypeDTO relationshipType6 = new RelationshipTypeDTO(6,"GRANDPARENT");
        RelationshipTypeDTO relationshipType7 = new RelationshipTypeDTO(7,"GRANDCHILD");
        RelationshipTypeDTO relationshipType8 = new RelationshipTypeDTO(8,"UNCLE_AUNT");
        RelationshipTypeDTO relationshipType9 = new RelationshipTypeDTO(9,"NEPHEW_NIECE");
        RelationshipTypeDTO relationshipType10 = new RelationshipTypeDTO(10,"COUSIN");
        DTOList.add(relationshipType1);
        DTOList.add(relationshipType2);
        DTOList.add(relationshipType3);
        DTOList.add(relationshipType4);
        DTOList.add(relationshipType5);
        DTOList.add(relationshipType6);
        DTOList.add(relationshipType7);
        DTOList.add(relationshipType8);
        DTOList.add(relationshipType9);
        DTOList.add(relationshipType10);

        RelationshipTypesListDTO relationshipTypesListDTO = new RelationshipTypesListDTO(DTOList);

        ResponseEntity<Object> result = controller.getRelationshipTypes();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(relationshipTypesListDTO, result.getBody());
    }

    @Test
    void getFamilyInformationSuccessfully(){
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();

        ResponseEntity<Object> result = controller.getFamilyInformation(familyId);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(familyOutputDTO, result.getBody());
    }

    @Test
    void getFamilyInformation_FamilyDoesNotExist(){
        long familyId = 0;
        String expectedMessage = "Family does not exist.";

        ResponseEntity<Object> result = controller.getFamilyInformation(familyId);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(expectedMessage, result.getBody());
    }

    @Test
    void getFamilyMembersSuccessfully(){
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail("admin@gmail.com");
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Rodrigues");
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
        personInputDTO.setAdminId("admin@gmail.com");
        String expectedBirthDate = "09/11/1988";
        personInputDTO.setBirthDate(expectedBirthDate);
        AddFamilyMemberDTO personId1 = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        PersonInputDTO personInputDTO2 = new PersonInputDTO();
        String expectedName2 = "Néne";
        personInputDTO2.setName(expectedName2);
        personInputDTO2.setVat("227475447");
        String expectedEmail2 = "nene@gmail.com";
        personInputDTO2.setEmail(expectedEmail2);
        personInputDTO2.setPhoneNumber("917899987");
        personInputDTO2.setLocation("Porto");
        personInputDTO2.setStreet("Rua das Flores");
        personInputDTO2.setPostCode("4444-333");
        personInputDTO2.setAdminId("admin@gmail.com");
        String expectedBirthDate2 = "09/11/1988";
        personInputDTO2.setBirthDate(expectedBirthDate);
        AddFamilyMemberDTO personId2 = familyMemberService.addFamilyMember(personInputDTO2, familyOutputDTO.getFamilyId());

        FamilyMemberOutputDTO memberOutputDTO1 = new FamilyMemberOutputDTO("David", "admin@gmail.com");
        FamilyMemberOutputDTO memberOutputDTO2 = new FamilyMemberOutputDTO(expectedName, expectedEmail);
        FamilyMemberOutputDTO memberOutputDTO3 = new FamilyMemberOutputDTO(expectedName2, expectedEmail2);

        List<FamilyMemberOutputDTO> memberDTO = new ArrayList<>();
        memberDTO.add(memberOutputDTO1);
        memberDTO.add(memberOutputDTO2);
        memberDTO.add(memberOutputDTO3);
        FamilyMembersOutputDTO expectedMembersList = new FamilyMembersOutputDTO(memberDTO);

        ResponseEntity<Object> result = controller.getFamilyMembers(familyOutputDTO.getFamilyId());

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedMembersList, result.getBody());
    }
}
