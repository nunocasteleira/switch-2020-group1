package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.applicationservices.implservices.*;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.category.CategoryInputDTO;
import switchtwentytwenty.project.dto.category.StandardCategoryOutputDTO;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.dto.transaction.*;
import switchtwentytwenty.project.repositories.irepositories.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@SpringBootTest
@Profile("TransactionControllerItTest")
class TransactionControllerItTest {

    @Autowired
    TransactionService transactionService;
    @Autowired
    FamilyMemberService familyMemberService;
    @Autowired
    FamilyAccountService familyAccountService;
    @Autowired
    PersonAccountService personAccountService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TransactionController transactionController;
    @Autowired
    IFamilyRepositoryJPA iFamilyRepositoryJPA;
    @Autowired
    IPersonRepositoryJPA iPersonRepositoryJPA;
    @Autowired
    ICategoryRepositoryJPA iCategoryRepositoryJPA;
    @Autowired
    IAccountRepositoryJPA iAccountRepositoryJPA;
    @Autowired
    ITransactionRepositoryJPA iTransactionRepositoryJPA;

    @AfterEach
    void clean() {
        iFamilyRepositoryJPA.deleteAll();
        //iAccountRepositoryJPA.deleteAll();
        iPersonRepositoryJPA.deleteAll();
        iCategoryRepositoryJPA.deleteAll();
        iTransactionRepositoryJPA.deleteAll();
    }

    @Test
    void paymentIsRegisteredFromFamilyCashAccountWithTheRightInformation() {
        //Arrange
        //Create family and administrator
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
        long familyId = familyOutputDTO.getFamilyId();
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(familyId, 40, 2, "TestDescription");
        AccountOutputDTO accountOutputDTO = familyAccountService.createFamilyCashAccount(accountInputDTO);
        long accountId = accountOutputDTO.getAccountId();
        //Create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();
        String categoryName = outputDTO.getCategoryName();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", categoryId);
        //Create expected payment output DTO
        TransactionOutputDTO expectedOutputDTO = new TransactionOutputDTO(accountId, -20, "GBP", "Electricity bill", "19/06/2021 12:54", "EDP", categoryId, categoryName, 20);
        //Act
        ResponseEntity<Object> registeredPayment = transactionController.registerPayment(paymentInputDTO, accountId);
        TransactionOutputDTO resultDTO = (TransactionOutputDTO) registeredPayment.getBody();
        long transactionIdForExpectedLink = resultDTO.getTransactionId();

        //Adding links to expectedOutputDTO
        Link selfLink =
                linkTo(TransactionController.class).slash("transactions").slash(transactionIdForExpectedLink).withRel("paymentInformation");
        Link accountOptions = linkTo(AccountController.class).slash("accounts").slash("accountId").withRel("accountOptions");
        expectedOutputDTO.add(selfLink, accountOptions);

        //Assert
        assertNotNull(registeredPayment);
        assertNotNull(registeredPayment.getBody());
        assertEquals(HttpStatus.CREATED, registeredPayment.getStatusCode());
        assertEquals(expectedOutputDTO, resultDTO);

    }

    @Test
    void paymentIsRegisteredFromBankAccountWithTheRightInformation() {
        //Arrange
        //Create family and administrator
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
        long familyId = familyOutputDTO.getFamilyId();
        String adminId = familyOutputDTO.getAdminId();
        //Create personal bank account
        AccountInputDTO accountInputDTO = new AccountInputDTO(40, 2, "TestDescription", "provider");
        AccountOutputDTO accountOutputDTO = personAccountService.createBankAccount(accountInputDTO, adminId);
        long accountId = accountOutputDTO.getAccountId();
        //Create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();
        String categoryName = outputDTO.getCategoryName();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", categoryId);
        //Create expected payment output DTO
        TransactionOutputDTO expectedOutputDTO = new TransactionOutputDTO(accountId, -20, "GBP", "Electricity bill", "19/06/2021 12:54", "EDP", categoryId, categoryName, 20);

        //Act
        ResponseEntity<Object> registeredPayment = transactionController.registerPayment(paymentInputDTO, accountId);
        TransactionOutputDTO resultDTO = (TransactionOutputDTO) registeredPayment.getBody();
        long transactionIdForExpectedLink = resultDTO.getTransactionId();

        //Adding links to expectedOutputDTO
        Link selfLink =
                linkTo(TransactionController.class).slash("transactions").slash(transactionIdForExpectedLink).withRel("paymentInformation");
        Link accountOptions = linkTo(AccountController.class).slash("accounts").slash("accountId").withRel("accountOptions");
        expectedOutputDTO.add(selfLink, accountOptions);

        //Assert
        assertNotNull(registeredPayment);
        assertNotNull(registeredPayment.getBody());
        assertEquals(HttpStatus.CREATED, registeredPayment.getStatusCode());
        assertEquals(expectedOutputDTO, resultDTO);
    }

