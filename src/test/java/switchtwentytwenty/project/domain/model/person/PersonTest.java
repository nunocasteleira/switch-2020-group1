package switchtwentytwenty.project.domain.model.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    Email id;
    PersonName name;
    Address address;
    PhoneNumber phoneNumber;
    PersonVat vat;
    BirthDate birthDate;
    FamilyId familyId;

    @BeforeEach
    void initialize() {
        id = new Email("antonio@gmail.com");
        name = new PersonName("António");
        address = new Address("Rua Clara", "Porto", "4000-000");
        phoneNumber = new PhoneNumber("911111111");
        vat = new PersonVat("222333222");
        birthDate = new BirthDate("05/03/2000");
        familyId = new FamilyId(hashCode());
    }

    @Test
    void personIsCreatedSuccessfully() {
        Person person = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        assertNotNull(person);
    }

    @Test
    void ensurePersonIdIsCorrect() {
        Email expected = new Email("antonio@gmail.com");
        Person person = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        Email result = person.getId();

        assertEquals(expected, result);
    }

    @Test
    void ensurePersonIdNotTheSame() {
        Email expected = new Email("xica@gmail.com");
        Person person = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        Email result = person.getId();

        assertNotEquals(expected, result);
    }

    @Test
    void ensureFamilyIdIsCorrect() {
        FamilyId expected = familyId;
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        FamilyId result = aPerson.getFamilyId();

        assertEquals(expected, result);
    }

    @Test
    void ensureFamilyIdNotSame() {
        FamilyId expected = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        FamilyId result = aPerson.getFamilyId();

        assertNotEquals(expected, result);
    }

    @Test
    void ensureValidEmailIsAdded() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        List<Email> expected = new ArrayList<>();
        expected.add(new Email("fabio@gmail.com"));

        String validEmail = "fabio@gmail.com";

        List<Email> result = aPerson.addEmail(validEmail);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void ensureInvalidEmailNotAdded() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        String invalidEmail = "fabio.gmail.com";

        assertThrows(IllegalArgumentException.class, () -> aPerson.addEmail(invalidEmail));
    }

    @Test
    void ensureNullEmailNotAdded() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        String nullEmail = null;

        assertThrows(IllegalArgumentException.class, () -> aPerson.addEmail(nullEmail));
    }

    @Test
    void ensureExistingEmailInPersonNotAdded() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        int emailSizeListExpected = 0;
        int emailSizeList;

        String sameEmail = "antonio@gmail.com";

        List<Email> expected = new ArrayList<>(0);

        List<Email> result = aPerson.addEmail(sameEmail);

        emailSizeList = aPerson.getEmailAddresses().size();

        assertEquals(expected, result);
        assertEquals(emailSizeListExpected, emailSizeList);
    }

    @Test
    void ensurePersonHasEmail() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        boolean result;

        Email otherEmail = new Email("antonio@gmail.com");
        result = aPerson.hasEmail(otherEmail);

        assertTrue(result);
    }

    @Test
    void ensurePersonHasPhoneNumber() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();
        aPerson.addPhoneNumber("914699000");
        boolean result;

        result = aPerson.hasPhoneNumber(new PhoneNumber("914699000"));

        assertTrue(result);
    }

    @Test
    void ensurePersonHasEqualEmail() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        boolean result;

        List<Email> list = aPerson.getEmailAddresses();
        Email otherEmail = new Email("antonio1@gmail.com");
        list.add(otherEmail);
        Email otherEmail1 = new Email("antonio1@gmail.com");
        list.add(otherEmail1);
        result = aPerson.hasEmail(otherEmail);


        assertTrue(result);
    }

    @Test
    void ensureEmailIsAddedToList() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        aPerson.addEmail("paulinho@gmail.com");
        int emailSizeListExpected = 1;
        int emailSizeList;

        emailSizeList = aPerson.getEmailAddresses().size();
        assertEquals(emailSizeListExpected, emailSizeList);
    }

    @Test
    void ensureEmailListCopyIsCreated() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();
        aPerson.addEmail("pedro@gmail.com");
        Email emailAddress = new Email("pedro@gmail.com");
        List<Email> expected = new ArrayList<>();
        expected.add(emailAddress);

        List<Email> result = aPerson.getEmailAddresses();

        assertNotSame(expected, result);
        assertEquals(expected, result);
    }

    @Test
    void hasId() {
        Email personId = new Email("xica@gmail.com");
        Person person = new Person.Builder(personId)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();
        boolean result;

        result = person.hasId(personId);

        assertTrue(result);
    }

    @Test
    void hasDifferentId() {
        Email personId = new Email("xica@gmail.com");
        Person person = new Person.Builder(personId)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();
        boolean result;

        result = person.hasId(new Email("something@gmail.com"));

        assertFalse(result);
    }

    @Test
    void getterTests() {
        Person person = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        List<Email> emailList = new ArrayList<>();

        assertEquals(name, person.getName());
        assertEquals(address, person.getAddress());
        assertEquals(birthDate, person.getBirthdate());
        assertEquals(vat, person.getVat());
        assertEquals(familyId, person.getFamilyId());
        assertEquals(phoneNumberList, person.getPhoneNumbers());
        assertEquals(emailList, person.getEmailAddresses());
    }

    @Test
    void testPhoneNumber() {
        Person person = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        boolean result = person.addPhoneNumber("913444555");

        assertTrue(result);
    }

    @Test
    void testHasPhoneNumber() {
        Person person = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        person.addPhoneNumber("913444555");

        boolean result = person.hasPhoneNumber(new PhoneNumber("913444555"));
        boolean result1 = person.hasPhoneNumber(new PhoneNumber("914567876"));
        boolean result2 = person.hasPhoneNumber(null);

        assertTrue(result);
        assertFalse(result1);
        assertFalse(result2);
    }

    @Test
    void ensureValidEmailIsRemoved() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        List<Email> expected = new ArrayList<>();
        aPerson.addEmail("antonio2@gmail.com");

        String validEmail = "antonio2@gmail.com";

        List<Email> result = aPerson.removeEmail(validEmail);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void ensureInvalidEmailIsNotRemoved() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        String invalidEmail = "antonio.gmail.com";

        assertThrows(IllegalArgumentException.class, () -> aPerson.removeEmail(invalidEmail));
    }

    @Test
    void ensureNullEmailNotRemoved() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        assertThrows(IllegalArgumentException.class, () -> aPerson.removeEmail(null));
    }

    @Test
    void ensurePrimaryEmailInPersonNotRemoved() {
        FamilyId familyId = new FamilyId(1);
        Person aPerson = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();

        int emailSizeListExpected = 0;
        int emailSizeList;

        String sameEmail = "antonio@gmail.com";

        List<Email> expected = new ArrayList<>(0);

        List<Email> result = aPerson.addEmail(sameEmail);

        emailSizeList = aPerson.getEmailAddresses().size();

        assertEquals(expected, result);
        assertEquals(emailSizeListExpected, emailSizeList);
    }

    @Test
    void ensureAccountIdIsAdded() {
        Person person = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();
        AccountId accountId = new AccountId(1);

        boolean result = person.addAccountId(accountId);

        assertTrue(result);
    }

    @Test
    void ensureRepeatedAccountIdIsNotAdded() {
        Person person = new Person.Builder(id)
                .withName(name)
                .withAddress(address)
                .withBirthDate(birthDate)
                .withVat(vat)
                .withFamilyId(familyId)
                .build();
        AccountId accountId = new AccountId(1);
        person.addAccountId(accountId);

        boolean result = person.addAccountId(accountId);

        assertFalse(result);
    }

}