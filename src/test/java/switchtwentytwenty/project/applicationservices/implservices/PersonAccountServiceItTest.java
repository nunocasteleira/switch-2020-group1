package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.domain.exceptions.InvalidAmountException;
import switchtwentytwenty.project.domain.exceptions.InvalidDescriptionException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.account.PersonalBankAccount;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.dto.account.AccountListDTO;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.person.EmailListDTO;
import switchtwentytwenty.project.dto.person.FamilyMembersOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.repositories.irepositories.IAccountRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("PersonalAccountServiceItTest")
class PersonAccountServiceItTest {

    FamilyOutputDTO familyOutputDTO;

    @Autowired
    PersonAccountService personAccountService;

    @Autowired
    FamilyMemberService familyMemberService;

    @Autowired
    FamilyAccountService familyAccountService;

    @Autowired
    IAccountRepositoryJPA accountRepositoryJPA;
    @Autowired
    IPersonRepositoryJPA personRepositoryJPA;
    @Autowired
    IFamilyRepositoryJPA familyRepositoryJPA;


    @AfterEach
    void clean() {
        personRepositoryJPA.deleteAll();
        familyRepositoryJPA.deleteAll();
    }


    @BeforeEach
    void setUpFamilyWithAdmin() {
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
    }

    @Test
    void ensurePersonalCashAccountCreatedSuccessfully() {
        //arrange
        double initialAmount = 30;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();
        String description = "Micaela Personal Cash Account";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);

