package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import switchtwentytwenty.project.datamodel.assembler.PersonDomainDataAssembler;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.EmailCannotBeNullException;
import switchtwentytwenty.project.domain.exceptions.InvalidRelationshipType;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.domain.model.shared.Relationship;
import switchtwentytwenty.project.domain.model.shared.RelationshipType;
import switchtwentytwenty.project.dto.family.*;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("RelationshipServiceTest")
class RelationshipServiceItTest {
    @Autowired
    RelationshipService relationshipService;

    @Autowired
    FamilyRepository familyRepository;

    @Autowired
    IFamilyRepositoryJPA familyRepositoryJPA;

    @Autowired
    IPersonRepositoryJPA personRepository;

    @Autowired
    FamilyMemberService familyMemberService;

    @Autowired
    PersonDomainDataAssembler assembler;

    String adminId;
    String adminId2;
    PersonInputDTO personInputDTO1;
    PersonInputDTO personInputDTO2;
    PersonInputDTO personInputDTO3;

    @BeforeEach
    void initialize() {
        personRepository.deleteAll();
        familyRepositoryJPA.deleteAll();

        adminId = "admin@gmail.com";
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
        personInputDTO1.setAdminId(adminId);

        personInputDTO2 = new PersonInputDTO();
        personInputDTO2.setName("Maria");
        personInputDTO2.setEmail("maria@gmail.com");
        personInputDTO2.setStreet("Rua escura");
        personInputDTO2.setLocation("Lisboa");
        personInputDTO2.setPostCode("5000-000");
        personInputDTO2.setPhoneNumber("912222222");
        personInputDTO2.setVat("212333444");
        personInputDTO2.setBirthDate("11/03/2000");
        personInputDTO2.setAdminId(adminId);


        personInputDTO3 = new PersonInputDTO();
        personInputDTO3.setName("Ze");
        personInputDTO3.setEmail("zedebraga@gmail.com");
        personInputDTO3.setStreet("Rua cansada");
        personInputDTO3.setLocation("Braga");
        personInputDTO3.setPostCode("3000-000");
        personInputDTO3.setPhoneNumber("913333333");
        personInputDTO3.setVat("253333445");
        personInputDTO3.setBirthDate("18/07/2003");
        personInputDTO3.setAdminId(adminId);

    }

    @Test
    void getRelationshipTypesList_Successfully() {
        List<RelationshipType> relationshipTypesList =
                relationshipService.getRelationshipTypesList();

        int expectedListSize = 10;
        int actualListSize = relationshipTypesList.size();

        assertNotNull(relationshipTypesList);
        assertEquals(expectedListSize, actualListSize);
    }

    @Test
    void getFamilyMembersList_Successfully() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

        familyMemberService.addFamilyMember(personInputDTO1, familyOutputDTO.getFamilyId());
        familyMemberService.addFamilyMember(personInputDTO2, familyOutputDTO.getFamilyId());
        familyMemberService.addFamilyMember(personInputDTO3, familyOutputDTO.getFamilyId());

        List<Email> actualPersonList = relationshipService.getFamilyMembers(new FamilyId(familyOutputDTO.getFamilyId()));

        int expectedListSize = 4;
        int actualListSize = actualPersonList.size();

