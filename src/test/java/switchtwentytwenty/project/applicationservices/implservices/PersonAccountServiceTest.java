package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.assemblers.AccountDTOAssembler;
import switchtwentytwenty.project.datamodel.person.PersonJPA;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.Currency;
import switchtwentytwenty.project.domain.model.shared.Description;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.InitialAmountValue;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.repositories.AccountRepository;
import switchtwentytwenty.project.repositories.PersonRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("PersonalAccountServiceTest")
class PersonAccountServiceTest {

    @InjectMocks
    PersonAccountService personAccountService;

    @Mock
    AccountDTOAssembler accountDTOAssembler;

    @Mock
    PersonRepository personRepository;

    @Mock
    AccountRepository accountRepository;


    @Test
    void ensurePersonalCashAccountCreatedSuccessfully() {
        //arrange
        double initialAmount = 30;
        int currencyValue = 1;
        String personId = "roberto_micaela@gmail.com";
        String description = "Micaela Personal Cash Account";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currencyValue, description);

        Currency currency = Currency.EUR;
        InitialAmountValue amount = new InitialAmountValue(initialAmount, currency);
        Description accountDescription = new Description(description);
        Email personIdVo = new Email(personId);
        CashAccount personalCashAccount = new CashAccount(amount, accountDescription);
        Person person = new Person.Builder(personIdVo)
                .build();

        long accountId = 35;
        AccountOutputDTO outputDTO = new AccountOutputDTO(accountId, initialAmount, currency.name(), description);
        PersonJPA personJPA = new PersonJPA();


        when(accountDTOAssembler.fromDTOToInitialAmountValue(inputDTO)).thenReturn(amount);
        when(accountDTOAssembler.fromDTOToAccountDescription(inputDTO)).thenReturn(accountDescription);
        when(accountRepository.saveCashAccount(personalCashAccount)).thenReturn(personalCashAccount);
        when(personRepository.getByEmail(personIdVo)).thenReturn(person);
        when(personRepository.savePerson(person)).thenReturn(personJPA);
        when(accountDTOAssembler.accountToOutputDTO(personalCashAccount)).thenReturn(outputDTO);

        //act
        AccountOutputDTO result = personAccountService.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertEquals(description, result.getAccountDescription());
        assertEquals(initialAmount, result.getInitialAmount());
        assertEquals(accountId, result.getAccountId());
    }


    @Test
    void ensurePersonalCashAccountNotCreatedWhenPersonDoesNotExist() {
        //arrange
        String description = "Personal cash account.";
        double initialAmount = 30;
        int currencyValue = 1;
        String personId = "roberto_carlos@gmail.com";

        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currencyValue, description);

        Currency currency = Currency.EUR;
        InitialAmountValue amount = new InitialAmountValue(initialAmount, currency);
        Description accountDescription = new Description(description);
        Email personIdVo = new Email(personId);
        CashAccount personalCashAccount = new CashAccount(amount, accountDescription);

        when(accountDTOAssembler.fromDTOToInitialAmountValue(inputDTO)).thenReturn(amount);
        when(accountDTOAssembler.fromDTOToAccountDescription(inputDTO)).thenReturn(accountDescription);
        when(accountRepository.saveCashAccount(personalCashAccount)).thenReturn(personalCashAccount);
        when(personRepository.getByEmail(personIdVo)).thenThrow(ObjectDoesNotExistException.class);


        //act + assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> personAccountService.createPersonalCashAccount(inputDTO, personId));
    }
}