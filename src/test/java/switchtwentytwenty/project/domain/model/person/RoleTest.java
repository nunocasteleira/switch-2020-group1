package switchtwentytwenty.project.domain.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertNull;

class RoleTest {

    @Test
    void familyAdminRoleIsCreatedSuccessfully() {
        Integer roleId = 3;
        ERole roleName = ERole.ROLE_FAMILY_ADMIN;
        Role role = new Role(roleId, roleName);

        assertNotNull(role);
        assertEquals(roleId, role.getId());
        assertEquals(roleName, role.getName());
    }

    @Test
    void systemManagerRoleIsCreatedSuccessfully() {
        Integer roleId = 3;
        ERole roleName = ERole.ROLE_SYSTEM_MANAGER;
        Role role = new Role(roleId, roleName);

        assertNotNull(role);
        assertEquals(roleId, role.getId());
        assertEquals(roleName, role.getName());
    }

    @Test
    void emptyRoleIsCreatedSuccessfully() {
        Role role = new Role();

        assertNotNull(role);
        assertNull(null, role.getId());
        assertNull(null, role.getName());
    }

    @Test
    void setRoleIdSuccessfully() {
        Integer expectedId = 1;
        ERole roleName = ERole.ROLE_SYSTEM_MANAGER;
        Role role = new Role(roleName);

        role.setId(1);

        assertNotNull(role);
        assertEquals(expectedId, role.getId());
    }

    @Test
    void setRoleNameSuccessfully() {
        ERole expectedRoleName = ERole.ROLE_FAMILY_ADMIN;
        Role role = new Role();

        role.setName(ERole.ROLE_FAMILY_ADMIN);

        assertNotNull(role);
        assertEquals(expectedRoleName, role.getName());
    }
}