        //act
        AccountOutputDTO result = personAccountService.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertEquals(description, result.getAccountDescription());
        assertEquals(initialAmount, result.getInitialAmount());
    }

    @Test
    void ensurePersonCanHaveTwoPersonalCashAccounts() {
        //arrange
        double initialAmount = 30;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();

        //first personal cash account
        String description = "Micaela Personal Cash Account";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);

        //second personal cash account
        String otherAccountDescription = "Micaela Personal Shopping cash account.";
        int otherCurrency = 2;
        String outputCurrency = Currency.USD.toString();
        AccountInputDTO inputDTOOtherAccount = new AccountInputDTO(initialAmount, otherCurrency, otherAccountDescription);


        //act
        personAccountService.createPersonalCashAccount(inputDTO, personId);
        AccountOutputDTO result = personAccountService.createPersonalCashAccount(inputDTOOtherAccount, personId);

        //assert
        assertNotNull(result);
        assertEquals(otherAccountDescription, result.getAccountDescription());
        assertEquals(initialAmount, result.getInitialAmount());
        assertEquals(outputCurrency, result.getCurrency());
    }

    @Test
    void ensurePersonalCashAccountNotCreatedWhenPersonDoesNotExist() {
        //arrange
        String description = "Personal cash account.";
        double initialAmount = 30;
        int currency = 1;
        String personId = "roberto_carlos@gmail.com";

        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);

        //act + assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> personAccountService.createPersonalCashAccount(inputDTO, personId));
    }

    @Test
    void ensurePersonalCashAccountNotCreatedWithInvalidAmount() {
        //arrange
        String description = "Personal cash account.";
        double initialAmount = -30.6;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);

        //act + assert
        assertThrows(InvalidAmountException.class,
                () -> personAccountService.createPersonalCashAccount(inputDTO, personId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void ensurePersonalCashAccountNotCreatedWithInvalidDescription(String candidate) {
        //arrange
        double initialAmount = 20;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();

        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, candidate);

        //act + assert
        assertThrows(InvalidDescriptionException.class,
                () -> personAccountService.createPersonalCashAccount(inputDTO, personId));

    }

    @Test
    void ensurePersonalBankAccountCreatedSuccessfully() {
        //arrange
        double initialAmount = 30;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();
        String description = "First Bank Account";
        String provider = "Santander";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description, provider);

        //act
        AccountOutputDTO result = personAccountService.createBankAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertEquals(description, result.getAccountDescription());
        assertEquals(initialAmount, result.getInitialAmount());
    }

    @Test
    void ensurePersonCanHaveTwoPersonalBankAccounts() {
        //arrange
        double initialAmount = 30;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();

        //first personal bank account
        String description = "First Bank Account";
        String provider = "Santander";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description, provider);

        //second personal bank account
        String otherAccountDescription = "Running from the police";
        int otherCurrency = 2;
        String outputCurrency = Currency.USD.toString();
        String otherProvider = "Secret";
        AccountInputDTO inputDTOOtherAccount = new AccountInputDTO(initialAmount, otherCurrency, otherAccountDescription, otherProvider);


        //act
        personAccountService.createBankAccount(inputDTO, personId);
        AccountOutputDTO result = personAccountService.createBankAccount(inputDTOOtherAccount, personId);

        //assert
        assertNotNull(result);
        assertEquals(otherAccountDescription, result.getAccountDescription());
        assertEquals(initialAmount, result.getInitialAmount());
        assertEquals(outputCurrency, result.getCurrency());
    }

    @Test
    void ensurePersonalBankAccountNotCreatedWhenPersonDoesNotExist() {
        //arrange
        String description = "Personal bank account.";
        double initialAmount = 30;
        int currency = 1;
        String provider = "else";
        String personId = "inexistente@gmail.com";

        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description, provider);

        //act + assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> personAccountService.createBankAccount(inputDTO, personId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void ensurePersonalBankAccountNotCreatedWithInvalidDescription(String candidate) {
        //arrange
        double initialAmount = 20;
        int currency = 1;
        String provider = "Provider";
        String personId = familyOutputDTO.getAdminId();

        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, candidate, provider);

        //act + assert
        assertThrows(InvalidDescriptionException.class,
                () -> personAccountService.createBankAccount(inputDTO, personId));

    }

    @Test
    void ensureListObtainedSuccessfully() {
        //arrange
        double initialAmount = 30;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();
        long familyId = familyOutputDTO.getFamilyId();

        String description = "Micaela Personal Cash Account";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
        personAccountService.createPersonalCashAccount(inputDTO, personId);

        double initialAmount1 = 30;
        int currency1 = 1;
        String description1 = "First Bank Account";
        String provider1 = "Santander";
        AccountInputDTO inputDTO1 = new AccountInputDTO(initialAmount1, currency1, description1, provider1);
        personAccountService.createBankAccount(inputDTO1, personId);

        String description2 = "Micaela Family Cash Account";
        AccountInputDTO inputDTO2 = new AccountInputDTO(familyId, initialAmount, currency, description2);
        familyAccountService.createFamilyCashAccount(inputDTO2);


        InitialAmountValue initialAmountValue = new InitialAmountValue(30, Currency.EUR);
        Description accountDescription = new Description("First Bank Account");
        Provider provider = new Provider("Santander");
        PersonalBankAccount expected = new PersonalBankAccount(initialAmountValue, accountDescription, provider);
        List<PersonalBankAccount> dtoBankList = new ArrayList<>();
        dtoBankList.add(expected);



        Currency EUR = Currency.EUR;
        InitialAmountValue initialAmountValue1 = new InitialAmountValue(30, EUR);
        Description accountDescription2 = new Description("Micaela Personal Cash Account");
        CashAccount account = new CashAccount(initialAmountValue1, accountDescription2);
        List<CashAccount> dtoCashList = new ArrayList<>();

        Currency EUR1 = Currency.EUR;
        InitialAmountValue initialAmountValue2 = new InitialAmountValue(30, EUR1);
        Description accountDescription3 = new Description("Micaela Family Cash Account");
        CashAccount account1 = new CashAccount(initialAmountValue2, accountDescription3);

        dtoCashList.add(account);
        dtoCashList.add(account1);

        AccountListDTO expectedList = new AccountListDTO(dtoBankList, dtoCashList);


        //act
        AccountListDTO result = personAccountService.getMemberAccountList(personId);

        //assert
        assertNotNull(result);
        assertEquals(result.toString(), expectedList.toString());
    }

    @Test
    void getFamilyMembersWithAccount() {
        PersonInputDTO personInputDTO = new PersonInputDTO();
        personInputDTO.setName("Maria");
        personInputDTO.setEmail("maria@gmail.com");
        personInputDTO.setStreet("Rua de Baixo");
        personInputDTO.setLocation("Porto");
        personInputDTO.setPostCode("4774-123");
        personInputDTO.setPhoneNumber("919999999");
        personInputDTO.setVat("123456789");
        personInputDTO.setBirthDate("13/05/1990");
        personInputDTO.setAdminId(familyOutputDTO.getAdminId());
        AddFamilyMemberDTO addFamilyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyOutputDTO.getFamilyId());

        double initialAmount = 30;
        int currency = 1;
        String personId = familyOutputDTO.getAdminId();

        String description = "Micaela Personal Cash Account";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
        personAccountService.createPersonalCashAccount(inputDTO, personId);

        double initialAmountother = 30;
        int currencyother = 1;
        String personIdother = addFamilyMemberDTO.getEmail();

        String descriptionother = "Maria Personal Cash Account";
        AccountInputDTO inputDTOother = new AccountInputDTO(initialAmountother, currencyother, descriptionother);
        personAccountService.createPersonalCashAccount(inputDTOother, personIdother);

        FamilyMembersOutputDTO result = personAccountService.getFamilyMembersWithCashAccounts(personId);

        assertNotNull(result);
    }
}
