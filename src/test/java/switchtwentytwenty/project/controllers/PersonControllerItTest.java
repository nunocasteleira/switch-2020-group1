package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import switchtwentytwenty.project.applicationservices.iservices.IFamilyMemberService;
import switchtwentytwenty.project.applicationservices.iservices.IPersonAccountService;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.account.CashAccountsOutputDTO;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.person.*;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;
import switchtwentytwenty.project.security.service.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Profile("PersonControllerTest")
class PersonControllerItTest {

    @Autowired
    IFamilyMemberService familyMemberService;

    @Autowired
    IPersonAccountService personAccountService;

    @Autowired
    PersonController controller;

    FamilyOutputDTO familyOutputDTO;
    @Autowired
    IPersonRepositoryJPA personRepositoryJPA;
    @Autowired
    IFamilyRepositoryJPA familyRepositoryJPA;

    @AfterEach
    void clean() {
        personRepositoryJPA.deleteAll();
        familyRepositoryJPA.deleteAll();
    }


    @Test
    void getProfileInformationDTOFromAnExistingPerson() {
        //Arrange
        //Create Family with administrator
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("David");
        familyInputDTO.setEmail("admin@gmail.com");
        familyInputDTO.setStreet("David St");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("1234-123");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("12/01/2000");
        familyInputDTO.setFamilyName("Maia");
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

        //Create DTO for the expected response
        ProfileInformationDTO expectedProfileInformation = new ProfileInformationDTO();
        expectedProfileInformation.setMainEmailAddress("fabio@gmail.com");
        expectedProfileInformation.setName("Fabio");
        expectedProfileInformation.setVat("227475987");
        expectedProfileInformation.setBirthDate("09/11/1988");
        expectedProfileInformation.setAddress("Rua das Flores, Porto, 4444-333");
        expectedProfileInformation.setEmailAddresses(new ArrayList<>());
        expectedProfileInformation.setPhoneNumbers(new ArrayList<>());
        expectedProfileInformation.setFamilyId(familyOutputDTO.getFamilyId());
        expectedProfileInformation.setFamilyName("Maia");
        expectedProfileInformation.setAdmin(false);
        Email expectedId = new Email("fabio@gmail.com");
        Link familyInformationLink = linkTo(FamilyController.class).slash(familyOutputDTO.getFamilyId()).withRel("accountList");
        Link selfLink = linkTo(PersonController.class).slash(expectedId).withSelfRel();
        Link accountListLink =
                linkTo(AccountController.class).slash(expectedId).withRel("accountList");

        expectedProfileInformation.add(selfLink, familyInformationLink, accountListLink);

        ResponseEntity<Object> expectedResponse =
                new ResponseEntity<>(expectedProfileInformation, HttpStatus.OK);

        //Create DTO for the Person whose information we want to get
        PersonInputDTO personInputDTO = new PersonInputDTO();
        String actualName = "Fabio";
        personInputDTO.setName(actualName);
        personInputDTO.setVat("227475987");
        String email = "fabio@gmail.com";
        personInputDTO.setEmail(email);
        personInputDTO.setLocation("Porto");
        personInputDTO.setStreet("Rua das Flores");
        personInputDTO.setPostCode("4444-333");
        String actualBirthDate = "09/11/1988";
        personInputDTO.setBirthDate(actualBirthDate);
        personInputDTO.setAdminId("admin@gmail.com");
        AddFamilyMemberDTO actualEmail = familyMemberService.addFamilyMember(personInputDTO,
                familyOutputDTO.getFamilyId());

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("fabio@gmail.com");

        //Act
        ResponseEntity<Object> actualResponse =
                controller.getProfileInformation(actualEmail.getEmail());

        //Assert
        assertNotNull(actualResponse);
        assertEquals((expectedResponse.getBody()), actualResponse.getBody());
    }

    @Test
    void getProfileInformationDTOFromAnNonExistingPerson() {
        //Arrange
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Person does not exist.",
                HttpStatus.BAD_REQUEST);

        //Act
        ResponseEntity<Object> result =
                controller.getProfileInformation("gervasio@hotmail.com");

