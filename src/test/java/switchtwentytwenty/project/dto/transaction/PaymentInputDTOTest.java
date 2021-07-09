package switchtwentytwenty.project.dto.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentInputDTOTest {

    @Test
    void createPaymentInputDTOWithNoArguments() {
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO();

        assertNotNull(paymentInputDTO);
    }

    @Test
    void createPaymentInputDTOWithAllArguments() {
        double amount = 12.67;
        int currency = 3;
        String description = "Phone bill";
        String date = "12-09-2010 13:33";
        String destinationEntity = "Vodafone";
        Object categoryId = 2;
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(amount, currency, description, date, destinationEntity, categoryId);

        assertNotNull(paymentInputDTO);
    }

    @Test
    void ensureDTOAttributesAreValid() {
        double amount = 12.67;
        int currency = 3;
        String description = "Phone bill";
        String date = "12-09-2010 13:33";
        String destinationEntity = "Vodafone";
        Object categoryId = 2;
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(amount, currency, description, date, destinationEntity, categoryId);

        double resultAmount = paymentInputDTO.getAmount();
        int resultCurrency = paymentInputDTO.getCurrency();
        String resultDescription = paymentInputDTO.getDescription();
        String resultDate = paymentInputDTO.getDate();
        String resultDestinationEntity = paymentInputDTO.getDestinationEntity();
        Object resultCategoryId = paymentInputDTO.getCategoryId();

        assertNotNull(paymentInputDTO);
        assertEquals(amount, resultAmount);
        assertEquals(currency, resultCurrency);
        assertEquals(description, resultDescription);
        assertEquals(date, resultDate);
        assertEquals(categoryId, resultCategoryId);
        assertEquals(destinationEntity, resultDestinationEntity);
    }

    @Test
    void ensureDTOAttributesAreSettable() {
        double amount = 12.67;
        int currency = 3;
        String description = "Phone bill";
        String date = "12-09-2010 13:33";
        String destinationEntity = "Vodafone";
        Object categoryId = 2;
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO();

        paymentInputDTO.setAmount(amount);
        paymentInputDTO.setCurrency(currency);
        paymentInputDTO.setDescription(description);
        paymentInputDTO.setDate(date);
        paymentInputDTO.setDestinationEntity(destinationEntity);
        paymentInputDTO.setCategoryId(categoryId);

        assertNotNull(paymentInputDTO);
        assertEquals(amount, paymentInputDTO.getAmount());
        assertEquals(currency, paymentInputDTO.getCurrency());
        assertEquals(description, paymentInputDTO.getDescription());
        assertEquals(date, paymentInputDTO.getDate());
        assertEquals(categoryId, paymentInputDTO.getCategoryId());
        assertEquals(destinationEntity, paymentInputDTO.getDestinationEntity());
    }
}
