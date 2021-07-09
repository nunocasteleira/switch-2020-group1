package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.applicationservices.implservices.RelationshipService;
import switchtwentytwenty.project.applicationservices.iservices.IFamilyMemberService;
import switchtwentytwenty.project.domain.exceptions.*;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.family.*;
import switchtwentytwenty.project.dto.person.FamilyMembersOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("FamilyControllerTest")
class FamilyControllerTest {

    @Mock
    RelationshipService relationshipService;
    String person1IdString;
    String person2IdString;
    String person3IdString;
    AddFamilyMemberDTO familyMemberDTO;
    @Mock
    private IFamilyMemberService familyMemberService;
    @InjectMocks
    private FamilyController controller;

    private static Stream<Arguments> provideParameters() {
        String person1Id = "antonio@gmail.com";
        String person2Id = "maria@gmail.com";
        String person3Id = "zedebraga@gmail.com";

        String person1Name = "Antonio";
        String person2Name = "Maria";
        String person3Name = "Ze";

        String spouse = "1";
        String child = "4";
        String cousin = "10";
        return Stream.of(
                Arguments.of(person1Id, person2Id, person1Name, person2Name, spouse),
                Arguments.of(person1Id, person3Id, person1Name, person3Name, child),
                Arguments.of(person2Id, person3Id, person2Name, person3Name, cousin)
        );
    }

    @BeforeEach
    void initializer() {
        person1IdString = "antonio@gmail.com";
        person2IdString = "maria@gmail.com";
        person3IdString = "zedebraga@gmail.com";
    }

