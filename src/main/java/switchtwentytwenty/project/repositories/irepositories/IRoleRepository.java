package switchtwentytwenty.project.repositories.irepositories;

import org.springframework.data.repository.CrudRepository;
import switchtwentytwenty.project.datamodel.person.RoleJPA;

import java.util.Optional;

public interface IRoleRepository extends CrudRepository<RoleJPA, Long> {
    Optional<RoleJPA> findByName(String name);
}
