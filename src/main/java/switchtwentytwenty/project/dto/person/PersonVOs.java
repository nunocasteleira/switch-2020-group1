package switchtwentytwenty.project.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import switchtwentytwenty.project.domain.model.shared.*;

@AllArgsConstructor
public class PersonVOs {

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
    private final String phoneNumber;
    @Getter
    @Setter
    private String password;

    public PersonVOs(Email email, PersonName personName, Address address, BirthDate birthDate, PersonVat personVat, String phoneNumber) {
        this.email = email;
        this.personName = personName;
        this.address = address;
        this.birthDate = birthDate;
        this.personVat = personVat;
        this.phoneNumber = phoneNumber;
    }
}
