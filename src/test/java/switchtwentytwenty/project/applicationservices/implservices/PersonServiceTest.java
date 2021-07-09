package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.datamodel.assembler.PersonDomainDataAssembler;
import switchtwentytwenty.project.datamodel.person.PersonJPA;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.person.EmailListDTO;
import switchtwentytwenty.project.repositories.PersonRepository;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("PersonServiceTest")
class PersonServiceTest {

    @Mock
    PersonRepository personRepository;
    @InjectMocks
    PersonService personService;
    @Mock
    PersonDomainDataAssembler personDomainDataAssembler;
    @Mock
    IPersonRepositoryJPA iPersonRepositoryJPA;

    @Test
    void ensureSameEmailIsNotAdded() {
        //Arrange
        String personId = "gervasio@hotmail.com";
        Email email1 = new Email("gervasio@hotmail.com");
        String email = "gervasio@hotmail.com";
        when(personRepository.getByEmail(email1)).thenThrow(IllegalArgumentException.class);

        //Assert
        assertThrows(IllegalArgumentException.class,
                () -> personService.addEmail(personId, email));
    }

    @Test
    void ensureEmailIsAdded() {
        //Arrange
        String expectedName = "Fabio";
        String expectedEmail = "fabio@gmail.com";
        String expectedBirthDate = "09/11/1988";
        Email personId = new Email(expectedEmail);
        Address address = new Address(
                "street",
                "location",
                "1234-123"
        );
        String vat = "123456789";
        Person person = new Person.Builder(personId)
                .withName(new PersonName(expectedName))
                .withBirthDate(new BirthDate(expectedBirthDate))
                .withVat(new PersonVat(vat))
                .withAddress(address)
                .withFamilyId(new FamilyId(0))
                .build();

        PersonJPA personJPA = new PersonJPA(new EmailJPA(person.getId().getEmailAddress()),
                new PersonNameJPA(person.getName().toString()),
                new AddressJPA(person.getAddress().getStreet(), person.getAddress().getLocation(),
                        person.getAddress().getPostalCode()),
                new BirthDateJPA(person.getBirthdate().toString()),
                new PersonVatJPA(person.getVat().toString()),
                person.getFamilyId().getFamilyId());

        List<Email> listOfEmails = new ArrayList<>();
        listOfEmails.add(new Email("jose@gmail.com"));

        when(personRepository.getByEmail(personId)).thenReturn(person);
        when(personRepository.savePerson(person)).thenReturn(personJPA);


        String email = "jose@gmail.com";

        //Act
        EmailListDTO result = personService.addEmail(expectedEmail, email);

        //Assert
        assertNotNull(result);
        assertNotEquals(0, result.getEmailAddresses().size());
        assertEquals(listOfEmails.get(0).toString(), result.getEmailAddresses().get(0));
        assertEquals(listOfEmails.toString(), result.getEmailAddresses().toString());
    }
}

