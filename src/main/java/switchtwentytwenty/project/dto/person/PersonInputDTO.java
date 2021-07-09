package switchtwentytwenty.project.dto.person;

import lombok.Getter;
import lombok.Setter;

public class PersonInputDTO {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String vat;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phoneNumber;
    @Getter
    @Setter
    private String location;
    @Getter
    @Setter
    private String street;
    @Getter
    @Setter
    private String postCode;
    @Getter
    @Setter
    private String birthDate;
    @Getter
    private String password = "password";
    @Getter
    @Setter
    private String adminId;

}
