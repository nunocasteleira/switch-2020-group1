package switchtwentytwenty.project.dto.person;

import lombok.Getter;
import lombok.Setter;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FamilyMemberVOs {

    @Getter
    private final Email email;
    @Getter
    private final PersonName personName;
    @Getter
    private final Address address;
    @Getter
    private final BirthDate birthDate;
    @Getter
    private final PersonVat personVat;
    @Getter
    private final FamilyId familyId;
    @Setter
    @Getter
    private List<PhoneNumber> phoneNumbers;
    @Setter
    @Getter
    private List<Email> emailAddresses;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private Set<Role> roles;

    public FamilyMemberVOs(Email email, PersonName personName, Address address, BirthDate birthdate, PersonVat personVat, FamilyId familyId, List<PhoneNumber> phoneNumbers) {
        this.email = email;
        this.personName = personName;
        this.address = address;
        this.birthDate = birthdate;
        this.personVat = personVat;
        this.familyId = familyId;
        this.phoneNumbers = new ArrayList<>(phoneNumbers);
    }

    public FamilyMemberVOs(Email email, PersonName personName, Address address, BirthDate birthdate, PersonVat personVat, String password, FamilyId familyId, List<PhoneNumber> phoneNumbers) {
        this.email = email;
        this.personName = personName;
        this.address = address;
        this.birthDate = birthdate;
        this.personVat = personVat;
        this.password = password;
        this.familyId = familyId;
        this.phoneNumbers = new ArrayList<>(phoneNumbers);
    }

    public FamilyMemberVOs(Email email, PersonName personName, Address address, BirthDate birthDate, PersonVat personVat, FamilyId familyId, List<PhoneNumber> phoneNumbers, List<Email> emailAddresses) {
        this.email = email;
        this.personName = personName;
        this.address = address;
        this.birthDate = birthDate;
        this.personVat = personVat;
        this.familyId = familyId;
        this.phoneNumbers = new ArrayList<>(phoneNumbers);
        this.emailAddresses = new ArrayList<>(emailAddresses);
    }
}
