package switchtwentytwenty.project.dto.authentication;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.authentication.LoginRequestDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginRequestDTOTest {

    @Test
    void ensureLoginRequestDTOIsWorkingProperlyAllArgs(){
        String email = "ricardo@gmail.com";
        String password = "password";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);

        assertNotNull(loginRequestDTO);
        assertEquals(email, loginRequestDTO.getEmail());
        assertEquals(password, loginRequestDTO.getPassword());

    }

    @Test
    void ensureLoginRequestDTOIsWorkingProperlyNoArgs(){
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();

        assertNotNull(loginRequestDTO);
        assertEquals(null, loginRequestDTO.getEmail());
        assertEquals(null, loginRequestDTO.getPassword());

    }
}
