package switchtwentytwenty.project.applicationservices.iservices;

import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.dto.account.AccountListDTO;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.account.CashAccountsOutputDTO;
import switchtwentytwenty.project.dto.person.FamilyMembersOutputDTO;

public interface IPersonAccountService {

    AccountOutputDTO createBankAccount(AccountInputDTO accountInputDTO, String personId);

    AccountOutputDTO createPersonalCashAccount(AccountInputDTO accountInputDTO, String personId);

    AccountListDTO getMemberAccountList(String personId);

    CashAccountsOutputDTO getCashAccounts(String personId);

    FamilyMembersOutputDTO getFamilyMembersWithCashAccounts(String personId);
}
