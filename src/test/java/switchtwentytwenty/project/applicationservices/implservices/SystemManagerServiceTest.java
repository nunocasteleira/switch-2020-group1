package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import switchtwentytwenty.project.datamodel.person.PersonJPA;
import switchtwentytwenty.project.datamodel.person.RoleJPA;
import switchtwentytwenty.project.datamodel.shared.EmailJPA;
import switchtwentytwenty.project.dto.person.SystemManagerDTO;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IRoleRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SystemManagerServiceTest {

    @Autowired
    IRoleRepository iRoleRepository;

    @Autowired
    IPersonRepositoryJPA iPersonRepositoryJPA;

    @Autowired
    SystemManagerService systemManagerService;

    @BeforeEach
    void prepare() {
        iPersonRepositoryJPA.deleteAll();
    }

    @AfterEach
    void cleanup() {
        iPersonRepositoryJPA.deleteAll();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    @Transactional // to brute force subsequent queries
    void addSystemManager() {
        String name = "System Manager";
        String email = "system@manager.com";
        String password = "testCode";
        SystemManagerDTO systemManagerDTO = new SystemManagerDTO(name, email, password);
        String roleName = "ROLE_SYSTEM_MANAGER";
        Optional<RoleJPA> optionalSystemManagerRole = iRoleRepository.findByName(roleName);
        RoleJPA systemManagerRole;
        PersonJPA systemManager;
        String resultName;
        String resultEmail;
        Set<RoleJPA> resultRoles;

        systemManagerService.addSystemManager(systemManagerDTO);

        Optional<PersonJPA> optionalSystemManager = iPersonRepositoryJPA.getByEmail(new EmailJPA(email));
        systemManager = optionalSystemManager.get();
        systemManagerRole = optionalSystemManagerRole.get();
        resultName = systemManager.getName().getPersonName();
        resultEmail = systemManager.getId().getEmailAddress();
        resultRoles = systemManager.getRoles();
        boolean existsRole = resultRoles.contains(systemManagerRole);

        assertNotNull(systemManager);
        assertEquals(name, resultName);
        assertEquals(email, resultEmail);
        assertNotEquals(Collections.EMPTY_SET, resultRoles);
        assertTrue(existsRole);
    }

}