package switchtwentytwenty.project.domain.model.person;

import lombok.Getter;
import lombok.Setter;
import switchtwentytwenty.project.domain.model.interfaces.AggregateRoot;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Person implements AggregateRoot<Email> {

    @Getter
    private Email id;
    @Getter
    @Setter
    private long databaseId;
    @Getter
    private PersonName name;
    @Getter
    @Setter
    private String password;
    @Getter
    private Address address;
    @Getter
    private BirthDate birthdate;
    @Getter
    private PersonVat vat;
    @Getter
    @Setter
    private FamilyId familyId;
    @Getter
    private List<PhoneNumber> phoneNumbers;
    @Getter
    private List<Email> emailAddresses;
    @Getter
    @Setter
    private List<AccountId> personalAccounts;
    @Getter
    @Setter
    private Set<Role> roles = new HashSet<>();

    private Person() {
    }

    /**
     * This method allows to validate the phone number of the family member.
     *
     * @param phoneNumber the family member phone number
     */
    public void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.equals("")) {
            PhoneNumber phoneNumber1 = new PhoneNumber(phoneNumber);
            this.phoneNumbers.add(phoneNumber1);
        }
    }

    /**
     * This method allow us to compare two e-mails address.
     *
     * @param email other e-mail address to compare.
     * @return true if the e-mail is the same or false if not.
     */
    @Override
    public boolean hasId(Email email) {
        return this.id.equals(email);
    }

    /**
     * Method to add an instance of EmailAddress to Person emailAddressList.
     *
     * @param email String with email to add
     * @return True if email is valid and was added, False otherwise
     */

    public List<Email> addEmail(String email) {
        Email anEmail = new Email(email);
        if (!hasEmail(anEmail)) {
            this.emailAddresses.add(anEmail);
        }
        return new ArrayList<>(emailAddresses);
    }

    /**
     * Method to remove an instance of EmailAddress from Person emailAddressList.
     *
     * @param email String with email to remove
     * @return array list of emailAddresses
     */

    public List<Email> removeEmail(String email) {
        Email anEmail = new Email(email);
        if (hasEmail(anEmail)) {
            this.emailAddresses.remove(anEmail);
        }
        return new ArrayList<>(emailAddresses);
    }

    /**
     * Method to verify if the email Address already exists.
     *
     * @param otherEmail instance of otherEmail
     * @return True if Person has the emailAddress, False otherwise
     */

    public boolean hasEmail(Email otherEmail) {
        boolean result = false;
        for (Email email1 : this.emailAddresses
        ) {
            if (email1.equals(otherEmail)) {
                result = true;
            }
        }
        if (this.id.equals(otherEmail)) {
            result = true;
        }
        return result;
    }

    /**
     * Adds a phone number to the person's phone number listper
     *
     * @param phoneNumber instance of a PhoneNumber
     * @return boolean true, if the phone number was successfully added
     */
    public boolean addPhoneNumber(String phoneNumber) {
        boolean result = false;
        PhoneNumber aPhoneNumber = new PhoneNumber(phoneNumber);
        if (!hasPhoneNumber(aPhoneNumber)) {
            result = this.phoneNumbers.add(aPhoneNumber);
        }
        return result;
    }

    /**
     * Verifies is the person already has that phoneNumber registered
     *
     * @param phoneNumber instance of PhoneNumber
     * @return boolean true, if the phone number is already registered
     */
    public boolean hasPhoneNumber(PhoneNumber phoneNumber) {
        boolean result = false;
        for (PhoneNumber aPhoneNumber : this.phoneNumbers) {
            if (aPhoneNumber.equals(phoneNumber)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Adds an account id to the persons list of accounts
     *
     * @param accountId instance of an account id
     * @return true is the account id is added
     */
    public boolean addAccountId(AccountId accountId) {
        boolean result = false;
        if (!hasAccountId(accountId)) {
            result = this.personalAccounts.add(accountId);
        }
        return result;
    }

    /**
     * checks if the person already has this account
     *
     * @param accountId the intended account id
     * @return true if the person already have this account
     */
    private boolean hasAccountId(AccountId accountId) {
        boolean result = false;
        for (AccountId accountId1 : this.personalAccounts) {
            if (accountId1.equals(accountId)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * This method allows to know if the person already has accounts.
     *
     * @return true if the person already has accounts and if not.
     */
    public boolean hasAccounts() {
        return this.personalAccounts.size() != 0;
    }

    public static class Builder {

        private final Email id;
        private PersonName name;
        private Address address;
        private BirthDate birthdate;
        private PersonVat vat;
        private FamilyId familyId;
        private long databaseId;
        private String password;

        public Builder(Email id) {
            this.id = id;
        }

        public Builder withName(PersonName name) {
            this.name = name;

            return this;
        }

        public Builder withAddress(Address address) {
            this.address = address;

            return this;
        }

        public Builder withBirthDate(BirthDate birthdate) {
            this.birthdate = birthdate;

            return this;
        }

        public Builder withVat(PersonVat vat) {
            this.vat = vat;

            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;

            return this;
        }

        public Builder withFamilyId(FamilyId familyId) {
            this.familyId = familyId;

            return this;
        }

        public Person build() {
            Person person = new Person();
            person.id = this.id;
            person.name = this.name;
            person.address = this.address;
            person.birthdate = this.birthdate;
            person.vat = this.vat;
            person.familyId = this.familyId;
            person.phoneNumbers = new ArrayList<>();
            person.emailAddresses = new ArrayList<>();
            person.personalAccounts = new ArrayList<>();
            person.databaseId = this.databaseId;
            person.password = this.password;
            return person;
        }
    }
}
