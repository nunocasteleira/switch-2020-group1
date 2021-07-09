package switchtwentytwenty.project.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.parameters.P;
import switchtwentytwenty.project.applicationservices.implservices.FamilyMemberService;
import switchtwentytwenty.project.datamodel.assembler.PersonDomainDataAssembler;
import switchtwentytwenty.project.datamodel.person.PersonJPA;
import switchtwentytwenty.project.datamodel.person.RoleJPA;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.person.FamilyMemberVOs;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("PersonRepository")
class PersonRepositoryTest {
    @Mock
    FamilyInputDTO familyInputDTO;
    @Mock
    FamilyMemberService familyMemberService;
    @Mock
    PersonDomainDataAssembler personDomainDataAssembler;
    @Mock
    IPersonRepositoryJPA iPersonRepositoryJPA;
    @InjectMocks
    PersonRepository personRepository;

    @Test
    void getPersonByEmailSuccessfully() {
        //Arrange
        FamilyOutputDTO familyOutputDTO = new FamilyOutputDTO();
        familyOutputDTO.setFamilyId(0);
        familyOutputDTO.setFamilyName("Martins");

        lenient().when(familyMemberService.createFamily(isA(familyInputDTO.getClass()))).thenReturn(familyOutputDTO);

        Email email = new Email("something@gmail.com");
        EmailJPA emailJPA = new EmailJPA("something@gmail.com");

        PersonJPA aPersonJPA = new PersonJPA(new EmailJPA("something@gmail.com"), new PersonNameJPA("David"),
                new AddressJPA("David St", "Porto", "1234-123"),
                new BirthDateJPA("12/01/2000"), new PersonVatJPA("969999999"), 0);

        FamilyMemberVOs familyMemberVOs = new FamilyMemberVOs(new Email("something@gmail.com"), new PersonName("David"), new Address("David St", "Porto", "1234-123"), new BirthDate("12/01/2000"), new PersonVat("123456789"), new FamilyId(familyOutputDTO.getFamilyId()), new ArrayList<>(), new ArrayList<>());

        when(iPersonRepositoryJPA.getByEmail(emailJPA)).thenReturn(Optional.of(aPersonJPA));
        when(personDomainDataAssembler.jpaValueObjectsToDomain(aPersonJPA)).thenReturn(familyMemberVOs);
        List<OtherEmailJPA> otherEmails = new ArrayList<>();
        when(iPersonRepositoryJPA.getOtherEmailsById(emailJPA)).thenReturn(otherEmails);
        List<Email> emails = new ArrayList<>();
        when(personDomainDataAssembler.emailAddressesJPATODomain(otherEmails)).thenReturn(emails);

        //Act
        Person person = personRepository.getByEmail(email);

        //Assert
        assertNotNull(person);
    }

    @Test
    void getPersonByEmailThrowsExceptionWhenPersonDoesNotExist() {
        //Arrange
        Email email = new Email("antonio@gmail.com");
        EmailJPA emailJPA = new EmailJPA("antonio@gmail.com");
        Optional<PersonJPA> personOptionalJPA = Optional.empty();

        when(iPersonRepositoryJPA.getByEmail(emailJPA)).thenReturn(personOptionalJPA);

        //Assert
        assertThrows(ObjectDoesNotExistException.class, () ->
                personRepository.getByEmail(email));
    }

    @Test
    void obtainFamilyMembersSuccessfully(){
        //Arrange
        //Create expected person list
        Person admin = new Person.Builder(new Email("antonio@gmail.com"))
                .withName(new PersonName("António"))
                .withAddress(new Address("Rua Daqui", "Ali", "4444-999"))
                .withBirthDate(new BirthDate("11/09/1999"))
                .withVat(new PersonVat("222333444"))
                .withPassword("password")
                .withFamilyId(new FamilyId(1))
                .build();

        PersonJPA adminJPA = new PersonJPA(new EmailJPA("antonio@gmail.com"), new PersonNameJPA("António"), new AddressJPA("Rua Daqui", "Ali", "4444-999"), new BirthDateJPA("11/09/1999"),
                new PersonVatJPA("222333444"), 1);

        List<Person> expectedPersonList = new ArrayList<>();
        expectedPersonList.add(admin);

        //Create input email list
        List<Email> emails = new ArrayList<>();
        emails.add((new Email("antonio@gmail.com")));

        FamilyMemberVOs antonioValueObjects = new FamilyMemberVOs(new Email("antonio@gmail.com"), new PersonName("António"), new Address("Rua Daqui", "Ali", "4444-999"), new BirthDate("11/09/1999"), new PersonVat("222333444"), "password", new FamilyId(1), new ArrayList<>());
        List<OtherEmailJPA> antonioEmailAddressesJPA = new ArrayList<>();

        List<OtherEmailJPA> emptyEmailAddressesJPA = new ArrayList<>();
        List<Email> emptyEmails = new ArrayList<>();
        List<AccountIdJPA> accountIdsJPA = new ArrayList<>();
        List<AccountId> personalAccounts = new ArrayList<>();
        Set<RoleJPA> rolesJPA = new HashSet<>();
        Set<Role> roles = new HashSet<>();

        Optional<PersonJPA> adminOptional = Optional.of(adminJPA);

        when(iPersonRepositoryJPA.getByEmail(new EmailJPA("antonio@gmail.com"))).thenReturn(adminOptional);
        when(personDomainDataAssembler.jpaValueObjectsToDomain(isA(PersonJPA.class))).thenReturn(antonioValueObjects);
        when(iPersonRepositoryJPA.getOtherEmailsById(new EmailJPA("antonio@gmail.com"))).thenReturn(emptyEmailAddressesJPA);
        when(personDomainDataAssembler.emailAddressesJPATODomain(antonioEmailAddressesJPA)).thenReturn(emptyEmails);
        when(iPersonRepositoryJPA.getAccountIdById(new EmailJPA("antonio@gmail.com"))).thenReturn(accountIdsJPA);
        when(personDomainDataAssembler.accountsToDomain(accountIdsJPA)).thenReturn(personalAccounts);
        when(iPersonRepositoryJPA.getRolesByEmailJPA(new EmailJPA("antonio@gmail.com"))).thenReturn(rolesJPA);
        when(personDomainDataAssembler.rolesJPAToDomain(rolesJPA)).thenReturn(roles);


        //Act
        List<Person> resultPersonList = personRepository.getFamilyMembers(emails);

        //Arrange
        assertNotNull(resultPersonList);
        assertEquals(expectedPersonList.size(), resultPersonList.size());
    }
}
