package switchtwentytwenty.project.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import switchtwentytwenty.project.domain.model.shared.*;

@AllArgsConstructor
public class PersonDTO {

    @Getter
    private Email id;
    @Getter
    private PersonName name;
    @Getter
    private Address address;
    @Getter
    private BirthDate birthdate;
    @Getter
    private PersonVat vat;

}
