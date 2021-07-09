package switchtwentytwenty.project.dto.person;

import lombok.Getter;
import lombok.Setter;

public class SystemManagerDTO {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;

    public SystemManagerDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
