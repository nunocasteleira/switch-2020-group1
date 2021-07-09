package switchtwentytwenty.project.dto.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountInputDTOTest {

    @Test
    void ensureAllArgsConstructorIsWorking() {
        int familyId = hashCode();
        double initialAmount = 10.50;
        int currency = 2;
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(familyId, initialAmount, currency, description);

        assertNotNull(inputDTO);
        assertEquals(familyId, inputDTO.getFamilyId());
        assertEquals(initialAmount, inputDTO.getInitialAmount());
        assertEquals(currency, inputDTO.getCurrency());
        assertEquals(description, inputDTO.getDescription());
    }

    @Test
    void ensureNoArgsConstructorIsWorking() {
        int familyId = hashCode();
        double initialAmount = 10.50;
        int currency = 1;
        String provider = "CGD";
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO();
        inputDTO.setFamilyId(familyId);
        inputDTO.setInitialAmount(initialAmount);
        inputDTO.setCurrency(currency);
        inputDTO.setDescription(description);
        inputDTO.setProvider(provider);

        assertNotNull(inputDTO);
        assertEquals(familyId, inputDTO.getFamilyId());
        assertEquals(initialAmount, inputDTO.getInitialAmount());
        assertEquals(currency, inputDTO.getCurrency());
        assertEquals(description, inputDTO.getDescription());
        assertEquals(provider, inputDTO.getProvider());
    }

    @Test
    void ensureArgsConstructorIsWorking() {
        String personId = "joao_bonifacio@gmail.com";
        String differentPersonId = "rodrigo_bonifacio@gmail.com";
        double initialAmount = 10.50;
        double differentInitialAmount = 30.50;
        int currency = 1;
        int differentCurrency = 2;
        String description = "Personal cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(initialAmount,currency,description);
        inputDTO.setPersonId(personId);

        assertNotNull(inputDTO);
        assertEquals(initialAmount, inputDTO.getInitialAmount());
        assertEquals(currency, inputDTO.getCurrency());
        assertEquals(description, inputDTO.getDescription());
        assertEquals(description, inputDTO.getDescription());
        assertEquals(personId,inputDTO.getPersonId());
        assertNotEquals(differentPersonId, inputDTO.getPersonId());
        assertNotEquals(differentInitialAmount, inputDTO.getInitialAmount());
        assertNotEquals(differentCurrency, inputDTO.getCurrency());
    }


}