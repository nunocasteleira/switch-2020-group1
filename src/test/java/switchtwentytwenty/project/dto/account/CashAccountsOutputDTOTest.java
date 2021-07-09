package switchtwentytwenty.project.dto.account;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.AccountId;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashAccountsOutputDTOTest {

    @Test
    void ensureAllArgsConstructorIsWorking() {
        int accountId = hashCode();
        double initialAmount = 10;
        String description = "Family cash account";
        String currency = "EUR";
        AccountOutputDTO outputDTO = new AccountOutputDTO(accountId, initialAmount,currency, description);
        List<AccountOutputDTO> listOfDTOs = new ArrayList<>();
        listOfDTOs.add(outputDTO);
        CashAccountsOutputDTO cashAccountsOutputDTO = new CashAccountsOutputDTO(listOfDTOs);

        assertNotNull(cashAccountsOutputDTO);
        assertEquals(listOfDTOs, cashAccountsOutputDTO.getCashAccounts());
    }

    @Test
    void ensureNoArgsConstructorIsWorking() {
        int accountId = hashCode();
        double initialAmount = 20;
        String description = "Family cash account";
        String currency = "EUR";
        AccountOutputDTO outputDTO = new AccountOutputDTO(accountId, initialAmount,currency, description);
        List<AccountOutputDTO> listOfDTOs = new ArrayList<>();
        listOfDTOs.add(outputDTO);
        CashAccountsOutputDTO cashAccountsOutputDTO = new CashAccountsOutputDTO();
        cashAccountsOutputDTO.setCashAccounts(listOfDTOs);

        assertNotNull(cashAccountsOutputDTO);
        assertEquals(listOfDTOs, cashAccountsOutputDTO.getCashAccounts());
    }

    @Test
    void equalsAndHashCode() {
        int accountId1 = 101;
        int accountId2 = 505;
        AccountId accountId = new AccountId(accountId1);
        double initialAmount1 = 20;
        double initialAmount2 = 30;
        String currency1 = "EUR";
        String currency2 = "USD";
        String description1 = "Family cash account";
        String description2 = "A cash account";

        AccountOutputDTO outputDTO = new AccountOutputDTO(accountId1, initialAmount1,currency1, description1);
        List<AccountOutputDTO> listOfDTOs = new ArrayList<>();
        listOfDTOs.add(outputDTO);
        CashAccountsOutputDTO cashAccountsOutputDTO = new CashAccountsOutputDTO(listOfDTOs);
        CashAccountsOutputDTO cashAccountsOutputDTOSame = cashAccountsOutputDTO;
        CashAccountsOutputDTO cashAccountsOutputDTOEqual = new CashAccountsOutputDTO(listOfDTOs);
        AccountOutputDTO outputDTOOther = new AccountOutputDTO(accountId2, initialAmount2, currency2, description2);
        List<AccountOutputDTO> listOfDTOOther = new ArrayList<>();
        listOfDTOs.add(outputDTOOther);
        CashAccountsOutputDTO cashAccountsOutputDTOOther = new CashAccountsOutputDTO(listOfDTOOther);

        assertEquals(cashAccountsOutputDTO, cashAccountsOutputDTOSame);
        assertSame(cashAccountsOutputDTO, cashAccountsOutputDTOSame);
        assertEquals(cashAccountsOutputDTO, cashAccountsOutputDTOEqual);
        assertNotSame(cashAccountsOutputDTO, cashAccountsOutputDTOEqual);
        assertEquals(cashAccountsOutputDTO.hashCode(), cashAccountsOutputDTOSame.hashCode());
        assertEquals(cashAccountsOutputDTO.getCashAccounts(), cashAccountsOutputDTOSame.getCashAccounts());
        assertEquals(cashAccountsOutputDTO.hashCode(), cashAccountsOutputDTOEqual.hashCode());
        assertEquals(cashAccountsOutputDTO.getCashAccounts(), cashAccountsOutputDTOEqual.getCashAccounts());
        assertNotEquals(null, cashAccountsOutputDTO);
        assertFalse(cashAccountsOutputDTO.equals(accountId));
        assertNotEquals(cashAccountsOutputDTO, cashAccountsOutputDTOOther);
        assertNotEquals(cashAccountsOutputDTO.getCashAccounts(), cashAccountsOutputDTOOther.getCashAccounts());
        assertNotEquals(0, cashAccountsOutputDTO.hashCode());
        assertNotEquals(cashAccountsOutputDTO, description1);
    }
}
