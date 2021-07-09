package switchtwentytwenty.project.datamodel.person;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RoleJPATest {

    @Test
    void ensureNoArgs() {
        RoleJPA roleJPA = new RoleJPA();

        assertNotNull(roleJPA);
    }

    @Test
    void ensureAllArgs() {
        Integer id = new Random().nextInt();
        String name = "ROLE_SYSTEM_MANAGER";

        RoleJPA roleJPA = new RoleJPA(id, name);

        assertNotNull(roleJPA);
    }

    @Test
    void ensureAllArgsWithOtherERoleJPA() {
        Integer id = new Random().nextInt();
        String name = "ROLE_SYSTEM_MANAGER";

        RoleJPA roleJPA = new RoleJPA(id, name);

        assertNotNull(roleJPA);
    }

    @Test
    void ensureSetIdEquals() {
        //arrange
        Integer id = new Random().nextInt();
        String name = "ROLE_SYSTEM_MANAGER";
        Integer otherId = 12345;
        RoleJPA roleJPA = new RoleJPA(id, name);
        Integer result;

        //act
        roleJPA.setId(12345);
        result = roleJPA.getId();

        //assert
        assertEquals(otherId, result);
    }

    @Test
    void ensureSetIdDoesNotEqual() {
        //arrange
        Integer id = new Random().nextInt();
        String name = "ROLE_SYSTEM_MANAGER";
        Integer otherId = 12345;
        RoleJPA roleJPA = new RoleJPA(id, name);
        Integer result;

        //act
        roleJPA.setId(12345);
        result = roleJPA.getId();

        //assert
        assertNotEquals(id, result);
    }

    @Test
    void ensureSetNameEquals() {
        //arrange
        Integer id = new Random().nextInt();
        String name = "ROLE_SYSTEM_MANAGER";
        String otherRoleName = "ROLE_FAMILY_ADMIN";
        RoleJPA roleJPA = new RoleJPA(id, name);
        String result;

        //act
        roleJPA.setName(otherRoleName);
        result = roleJPA.getName();

        //assert
        assertEquals(otherRoleName, result);
    }

    @Test
    void ensureSetNameDoesNotEqual() {
        //arrange
        Integer id = new Random().nextInt();
        String name = "ROLE_SYSTEM_MANAGER";
        String otherRoleName = "ROLE_FAMILY_ADMIN";
        RoleJPA roleJPA = new RoleJPA(id, name);
        String result;

        //act
        roleJPA.setName(otherRoleName);
        result = roleJPA.getName();

        //assert
        assertNotEquals(name, result);
    }

    @Test
    void ensureGetIdEquals() {
        //arrange
        Integer id = new Random().nextInt();
        String name = "ROLE_SYSTEM_MANAGER";
        RoleJPA roleJPA = new RoleJPA(id, name);
        Integer result;

        //act
        result = roleJPA.getId();

        //assert
        assertEquals(id, result);
    }

    @Test
    void ensureGetIdNotEquals() {
        //arrange
        Integer id = new Random().nextInt();
        Integer otherId = new Random().nextInt();
        String name = "ROLE_SYSTEM_MANAGER";
        RoleJPA roleJPA = new RoleJPA(id, name);
        Integer result;

        //act
        result = roleJPA.getId();

        //assert
        assertNotEquals(otherId, result);
    }

    @Test
    void ensureGetNameEquals() {

        //arrange
        Integer id = new Random().nextInt();
        String name = "ROLE_SYSTEM_MANAGER";
        RoleJPA roleJPA = new RoleJPA(id, name);
        String result;

        //act
        result = roleJPA.getName();

        //assert
        assertEquals(name, result);
    }

    @Test
    void ensureGetNameDoesNotEqual() {

        //arrange
        Integer id = new Random().nextInt();
        String name = "ROLE_SYSTEM_MANAGER";
        String otherRoleName = "ROLE_FAMILY_ADMIN";
        RoleJPA roleJPA = new RoleJPA(id, name);
        String result;

        //act
        result = roleJPA.getName();

        //assert
        assertNotEquals(otherRoleName, result);
    }
}