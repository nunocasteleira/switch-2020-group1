package switchtwentytwenty.project.dto.account;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.AccountId;

import static org.junit.jupiter.api.Assertions.*;

public class AccountOutputDTOTest {

    @Test
    void ensureAllArgsConstructorIsWorking() {
        int accountId = hashCode();
        double initialAmount = 10;
        String description = "Family cash account";
        String currency = "EUR";
        AccountOutputDTO outputDTO = new AccountOutputDTO(accountId, initialAmount,currency, description);

        assertNotNull(outputDTO);
        assertEquals(accountId, outputDTO.getAccountId());
        assertEquals(initialAmount, outputDTO.getInitialAmount());
        assertEquals(currency, outputDTO.getCurrency());
        assertEquals(description, outputDTO.getAccountDescription());
    }

    @Test
    void ensureNoArgsConstructorIsWorking() {
        int accountId = hashCode();
        double initialAmount = 20;
        String description = "Family cash account";
        String currency = "EUR";
        String provider = "CGD";
        AccountOutputDTO outputDTO = new AccountOutputDTO();
        outputDTO.setAccountId(accountId);
        outputDTO.setInitialAmount(initialAmount);
        outputDTO.setAccountDescription(description);
        outputDTO.setCurrency(currency);
        outputDTO.setProvider(provider);

        assertNotNull(outputDTO);
        assertEquals(accountId, outputDTO.getAccountId());
        assertEquals(currency, outputDTO.getCurrency());
        assertEquals(initialAmount, outputDTO.getInitialAmount());
        assertEquals(provider, outputDTO.getProvider());
    }

    @Test
    void equalsAndHashCode() {
        int accountId1 = 101;
        int accountId2 = 505;
        AccountId accountId = new AccountId(accountId1);
        double initialAmount1 = 20;
        double initialAmount2 = 30;
        String description1 = "Family cash account";
        String description2 = "A cash account";

        AccountOutputDTO outputDTO = new AccountOutputDTO(accountId1, initialAmount1,"EUR",description1);
        AccountOutputDTO outputDTO1 = outputDTO;
        AccountOutputDTO outputDTONull = null;
        AccountOutputDTO outputDTOSame = new AccountOutputDTO(accountId1, initialAmount1, "EUR",description1);
        AccountOutputDTO outputDTODifferentId = new AccountOutputDTO(accountId2,initialAmount1,"EUR", description1);
        AccountOutputDTO outputDTODifferentAmount = new AccountOutputDTO(accountId1,initialAmount2, "EUR", description1);
        AccountOutputDTO outputDTODifferentCurrency = new AccountOutputDTO(accountId1,initialAmount1, "USD",description1);
        AccountOutputDTO outputDTODifferentDescription = new AccountOutputDTO(accountId1,initialAmount1, "EUR",description2);

        assertEquals(outputDTO, outputDTOSame);
        assertNotSame(outputDTO, outputDTOSame);
        assertEquals(outputDTO, outputDTO1);
        assertEquals(outputDTO.hashCode(), outputDTOSame.hashCode());
        assertNotEquals(outputDTO, outputDTONull);
        assertFalse(outputDTO.equals(accountId));
        assertFalse(outputDTO.equals(description1));
        assertFalse(outputDTO.equals(initialAmount1));
        assertNotEquals(outputDTO, outputDTODifferentId);
        assertNotEquals(outputDTO, outputDTODifferentAmount);
        assertNotEquals(outputDTO, outputDTODifferentCurrency);
        assertNotEquals(outputDTO, outputDTODifferentDescription);
        assertFalse(outputDTO.equals(outputDTODifferentId));
        assertFalse(outputDTO.equals(outputDTODifferentCurrency));
        assertFalse(outputDTO.equals(outputDTODifferentAmount));
        assertFalse(outputDTO.equals(outputDTODifferentDescription));
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentId.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentAmount.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentCurrency.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentDescription.hashCode());
        assertNotEquals(0, outputDTO.hashCode());
        assertNotEquals(outputDTO, description1);
        assertNotEquals(outputDTO, initialAmount1);
    }
}