        //Assert
        assertEquals(expectedResponse.getBody(), result.getBody());
    }

    @Test
    void getProfileInformationDTOFromTwoDifferentPersons() {
        //Arrange
        //Create family with administrator
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

        //Create first person
        PersonInputDTO firstPersonInputDTO = new PersonInputDTO();
        String expectedNameFirstPerson = "Fabio";
        firstPersonInputDTO.setName(expectedNameFirstPerson);
        firstPersonInputDTO.setVat("227475987");
        String firstPersonEmail = "fabio@gmail.com";
        firstPersonInputDTO.setEmail(firstPersonEmail);
        firstPersonInputDTO.setPhoneNumber("917899987");
        firstPersonInputDTO.setLocation("Porto");
        firstPersonInputDTO.setStreet("Rua das Flores");
        firstPersonInputDTO.setPostCode("4444-333");
        firstPersonInputDTO.setBirthDate("09/11/1988");
        firstPersonInputDTO.setAdminId("admin@gmail.com");
        familyMemberService.addFamilyMember(firstPersonInputDTO, familyOutputDTO.getFamilyId());

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("fabio@gmail.com");

        ResponseEntity<Object> firstProfileInformationDTO =
                controller.getProfileInformation("fabio@gmail.com");

        //Create second person
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
        secondPersonInputDTO.setAdminId("admin@gmail.com");
        familyMemberService.addFamilyMember(secondPersonInputDTO, familyOutputDTO.getFamilyId());

        Authentication auth2 = mock(Authentication.class);
        SecurityContext securityContext2 = mock(SecurityContext.class);
        when(securityContext2.getAuthentication()).thenReturn(auth2);
        SecurityContextHolder.setContext(securityContext2);
        UserDetailsImpl userDetails2 = mock(UserDetailsImpl.class);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails2);
        when(userDetails2.getUsername()).thenReturn("gervasio@gmail.com");

        ResponseEntity<Object> secondProfileInformationDTO =
                controller.getProfileInformation("gervasio@gmail.com");


        //Assert
        assertNotNull(firstProfileInformationDTO);
        assertNotNull(secondProfileInformationDTO);
        assertEquals(firstProfileInformationDTO.getStatusCode(), secondProfileInformationDTO.getStatusCode());
        assertNotSame(firstProfileInformationDTO.getBody(), secondProfileInformationDTO.getBody());
    }

    @Test
    void ensureValidEmailAdded() {
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setBirthDate("31/12/2012");
        personInputDTO.setPhoneNumber("919999993");
        personInputDTO.setEmail("antonio123@gmail.com");
        personInputDTO.setVat("123436789");
        personInputDTO.setStreet("Rua das Flores");
        personInputDTO.setPostCode("6000-420");
        personInputDTO.setLocation("Porto");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO email = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        String emailAddress = "antonio_margarida@gmail.com";

        List<String> dtoList = new ArrayList<>();
        dtoList.add("antonio_margarida@gmail.com");
        EmailListDTO expectedList = new EmailListDTO(dtoList);

        ResponseEntity<Object> expected = new ResponseEntity<>(expectedList,
                HttpStatus.CREATED);
        //Act
        ResponseEntity<Object> result = controller.addEmail(email.getEmail(), emailAddress);

        //Assert
        assertNotNull(result);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expectedList, result.getBody());
    }

    @Test
    void ensureExistingEmailInPersonNotAdded() {
        //Arrange
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

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
        AddFamilyMemberDTO personId = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        String emailAddress = "fabio@gmail.com";
        ResponseEntity<Object> expected = new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);

        //Act
        ResponseEntity<Object> result = controller.addEmail(personId.getEmail(), emailAddress);

        //Assert
        assertNotNull(result);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void ensureExistingEmailInAnotherPersonNotAdded() {
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setBirthDate("31/12/2012");
        personInputDTO.setPhoneNumber("919999993");
        personInputDTO.setEmail("antonio123@gmail.com");
        personInputDTO.setVat("123436789");
        personInputDTO.setStreet("Rua das Flores");
        personInputDTO.setPostCode("6000-420");
        personInputDTO.setLocation("Porto");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO email = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        String emailAddress = "rita.antonio@gmail.com";

        PersonInputDTO personInputDTO1 = new PersonInputDTO();
        personInputDTO1.setName("Rita");
        personInputDTO1.setBirthDate("31/12/2000");
        personInputDTO1.setPhoneNumber("919992993");
        personInputDTO1.setEmail("rita.antonio@gmail.com");
        personInputDTO1.setVat("123266789");
        personInputDTO1.setStreet("Rua das Dores");
        personInputDTO1.setPostCode("6020-420");
        personInputDTO1.setLocation("Lisboa");
        personInputDTO1.setAdminId("antonio@gmail.com");
        familyMemberService.addFamilyMember(personInputDTO1, familyOutputDTO.getFamilyId());
        ResponseEntity<Object> expected = new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);

        //Act
        ResponseEntity<Object> result = controller.addEmail(email.getEmail(), emailAddress);

        //Assert
        assertNotNull(result);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void ensureNullEmailNotAdded() {
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setBirthDate("31/12/2012");
        personInputDTO.setPhoneNumber("919999993");
        personInputDTO.setEmail("antonio123@gmail.com");
        personInputDTO.setVat("123436789");
        personInputDTO.setStreet("Rua das Flores");
        personInputDTO.setPostCode("6000-420");
        personInputDTO.setLocation("Porto");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO email = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        String emailAddress = null;
        ResponseEntity<Object> expected = new ResponseEntity<>("The e-mail can not be null", HttpStatus.BAD_REQUEST);

        //Act
        ResponseEntity<Object> result = controller.addEmail(email.getEmail(), emailAddress);

        //Assert
        assertNotNull(result);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void ensureEmptyEmailNotAdded() {
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setBirthDate("31/12/2012");
        personInputDTO.setPhoneNumber("919999993");
        personInputDTO.setEmail("antonio123@gmail.com");
        personInputDTO.setVat("123436789");
        personInputDTO.setStreet("Rua das Flores");
        personInputDTO.setPostCode("6000-420");
        personInputDTO.setLocation("Porto");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO email = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        String emailAddress = "";
        ResponseEntity<Object> expected = new ResponseEntity<>("The e-mail can not be null", HttpStatus.BAD_REQUEST);

        //Act
        ResponseEntity<Object> result = controller.addEmail(email.getEmail(), emailAddress);

        //Assert
        assertNotNull(result);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void ensureInvalidEmailNotAdded() {
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setBirthDate("31/12/2012");
        personInputDTO.setPhoneNumber("919999993");
        personInputDTO.setEmail("antonio123@gmail.com");
        personInputDTO.setVat("123436789");
        personInputDTO.setStreet("Rua das Flores");
        personInputDTO.setPostCode("6000-420");
        personInputDTO.setLocation("Porto");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO email = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        String emailAddress = "antonio123.gmail.com";
        ResponseEntity<Object> expected = new ResponseEntity<>("E-mail Address is not valid.", HttpStatus.BAD_REQUEST);

        //Act
        ResponseEntity<Object> result = controller.addEmail(email.getEmail(), emailAddress);

        //Assert
        assertNotNull(result);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void ensureEmailIsRemoved() {
        //Arrange
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

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
        personInputDTO.setAdminId("admin@gmail.com");
        AddFamilyMemberDTO personId = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        String emailAddress = "fabio2@gmail.com";
        EmailListDTO expectedEmailListDTO = new EmailListDTO(new ArrayList<>());
        ResponseEntity<Object> expected = new ResponseEntity<>(expectedEmailListDTO, HttpStatus.OK);

        //Act
        controller.addEmail(personId.getEmail(), emailAddress);
        ResponseEntity<Object> result = controller.removeEmail(personId.getEmail(), emailAddress);

        //Assert
        assertNotNull(result);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void ensurePrimaryEmailIsNotRemoved() {
        //Arrange
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

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
        AddFamilyMemberDTO personId = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        String emailAddress = "fabio@gmail.com";
        ResponseEntity<Object> expected = new ResponseEntity<>("Can't remove primary email", HttpStatus.BAD_REQUEST);

        //Act
        ResponseEntity<Object> result = controller.removeEmail(personId.getEmail(), emailAddress);

        //Assert
        assertNotNull(result);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void getCashAccounts_Successfully() {
        //arrange
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        String adminId = familyOutputDTO.getAdminId();

        //create personal cash account
        AccountInputDTO originAccountInputDTO = new AccountInputDTO(20, 2, "TestDescription");
        AccountOutputDTO originAccountOutputDTO = personAccountService.createPersonalCashAccount(originAccountInputDTO, adminId);

        List<AccountOutputDTO> accountOutputDTOS = new ArrayList<>();
        accountOutputDTOS.add(originAccountOutputDTO);
        CashAccountsOutputDTO expected = new CashAccountsOutputDTO(accountOutputDTOS);

        //act
        ResponseEntity<Object> result = controller.getCashAccounts(adminId);

        //add links to expectedOutputDTO
        Link selfLink = linkTo(PersonController.class).slash(adminId).withSelfRel();
        Link accountListLink = linkTo(AccountController.class).slash("accounts").slash(adminId).withRel("accountList");
        expected.add(selfLink, accountListLink);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getCashAccounts_Successfully_WithBankAccount() {
        //arrange
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        String adminId = familyOutputDTO.getAdminId();

        //create personal cash account
        AccountInputDTO originAccountInputDTO = new AccountInputDTO(20, 2, "TestDescription");
        AccountOutputDTO originAccountOutputDTO = personAccountService.createPersonalCashAccount(originAccountInputDTO, adminId);

        List<AccountOutputDTO> accountOutputDTOS = new ArrayList<>();
        accountOutputDTOS.add(originAccountOutputDTO);
        CashAccountsOutputDTO expected = new CashAccountsOutputDTO(accountOutputDTOS);

        //create personal bank account
        AccountInputDTO originBankAccountInputDTO = new AccountInputDTO(20, 2, "TestDescription", "Test");
        personAccountService.createBankAccount(originBankAccountInputDTO, adminId);

        //act
        ResponseEntity<Object> result = controller.getCashAccounts(adminId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getMembersWithCashAccounts_Successfully() {
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
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        String adminId = familyOutputDTO.getAdminId();
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
        AddFamilyMemberDTO personId = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());
        String emailAddress = "fabio@gmail.com";

        AccountInputDTO accountInputDTO = new AccountInputDTO(20, 2, "TestDescription");
        AccountOutputDTO accountOutputDTO = personAccountService.createPersonalCashAccount(accountInputDTO, emailAddress);
        FamilyMemberOutputDTO memberOutputDTO = new FamilyMemberOutputDTO("Fabio", emailAddress);

        List<FamilyMemberOutputDTO> memberDTO = new ArrayList<>();
        memberDTO.add(memberOutputDTO);
        FamilyMembersOutputDTO expectedMembersList = new FamilyMembersOutputDTO(memberDTO);

        ResponseEntity<Object> result = controller.getFamilyMembersWithCashAccounts(adminId);

        //assert
        assertNotNull(result);
        assertEquals(expectedMembersList, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
