package switchtwentytwenty.project.domain.factories;

import lombok.Getter;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.dto.family.FamilyInputVOs;
import switchtwentytwenty.project.dto.person.PersonVOs;

public class FamilyFactory {
    @Getter
    private final Family family;
    @Getter
    private final Person administrator;

    public FamilyFactory(FamilyInputVOs familyInputVOs, PersonVOs personVOs) {
        this.family = createFamily(familyInputVOs);
        this.administrator = createAdministrator(personVOs);
    }

    private Family createFamily(FamilyInputVOs familyInputVOs) {
        return buildFamily(familyInputVOs);
    }

    private Family buildFamily(FamilyInputVOs familyInputVOs) {
        return new Family.FamilyBuilder(familyInputVOs.getFamilyName(), familyInputVOs.getEmail())
                .withRegistrationDate()
                .withId()
                .build();
    }

    private Person createAdministrator(PersonVOs personVOs) {
        return buildPerson(personVOs, family.getFamilyId());
    }

    private Person buildPerson(PersonVOs personVOs, FamilyId familyId) {
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