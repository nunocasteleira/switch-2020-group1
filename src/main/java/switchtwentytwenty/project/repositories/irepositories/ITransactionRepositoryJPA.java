package switchtwentytwenty.project.repositories.irepositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import switchtwentytwenty.project.datamodel.shared.OriginAccountIdJPA;
import switchtwentytwenty.project.datamodel.transaction.TransactionJPA;

import java.util.List;

public interface ITransactionRepositoryJPA extends CrudRepository<TransactionJPA, Long> {

    @Query("SELECT a FROM TransactionJPA a WHERE a.accountIdValue = (:id)")
    List<TransactionJPA> findAllByAccountId(@Param("id") OriginAccountIdJPA accountId);
}
