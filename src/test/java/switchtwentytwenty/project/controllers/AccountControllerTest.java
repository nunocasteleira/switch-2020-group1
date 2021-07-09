package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.applicationservices.implservices.FamilyAccountService;
import switchtwentytwenty.project.applicationservices.iservices.IPersonAccountService;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.InvalidAmountException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.shared.Currency;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.account.AccountInputDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@ExtendWith(MockitoExtension.class)
@Profile("CreateFamilyCashAccountControllerTest")
class AccountControllerTest {
    @Mock
    FamilyAccountService familyAccountService;

    @Mock
    IPersonAccountService personAccountService;

    @InjectMocks
    AccountController cashAccountController;

    @Test
    void createFamilyCashAccountSuccessfully() {
    FamilyId aFamilyId = new FamilyId(hashCode());
    double initialAmount = 10.80;
    long accountId = 1;
    String description = "Family cash account.";
    AccountOutputDTO anOutputDTO = new AccountOutputDTO(accountId, initialAmount, "EUR", description);
    AccountInputDTO inputDTO = new AccountInputDTO(aFamilyId.getFamilyId()
                ,initialAmount,1, description);
    ResponseEntity<Object> expectedResponse = new ResponseEntity<>(anOutputDTO,
                HttpStatus.CREATED);

    when(familyAccountService.createFamilyCashAccount(inputDTO)).thenReturn(anOutputDTO);
    ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

    assertNotNull(result);
    assertEquals(expectedResponse.getBody(), result.getBody());
    assertNotEquals(0, anOutputDTO.getAccountId());
}

    @Test
    void failToCreateFamilyCashAccount_WhenFamilyHasAlreadyACashAccount() {
        FamilyId familyId = new FamilyId(hashCode());
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(familyId.getFamilyId()
                ,10,1, description);
        String expectedMessage = "Family already has a cash account.";
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(expectedMessage,
                HttpStatus.BAD_REQUEST);

        when(familyAccountService.createFamilyCashAccount(inputDTO)).thenThrow(new DuplicateObjectException("Family already has a cash account."));
        ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

        assertNotNull(result);
        assertEquals(expectedResponse.getBody(), result.getBody());

    }

    @Test
    void failToCreateFamilyCashAccount_WithInvalidInitialAmount() {
        FamilyId familyId = new FamilyId(hashCode());
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(familyId.getFamilyId()
                ,-50, 1, description);
        String expectedMessage = "The initial cash amount cannot be negative.";
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(expectedMessage,
                HttpStatus.BAD_REQUEST);

        when(familyAccountService.createFamilyCashAccount(inputDTO)).thenThrow(new InvalidAmountException("The initial cash amount cannot be negative."));
        ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

        assertEquals(expectedResponse.getBody(), result.getBody());
    }

    @Test
    void failToCreateFamilyCashAccount_WithNoExistingFamily() {
        FamilyId nonExistingFamilyId = new FamilyId(2);
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(nonExistingFamilyId.getFamilyId()
                ,30, 1, description);
        String expectedMessage = "Family does not exist.";
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(expectedMessage,
                HttpStatus.BAD_REQUEST);

        when(familyAccountService.createFamilyCashAccount(inputDTO)).thenThrow(new ObjectDoesNotExistException("Family does not exist."));
        ResponseEntity<Object> result = cashAccountController.createFamilyCashAccount(inputDTO);

        assertEquals(expectedResponse.getBody(), result.getBody());
    }


    @Test
    void ensurePersonalCashAccountIsCreatedSuccessfully() {
        //arrange
        String description = "Personal cash account.";
        double initialAmount = 30;
        int currency = 1;
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
        String personId = "joaobonifacio@hotmail.com";

        long accountId = 10;
        Currency outputCurrency = Currency.EUR;
        AccountOutputDTO accountOutputDTO = new AccountOutputDTO(accountId, initialAmount, outputCurrency.name(), description);
        when(personAccountService.createPersonalCashAccount(inputDTO,personId)).thenReturn(accountOutputDTO);

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
        int otherCurrency = 2;
        String personId = "joaobonifacio@hotmail.com";
        HttpStatus expectedStatus = HttpStatus.CREATED;

        //first personal cash account
        String description = "Personal cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
        long accountId = 10;
        Currency outputCurrency = Currency.EUR;
        AccountOutputDTO accountOutputDTO = new AccountOutputDTO(accountId, initialAmount, outputCurrency.name(), description);

        when(personAccountService.createPersonalCashAccount(inputDTO,personId)).thenReturn(accountOutputDTO);
        cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //second personal cash account
        String otherAccountDescription = "Personal american cash account.";
        AccountInputDTO inputDTOOtherAccount = new AccountInputDTO(initialAmount, otherCurrency, otherAccountDescription);
        long otherAccountId = 11;
        Currency otherOutputCurrency  = Currency.USD;
        AccountOutputDTO otherAccountOutputDTO = new AccountOutputDTO(otherAccountId, initialAmount, otherOutputCurrency.name(), otherAccountDescription);

        when(personAccountService.createPersonalCashAccount(inputDTOOtherAccount,personId)).thenReturn(otherAccountOutputDTO);

        //act
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
        String personId = "joaobonifacio@hotmail.com";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        when(personAccountService.createPersonalCashAccount(inputDTO,personId)).thenThrow(new InvalidAmountException("The initial cash amount cannot be negative."));


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
        String personId = "joaobonifacio@hotmail.com";
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, candidate);
        when(personAccountService.createPersonalCashAccount(inputDTO,personId)).thenThrow(new InvalidAmountException("Invalid description."));

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
        when(personAccountService.createPersonalCashAccount(inputDTO,personId)).thenThrow(new ObjectDoesNotExistException("Person does not exist."));

