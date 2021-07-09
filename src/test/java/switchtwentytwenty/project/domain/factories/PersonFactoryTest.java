package switchtwentytwenty.project.domain.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.person.PersonVOs;

import static org.junit.jupiter.api.Assertions.*;

class PersonFactoryTest {

    FamilyId familyId = new FamilyId(1L);
    PersonVOs personVOs;
    Email email;
    PersonName personName;
    Address address;
    BirthDate birthDate;
    PersonVat vat;

    @BeforeEach
    void initialize(){
        email = new Email("bla@gmail.com");
        personName = new PersonName("bla");
        address = new Address("la la la", "la", "4433-001");
        birthDate = new BirthDate("03/07/1990");
        vat = new PersonVat("222222888");

        personVOs = new PersonVOs(email, personName, address, birthDate,vat, "914444555");
    }

    @Test
    void buildPerson() {
        Person person = PersonFactory.buildPerson(personVOs, familyId);

        assertNotNull(person);
    }

    @Test
    void ensureBuild(){
        Person expected = new Person.Builder(email).withName(personName)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        Person person = PersonFactory.buildPerson(personVOs, familyId);

        assertEquals(expected.getId(), person.getId());
        assertEquals(expected.getAddress(), person.getAddress());
        assertEquals(expected.getName(), person.getName());
        assertEquals(expected.getVat(), person.getVat());
        assertEquals(expected.getFamilyId(), person.getFamilyId());
        assertNotEquals(email.hashCode(), person.hashCode());
    }

    @Test
    void ensureNoArgs(){
        PersonFactory personFactory = new PersonFactory();

        assertNotNull(personFactory);
    }
}