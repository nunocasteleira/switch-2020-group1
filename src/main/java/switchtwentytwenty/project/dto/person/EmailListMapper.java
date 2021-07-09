package switchtwentytwenty.project.dto.person;

import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.Email;

import java.util.ArrayList;
import java.util.List;

public class EmailListMapper {

    public EmailListDTO toDTO(Person aPerson) {
        if (aPerson == null) {
            throw new ObjectDoesNotExistException("The person does not exist");
        }
        List<String> emailAddresses = getEmailAddresses(aPerson);

        return new EmailListDTO(emailAddresses);
    }

    private List<String> getEmailAddresses(Person aPerson) {
        List<Email> emailAddresses = aPerson.getEmailAddresses();
        List<String> newEmailAddresses = new ArrayList<>();

        for (Email emailAddress : emailAddresses) {
            newEmailAddresses.add(emailAddress.toString());
        }
        return newEmailAddresses;
    }
}
