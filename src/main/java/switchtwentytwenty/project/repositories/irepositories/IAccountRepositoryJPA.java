package switchtwentytwenty.project.repositories.irepositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import switchtwentytwenty.project.datamodel.account.AccountJPA;
import switchtwentytwenty.project.datamodel.account.CashAccountJPA;
import switchtwentytwenty.project.datamodel.account.PersonalBankJPA;

import java.util.Optional;

public interface IAccountRepositoryJPA extends CrudRepository<AccountJPA, Long> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM CashAccountJPA e WHERE e.accountId = (:id)")
    boolean existsCashAccount(@Param("id") long accountId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM PersonalBankJPA f WHERE f.accountId = (:id)")
    boolean existsBankAccount(@Param("id") long accountId);

    @Query("SELECT g FROM PersonalBankJPA g WHERE g.accountId = (:id)")
    Optional<PersonalBankJPA> findBankAccountById(@Param("id") long accountId);

    @Query("SELECT g FROM CashAccountJPA g WHERE g.accountId = (:id)")
    Optional<CashAccountJPA> findCashAccountById(@Param("id") long accountId);
}
