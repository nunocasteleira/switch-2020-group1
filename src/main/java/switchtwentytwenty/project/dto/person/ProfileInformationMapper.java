package switchtwentytwenty.project.dto.person;

import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

public class ProfileInformationMapper {

    /**
     * This method obtains a user's profile information, by getting it directly from the person
     * object
     *
     * @param aPerson person object whose profile information we want to obtain
     * @return profile information as DTO
     */
    public ProfileInformationDTO mapProfileInformation(Person aPerson, Family aFamily) {
        if (aPerson == null) {
            throw new ObjectDoesNotExistException("The person does not exist");
        }
        if (aFamily == null) {
            throw new ObjectDoesNotExistException("The family does not exist");
        }
        String name = aPerson.getName().toString();
        String mainEmailAccount = aPerson.getId().toString();
        String birthDate = aPerson.getBirthdate().toString();
        String vatNumber = aPerson.getVat().toString();
        String address = aPerson.getAddress().toString();
        long familyId = aPerson.getFamilyId().getFamilyId();
        String familyName = aFamily.getFamilyName().toString();
        String adminEmail = aFamily.getAdminId().getEmailAddress();
        boolean isAdmin = mainEmailAccount.equals(adminEmail);
        List<String> phoneNumbers = getPhoneNumbers(aPerson);
        List<String> emailAddresses = getEmailAddresses(aPerson);

        return new ProfileInformationDTO(name, mainEmailAccount, birthDate,
                vatNumber, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);
    }

    /**
     * This method obtains the user's phone numbers as list of Strings
     *
     * @param aPerson person object whose profile information we want to obtain
     * @return user's phone numbers as a list of strings
     */
    private List<String> getPhoneNumbers(Person aPerson) {
        List<PhoneNumber> phoneNumbers = aPerson.getPhoneNumbers();
        List<String> newPhoneNumbers = new ArrayList<>();

        for (PhoneNumber phoneNumber : phoneNumbers) {
            newPhoneNumbers.add(phoneNumber.toString());
        }
        return newPhoneNumbers;
    }

    /**
     * This method obtainsthe user's email addresses as a list of Strings
     *
     * @param aPerson person object whose profile information we want to obtain
     * @return user's email addresses as a list of Strings
     */
    private List<String> getEmailAddresses(Person aPerson) {
        List<Email> emailAddresses = aPerson.getEmailAddresses();
        List<String> newEmailAddresses = new ArrayList<>();

        for (Email emailAddress : emailAddresses) {
            newEmailAddresses.add(emailAddress.toString());
        }
        return newEmailAddresses;
    }
}
