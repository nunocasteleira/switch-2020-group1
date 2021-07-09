package switchtwentytwenty.project.domain.factories;

import lombok.NoArgsConstructor;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.dto.person.PersonVOs;

@NoArgsConstructor
public class PersonFactory {

    /**
     * this method instantiate a person
     *
     * @param personVOs all parameters needed to build a person
     * @param familyId  the family id where that person belongs
     * @return the person with all parameters
     */
    public static Person buildPerson(PersonVOs personVOs, FamilyId familyId) {
        Person person = new Person.Builder(personVOs.getEmail())
                .withName(personVOs.getPersonName())
                .withAddress(personVOs.getAddress())
                .withBirthDate(personVOs.getBirthDate())
                .withVat(personVOs.getPersonVat())
                .withPassword(personVOs.getPassword())
                .withFamilyId(familyId)
                .build();
        person.validatePhoneNumber(personVOs.getPhoneNumber());
        return person;
    }
}
