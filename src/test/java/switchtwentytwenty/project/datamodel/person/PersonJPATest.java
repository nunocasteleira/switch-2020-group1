package switchtwentytwenty.project.datamodel.person;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.domain.model.shared.AccountId;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonJPATest {

    @Test
    void ensureAllArgsIsWorking() {
        EmailJPA emailJPA = new EmailJPA();
        PersonNameJPA personNameJPA = new PersonNameJPA();
        AddressJPA addressJPA = new AddressJPA();
        BirthDateJPA birthDateJPA = new BirthDateJPA();
        PersonVatJPA personVatJPA = new PersonVatJPA();
        long familyIdJPA = 0;

        PersonJPA personJPA = new PersonJPA(emailJPA, personNameJPA, addressJPA, birthDateJPA,
                personVatJPA, familyIdJPA);

        assertNotNull(personJPA);
        assertEquals(personJPA, personJPA);
        assertEquals(emailJPA, personJPA.getId());
        assertEquals(personNameJPA, personJPA.getName());
        assertEquals(addressJPA, personJPA.getAddress());
        assertEquals(birthDateJPA, personJPA.getBirthdate());
        assertEquals(personVatJPA, personJPA.getVat());
        assertEquals(familyIdJPA, personJPA.getFamilyId());
    }

    @Test
    void noArgsConstructor() {
        PersonJPA personJPA = new PersonJPA();

        assertNotNull(personJPA);
    }

    @Test
    void ensureEqualsIsWorking() {
        EmailJPA emailJPA = new EmailJPA();
        PersonNameJPA personNameJPA = new PersonNameJPA();
        AddressJPA addressJPA = new AddressJPA();
        BirthDateJPA birthDateJPA = new BirthDateJPA();
        PersonVatJPA personVatJPA = new PersonVatJPA();
        long familyIdJPA = 0;

        PersonJPA personJPA = new PersonJPA(emailJPA, personNameJPA, addressJPA, birthDateJPA,
                personVatJPA, familyIdJPA);
        PersonJPA personJPA1 = new PersonJPA(new EmailJPA("something@gmail.com"),
                personNameJPA,
                addressJPA,
                birthDateJPA,
                personVatJPA, familyIdJPA);
        PersonJPA personJPA2 = new PersonJPA(emailJPA,
                new PersonNameJPA("test"),
                addressJPA,
                birthDateJPA,
                personVatJPA, familyIdJPA);
        PersonJPA personJPA3 = new PersonJPA(emailJPA, personNameJPA, new AddressJPA("asfgwdgwf",
                "dsfs", "4400-999"), birthDateJPA, personVatJPA, familyIdJPA);
        PersonJPA personJPA4 = new PersonJPA(emailJPA, personNameJPA, addressJPA,
                new BirthDateJPA("30/06/1990"), personVatJPA, familyIdJPA);
        PersonJPA personJPA5 = new PersonJPA(emailJPA, personNameJPA, addressJPA, birthDateJPA,
                new PersonVatJPA("234444444"), familyIdJPA);
        PersonJPA personJPA6 = new PersonJPA(emailJPA, personNameJPA, addressJPA, birthDateJPA,
                personVatJPA, 21542);
        PersonJPA personJPAWithSetDBId = new PersonJPA(emailJPA, personNameJPA, addressJPA, birthDateJPA,
                personVatJPA, familyIdJPA);
        PersonJPA personJPADifferentDBId = new PersonJPA(emailJPA, personNameJPA, addressJPA, birthDateJPA,
                personVatJPA, familyIdJPA);
        personJPAWithSetDBId.setDataBaseId(1L);
        personJPADifferentDBId.setDataBaseId(2L);

        assertNotEquals(personJPA, personJPA1);
        assertNotEquals(personJPA, personJPA2);
        assertNotEquals(personJPA1, personJPA2);
        assertFalse(personJPA.equals(personJPA1));
        assertNotEquals(personJPA.hashCode(), personJPA1.hashCode());
        assertFalse(personJPA.equals(familyIdJPA));
        assertNotEquals(personJPA, personJPA3);
        assertNotEquals(personJPA, personJPA4);
        assertNotEquals(personJPA, personJPA5);
        assertNotEquals(personJPA, personJPA6);
        assertNotEquals(personJPAWithSetDBId, personJPADifferentDBId);
    }

    @Test
    void getEmailList() {
        EmailJPA emailJPA = new EmailJPA();
        PersonNameJPA personNameJPA = new PersonNameJPA();
        AddressJPA addressJPA = new AddressJPA();
        BirthDateJPA birthDateJPA = new BirthDateJPA();
        PersonVatJPA personVatJPA = new PersonVatJPA();
        long familyIdJPA = 0;
        PersonJPA personJPA = new PersonJPA(emailJPA, personNameJPA, addressJPA, birthDateJPA,
                personVatJPA, familyIdJPA);
        String email = "new@email.com";
        OtherEmailJPA otherEmailJPA = new OtherEmailJPA();
        otherEmailJPA.setOtherEmail(email);
        otherEmailJPA.setPerson(personJPA);
        List<OtherEmailJPA> expected = new ArrayList<>();
        expected.add(otherEmailJPA);
        personJPA.setEmailAddresses(expected);

        List<OtherEmailJPA> result = personJPA.getEmailAddresses();

        assertEquals(expected, result);
    }

    @Test
    void tryToAddAccounts(){
        EmailJPA emailJPA = new EmailJPA();
        PersonNameJPA personNameJPA = new PersonNameJPA();
        AddressJPA addressJPA = new AddressJPA();
        BirthDateJPA birthDateJPA = new BirthDateJPA();
        PersonVatJPA personVatJPA = new PersonVatJPA();
        long familyIdJPA = 0;
        PersonJPA personJPA = new PersonJPA(emailJPA, personNameJPA, addressJPA, birthDateJPA,
                personVatJPA, familyIdJPA);
        AccountIdJPA id = new AccountIdJPA(personJPA, 1);
        List<AccountIdJPA> accountIdJPAS = new ArrayList<>();
        accountIdJPAS.add(id);

        personJPA.setAccounts(accountIdJPAS);

        assertEquals(accountIdJPAS, personJPA.getAccounts());
    }

    @Test
    void getPhoneList() {
        EmailJPA emailJPA = new EmailJPA();
        PersonNameJPA personNameJPA = new PersonNameJPA();
        AddressJPA addressJPA = new AddressJPA();
        BirthDateJPA birthDateJPA = new BirthDateJPA();
        PersonVatJPA personVatJPA = new PersonVatJPA();
        long familyIdJPA = 0;
        PersonJPA personJPA = new PersonJPA(emailJPA, personNameJPA, addressJPA, birthDateJPA,
                personVatJPA, familyIdJPA);
        String phoneNumber = "213456789";
        PhoneNumberJPA phoneNumberJPA = new PhoneNumberJPA();
        phoneNumberJPA.setPerson(personJPA);
        phoneNumberJPA.setPhoneNumber(phoneNumber);
        List<PhoneNumberJPA> expected = new ArrayList<>();
        expected.add(phoneNumberJPA);
        personJPA.setPhoneNumbers(expected);

        List<PhoneNumberJPA> result = personJPA.getPhoneNumbers();

        assertEquals(expected, result);
    }

    @Test
    void getRoles() {
        EmailJPA emailJPA = new EmailJPA();
        PersonNameJPA personNameJPA = new PersonNameJPA();
        AddressJPA addressJPA = new AddressJPA();
        BirthDateJPA birthDateJPA = new BirthDateJPA();
        PersonVatJPA personVatJPA = new PersonVatJPA();
        String password = "password";
        long familyIdJPA = 0;

        //noinspection unchecked
        Set<RoleJPA> unexpected = Collections.EMPTY_SET;
        Integer roleId = new Random().nextInt();
        String roleFamilyAdmin = "ROLE_FAMILY_ADMIN";
        RoleJPA roleJPA = new RoleJPA(roleId, roleFamilyAdmin);
        Set<RoleJPA> expected = new HashSet<>();
        expected.add(roleJPA);

        PersonJPA personJPA = new PersonJPA(emailJPA, personNameJPA, addressJPA, birthDateJPA,
                personVatJPA, familyIdJPA, password, expected);
        PersonJPA anotherPersonJPA = new PersonJPA();
        anotherPersonJPA.setRoles(expected);

        Set<RoleJPA> result = personJPA.getRoles();
        Set<RoleJPA> anotherResult = anotherPersonJPA.getRoles();

        assertEquals(expected, result);
        assertNotEquals(unexpected, result);
        assertEquals(expected, anotherResult);
        assertNotEquals(unexpected, anotherResult);
    }
}