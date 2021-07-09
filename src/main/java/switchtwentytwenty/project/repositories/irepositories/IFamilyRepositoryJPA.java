package switchtwentytwenty.project.repositories.irepositories;

import org.springframework.data.repository.CrudRepository;
import switchtwentytwenty.project.datamodel.family.FamilyJPA;

public interface IFamilyRepositoryJPA extends CrudRepository<FamilyJPA, Long> {

}
