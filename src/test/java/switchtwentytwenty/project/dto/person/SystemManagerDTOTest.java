package switchtwentytwenty.project.dto.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemManagerDTOTest {

    @Test
    void ensureCreation() {
        String name = "System Manager";
        String email = "system@manager.com";
        String password = "testCode";

        SystemManagerDTO result = new SystemManagerDTO(name, email, password);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
    }

    @Test
    void setName() {
        String name = "System Manager";
        String email = "system@manager.com";
        String password = "testCode";
        String otherName = "ManagerSystem";
        SystemManagerDTO systemManagerDTO = new SystemManagerDTO(name, email, password);

        systemManagerDTO.setName(otherName);
        String result = systemManagerDTO.getName();

        assertNotNull(systemManagerDTO);
        assertEquals(otherName, result);
        assertNotEquals(name, result);
    }

    @Test
    void setEmail() {
        String name = "System Manager";
        String email = "system@manager.com";
        String password = "testCode";
        String otherEmail = "manager@system.com";
        SystemManagerDTO systemManagerDTO = new SystemManagerDTO(name, email, password);

        systemManagerDTO.setEmail(otherEmail);
        String result = systemManagerDTO.getEmail();

        assertNotNull(systemManagerDTO);
        assertEquals(otherEmail, result);
        assertNotEquals(email, result);
    }

    @Test
    void setPassword() {
        String name = "System Manager";
        String email = "system@manager.com";
        String password = "testCode";
        String otherPassword = "codeTest";
        SystemManagerDTO systemManagerDTO = new SystemManagerDTO(name, email, password);

        systemManagerDTO.setPassword(otherPassword);
        String result = systemManagerDTO.getPassword();

        assertNotNull(systemManagerDTO);
        assertEquals(otherPassword, result);
        assertNotEquals(password, result);
    }
}