package switchtwentytwenty.project.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.repositories.irepositories.IRoleRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoleRepositoryItTest {
    @Autowired
    IRoleRepository iRoleRepository;
    @Autowired
    RoleRepository roleRepository;

//    @BeforeEach
//    void clean() {
//        iRoleRepository.deleteAll();
//    }

    @Test
    void getByNameSuccessfully() {
        //Arrange
        ERole name = ERole.valueOf("ROLE_FAMILY_ADMIN");

        //Act
        Role role = roleRepository.getByName(name);

        //Assert
        assertNotNull(role);
    }


    @Test
    void getByNameThrowsExceptionWhenRoleDoesNotExist() {
        //Arrange
        ERole name = ERole.valueOf("ROLE_TEST_ONLY");

        //Act + Assert
        assertThrows(ObjectDoesNotExistException.class, () -> roleRepository.getByName(name));
    }

    @Test
    void addRoleSuccessfully() {
        //Arrange
        ERole name = ERole.valueOf("ROLE_TEST_ONLY");

        //Act
        roleRepository.addRole(name);
        Role role = roleRepository.getByName(name);

        //Assert
        assertNotNull(role);
    }

}
