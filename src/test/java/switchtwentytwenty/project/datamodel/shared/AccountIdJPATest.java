package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.person.PersonJPA;

import static org.junit.jupiter.api.Assertions.*;

class AccountIdJPATest {


    @Test
    void noArgsConstructor() {
        AccountIdJPA accountIdJPA = new AccountIdJPA();

        assertNotNull(accountIdJPA);
    }

    @Test
    void AllArgsConstructor() {
        EmailJPA id = new EmailJPA("antonio@sapo.pt");
        PersonNameJPA name = new PersonNameJPA("António");
        AddressJPA addressJPA = new AddressJPA("Rua das Flores", "Porto", "4678-567");
        BirthDateJPA birthdate = new BirthDateJPA("1995/08/12");
        PersonVatJPA vat = new PersonVatJPA("123456789");
        long familyId = 1;
        PersonJPA personJPA = new PersonJPA(id, name, addressJPA, birthdate, vat, familyId);
        long accountId = 89;

        AccountIdJPA accountIdJPA = new AccountIdJPA(personJPA, accountId);

        assertNotNull(accountIdJPA);
    }


    @Test
    void ensureGetPersonJPA() {
        EmailJPA id = new EmailJPA("antonio@sapo.pt");
        EmailJPA otherId = new EmailJPA("antonio_ramos@sapo.pt");
        PersonNameJPA name = new PersonNameJPA("António");
        AddressJPA addressJPA = new AddressJPA("Rua das Flores", "Porto", "4678-567");
        BirthDateJPA birthdate = new BirthDateJPA("1995/08/12");
        PersonVatJPA vat = new PersonVatJPA("123456789");
        long familyId = 1;

        PersonJPA personJPA = new PersonJPA(id, name, addressJPA, birthdate, vat, familyId);
        PersonJPA otherPersonJPA =new PersonJPA(otherId, name, addressJPA, birthdate, vat, familyId);
        long accountId = 89;
        PersonJPA result;

        AccountIdJPA accountIdJPA = new AccountIdJPA(personJPA, accountId);
        result = accountIdJPA.getPersonJPA();

        assertEquals(personJPA, result);
        assertNotEquals(otherPersonJPA, result);
    }

    @Test
    void ensureGetAccountId() {
        EmailJPA id = new EmailJPA("antonio@sapo.pt");
        PersonNameJPA name = new PersonNameJPA("António");
        AddressJPA addressJPA = new AddressJPA("Rua das Flores", "Porto", "4678-567");
        BirthDateJPA birthdate = new BirthDateJPA("1995/08/12");
        PersonVatJPA vat = new PersonVatJPA("123456789");
        long familyId = 1;

        PersonJPA personJPA = new PersonJPA(id, name, addressJPA, birthdate, vat, familyId);
        long accountId = 89;
        long otherAccountId = 1;
        long result;

        AccountIdJPA accountIdJPA = new AccountIdJPA(personJPA, accountId);
        result = accountIdJPA.getAccountId();

        assertEquals(accountId, result);
        assertNotEquals(otherAccountId, result);
    }
}