package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.applicationservices.implservices.*;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.category.CategoryInputDTO;
import switchtwentytwenty.project.dto.category.StandardCategoryOutputDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.transaction.PaymentInputDTO;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Profile("CreateFamilyCashAccountControllerTest")
class AccountControllerItTest {
    FamilyOutputDTO familyOutputDTO;
    @Autowired
    AccountController cashAccountController;
    @Autowired
    FamilyMemberService familyMemberService;
    @Autowired
    FamilyAccountService familyAccountService;
    @Autowired
    PersonAccountService accountService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    IPersonRepositoryJPA personRepositoryJPA;
    @Autowired
    IFamilyRepositoryJPA familyRepositoryJPA;


    @BeforeEach
    void setUpFamily() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("Julião");
        familyInputDTO.setEmail("juliao@hotmail.com");
        familyInputDTO.setStreet("Rua das Flores");
        familyInputDTO.setLocation("Guimarães");
        familyInputDTO.setPostCode("4810-500");
        familyInputDTO.setPhoneNumber("912223344");
        familyInputDTO.setVat("222222222");
        familyInputDTO.setBirthDate("31/07/1989");
        familyInputDTO.setFamilyName("Fonseca");
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
    }

    @AfterEach
    void clean() {
        personRepositoryJPA.deleteAll();
        familyRepositoryJPA.deleteAll();
    }


    @Test
    void createFamilyCashAccountSuccessfully() {
        HttpStatus expectedStatus = HttpStatus.CREATED;
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(familyOutputDTO.getFamilyId()
                , 30, 1, description);

        ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void failToCreateMoreThanOneFamilyCashAccount() {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expectedMessage = "Family already has a cash account.";
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(familyOutputDTO.getFamilyId()
                , 30, 1, description);

        familyAccountService.createFamilyCashAccount(inputDTO);

        ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void failToCreateFamilyCashAccount_WithInvalidAmount() {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expectedMessage = "The initial cash amount cannot be negative.";
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(familyOutputDTO.getFamilyId()
                , -10, 1, description);

        ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void failToCreateFamilyCashAccount_WithInvalidDescription_Null() {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expectedMessage = "Invalid description.";
        String description = null;
        AccountInputDTO inputDTO = new AccountInputDTO(familyOutputDTO.getFamilyId()
                , 20, 1, description);

        ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void failToCreateFamilyCashAccount_WithInvalidDescription_Empty() {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expectedMessage = "Invalid description.";
        String description = "";
        AccountInputDTO inputDTO = new AccountInputDTO(familyOutputDTO.getFamilyId()
                , 10, 1, description);

        ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void failToCreateFamilyCashAccount_WithNoExistingFamily() {
        FamilyId nonExistingFamilyId = new FamilyId(500);
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expectedMessage = "Family does not exist.";
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(nonExistingFamilyId.getFamilyId()
                , 30, 1, description);

        ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void ensureLinkIsAdded() {
        HttpStatus expectedStatus = HttpStatus.CREATED;
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(familyOutputDTO.getFamilyId()
                , 30, 1, description);

        ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void ensurePersonalBankAccountIsCreated() {
        HttpStatus expectedStatus = HttpStatus.CREATED;
        String description = "Personal bank account.";
        String provider = "Provider";
        AccountInputDTO inputDTO = new AccountInputDTO(30, 1, description, provider);

        ResponseEntity<Object> result = cashAccountController.createPersonalBankAccount(inputDTO, familyOutputDTO.getAdminId());
        cashAccountController.createPersonalBankAccount(inputDTO, familyOutputDTO.getAdminId());


        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void failToCreatePersonalBankAccountWithInvalidProvider(String value) {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expectedMessage = "Please enter the provider name";
        String description = "Personal Bank Account";
        String provider = value;
        AccountInputDTO inputDTO = new AccountInputDTO(10, 1, description, provider);

        ResponseEntity<Object> result = cashAccountController.createPersonalBankAccount(inputDTO, familyOutputDTO.getAdminId());

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void failToCreatePersonalBankAccountWithInvalidDescription(String value) {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expectedMessage = "Invalid description.";
        String description = value;
        String provider = "provider";
        AccountInputDTO inputDTO = new AccountInputDTO(10, 1, description, provider);

        ResponseEntity<Object> result = cashAccountController.createPersonalBankAccount(inputDTO, familyOutputDTO.getAdminId());

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void failToCreatePersonalBankAccountWithNoExistingPerson() {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expectedMessage = "Person does not exist.";
        String description = "Personal bank account.";
        String provider = "provider";
        AccountInputDTO inputDTO = new AccountInputDTO(30, 1, description, provider);

        ResponseEntity<Object> result = cashAccountController.createPersonalBankAccount(inputDTO, "something@gmail.com");

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void ensurePersonalCashAccountIsCreatedSuccessfully() {
        //arrange
        String description = "Personal cash account.";
        double initialAmount = 30;
        int currency = 1;
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
        String personId = familyOutputDTO.getAdminId();
        HttpStatus expectedStatus = HttpStatus.CREATED;

        //act
        ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void ensurePersonCanHaveMoreThanOnePersonalCashAccount() {
        //arrange
        double initialAmount = 30;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();

        //first personal cash account
        String description = "Personal cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);

        //second personal cash account
        int otherCurrency = 2;
        String otherAccountDescription = "Personal american cash account.";
        AccountInputDTO inputDTOOtherAccount = new AccountInputDTO(initialAmount, otherCurrency, otherAccountDescription);

        HttpStatus expectedStatus = HttpStatus.CREATED;

        //act
        cashAccountController.createPersonalCashAccount(inputDTO, personId);
        ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTOOtherAccount, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
    }


    @Test
    void ensurePersonalCashAccountNotCreatedWithInvalidAmount() {
        //arrange
        String expectedMessage = "The initial cash amount cannot be negative.";
        String description = "Personal cash account.";
        double initialAmount = -30;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        //act
        ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }


    @ParameterizedTest
    @NullAndEmptySource
    void ensurePersonalCashAccountNotCreatedWithInvalidDescription(String candidate) {
        //arrange
        String expectedMessage = "Invalid description.";
        double initialAmount = 20;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, candidate);

        //act
        ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void ensurePersonalCashAccountNotCreatedWhenPersonDoesNotExist() {
        //arrange
        String expectedMessage = "Person does not exist.";
        String description = "Personal cash account.";
        double initialAmount = 30;
        int currency = 1;
        String personId = "roberto_carlos@gmail.com";
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);

        //act
        ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void getAccountListSuccessfully(){
        //arrange
        HttpStatus expectedStatus = HttpStatus.OK;

        String personId = "juliao@hotmail.com";
        String description = "Personal bank account.";
        String provider = "Provider";
        AccountInputDTO inputDTO = new AccountInputDTO(30, 1, description, provider);
        cashAccountController.createPersonalBankAccount(inputDTO, familyOutputDTO.getAdminId());

        String description1 = "Family cash account.";
        AccountInputDTO inputDTO1 = new AccountInputDTO(familyOutputDTO.getFamilyId()
                , 30, 1, description1);

        cashAccountController.createFamilyCashAccount(inputDTO1);

        //act
        ResponseEntity<Object> result = cashAccountController.getAccountList(personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void getAccountListUnsuccessfullyWhenPersonDoesNotExist(){
        //Arrange
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Person does not exist.",
                HttpStatus.BAD_REQUEST);

        //Act
        ResponseEntity<Object> result =
                cashAccountController.getAccountList("gervasio@hotmail.com");

        //Assert
        assertEquals(expectedResponse.getBody(), result.getBody());
    }


    /*@Test
    void getBalanceSuccessfully(){
        //arrange
        HttpStatus expectedStatus = HttpStatus.OK;

        String personId = "juliao@hotmail.com";
        String description = "Personal bank account.";
        String provider = "Provider";
        AccountInputDTO inputDTO = new AccountInputDTO(30, 1, description, provider);
        AccountOutputDTO accountOutputDTO = accountService.createBankAccount(inputDTO, familyOutputDTO.getAdminId());
        long accountId = accountOutputDTO.getAccountId();

        //Create category
        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName(name);
        categoryInputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(categoryInputDTO);
        Object categoryId = outputDTO.getCategoryId();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 1, "Electricity bill", "19/06/2025 12:54", "EDP", categoryId);
        transactionService.registerPayment(paymentInputDTO, accountId);

        //act
        ResponseEntity<Object> result = cashAccountController.getAccountBalance(personId, accountId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
    }*/
}