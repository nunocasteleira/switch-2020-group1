package switchtwentytwenty.project.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.datamodel.person.RoleJPA;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.repositories.irepositories.IRoleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("RoleRepository")
public class RoleRepositoryTest {
    @Mock
    IRoleRepository iRoleRepository;
    @InjectMocks
    RoleRepository roleRepository;


    @Test
    void getByNameSuccessfully() {
        //Arrange
        ERole name = ERole.valueOf("ROLE_FAMILY_ADMIN");
        String nameJPA = "ROLE_FAMILY_ADMIN";
        RoleJPA aRoleJPA = new RoleJPA(nameJPA);

        when(iRoleRepository.findByName(nameJPA)).thenReturn(Optional.of(aRoleJPA));

        //Act
        Role role = roleRepository.getByName(name);

        //Assert
        assertNotNull(role);
    }

    @Test
    void getByNameThrowsExceptionWhenRoleDoesNotExist() {
        //Arrange
        ERole name = ERole.valueOf("ROLE_FAMILY_ADMIN");
        String nameJPA = "ROLE_FAMILY_ADMIN";
        Optional<RoleJPA> roleJPA = Optional.empty();

        when(iRoleRepository.findByName(nameJPA)).thenReturn(roleJPA);

        //Act + Assert
        assertThrows(ObjectDoesNotExistException.class, () -> roleRepository.getByName(name));
    }

}
