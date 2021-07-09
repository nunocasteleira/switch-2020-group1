package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.domain.exceptions.InvalidAmountException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.shared.Balance;
import switchtwentytwenty.project.dto.account.AccountBalanceDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("TransactionServiceItTest")
class TransactionServiceItTest {

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
    IFamilyRepositoryJPA iFamilyRepositoryJPA;
    @Autowired
    IPersonRepositoryJPA iPersonRepositoryJPA;
    @Autowired
    ICategoryRepositoryJPA iCategoryRepositoryJPA;
    @Autowired
    IAccountRepositoryJPA iAccountRepositoryJPA;
    @Autowired
    ITransactionRepositoryJPA iTransactionRepositoryJPA;

    @BeforeEach
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
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", categoryId);
        //Create expected payment output DTO
        TransactionOutputDTO expectedOutputDTO = new TransactionOutputDTO(accountId, -20, "GBP", "Electricity bill", "19/06/2021 12:54", "EDP", categoryId, name, 20);

        //Act
        TransactionOutputDTO resultDTO = transactionService.registerPayment(paymentInputDTO, accountId);

        //Assert
        assertNotNull(resultDTO);
        assertEquals(expectedOutputDTO, resultDTO);
    }

    @Test
    void paymentIsNotRegisteredIfBalanceIsLowerThanPaymentAmount() {
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
        AccountInputDTO accountInputDTO = new AccountInputDTO(familyId, 10, 2, "TestDescription");
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
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", categoryId);

        //Act and assert
        assertThrows(InvalidAmountException.class, () -> transactionService.registerPayment(paymentInputDTO, accountId));
    }

    @Test
    void paymentIsRegisteredFromPersonalBankAccountWithTheRightInformation() {
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
        String adminId = familyOutputDTO.getAdminId();
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(40, 2, "TestDescription", "Provider");
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
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", categoryId);
        //Create expected payment output DTO
        TransactionOutputDTO expectedOutputDTO = new TransactionOutputDTO(accountId, -20, "GBP", "Electricity bill", "19/06/2021 12:54", "EDP", categoryId, name, 20);

        //Act
        TransactionOutputDTO resultDTO = transactionService.registerPayment(paymentInputDTO, accountId);

        //Assert
        assertNotNull(resultDTO);
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

        //Act and assert
        assertThrows(ObjectDoesNotExistException.class, () -> transactionService.registerPayment(paymentInputDTO, 1234567));
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
        AccountInputDTO accountInputDTO = new AccountInputDTO(familyId, 20, 2, "TestDescription");
        AccountOutputDTO accountOutputDTO = familyAccountService.createFamilyCashAccount(accountInputDTO);
        long accountId = accountOutputDTO.getAccountId();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", 1234);

        //Act ans assert
        assertThrows(ObjectDoesNotExistException.class, () -> transactionService.registerPayment(paymentInputDTO, accountId));
    }

    @Test
    void paymentIsNotRegisteredFromBankAccountWithWrongCategoryId() {
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
        String adminId = familyOutputDTO.getAdminId();
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(20, 2, "TestDescription", "Provider");
        AccountOutputDTO accountOutputDTO = personAccountService.createBankAccount(accountInputDTO, adminId);
        long accountId = accountOutputDTO.getAccountId();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", 1234);

        //Act and assert
        assertThrows(ObjectDoesNotExistException.class, () -> transactionService.registerPayment(paymentInputDTO, accountId));
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
        TransactionOutputDTO expected = new TransactionOutputDTO(originAccountId, destinationAccountId,-20, "USD", "Electricity bill", "19/06/2021 12:54", categoryId, name, 20);

        //act
        TransactionOutputDTO result = transactionService.transfer(transferInputDTO, originAccountId);

        //Assert
        assertNotNull(result);
        assertEquals(expected, result);
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

        //act && assert
        assertThrows(ObjectDoesNotExistException.class, () -> transactionService.transfer(transferInputDTO, 123456));
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

        //act && assert
        assertThrows(ObjectDoesNotExistException.class, () -> transactionService.transfer(transferInputDTO, originAccountId));
    }

    @Test
    void getAccountTransactionsBetweenDates() {
        String startDate = "01/01/2021";
        String endDate = "01/01/2022";
        DateRangeDTO dateRangeDTO = new DateRangeDTO(startDate, endDate);

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
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(100, 2, "TestDescription", "Provider");
        AccountOutputDTO accountOutputDTO = personAccountService.createBankAccount(accountInputDTO, adminId);
        long accountId = accountOutputDTO.getAccountId();
        //create destination personal cash account
        AccountInputDTO destinationAccountInputDTO = new AccountInputDTO(40, 2, "Maria Account");
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
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", categoryId);
        transactionService.registerPayment(paymentInputDTO, accountId);
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "Electricity bill", "19/06/2021 12:54", categoryId, destinationAccountId);
        transactionService.transfer(transferInputDTO, accountId);

        TransactionListDTO result = transactionService.getAccountTransactionsBetweenDates(accountId, dateRangeDTO);
        int expectedLength = 2;

        assertNotNull(result);
        assertEquals(expectedLength, result.getTransactionList().size());
    }

    @Test
    void getAccountTransactionsBetweenDatesTransactionsWithNonConformingDates() {
        String startDate = "01/01/2021";
        String endDate = "01/01/2022";
        DateRangeDTO dateRangeDTO = new DateRangeDTO(startDate, endDate);

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
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", categoryId);
        transactionService.registerPayment(paymentInputDTO, accountId);
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "Electricity bill", "19/06/2021 12:54", categoryId, destinationAccountId);
        transactionService.transfer(transferInputDTO, accountId);
        //Non-Conforming Dates
        PaymentInputDTO paymentInputDTOBefore = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2018 12:54", "EDP", categoryId);
        PaymentInputDTO paymentInputDTOOnStartDate = new PaymentInputDTO(20, 3, "Electricity bill", "01/01/2021 00:00", "EDP", categoryId);
        PaymentInputDTO paymentInputDTOOnEndDate = new PaymentInputDTO(20, 3, "Electricity bill", "01/01/2022 23:59", "EDP", categoryId);
        PaymentInputDTO paymentInputDTOAfter = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2025 12:54", "EDP", categoryId);
        transactionService.registerPayment(paymentInputDTOBefore, accountId);
        transactionService.registerPayment(paymentInputDTOOnStartDate, accountId);
        transactionService.registerPayment(paymentInputDTOOnEndDate, accountId);
        transactionService.registerPayment(paymentInputDTOAfter, accountId);

        TransactionListDTO result = transactionService.getAccountTransactionsBetweenDates(accountId, dateRangeDTO);
        int expectedLength = 4;

        assertNotNull(result);
        assertEquals(expectedLength, result.getTransactionList().size());
    }

    @Test
    void getBalanceSuccessfully() {
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

        AccountInputDTO accountInputDTO = new AccountInputDTO(familyId, 40, 2, "TestDescription");
        AccountOutputDTO accountOutputDTO = familyAccountService.createFamilyCashAccount(accountInputDTO);
        long accountId = accountOutputDTO.getAccountId();

        String name = "G1 TRAVEL";
        Integer parentId = null;
        CategoryInputDTO inputDTO = new CategoryInputDTO();
        inputDTO.setName(name);
        inputDTO.setParentId(parentId);
        StandardCategoryOutputDTO outputDTO = categoryService.createStandardCategory(inputDTO);
        Object categoryId = outputDTO.getCategoryId();
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 2, "Electricity bill", "19/06/2025 12:54", "EDP", categoryId);
        //TransactionOutputDTO expectedOutputDTO = new TransactionOutputDTO(accountId, -20, "GBP", "Electricity bill", "19-06-2021 12:54", "EDP", categoryId, name, 20);
        transactionService.registerPayment(paymentInputDTO, accountId);

        AccountBalanceDTO expected = new AccountBalanceDTO(new Balance(20));

        //Act

        AccountBalanceDTO result = transactionService.getAccountBalance(accountId);

        //Assert
        assertNotNull(result);
        assertEquals(result, expected);
    }
    @Test
    void paymentIsNotRegisteredDueToLackOfFunds() {
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
        String adminId = familyOutputDTO.getAdminId();
        //Create family cash account
        AccountInputDTO accountInputDTO = new AccountInputDTO(10, 2, "TestDescription", "Provider");
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
        //Create input DTO with information to create the payment
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2025 12:54", "EDP", categoryId);
        //Create expected payment output DTO
        TransactionOutputDTO expectedOutputDTO = new TransactionOutputDTO(accountId, -20, "GBP", "Electricity bill", "19-06-2021 12:54", "EDP", categoryId, name, 20);

        //Assert
        assertThrows(InvalidAmountException.class, () -> transactionService.registerPayment(paymentInputDTO, accountId));
    }
}