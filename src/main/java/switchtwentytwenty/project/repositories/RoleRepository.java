package switchtwentytwenty.project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import switchtwentytwenty.project.datamodel.person.RoleJPA;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.repositories.irepositories.IRoleRepository;

import java.util.Optional;

@Repository
public class RoleRepository {
    public static final String ROLE_DOES_NOT_EXIST = "Role does not exist";
    @Autowired
    IRoleRepository iRoleRepository;

    private static Role fromJPAToDomain(RoleJPA roleJPA) {
        String roleName = roleJPA.getName();
        ERole eRole = ERole.valueOf(roleName);
        Integer id = roleJPA.getId();
        return new Role(id, eRole);
    }

    private static RoleJPA toData(ERole role) {
        return new RoleJPA(role.name());
    }

    public Role getByName(ERole role) {
        String roleName = role.name();
        RoleJPA roleJPA = fromOptionalToRoleJPA(roleName);
        return fromJPAToDomain(roleJPA);
    }

    private RoleJPA fromOptionalToRoleJPA(String name) {
        Optional<RoleJPA> roleJPAOptional = iRoleRepository.findByName(name);
        if (roleJPAOptional.isPresent()) {
            return roleJPAOptional.get();
        }
        throw new ObjectDoesNotExistException(ROLE_DOES_NOT_EXIST);
    }

    public void addRole(ERole role) {
        RoleJPA roleJPA = toData(role);
        iRoleRepository.save(roleJPA);
    }
}