        assertEquals(expectedListSize, actualListSize);
        assertNotNull(actualPersonList);
    }

    @Test
    void createRelationships_Successfully() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO person3Id = familyMemberService.addFamilyMember(personInputDTO3,
                familyOutputDTO.getFamilyId());

        RelationshipInputDTO relationshipInputDTO1 = new RelationshipInputDTO(person1Id.getEmail(), person2Id.getEmail(), "4");
        RelationshipInputDTO relationshipInputDTO2 = new RelationshipInputDTO(person2Id.getEmail(), person3Id.getEmail(), "4");
        RelationshipInputDTO relationshipInputDTO3 = new RelationshipInputDTO(person3Id.getEmail(), person1Id.getEmail(), "10");

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(personInputDTO1.getEmail());
        Email email2 = new Email(personInputDTO2.getEmail());
        Email email3 = new Email(personInputDTO3.getEmail());

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);
        Relationship relationship2 = new Relationship(RelationshipType.valueOf("CHILD"), email2, email3);
        Relationship relationship3 = new Relationship(RelationshipType.valueOf("COUSIN"), email3, email1);

        RelationshipOutputDTO expected1 = relationshipMapper.toDTO(relationship1, personInputDTO1.getName(), personInputDTO2.getName());
        RelationshipOutputDTO expected2 = relationshipMapper.toDTO(relationship2, personInputDTO2.getName(), personInputDTO3.getName());
        RelationshipOutputDTO expected3 = relationshipMapper.toDTO(relationship3, personInputDTO3.getName(), personInputDTO1.getName());

        RelationshipOutputDTO relationshipDTO1 = relationshipService.createRelationship(familyIdInt, relationshipInputDTO1);
        RelationshipOutputDTO relationshipDTO2 = relationshipService.createRelationship(familyIdInt, relationshipInputDTO2);
        RelationshipOutputDTO relationshipDTO3 = relationshipService.createRelationship(familyIdInt, relationshipInputDTO3);

        assertEquals(expected1, relationshipDTO1);
        assertEquals(expected2, relationshipDTO2);
        assertEquals(expected3, relationshipDTO3);
    }

    @Test
    void createRelationships_DuplicatedRelationship() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                familyOutputDTO.getFamilyId());

        RelationshipInputDTO relationshipInputDTO1 = new RelationshipInputDTO(person1Id.getEmail(), person2Id.getEmail(), "4");
        RelationshipInputDTO relationshipInputDTO2 = new RelationshipInputDTO(person1Id.getEmail(), person2Id.getEmail(), "10");

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(personInputDTO1.getEmail());
        Email email2 = new Email(personInputDTO2.getEmail());

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);

        RelationshipOutputDTO expected = relationshipMapper.toDTO(relationship1, personInputDTO1.getName(), personInputDTO2.getName());

        RelationshipOutputDTO relationshipDTO1 = relationshipService.createRelationship(familyIdInt, relationshipInputDTO1);

        assertEquals(expected, relationshipDTO1);
        assertThrows(DuplicateObjectException.class, () -> relationshipService.createRelationship(familyIdInt, relationshipInputDTO2));
    }

    @Test
    void createRelationship_WithNullPersonId() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        AddFamilyMemberDTO person1IdString = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO person2IdString = familyMemberService.addFamilyMember(personInputDTO2,
                familyOutputDTO.getFamilyId());

        RelationshipInputDTO relationshipInputDTO1 = new RelationshipInputDTO(null, person2IdString.getEmail(), "1");

        RelationshipInputDTO relationshipInputDTO2 = new RelationshipInputDTO(person1IdString.getEmail(), null, "1");

        assertThrows(EmailCannotBeNullException.class, () -> relationshipService.createRelationship(familyIdInt, relationshipInputDTO1));

        assertThrows(EmailCannotBeNullException.class, () -> relationshipService.createRelationship(familyIdInt, relationshipInputDTO2));
    }

    @Test
    void createRelationship_WithNullRelationshipType() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        AddFamilyMemberDTO person1IdString = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO person2IdString = familyMemberService.addFamilyMember(personInputDTO2,
                familyOutputDTO.getFamilyId());

        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(person1IdString.getEmail(), person2IdString.getEmail(), null);

        assertThrows(InvalidRelationshipType.class, () -> relationshipService.createRelationship(familyIdInt, relationshipInputDTO));
    }

    @Test
    void createRelationship_WithTwoPeopleThatAreTheSame() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());

        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(person1Id.getEmail(), person1Id.getEmail(), "1");

        assertThrows(DuplicateObjectException.class, () -> relationshipService.createRelationship(familyIdInt, relationshipInputDTO));

    }

    @Test
    void getFamilyRelationshipList() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setPhoneNumber("969999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyIdInt = familyOutputDTO.getFamilyId();

        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyOutputDTO.getFamilyId());
        AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                familyOutputDTO.getFamilyId());

        RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(person1Id.getEmail(), person2Id.getEmail(), "4");

        relationshipService.createRelationship(familyIdInt, relationshipInputDTO);

        List<RelationshipOutputDTO> relationshipOutputDTOArrayList = new ArrayList<>();

        RelationshipMapper relationshipMapper = new RelationshipMapper();
        Email email1 = new Email(personInputDTO1.getEmail());
        Email email2 = new Email(personInputDTO2.getEmail());

        Relationship relationship1 = new Relationship(RelationshipType.valueOf("CHILD"), email1, email2);

        RelationshipOutputDTO relationshipOutputDTO = relationshipMapper.toDTO(relationship1, personInputDTO1.getName(), personInputDTO2.getName());

        relationshipOutputDTOArrayList.add(relationshipOutputDTO);
        RelationshipListDTO expected = new RelationshipListDTO(relationshipOutputDTOArrayList);

        RelationshipListDTO result =
                relationshipService.getFamilyRelationshipList(familyIdInt);

        assertEquals(expected, result);
    }

    @Test
    void changeRelationshipSuccessfully() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("Carmen");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("Carmen St");
        familyInputDTO.setLocation("Guimarães");
        familyInputDTO.setPostCode("4800-123");
        familyInputDTO.setPhoneNumber("919999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/07/1980");
        familyInputDTO.setFamilyName("Miranda");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();
        AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                familyId);
        AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                familyId);
        int newRelationshipType = 2;
        RelationshipInputDTO relationship1 = new RelationshipInputDTO(person1Id.getEmail(), person2Id.getEmail(), "4");
        RelationshipOutputDTO createdRelationship = relationshipService.createRelationship(familyId, relationship1);
        int relationshipId = createdRelationship.getRelationshipId();

        RelationshipUserDTO mainUser = new RelationshipUserDTO(person1Id.getName(), person1Id.getEmail());
        RelationshipUserDTO otherUser = new RelationshipUserDTO(person2Id.getName(), person2Id.getEmail());

        RelationshipOutputDTO expected = new RelationshipOutputDTO(mainUser, "PARTNER", otherUser, relationshipId);

        RelationshipOutputDTO result = relationshipService.changeRelationship(familyId, relationshipId, newRelationshipType);

        assertEquals(expected, result);
    }

    @Test
    void failToChangeRelationship_WhenRelationshipIdDoesNotExist() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("Carmen");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("Carmen St");
        familyInputDTO.setLocation("Guimarães");
        familyInputDTO.setPostCode("4800-123");
        familyInputDTO.setPhoneNumber("919999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/07/1980");
        familyInputDTO.setFamilyName("Miranda");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();

        int nonexistentRelationshipId = 2;

        assertThrows(ObjectDoesNotExistException.class, () -> relationshipService.changeRelationship(familyId, nonexistentRelationshipId, 2));
    }

    @Test
    void failToChangeRelationship_WhenFamilyIdDoesNotExist() {
        int nonexistentFamilyId = 0;

        assertThrows(ObjectDoesNotExistException.class, () -> relationshipService.changeRelationship(nonexistentFamilyId, 1, 1));
    }

    @Test
    void failToChangeRelationship_InvalidRelationshipType() {
        int invalidRelationshipType = 0;

        assertThrows(InvalidRelationshipType.class, () -> relationshipService.changeRelationship(1, 1, invalidRelationshipType));
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

        RelationshipTypesListDTO expectedDTO = new RelationshipTypesListDTO(DTOList);

        RelationshipTypesListDTO result = relationshipService.getRelationshipTypesListDTO();

        assertEquals(expectedDTO, result);
    }
}
