package switchtwentytwenty.project.datamodel.assembler;

import org.springframework.stereotype.Service;
import switchtwentytwenty.project.datamodel.person.PersonJPA;
import switchtwentytwenty.project.datamodel.person.RoleJPA;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.person.FamilyMemberVOs;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PersonDomainDataAssembler {

    private List<OtherEmailJPA> emailsToJPA(PersonJPA personJPA, List<Email> emailList) {
        List<OtherEmailJPA> listToReturn = new ArrayList<>();
        for (Email email : emailList) {
            OtherEmailJPA emailAddressJPA = new OtherEmailJPA();
            emailAddressJPA.setPerson(personJPA);
            emailAddressJPA.setOtherEmail(email.toString());
            listToReturn.add(emailAddressJPA);
        }
        return listToReturn;
    }

    private List<PhoneNumberJPA> phoneNumbersToJPA(PersonJPA personJPA,
                                                   List<PhoneNumber> phoneList) {
        List<PhoneNumberJPA> phoneListToReturn = new ArrayList<>();
        for (PhoneNumber phonenumber : phoneList) {
            PhoneNumberJPA phoneJPA = new PhoneNumberJPA();
            phoneJPA.setPerson(personJPA);
            phoneJPA.setPhoneNumber(phonenumber.toString());
            phoneListToReturn.add(phoneJPA);
        }
        return phoneListToReturn;
    }

    private Set<RoleJPA> rolesToJPA(Set<Role> roles) {
        Set<RoleJPA> rolesToReturn = new HashSet<>();
        roles.forEach(role -> {
            String roleName = role.getName().toString();
            Integer id = role.getId();
            RoleJPA roleJPA = new RoleJPA(id, roleName);
            rolesToReturn.add(roleJPA);
        });
        return rolesToReturn;
    }

    private List<AccountIdJPA> accountsToJPA(PersonJPA personJPA, List<AccountId> accounts) {
        List<AccountIdJPA> listToReturn = new ArrayList<>();
        for (AccountId id : accounts) {
            AccountIdJPA accountIdJPA = new AccountIdJPA(personJPA, id.getAccountIdNumber());
            listToReturn.add(accountIdJPA);
        }
        return listToReturn;
    }

    public PersonJPA toData(Person aPerson) {
        EmailJPA personJPAId = new EmailJPA(aPerson.getId().getEmailAddress());
        BirthDateJPA birthDateJPA = new BirthDateJPA(aPerson.getBirthdate().toString());
        long familyIdJPA = aPerson.getFamilyId().getFamilyId();
        PersonNameJPA personNameJPA = new PersonNameJPA(aPerson.getName().toString());
        PersonVatJPA personVatJPA = new PersonVatJPA(aPerson.getVat().toString());
        long databaseId = aPerson.getDatabaseId();
        String street = aPerson.getAddress().getStreet();
        String location = aPerson.getAddress().getLocation();
        String postCode = aPerson.getAddress().getPostalCode();
        AddressJPA addressJPA = new AddressJPA(street, location, postCode);
        String password = aPerson.getPassword();
        Set<Role> roles = aPerson.getRoles();
        Set<RoleJPA> rolesJPA = rolesToJPA(roles);
        PersonJPA personJPA = new PersonJPA(personJPAId, personNameJPA, addressJPA, birthDateJPA,
                personVatJPA, familyIdJPA, password, rolesJPA);
        List<AccountIdJPA> accountIdJPAS = accountsToJPA(personJPA, aPerson.getPersonalAccounts());
        personJPA.setAccounts(accountIdJPAS);
        List<OtherEmailJPA> emailList = emailsToJPA(personJPA, aPerson.getEmailAddresses());
        personJPA.setEmailAddresses(emailList);
        List<PhoneNumberJPA> phoneNumberList = phoneNumbersToJPA(personJPA, aPerson.getPhoneNumbers());
        personJPA.setPhoneNumbers(phoneNumberList);
        personJPA.setDataBaseId(databaseId);
        return personJPA;
    }

    public FamilyMemberVOs jpaValueObjectsToDomain(PersonJPA aPersonJPA) {
        Email email = new Email(aPersonJPA.getId().getEmailAddress());
        BirthDate birthDate = new BirthDate(aPersonJPA.getBirthdate().getDate());
        PersonName name = new PersonName(aPersonJPA.getName().getPersonName());
        String street = aPersonJPA.getAddress().getStreet();
        String location = aPersonJPA.getAddress().getLocation();
        String postCode = aPersonJPA.getAddress().getPostalCode();
        Address address = new Address(street, location, postCode);
        PersonVat vat = new PersonVat(aPersonJPA.getVat().getVat());
        String password = aPersonJPA.getPassword();
        FamilyId familyId = new FamilyId(aPersonJPA.getFamilyId());
        List<PhoneNumberJPA> phoneNumbersJPA = aPersonJPA.getPhoneNumbers();
        List<PhoneNumber> phoneNumbers = phoneNumbersJPAToDomain(phoneNumbersJPA);

        return new FamilyMemberVOs(email, name, address, birthDate, vat, password, familyId, phoneNumbers);
    }

    /**
     * This method converts the list of email addresses JPA into domain email addresses
     *
     * @param otherEmailJPAList
     * @return
     */
    public List<Email> emailAddressesJPATODomain(List<OtherEmailJPA> otherEmailJPAList) {
        List<Email> newEmailAddresses = new ArrayList<>();

        for (OtherEmailJPA emailAddressJPA : otherEmailJPAList) {
            newEmailAddresses.add(new Email(emailAddressJPA.getOtherEmail()));
        }
        return newEmailAddresses;
    }

    /**
     * This method converts the list of phone numbers JPA into domain phone numbers
     *
     * @param phoneNumberJPAList
     * @return
     */
    private List<PhoneNumber> phoneNumbersJPAToDomain(List<PhoneNumberJPA> phoneNumberJPAList) {
        List<PhoneNumber> newPhoneNumbers = new ArrayList<>();

        for (PhoneNumberJPA phoneNumberJPA : phoneNumberJPAList) {
            newPhoneNumbers.add(new PhoneNumber(phoneNumberJPA.getPhoneNumber()));
        }
        return newPhoneNumbers;
    }

    public Set<Role> rolesJPAToDomain(Set<RoleJPA> rolesJPA) {
        Set<Role> rolesToReturn = new HashSet<>();
        for (RoleJPA roleJPA : rolesJPA) {
            String roleName = roleJPA.getName();
            ERole eRole = ERole.valueOf(roleName);
            Integer id = roleJPA.getId();
            Role role = new Role(id, eRole);
            rolesToReturn.add(role);
        }
        return rolesToReturn;
    }

    public List<AccountId> accountsToDomain(List<AccountIdJPA> accountsJPA){
        List<AccountId> listToReturn = new ArrayList<>();
        for (AccountIdJPA id : accountsJPA) {
            AccountId accountId = new AccountId(id.getAccountId());
            listToReturn.add(accountId);
        }
        return listToReturn;
    }
}
