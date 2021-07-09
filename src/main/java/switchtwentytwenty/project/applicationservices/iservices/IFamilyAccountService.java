package switchtwentytwenty.project.applicationservices.iservices;

import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.account.AccountInputDTO;

public interface IFamilyAccountService {
    AccountOutputDTO createFamilyCashAccount(AccountInputDTO inputDTO);
}
