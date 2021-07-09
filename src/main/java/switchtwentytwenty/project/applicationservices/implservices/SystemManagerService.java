package switchtwentytwenty.project.applicationservices.implservices;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import switchtwentytwenty.project.applicationservices.iservices.ISystemManagerService;
import switchtwentytwenty.project.datamodel.person.PersonJPA;
import switchtwentytwenty.project.datamodel.person.RoleJPA;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.dto.person.SystemManagerDTO;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IRoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class SystemManagerService implements ISystemManagerService {

    private final IRoleRepository iRoleRepository;

    private final IPersonRepositoryJPA iPersonRepositoryJPA;

    PasswordEncoder encoder;

    public SystemManagerService(IRoleRepository iRoleRepository, IPersonRepositoryJPA iPersonRepositoryJPA, PasswordEncoder encoder) {
        this.iRoleRepository = iRoleRepository;
        this.iPersonRepositoryJPA = iPersonRepositoryJPA;
        this.encoder = encoder;
    }

    public void addSystemManager(SystemManagerDTO systemManagerDTO) {
        EmailJPA emailJPA = new EmailJPA(systemManagerDTO.getEmail());
        PersonNameJPA nameJPA = new PersonNameJPA(systemManagerDTO.getName());
        String passwordJPA = encoder.encode(systemManagerDTO.getPassword());
        AddressJPA addressJPA = new AddressJPA("System", "Manager", "1234-123");
        BirthDateJPA birthDateJPA = new BirthDateJPA("01/01/2000");
        PersonVatJPA personVatJPA = new PersonVatJPA("132435465");
        int fakeFamilyId = -1;
        Set<RoleJPA> rolesJPA = new HashSet<>();

        PersonJPA systemManagerJPA = new PersonJPA(emailJPA, nameJPA, addressJPA, birthDateJPA, personVatJPA, fakeFamilyId, passwordJPA, rolesJPA);
        promoteToSystemManager(systemManagerJPA);

        iPersonRepositoryJPA.save(systemManagerJPA);
    }

    private void promoteToSystemManager(PersonJPA personJPA) {
        Set<RoleJPA> rolesJPA;
        Set<RoleJPA> previousRoles = personJPA.getRoles();
        if (previousRoles.isEmpty()) {
            rolesJPA = new HashSet<>();
        } else {
            rolesJPA = previousRoles;
        }
        String roleSystemManager = "ROLE_SYSTEM_MANAGER";
        Optional<RoleJPA> sysManRoleOpt = iRoleRepository.findByName(roleSystemManager);
        if (sysManRoleOpt.isPresent()) {
            RoleJPA sysManRole = sysManRoleOpt.get();
            rolesJPA.add(sysManRole);
        }
        personJPA.setRoles(rolesJPA);
    }
}
