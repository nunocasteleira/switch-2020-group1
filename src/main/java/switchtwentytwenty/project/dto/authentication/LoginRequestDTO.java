package switchtwentytwenty.project.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @Getter
    private String email;
    @Getter
    private String password;
}
