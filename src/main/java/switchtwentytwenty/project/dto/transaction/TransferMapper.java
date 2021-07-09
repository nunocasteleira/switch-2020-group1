package switchtwentytwenty.project.dto.transaction;

import org.springframework.stereotype.Component;
import switchtwentytwenty.project.assemblers.AccountDTOAssembler;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.account.CashAccountsOutputDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransferMapper {

    AccountDTOAssembler accountDTOAssembler = new AccountDTOAssembler();

    public CashAccountsOutputDTO toDTO(List<CashAccount> cashAccounts) {
        List<AccountOutputDTO> cashAccountsDTO = new ArrayList<>();
        for (CashAccount cashAccount : cashAccounts) {
            AccountOutputDTO cashAccountDTO = accountDTOAssembler.accountToOutputDTO(cashAccount);
            cashAccountsDTO.add(cashAccountDTO);
        }
        return new CashAccountsOutputDTO(cashAccountsDTO);
    }
}
