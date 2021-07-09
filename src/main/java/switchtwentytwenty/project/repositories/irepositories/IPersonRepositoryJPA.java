package switchtwentytwenty.project.repositories.irepositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import switchtwentytwenty.project.datamodel.person.PersonJPA;
import switchtwentytwenty.project.datamodel.person.RoleJPA;
import switchtwentytwenty.project.datamodel.shared.AccountIdJPA;
import switchtwentytwenty.project.datamodel.shared.EmailJPA;
import switchtwentytwenty.project.datamodel.shared.OtherEmailJPA;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IPersonRepositoryJPA extends CrudRepository<PersonJPA, EmailJPA> {

    /**
     * Method that queries the database to obtain the list of other emails from an user using the
     * PersonJPA Id
     *
     * @param id emailJPA
     * @return list of an user's other emails
     */
    @Query("SELECT e FROM OtherEmailJPA e WHERE e.person.id = (:id)")
    List<OtherEmailJPA> getOtherEmailsById(@Param("id") EmailJPA id);

    @Query("SELECT p FROM PersonJPA p WHERE p.id = (:id)")
    Optional<PersonJPA> getByEmail(@Param("id") EmailJPA id);

    @Query("SELECT r FROM PersonJPA p JOIN p.roles r WHERE p.id = (:id)")
    Set<RoleJPA> getRolesByEmailJPA(@Param("id") EmailJPA id);

    @Query("SELECT a FROM AccountIdJPA a WHERE a.personJPA.id = (:id)")
    List<AccountIdJPA> getAccountIdById(@Param("id") EmailJPA id);
}
