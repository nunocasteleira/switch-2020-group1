package switchtwentytwenty.project.dto.person;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileInformationMapperTest {

    ProfileInformationMapper mapper = new ProfileInformationMapper();

    @Test
    void getProfileInformationDTOFromAnExistingPerson() {
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

        FamilyName familyName = new FamilyName("Maia");
        FamilyId familyId = new FamilyId(10);
        Family aFamily = new Family.FamilyBuilder(familyName, personId).withRegistrationDate().withId(familyId).build();

        //Act
        ProfileInformationDTO profileInformationDTO = mapper.mapProfileInformation(aPerson, aFamily);
        String actualName = profileInformationDTO.getName();
        long actualFamilyId = profileInformationDTO.getFamilyId();

        //Assert
        assertNotNull(profileInformationDTO);
        assertEquals(expectedName, actualName);
        assertEquals(10, actualFamilyId);
    }

    @Test
    void getProfileInformationDTOFromAnExistingPersonWithThreeEmailAddresses() {
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
        aPerson.addEmail("aFirstEmail@gmail.com");
        aPerson.addEmail("aSecondEmail@gmail.com");
        aPerson.addEmail("aThirdEmail@gmail.com");

        List<String> expectedEmailAddresses = new ArrayList<>();
        expectedEmailAddresses.add("aFirstEmail@gmail.com");
        expectedEmailAddresses.add("aSecondEmail@gmail.com");
        expectedEmailAddresses.add("aThirdEmail@gmail.com");

        FamilyName familyName = new FamilyName("Maia");
        FamilyId familyId = new FamilyId(10);
        Family aFamily = new Family.FamilyBuilder(familyName, personId).withRegistrationDate().withId(familyId).build();

        //Act
        ProfileInformationDTO profileInformationDTO = mapper.mapProfileInformation(aPerson, aFamily);
        String actualName = profileInformationDTO.getName();
        List<String> actualEmailAddresses = profileInformationDTO.getEmailAddresses();
        String actualFamilyName = profileInformationDTO.getFamilyName();

        //Assert
        assertNotNull(profileInformationDTO);
        assertEquals(expectedName, actualName);
        assertEquals(expectedEmailAddresses, actualEmailAddresses);
        assertEquals("Maia", actualFamilyName);
    }

    @Test
    void getProfileInformationDTOFromAnNonExistingPersonThrowsException() {
        //Arrange
        Email personId = new Email("fabio@gmail.com");
        FamilyName familyName = new FamilyName("Maia");
        FamilyId familyId = new FamilyId(10);
        Family aFamily = new Family.FamilyBuilder(familyName, personId).withRegistrationDate().withId(familyId).build();

        //Assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> mapper.mapProfileInformation(null, aFamily));
    }

    @Test
    void getProfileInformationDTOFromAnNonExistingFamilyThrowsException() {
        //Arrange
        String expectedName = "Fabio";
        Email personId = new Email("fabio@gmail.com");

        Person aPerson = new Person.Builder(personId)
                .withName(new PersonName(expectedName))
                .withAddress(new Address("Rua das Flores", "Porto", "4444-333"))
                .withBirthDate(new BirthDate("09/11/1988"))
                .withVat(new PersonVat("222333444"))
                .withFamilyId(new FamilyId(123))
                .build();

        //Assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> mapper.mapProfileInformation(aPerson, null));
    }

    @Test
    void getProfileInformationDTOFromTwoExistingPersons() {
        //Arrange
        String expectedNameFirstPerson = "Fabio";
        Email personOneId = new Email("fabio@gmail.com");

        Person aPersonOne = new Person.Builder(personOneId)
                .withName(new PersonName(expectedNameFirstPerson))
                .withAddress(new Address("Rua das Flores", "Porto", "4444-333"))
                .withBirthDate(new BirthDate("09/11/1988"))
                .withVat(new PersonVat("222333444"))
                .withFamilyId(new FamilyId(10))
                .build();

        String expectedNameSecondPerson = "Gervasio";
        Email aPersonTwoId = new Email("gervasio@gmail.com");

        Person aPersonTwo = new Person.Builder(aPersonTwoId)
                .withName(new PersonName(expectedNameSecondPerson))
                .withAddress(new Address("Rua das Flores", "Porto", "4444-333"))
                .withBirthDate(new BirthDate("09/11/1988"))
                .withVat(new PersonVat("227475777"))
                .withFamilyId(new FamilyId(10))
                .build();

        FamilyName familyName = new FamilyName("Maia");
        FamilyId familyId = new FamilyId(10);
        Family aFamily = new Family.FamilyBuilder(familyName, personOneId).withRegistrationDate().withId(familyId).build();

        //Act
        ProfileInformationDTO firstProfileInformationDTO =
                mapper.mapProfileInformation(aPersonOne, aFamily);

        ProfileInformationDTO secondProfileInformationDTO =
                mapper.mapProfileInformation(aPersonTwo, aFamily);

        String firstPersonName = firstProfileInformationDTO.getName();
        String secondPersonName = secondProfileInformationDTO.getName();

        long familyIdPersonOne = firstProfileInformationDTO.getFamilyId();
        long familyIdPersonTwo = secondProfileInformationDTO.getFamilyId();

        //Assert
        assertNotNull(firstProfileInformationDTO);
        assertNotNull(secondProfileInformationDTO);
        assertNotEquals(firstPersonName, secondPersonName);
        assertEquals(familyIdPersonOne, familyIdPersonTwo);
    }

    @Test
    void assertPhoneNumbers() {
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

        String phoneNumber = "919999999";
        List<String> expected = new ArrayList<>();
        expected.add(phoneNumber);
        aPerson.addPhoneNumber(phoneNumber);
        List<String> unexpected = Collections.emptyList();

        FamilyName familyName = new FamilyName("Maia");
        FamilyId familyId = new FamilyId(10);
        Family aFamily = new Family.FamilyBuilder(familyName, personId).withRegistrationDate().withId(familyId).build();
        ProfileInformationDTO profileInformationDTO = mapper.mapProfileInformation(aPerson, aFamily);

        //Act
        List<String> result = profileInformationDTO.getPhoneNumbers();

        //Assert
        assertNotNull(profileInformationDTO);
        assertEquals(expected, result);
        assertNotEquals(unexpected, result);
    }
}
