package switchtwentytwenty.project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import switchtwentytwenty.project.datamodel.assembler.PersonDomainDataAssembler;
import switchtwentytwenty.project.datamodel.person.PersonJPA;
import switchtwentytwenty.project.datamodel.person.RoleJPA;
import switchtwentytwenty.project.datamodel.shared.AccountIdJPA;
import switchtwentytwenty.project.datamodel.shared.EmailJPA;
import switchtwentytwenty.project.datamodel.shared.OtherEmailJPA;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.AccountId;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.PhoneNumber;
import switchtwentytwenty.project.dto.person.FamilyMemberVOs;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Repository("manual repository")
public class PersonRepository {
    public static final String DUPLICATE_EMAIL = "This e-mail is already registered in the app";
    @Autowired
    IPersonRepositoryJPA iPersonRepositoryJPA;
    @Autowired
    PersonDomainDataAssembler personDomainDataAssembler;

    /**
     * This method finds an instance of Person from the database
     *
     * @param aPersonId id of the Person we want to obtain
     * @return instance of Person
     */
    public Person getByEmail(Email aPersonId) {
        EmailJPA anEmailJPA = new EmailJPA(aPersonId.getEmailAddress());
        PersonJPA aPersonJPA = fromOptionalToPersonJPA(anEmailJPA);
        return fromJPAToDomain(aPersonJPA);
    }

    /**
     * This method finds an instance of PersonJPA from the database by its email
     *
     * @param aPersonIdJPA id of the PersonJPA we want to get
     * @return the instance of PersonJPA
     */
    public PersonJPA fromOptionalToPersonJPA(EmailJPA aPersonIdJPA) {
        Optional<PersonJPA> personJPAOptional = iPersonRepositoryJPA.getByEmail(aPersonIdJPA);
        if (personJPAOptional.isPresent()) {
            return personJPAOptional.get();
        }
        throw new ObjectDoesNotExistException("Person does not exist.");
    }

    /**
     * This method converts an instance of PersonJPA into the corresponding instance of Person
     *
     * @param aPersonJPA instance we want to to convert
     * @return corresponding instance of Person
     */
    private Person fromJPAToDomain(PersonJPA aPersonJPA) {
        FamilyMemberVOs personValueObjects = personDomainDataAssembler.jpaValueObjectsToDomain(aPersonJPA);
        EmailJPA aPersonIdJPA = aPersonJPA.getId();
        List<OtherEmailJPA> otherEmailAddressesJPA = iPersonRepositoryJPA.getOtherEmailsById(aPersonIdJPA);
        List<Email> emailAddresses = personDomainDataAssembler.emailAddressesJPATODomain(otherEmailAddressesJPA);
        List<AccountIdJPA> accountsJPA = iPersonRepositoryJPA.getAccountIdById(aPersonIdJPA);
        List<AccountId> personalAccounts = personDomainDataAssembler.accountsToDomain(accountsJPA);
        Set<RoleJPA> rolesJPA = iPersonRepositoryJPA.getRolesByEmailJPA(aPersonIdJPA);
        Set<Role> roles = personDomainDataAssembler.rolesJPAToDomain(rolesJPA);

        Person aPerson = new Person.Builder(personValueObjects.getEmail())
                .withName(personValueObjects.getPersonName())
                .withBirthDate(personValueObjects.getBirthDate())
                .withVat(personValueObjects.getPersonVat())
                .withPassword(personValueObjects.getPassword())
                .withFamilyId(personValueObjects.getFamilyId())
                .withAddress(personValueObjects.getAddress())
                .build();
        aPerson.setDatabaseId(aPersonJPA.getDataBaseId());
        aPerson.setPersonalAccounts(personalAccounts);

        List<PhoneNumber> phoneNumbers = personValueObjects.getPhoneNumbers();
        for (PhoneNumber phoneNumber : phoneNumbers) {
            aPerson.addPhoneNumber(phoneNumber.toString());
        }
        for (Email emailAddress : emailAddresses) {
            aPerson.addEmail(emailAddress.toString());
        }
        aPerson.setRoles(roles);
        return aPerson;
    }

    public boolean existsByEmailJPA(Email personId) {
        boolean result = false;
        EmailJPA emailJPA = new EmailJPA(personId.getEmailAddress());
        Optional<PersonJPA> optional = iPersonRepositoryJPA.getByEmail(emailJPA);
        if (optional.isPresent()) {
            result = true;
        }
        return result;
    }

    /**
     * This method ensures that the person is added to the repository with the family id
     *
     * @param person the person to be added
     * @return the updated person
     */
    public Person addPerson(Person person) {
        if (!existsByEmailJPA(person.getId())) {
            PersonJPA resultPerson = savePerson(person);
            return fromJPAToDomain(resultPerson);
        }
        throw new DuplicateObjectException("This e-mail already exists in the app");
    }

    public PersonJPA savePerson(Person person) {
        PersonJPA personJPA = personDomainDataAssembler.toData(person);
        return iPersonRepositoryJPA.save(personJPA);
    }

    public List<Email> getEmailList(Email personId) {
        Person person = getByEmail(personId);
        return person.getEmailAddresses();
    }

    /**
     * This method allows to obtain the list of all accounts of a specific family member.
     *
     * @param aPersonId family member's email address.
     * @return list of family member account id's.
     */
    public List<AccountId> getCashAccounts(Email aPersonId) {
        Person person = getByEmail(aPersonId);
        return person.getPersonalAccounts();
    }

    public List<Person> getFamilyMembersWithAccounts(List<Email> familyMembers, Person aPerson) {
        List<Person> familyMembersWithAccounts = new ArrayList<>();
        for (Email email : familyMembers) {
            Person person = getByEmail(email);
            if (!(person.hasId(aPerson.getId())) && person.hasAccounts()) {
                familyMembersWithAccounts.add(person);
            }
        }
        return familyMembersWithAccounts;
    }

    public List<Person> getFamilyMembers(List<Email> emailFamilyMembers) {
        List<Person> familyMembers = new ArrayList<>();
        for (Email email : emailFamilyMembers) {
            Person person = getByEmail(email);
            familyMembers.add(person);
        }
        return familyMembers;
    }
}