    @Test
    void paymentIsNotRegisteredIfAccountIdIsFromANonexistentAccount() {
        //Arrange
        //Create family and administrator
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
        long familyId = familyOutputDTO.getFamilyId();
        String adminId = familyOutputDTO.getAdminId();
        //Create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", categoryId);

        //Act
        ResponseEntity<Object> registeredPayment = transactionController.registerPayment(paymentInputDTO, 1234567);

        //Assert
        assertNotNull(registeredPayment);
        assertNotNull(registeredPayment.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, registeredPayment.getStatusCode());
    }

    @Test
    void paymentIsNotRegisteredFromFamilyCashAccountWithWrongCategoryId() {
        //Arrange
        //Create family and administrator
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
        long familyId = familyOutputDTO.getFamilyId();
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(familyId, 20, 2, "AccountDescription");
        AccountOutputDTO accountOutputDTO = familyAccountService.createFamilyCashAccount(accountInputDTO);
        long accountId = accountOutputDTO.getAccountId();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", 12345);

        //Act
        ResponseEntity<Object> registeredPayment = transactionController.registerPayment(paymentInputDTO, accountId);

        //Assert
        assertNotNull(registeredPayment);
        assertNotNull(registeredPayment.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, registeredPayment.getStatusCode());
    }

    @Test
    void paymentIsNotRegisteredFromBankAccountWithWrongCategoryId() {
        //Arrange
        //Create family and administrator
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("António");
        familyInputDTO.setEmail("antonio_guilherme@gmail.com");
        familyInputDTO.setStreet("Rua clara");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("4000-000");
        familyInputDTO.setPhoneNumber("911111111");
        familyInputDTO.setVat("222333222");
        familyInputDTO.setBirthDate("11/09/1999");
        familyInputDTO.setFamilyName("Silva");
        FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        long familyId = familyOutputDTO.getFamilyId();
        String adminId = familyOutputDTO.getAdminId();
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(20, 2, "AccountDescription", "provider");
        AccountOutputDTO accountOutputDTO = personAccountService.createBankAccount(accountInputDTO, adminId);
        long accountId = accountOutputDTO.getAccountId();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", 12345);

        //Act
        ResponseEntity<Object> registeredPayment = transactionController.registerPayment(paymentInputDTO, accountId);

        //Assert
        assertNotNull(registeredPayment);
        assertNotNull(registeredPayment.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, registeredPayment.getStatusCode());
    }

    @Test
    void paymentIsNotRegisteredFromFamilyCashAccountIfInputDTOHasABlankField() {
        //Arrange
        //Create family and administrator
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
        long familyId = familyOutputDTO.getFamilyId();
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(familyId, 20, 2, "TestDescription");
        AccountOutputDTO accountOutputDTO = familyAccountService.createFamilyCashAccount(accountInputDTO);
        long accountId = accountOutputDTO.getAccountId();
        //Create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "", "19/06/2021 12:54", "EDP", categoryId);

        //Act
        ResponseEntity<Object> registeredPayment = transactionController.registerPayment(paymentInputDTO, accountId);

        //Assert
        assertNotNull(registeredPayment);
        assertNotNull(registeredPayment.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, registeredPayment.getStatusCode());
    }

    @Test
    void paymentIsNotRegisteredFromPersonalBankAccountIfInputDTOHasABlankField() {
        //Arrange
        //Create family and administrator
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
        long familyId = familyOutputDTO.getFamilyId();
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(familyId, 20, 2, "TestDescription");
        AccountOutputDTO accountOutputDTO = familyAccountService.createFamilyCashAccount(accountInputDTO);
        long accountId = accountOutputDTO.getAccountId();
        //Create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "", "EDP", categoryId);

        //Act
        ResponseEntity<Object> registeredPayment = transactionController.registerPayment(paymentInputDTO, accountId);

        //Assert
        assertNotNull(registeredPayment);
        assertNotNull(registeredPayment.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, registeredPayment.getStatusCode());
    }