    @Test
    void createFamily_Successfully() {
        // arrange
        HttpStatus expected = HttpStatus.CREATED;
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
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO(family.getFamilyId().getFamilyId(), family.getFamilyName().toString(), family.getAdminId().toString(), family.getRegistrationDate().toString());

        Mockito.when(familyMemberService.createFamily(familyInputDTO)).thenReturn(familyOutputDTO);

        // act
        ResponseEntity<Object> result = controller.createFamily(familyInputDTO);

        // assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void createFamily_Invalid_NullFamilyName() {
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
        when(familyMemberService.createFamily(familyInputDTO)).thenThrow(InvalidNameException.class);

        // act
        ResponseEntity<Object> responseEntity = controller.createFamily(familyInputDTO);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void createFamily_Invalid_NullAdministrator() {
        // arrange
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setFamilyName("Silva");
        when(familyMemberService.createFamily(familyInputDTO)).thenThrow(IllegalArgumentException.class);

        // act
        ResponseEntity<Object> responseEntity = controller.createFamily(familyInputDTO);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void ensureFamilyMemberIsAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        Set<Role> roles = new HashSet<>();
        Role familyMember = new Role(ERole.ROLE_FAMILY_MEMBER);
        roles.add(familyMember);
        familyMemberDTO = new AddFamilyMemberDTO(personInputDTO.getEmail(), personInputDTO.getName(), personInputDTO.getStreet(), personInputDTO.getLocation(), personInputDTO.getPostCode(), personInputDTO.getBirthDate(), personInputDTO.getVat(), familyId.getFamilyId(), personInputDTO.getPhoneNumber(), roles);
        HttpStatus expected = HttpStatus.CREATED;
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenReturn(familyMemberDTO);

        ResponseEntity<Object> result = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void ensureFamilyMemberWithRepeteadEmailIsNotAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenThrow(DuplicateObjectException.class);

        ResponseEntity<Object> responseEntity = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void ensureFamilyMemberWithInvalidNameIsNotAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("1234");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenThrow(InvalidNameException.class);

        ResponseEntity<Object> responseEntity = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void ensureFamilyMemberWithInvalidEmailIsNotAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antoniogmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenThrow(InvalidEmailException.class);

        ResponseEntity<Object> responseEntity = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void ensureFamilyMemberWithInvalidStreetIsNotAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet(null);
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenThrow(InvalidStreetException.class);

        ResponseEntity<Object> responseEntity = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void ensureFamilyMemberWithInvalidLocationIsNotAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua Clara");
        personInputDTO.setLocation(null);
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenThrow(InvalidLocationException.class);

        ResponseEntity<Object> responseEntity = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void ensureFamilyMemberWithInvalidPostCodeIsNotAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua Clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("0000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenThrow(InvalidPostCodeException.class);

        ResponseEntity<Object> responseEntity = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void ensureFamilyMemberWithInvalidPhoneNumberIsNotAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua Clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("666666666");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenThrow(InvalidPhoneNumberException.class);

        ResponseEntity<Object> responseEntity = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void ensureFamilyMemberWithInvalidVatIsNotAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua Clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("666666666");
        personInputDTO.setVat("022333222");
        personInputDTO.setBirthDate("05/03/2000");
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenThrow(InvalidVatNumberException.class);

        ResponseEntity<Object> responseEntity = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void ensureFamilyMemberWithInvalidBirthDateIsNotAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("antonio@gmail.com");
        personInputDTO.setStreet("Rua Clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("666666666");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05-03/2000");
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenThrow(InvalidDateException.class);

        ResponseEntity<Object> responseEntity = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void ensureFamilyMemberWithNullEmailIsNotAdded() {
        FamilyId familyId = new FamilyId(0);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail(null);
        personInputDTO.setStreet("Rua Clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("666666666");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("05/03/2000");
        when(familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId())).thenThrow(EmailCannotBeNullException.class);

        ResponseEntity<Object> responseEntity = controller.addFamilyMember(personInputDTO, 0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }


//    @Test
//    void getRelationshipTypesList_Successfully() {
//        //Arrange
//        int expectedListSize = 10;
//
//        List<RelationshipType> relationshipTypeList = Arrays.asList(RelationshipType.values());
//
//        when(relationshipService.getRelationshipTypesList()).thenReturn(relationshipTypeList);
//
//        //Act
//        List<RelationshipType> relationshipTypesList =
//                controller.getRelationshipTypesList();
//
//        int actualListSize = relationshipTypesList.size();
//
//        //Assert
//        assertNotNull(relationshipTypesList);
//        assertEquals(expectedListSize, actualListSize);
//    }

//    @Test
//    void getFamilyMembersList_Successfully() {
//        //Arrange
//        Email email1;
//        Email email2;
//        Email email3;
//        List<Email> mockedList = new ArrayList<>();
//
//        email1 = new Email(personInputDTO1.getEmail());
//        email2 = new Email(personInputDTO2.getEmail());
//        email3 = new Email(personInputDTO3.getEmail());
//
//        mockedList.add(email1);
//        mockedList.add(email2);
//        mockedList.add(email3);
//
//        when(relationshipService.getFamilyMembersList(familyId))
//                .thenReturn(mockedList);
//
//        int expectedListSize = mockedList.size();
//
//        //Act
//        List<Email> actualPersonList = controller.getFamilyMembersList(familyId);
//        int actualListSize = actualPersonList.size();
//
//        //Assert
//        assertEquals(expectedListSize, actualListSize);
//    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void createRelationshipsSuccessfully(String person1Id, String person2Id, String person1Name, String person2Name,
                                         String relationshipType) {
        //Arrange
        ResponseEntity<Object> responseEntity;
        HttpStatus expected = HttpStatus.CREATED;
        int familyId = 0;

        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(person1Id, person2Id, relationshipType);

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(person1Id);
        Email email2 = new Email(person2Id);

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);

        RelationshipOutputDTO relationshipOutputDTO = relationshipMapper.toDTO(relationship1, person1Name, person2Name);

        when(relationshipService.createRelationship(familyId, relationshipInputDTO)).thenReturn(relationshipOutputDTO);

        //Act
        responseEntity = controller.createRelationship(relationshipInputDTO, familyId);

        //Assert
        assertNotNull(responseEntity);
        assertEquals(expected, responseEntity.getStatusCode());
    }

    @Test
    void createRelationshipWithTwoPeopleThatAreTheSame() {
        //Arrange
        PersonInputDTO personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("António");
        personInputDTO1.setEmail("antonio@gmail.com");
        personInputDTO1.setStreet("Rua clara");
        personInputDTO1.setLocation("Porto");
        personInputDTO1.setPostCode("4000-000");
        personInputDTO1.setPhoneNumber("911111111");
        personInputDTO1.setVat("222333222");
        personInputDTO1.setBirthDate("05/03/2000");

        ResponseEntity<Object> responseEntity;
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        String relationshipType;
        relationshipType = "4";

        int familyId = 0;

        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(person1IdString, person1IdString, relationshipType);

        when(relationshipService.createRelationship(familyId, relationshipInputDTO)).thenThrow(DuplicateObjectException.class);

        //Act
        responseEntity = controller.createRelationship(relationshipInputDTO, familyId);

        //Assert
        assertEquals(expected, responseEntity.getStatusCode());
    }

    @Test
    void getListOfMembersAndTheirRelations() {
        int familyId = 0;

        String mainUserId = "david@gmail.com";
        String mainUserName = "David";
        String otherUserId = "maria@gmail.com";
        String otherUserName = "Maria";

        List<RelationshipOutputDTO> mockedArrayList = new ArrayList<>();

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(mainUserId);
        Email email2 = new Email(otherUserId);

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);

        RelationshipOutputDTO relationshipOutputDTO = relationshipMapper.toDTO(relationship1, mainUserName, otherUserName);

        RelationshipOutputDTO mockedRelation = relationshipOutputDTO;
        mockedArrayList.add(mockedRelation);
        RelationshipListDTO mockedList = new RelationshipListDTO(mockedArrayList);
        HttpStatus expected = HttpStatus.OK;

        given(relationshipService.getFamilyRelationshipList(familyId))
                .willReturn(mockedList);

        ResponseEntity<Object> result =
                controller.getListOfMembersAndTheirRelations(familyId);

        assertEquals(expected, result.getStatusCode());
    }

    @Test
    void tryToRetrieveRelationshipFromNonExistingFamily() {
        int familyId = 0;
        when(relationshipService.getFamilyRelationshipList(familyId)).thenThrow(ObjectDoesNotExistException.class);
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        ResponseEntity<Object> result =
                controller.getListOfMembersAndTheirRelations(familyId);

        assertEquals(expected, result.getStatusCode());
    }

//    @Test
//    void getFamilyMembers_Successfully() {
//        //arrange
//        long familyId = 1;
//        List<Email> familyMembers = new ArrayList<>();
//        familyMembers.add(new Email("admin@gmail.com"));
//        FamilyMembersOutputDTO familyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);
//        when(familyMemberService.getFamilyMembers(familyId)).thenReturn(familyMembersOutputDTO);
//        HttpStatus expected = HttpStatus.OK;
//
//        //act
//        ResponseEntity<Object> result = controller.getFamilyMembers(familyId);
//
//        //assert
//        assertEquals(expected, result.getStatusCode());
//    }
//
//    @Test
//    void getFamilyMembers_EmptyList() {
//        //arrange
//        long familyId = 1;
//        List<Email> familyMembers = new ArrayList<>();
//        FamilyMembersOutputDTO familyMembersOutputDTO = new FamilyMembersOutputDTO(familyMembers);
//        when(familyMemberService.getFamilyMembers(familyId)).thenReturn(familyMembersOutputDTO);
//        HttpStatus expected = HttpStatus.OK;
//
//        //act
//        ResponseEntity<Object> result = controller.getFamilyMembers(familyId);
//
//        //assert
//        assertEquals(expected, result.getStatusCode());
//    }
}
