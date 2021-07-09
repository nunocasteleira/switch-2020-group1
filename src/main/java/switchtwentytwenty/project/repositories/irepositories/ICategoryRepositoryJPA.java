package switchtwentytwenty.project.repositories.irepositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import switchtwentytwenty.project.datamodel.category.BaseCategoryJPA;
import switchtwentytwenty.project.datamodel.category.FamilyCategoryJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;

import java.util.Optional;

public interface ICategoryRepositoryJPA extends CrudRepository<BaseCategoryJPA, CategoryIdJPA> {

    boolean existsByCategoryId(CategoryIdJPA categoryIdJPA);

    @Query("SELECT c FROM BaseCategoryJPA c WHERE c.isStandard = true")
    Iterable<BaseCategoryJPA> findAllStandardCategories();

    @Query("SELECT c FROM BaseCategoryJPA c WHERE c.categoryId = (:id)")
    Optional<BaseCategoryJPA> findByCategoryId(@Param("id") CategoryIdJPA id);

    @Query("SELECT c FROM FamilyCategoryJPA c WHERE c.familyId = (:familyId)")
    Iterable<FamilyCategoryJPA> findAllFamilyCategories(@Param("familyId") long familyIdJPA);

}
