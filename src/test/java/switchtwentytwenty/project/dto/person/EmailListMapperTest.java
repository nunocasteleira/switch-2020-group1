package switchtwentytwenty.project.dto.person;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.person.EmailListDTO;
import switchtwentytwenty.project.dto.person.EmailListMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmailListMapperTest {

    EmailListMapper mapper = new EmailListMapper();

    @Test
    void toDTOTest() {
        //Arrange
        String expectedName = "Fabio";
        Email personId = new Email("fabio@gmail.com");

        Person aPerson = new Person.Builder(personId)
                .withName(new PersonName(expectedName))
                .withAddress(new Address("Rua das Flores", "Porto", "4444-333"))
                .withBirthDate(new BirthDate("09/11/1988"))
                .withVat(new PersonVat("222333444"))
                .withFamilyId(new FamilyId(10))
                .build();

        //Act
        EmailListDTO emailListDTO = mapper.toDTO(aPerson);

        //Assert
        assertNotNull(emailListDTO);
    }

    @Test
    void toDTONullPersonTest() {

        //Assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> mapper.toDTO(null));
    }

    @Test
    void getEmailAddressesTest(){

        //Arrange
        String expectedName = "Fabio";
        Email personId = new Email("fabio@gmail.com");

        Person aPerson = new Person.Builder(personId)
                .withName(new PersonName(expectedName))
                .withAddress(new Address("Rua das Flores", "Porto", "4444-333"))
                .withBirthDate(new BirthDate("09/11/1988"))
                .withVat(new PersonVat("222333444"))
                .withFamilyId(new FamilyId(10))
                .build();

        Email email = new Email("jo@gmail.com");
        List<Email> emailAddresses = aPerson.getEmailAddresses();
        emailAddresses.add(email);
        List<String> newEmailAddresses = new ArrayList<>();
        newEmailAddresses.add(email.toString());

        assertNotNull(newEmailAddresses);
        assertEquals(emailAddresses.get(0).toString(), newEmailAddresses.get(0));
    }
}