    @Test
    void paymentIsNotRegisteredFromPersonalBankAccountIfInputDTOIsNull() {
        //Arrange
        //Create family and administrator
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
        long familyId = familyOutputDTO.getFamilyId();
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(familyId, 20, 2, "TestDescription");
        AccountOutputDTO accountOutputDTO = familyAccountService.createFamilyCashAccount(accountInputDTO);
        long accountId = accountOutputDTO.getAccountId();

        //Act
        ResponseEntity<Object> registeredPayment = transactionController.registerPayment(null, accountId);

        //Assert
        assertNotNull(registeredPayment);
        assertEquals(HttpStatus.BAD_REQUEST, registeredPayment.getStatusCode());
    }

    @Test
    void transfer_Successfully() {
        //arrange
        //create family and administrator
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
        String adminId = familyOutputDTO.getAdminId();
        long familyId = familyOutputDTO.getFamilyId();
        //create family member
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Maria");
        personInputDTO.setEmail("destinationMember@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO addFamilyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyId);
        String destinationMember = addFamilyMemberDTO.getEmail();

        //create origin personal cash account
        AccountInputDTO originAccountInputDTO = new AccountInputDTO(40, 2, "António Account");
        AccountOutputDTO originAccountOutputDTO = personAccountService.createPersonalCashAccount(originAccountInputDTO, adminId);
        long originAccountId = originAccountOutputDTO.getAccountId();

        //create destination personal cash account
        AccountInputDTO destinationAccountInputDTO = new AccountInputDTO(40, 2, "Maria Account");
        AccountOutputDTO destinationAccountOutputDTO = personAccountService.createPersonalCashAccount(destinationAccountInputDTO, destinationMember);
        long destinationAccountId = destinationAccountOutputDTO.getAccountId();

        //create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();

        //create input DTO with information to create the transfer
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "Electricity bill", "19/06/2021 12:54", categoryId, destinationAccountId);

        //create expected transfer output DTO
        TransactionOutputDTO expected = new TransactionOutputDTO(originAccountId, destinationAccountId, -20, "USD", "Electricity bill", "19/06/2021 12:54", categoryId, name, 20);

        //act
        ResponseEntity<Object> result = transactionController.transfer(transferInputDTO, originAccountId);
        TransactionOutputDTO resultDTO = (TransactionOutputDTO) result.getBody();
        long transactionId = resultDTO.getTransactionId();

        //add links to expectedOutputDTO
        Link selfLink = linkTo(TransactionController.class).slash("transactions").slash(transactionId).withRel("transferInformation");
        Link accountOptions = linkTo(AccountController.class).slash("accounts").slash("accountId").withRel("accountOptions");
        expected.add(selfLink, accountOptions);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expected, resultDTO);
    }

    @Test
    void transfer_Invalid_NonexistentAccount() {
        //arrange
        //create family and administrator
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
        long familyId = familyOutputDTO.getFamilyId();
        //create family member
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("destinationMember@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO addFamilyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyId);
        String destinationMember = addFamilyMemberDTO.getEmail();

        //create destination personal cash account
        AccountInputDTO destinationAccountInputDTO = new AccountInputDTO(40, 2, "Maria Account");
        AccountOutputDTO destinationAccountOutputDTO = personAccountService.createPersonalCashAccount(destinationAccountInputDTO, destinationMember);
        long destinationAccountId = destinationAccountOutputDTO.getAccountId();

        //create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();

        //create input DTO with information to create the transfer
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "Electricity bill", "19/06/2021 12:54", categoryId, destinationAccountId);

        //act
        ResponseEntity<Object> result = transactionController.transfer(transferInputDTO, 123456);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void transfer_Invalid_WrongCategoryId() {
        //arrange
        //create family and administrator
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
        String adminId = familyOutputDTO.getAdminId();
        long familyId = familyOutputDTO.getFamilyId();
        //create family member
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("destinationMember@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO addFamilyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyId);
        String destinationMember = addFamilyMemberDTO.getEmail();

        //create origin personal cash account
        AccountInputDTO originAccountInputDTO = new AccountInputDTO(40, 2, "António Account");
        AccountOutputDTO originAccountOutputDTO = personAccountService.createPersonalCashAccount(originAccountInputDTO, adminId);
        long originAccountId = originAccountOutputDTO.getAccountId();

        //create destination personal cash account
        AccountInputDTO destinationAccountInputDTO = new AccountInputDTO(40, 2, "Maria Account");
        AccountOutputDTO destinationAccountOutputDTO = personAccountService.createPersonalCashAccount(destinationAccountInputDTO, destinationMember);
        long destinationAccountId = destinationAccountOutputDTO.getAccountId();

        //create input DTO with information to create the transfer
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "Electricity bill", "19/06/2021 12:54", 123456, destinationAccountId);

        //act
        ResponseEntity<Object> result = transactionController.transfer(transferInputDTO, originAccountId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void transfer_Invalid_InputDTOWithABlankField() {
        //arrange
        //create family and administrator
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
        String adminId = familyOutputDTO.getAdminId();
        long familyId = familyOutputDTO.getFamilyId();
        //create family member
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("destinationMember@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO addFamilyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyId);
        String destinationMember = addFamilyMemberDTO.getEmail();

        //create origin personal cash account
        AccountInputDTO originAccountInputDTO = new AccountInputDTO(40, 2, "António Account");
        AccountOutputDTO originAccountOutputDTO = personAccountService.createPersonalCashAccount(originAccountInputDTO, adminId);
        long originAccountId = originAccountOutputDTO.getAccountId();

        //create destination personal cash account
        AccountInputDTO destinationAccountInputDTO = new AccountInputDTO(40, 2, "Maria Account");
        AccountOutputDTO destinationAccountOutputDTO = personAccountService.createPersonalCashAccount(destinationAccountInputDTO, destinationMember);
        long destinationAccountId = destinationAccountOutputDTO.getAccountId();

        //create input DTO with information to create the transfer
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "", "19/06/2021 12:54", 123456, destinationAccountId);

        //act
        ResponseEntity<Object> result = transactionController.transfer(transferInputDTO, originAccountId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void transfer_Invalid_InputDTOWithNullField() {
        //arrange
        //create family and administrator
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
        String adminId = familyOutputDTO.getAdminId();
        long familyId = familyOutputDTO.getFamilyId();
        //create family member
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("destinationMember@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO addFamilyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyId);
        String destinationMember = addFamilyMemberDTO.getEmail();

        //create origin personal cash account
        AccountInputDTO originAccountInputDTO = new AccountInputDTO(40, 2, "António Account");
        AccountOutputDTO originAccountOutputDTO = personAccountService.createPersonalCashAccount(originAccountInputDTO, adminId);
        long originAccountId = originAccountOutputDTO.getAccountId();

        //create destination personal cash account
        AccountInputDTO destinationAccountInputDTO = new AccountInputDTO(40, 2, "Maria Account");
        AccountOutputDTO destinationAccountOutputDTO = personAccountService.createPersonalCashAccount(destinationAccountInputDTO, destinationMember);
        long destinationAccountId = destinationAccountOutputDTO.getAccountId();

        //create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();

        //create input DTO with information to create the transfer
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, null, "19/06/2021 12:54", categoryId, destinationAccountId);

        //act
        ResponseEntity<Object> result = transactionController.transfer(transferInputDTO, originAccountId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void transfer_Invalid_NullInputDTO() {
        //arrange
        //create family and administrator
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
        String adminId = familyOutputDTO.getAdminId();
        long familyId = familyOutputDTO.getFamilyId();
        //create family member
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("destinationMember@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO addFamilyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyId);
        String destinationMember = addFamilyMemberDTO.getEmail();

        //create origin personal cash account
        AccountInputDTO originAccountInputDTO = new AccountInputDTO(20, 2, "TestDescription");
        AccountOutputDTO originAccountOutputDTO = personAccountService.createPersonalCashAccount(originAccountInputDTO, adminId);
        long originAccountId = originAccountOutputDTO.getAccountId();

        //create destination personal cash account
        AccountInputDTO destinationAccountInputDTO = new AccountInputDTO(20, 2, "TestDescription");
        AccountOutputDTO destinationAccountOutputDTO = personAccountService.createPersonalCashAccount(destinationAccountInputDTO, destinationMember);
        long destinationAccountId = destinationAccountOutputDTO.getAccountId();

        //create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();

        //act
        ResponseEntity<Object> result = transactionController.transfer(null, originAccountId);

        //assert
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void getAccountTransactionsBetweenDates() {
        String startDate = "01/01/2021";
        String endDate = "01/01/2022";

        //arrange
        //create family and administrator
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
        String adminId = familyOutputDTO.getAdminId();
        long familyId = familyOutputDTO.getFamilyId();
        //create family member
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("destinationMember@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO addFamilyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyId);
        String destinationMember = addFamilyMemberDTO.getEmail();
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(200, 2, "TestDescription", "Provider");
        AccountOutputDTO accountOutputDTO = personAccountService.createBankAccount(accountInputDTO, adminId);
        long accountId = accountOutputDTO.getAccountId();
        //create destination personal cash account
        AccountInputDTO destinationAccountInputDTO = new AccountInputDTO(20, 2, "TestDescription");
        AccountOutputDTO destinationAccountOutputDTO = personAccountService.createPersonalCashAccount(destinationAccountInputDTO, destinationMember);
        long destinationAccountId = destinationAccountOutputDTO.getAccountId();
        //Create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "18/06/2021 12:54", "EDP", categoryId);
        //long transactionId, double amount, String currency, String description, String date, String categoryName
        TransactionOutputDTO paymentOutputDTO = transactionService.registerPayment(paymentInputDTO, accountId);
        TransactionOutputDTO mappedPaymentDTO = new TransactionOutputDTO(paymentOutputDTO.getTransactionId(), paymentOutputDTO.getAmount(), paymentOutputDTO.getCurrency(), paymentOutputDTO.getDescription(),paymentOutputDTO.getDate(), paymentOutputDTO.getCategoryName());
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "Electricity bill", "19/06/2021 12:54", categoryId, destinationAccountId);
        TransactionOutputDTO transferOutputDTO = transactionService.transfer(transferInputDTO, accountId);
        TransactionOutputDTO mappedTransferDTO = new TransactionOutputDTO(transferOutputDTO.getTransactionId(), transferOutputDTO.getAmount(), transferOutputDTO.getCurrency(), transferOutputDTO.getDescription(), transferOutputDTO.getDate(), transferOutputDTO.getCategoryName());
        //Non-Conforming Dates
        PaymentInputDTO paymentInputDTOBefore = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2018 12:54", "EDP", categoryId);
        PaymentInputDTO paymentInputDTOOnStartDate = new PaymentInputDTO(20, 3, "Electricity bill", "01/01/2021 00:00", "EDP", categoryId);
        PaymentInputDTO paymentInputDTOOnEndDate = new PaymentInputDTO(20, 3, "Electricity bill", "01/01/2022 23:59", "EDP", categoryId);
        PaymentInputDTO paymentInputDTOAfter = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2025 12:54", "EDP", categoryId);
        transactionService.registerPayment(paymentInputDTOBefore, accountId);
        TransactionOutputDTO paymentOnStartDateOutputDTO = transactionService.registerPayment(paymentInputDTOOnStartDate, accountId);
        TransactionOutputDTO mappedPaymentOnStartDateDTO = new TransactionOutputDTO(paymentOnStartDateOutputDTO.getTransactionId(), paymentOnStartDateOutputDTO.getAmount(), paymentOnStartDateOutputDTO.getCurrency(), paymentOnStartDateOutputDTO.getDescription(), paymentOnStartDateOutputDTO.getDate(), paymentOnStartDateOutputDTO.getCategoryName());
        TransactionOutputDTO paymentOnEndDateOutputDTO = transactionService.registerPayment(paymentInputDTOOnEndDate, accountId);
        TransactionOutputDTO mappedPaymentOnEndDateDTO = new TransactionOutputDTO(paymentOnEndDateOutputDTO.getTransactionId(), paymentOnEndDateOutputDTO.getAmount(), paymentOnEndDateOutputDTO.getCurrency(), paymentOnEndDateOutputDTO.getDescription(), paymentOnEndDateOutputDTO.getDate(), paymentOnEndDateOutputDTO.getCategoryName());
        transactionService.registerPayment(paymentInputDTOAfter, accountId);
        List<TransactionOutputDTO> expectedList = new ArrayList<>();
        expectedList.add(mappedPaymentDTO);
        expectedList.add(mappedPaymentOnStartDateDTO);
        expectedList.add(mappedPaymentOnEndDateDTO);
        expectedList.add(mappedTransferDTO);
        TransactionListDTO expectedBody = new TransactionListDTO(expectedList);
        Link selfLink = linkTo(TransactionController.class).slash("transactions").slash(accountId).withRel("transactions");
        Link accountOptions = linkTo(AccountController.class).slash("accounts").slash(accountId).withRel("accountOptions");
        expectedBody.add(selfLink, accountOptions);

        ResponseEntity<Object> result = transactionController.getAccountTransactionsBetweenDates(accountId, startDate, endDate);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        TransactionListDTO body = (TransactionListDTO) result.getBody();
        assertEquals(expectedBody, body);
    }


    @Test
    void getAccountTransactionsBetweenDates_Invalid_NullInputDTO() {
        //arrange
        String startDate = null;
        String endDate = null;

        //create family and administrator
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
        String adminId = familyOutputDTO.getAdminId();
        long familyId = familyOutputDTO.getFamilyId();
        //create family member
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("António");
        personInputDTO.setEmail("destinationMember@gmail.com");
        personInputDTO.setStreet("Rua clara");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4000-000");
        personInputDTO.setPhoneNumber("911111111");
        personInputDTO.setVat("222333222");
        personInputDTO.setBirthDate("11/09/1999");
        personInputDTO.setAdminId("antonio@gmail.com");
        AddFamilyMemberDTO addFamilyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyId);
        String destinationMember = addFamilyMemberDTO.getEmail();

        //create origin personal cash account
        AccountInputDTO originAccountInputDTO = new AccountInputDTO(1000, 2, "TestDescription");
        AccountOutputDTO originAccountOutputDTO = personAccountService.createPersonalCashAccount(originAccountInputDTO, adminId);
        long originAccountId = originAccountOutputDTO.getAccountId();

        //create destination personal cash account
        AccountInputDTO destinationAccountInputDTO = new AccountInputDTO(20, 2, "TestDescription");
        AccountOutputDTO destinationAccountOutputDTO = personAccountService.createPersonalCashAccount(destinationAccountInputDTO, destinationMember);
        long destinationAccountId = destinationAccountOutputDTO.getAccountId();

        //create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();

        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", categoryId);
        //long transactionId, double amount, String currency, String description, String date, String categoryName
        TransactionOutputDTO paymentOutputDTO = transactionService.registerPayment(paymentInputDTO, originAccountId);
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "Electricity bill", "19/06/2021 12:54", categoryId, destinationAccountId);
        TransactionOutputDTO transferOutputDTO = transactionService.transfer(transferInputDTO, originAccountId);
        //Non-Conforming Dates
        PaymentInputDTO paymentInputDTOBefore = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2018 12:54", "EDP", categoryId);
        PaymentInputDTO paymentInputDTOOnStartDate = new PaymentInputDTO(20, 3, "Electricity bill", "01/01/2021 00:00", "EDP", categoryId);
        PaymentInputDTO paymentInputDTOOnEndDate = new PaymentInputDTO(20, 3, "Electricity bill", "01/01/2022 23:59", "EDP", categoryId);
        PaymentInputDTO paymentInputDTOAfter = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2025 12:54", "EDP", categoryId);
        transactionService.registerPayment(paymentInputDTOBefore, originAccountId);
        TransactionOutputDTO paymentOnStartDateOutputDTO = transactionService.registerPayment(paymentInputDTOOnStartDate, originAccountId);
        transactionService.registerPayment(paymentInputDTOOnEndDate, originAccountId);
        transactionService.registerPayment(paymentInputDTOAfter, originAccountId);
        List<TransactionOutputDTO> expectedList = new ArrayList<>();
        expectedList.add(paymentOutputDTO);
        expectedList.add(transferOutputDTO);
        expectedList.add(paymentOnStartDateOutputDTO);

        //act
        ResponseEntity<Object> result = transactionController.getAccountTransactionsBetweenDates(originAccountId, startDate, endDate);

        //assert
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}