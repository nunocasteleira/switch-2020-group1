package switchtwentytwenty.project.dto.authentication;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.authentication.JwtResponseDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtResponseDTOTest {

    @Test
    void createJwtResponseDTOSuccessfully() {
        String type = "Bearer";
        String token = "edfhweho238uoinfe";
        String email = "test@gmail.com";
        String personName = "Jules Caesar";
        long familyId = 1;
        List<String> roles = new ArrayList<>();
        roles.add("generalRole");
        roles.add("systemManager");

        JwtResponseDTO responseDTO = new JwtResponseDTO(token, email, personName, familyId, roles);

        assertNotNull(responseDTO);
        assertEquals(token, responseDTO.getToken());
        assertEquals(email, responseDTO.getEmail());
        assertEquals(personName, responseDTO.getPersonName());
        assertEquals(familyId, responseDTO.getFamilyId());
        assertEquals(roles, responseDTO.getRole());
        assertEquals(type, responseDTO.getType());
    }

    @Test
    void createEMptyJwtResponseDTOSuccessfully() {
        JwtResponseDTO responseDTO = new JwtResponseDTO();

        assertNotNull(responseDTO);
        assertNull(null, responseDTO.getToken());
        assertNull(null, responseDTO.getEmail());
    }

    @Test
    void setJwtResponseDTOAttributesSuccessfully() {
        String token = "edfhweho238uoinfe";
        String email = "test@gmail.com";
        String personName = "Jules Caesar";
        long familyId = 1;
        List<String> roles = new ArrayList<>();
        roles.add("generalRole");
        roles.add("systemManager");

        JwtResponseDTO responseDTO = new JwtResponseDTO();
        responseDTO.setToken(token);
        responseDTO.setType("Bearer");
        responseDTO.setEmail(email);
        responseDTO.setPersonName(personName);
        responseDTO.setFamilyId(familyId);
        responseDTO.setRole(roles);

        assertNotNull(responseDTO);
        assertEquals(token, responseDTO.getToken());
        assertEquals(email, responseDTO.getEmail());
        assertEquals(personName, responseDTO.getPersonName());
        assertEquals(roles, responseDTO.getRole());
    }

    @Test
    void testEquals() {
        String token = "edfhweho238uoinfe";
        String email = "test@gmail.com";
        String personName = "Jules Caesar";
        long familyId = 1;
        List<String> roles = new ArrayList<>();
        roles.add("generalRole");
        roles.add("systemManager");

        JwtResponseDTO responseDTOOriginal = new JwtResponseDTO(token, email, personName, familyId, roles);
        JwtResponseDTO responseDTOSame = responseDTOOriginal;
        JwtResponseDTO responseDTOEquals = new JwtResponseDTO(token, email, personName, familyId, roles);
        JwtResponseDTO responseDTODifferentToken = new JwtResponseDTO("edfhweho238uoinfeasdf", email, personName, familyId, roles);
        JwtResponseDTO responseDTODifferentEmail = new JwtResponseDTO(token, "other@gmail.com", personName, familyId, roles);
        JwtResponseDTO responseDTODifferentPersonName = new JwtResponseDTO(token, email, "Caesar Jules", familyId, roles);
        JwtResponseDTO responseDTODifferentFamilyId = new JwtResponseDTO(token, email, personName, 2L, roles);
        JwtResponseDTO responseDTODifferentRoles = new JwtResponseDTO(token, email, personName, familyId, new ArrayList<>());
        JwtResponseDTO responseDTODifferentType = new JwtResponseDTO(token, email, personName, familyId, roles);
        responseDTODifferentType.setType("AnotherType");

        assertNotNull(responseDTOOriginal);
        assertEquals(responseDTOOriginal, responseDTOSame);
        assertSame(responseDTOOriginal, responseDTOSame);
        assertEquals(responseDTOOriginal.hashCode(), responseDTOSame.hashCode());
        assertNotEquals(0, responseDTOOriginal.hashCode());
        assertEquals(responseDTOOriginal, responseDTOEquals);
        assertNotSame(responseDTOOriginal, responseDTOEquals);
        assertEquals(responseDTOOriginal.hashCode(), responseDTOEquals.hashCode());
        assertNotEquals(responseDTOOriginal, responseDTODifferentToken);
        assertNotEquals(responseDTOOriginal.hashCode(), responseDTODifferentToken.hashCode());
        assertNotEquals(responseDTOOriginal, responseDTODifferentEmail);
        assertNotEquals(responseDTOOriginal.hashCode(), responseDTODifferentEmail.hashCode());
        assertNotEquals(responseDTOOriginal, responseDTODifferentPersonName);
        assertNotEquals(responseDTOOriginal.hashCode(), responseDTODifferentPersonName.hashCode());
        assertNotEquals(responseDTOOriginal, responseDTODifferentFamilyId);
        assertNotEquals(responseDTOOriginal.hashCode(), responseDTODifferentFamilyId.hashCode());
        assertNotEquals(responseDTOOriginal, responseDTODifferentRoles);
        assertNotEquals(responseDTOOriginal.hashCode(), responseDTODifferentRoles.hashCode());
        assertNotEquals(responseDTOOriginal, responseDTODifferentType);
        assertNotEquals(responseDTOOriginal.hashCode(), responseDTODifferentType.hashCode());
        assertNotEquals(responseDTOOriginal, roles);
    }
}