        //act
        ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void ensureLinkIsAddedInPersonalCashAccount() {
        //arrange
        String description = "Personal cash account.";
        double initialAmount = 30;
        int currency = 1;
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
        String personId = "joaobonifacio@hotmail.com";

        long accountId = 10;
        Currency outputCurrency = Currency.EUR;
        AccountOutputDTO accountOutputDTO = new AccountOutputDTO(accountId, initialAmount, outputCurrency.name(), description);
        Link selfLink = linkTo(AccountController.class).slash("accounts").slash(accountId).withRel("accountInformation");
        accountOutputDTO.add(selfLink);
        when(personAccountService.createPersonalCashAccount(inputDTO,personId)).thenReturn(accountOutputDTO);

        HttpStatus expectedStatus = HttpStatus.CREATED;

        //act
        ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(accountOutputDTO, result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
    }



    @Test
    void ensurePersonalBankAccountIsCreatedSuccessfully() {
        //arrange
        String description = "Personal bank account.";
        double initialAmount = 30;
        int currency = 1;
        String provider = "Bank";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description, provider);
        String personId = "joaobonifacio@hotmail.com";

        long accountId = 10;
        Currency outputCurrency = Currency.EUR;
        AccountOutputDTO accountOutputDTO = new AccountOutputDTO(accountId, initialAmount, outputCurrency.name(), description);
        when(personAccountService.createBankAccount(inputDTO,personId)).thenReturn(accountOutputDTO);

        HttpStatus expectedStatus = HttpStatus.CREATED;

        //act
        ResponseEntity<Object> result = cashAccountController.createPersonalBankAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @Test
    void ensurePersonCanHaveMoreThanOnePersonalBankAccount() {
        //arrange
        double initialAmount = 30;
        String provider = "Bank";
        int currency = 1;
        int otherCurrency = 2;
        String personId = "joaobonifacio@hotmail.com";
        HttpStatus expectedStatus = HttpStatus.CREATED;

        //first personal bank account
        String description = "Personal bank account.";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description, provider);
        long accountId = 10;
        Currency outputCurrency = Currency.EUR;
        AccountOutputDTO accountOutputDTO = new AccountOutputDTO(accountId, initialAmount, outputCurrency.name(), description);

        when(personAccountService.createBankAccount(inputDTO,personId)).thenReturn(accountOutputDTO);
        cashAccountController.createPersonalBankAccount(inputDTO, personId);

        //second personal bank account
        String otherAccountDescription = "Personal travel account.";
        AccountInputDTO inputDTOOtherAccount = new AccountInputDTO(initialAmount, otherCurrency, otherAccountDescription, provider);
        long otherAccountId = 11;
        Currency otherOutputCurrency  = Currency.USD;
        AccountOutputDTO otherAccountOutputDTO = new AccountOutputDTO(otherAccountId, initialAmount, otherOutputCurrency.name(), otherAccountDescription);

        when(personAccountService.createBankAccount(inputDTOOtherAccount,personId)).thenReturn(otherAccountOutputDTO);

        //act
        ResponseEntity<Object> result = cashAccountController.createPersonalBankAccount(inputDTOOtherAccount, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void ensurePersonalBankAccountNotCreatedWithInvalidDescription(String candidate) {
        //arrange
        String expectedMessage = "Invalid description.";
        String provider = "Bank";
        double initialAmount = 20;
        int currency = 1;
        String personId = "joaobonifacio@hotmail.com";
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, candidate, provider);
        when(personAccountService.createBankAccount(inputDTO,personId)).thenThrow(new InvalidAmountException("Invalid description."));

        //act
        ResponseEntity<Object> result = cashAccountController.createPersonalBankAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }


    @Test
    void ensurePersonalBankAccountNotCreatedWhenPersonDoesNotExist() {
        //arrange
        String expectedMessage = "Person does not exist.";
        String description = "Personal bank account.";
        String provider = "Bank";
        double initialAmount = 30;
        int currency = 1;
        String personId = "roberto_carlos@gmail.com";
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description, provider);
        when(personAccountService.createBankAccount(inputDTO,personId)).thenThrow(new ObjectDoesNotExistException("Person does not exist."));

        //act
        ResponseEntity<Object> result = cashAccountController.createPersonalBankAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
    }

}