package switchtwentytwenty.project.dto.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransferInputDTOTest {

    @Test
    void verifyNoArgsConstructor() {
        TransferInputDTO transferInputDTO = new TransferInputDTO();

        assertNotNull(transferInputDTO);
    }

    @Test
    void testGetterAndSetter() {
        //arrange
        double amount = 20;
        int currency = 2;
        String description = "Jantar Mexicano";
        String date = "12/12/1212 12:12";
        int categoryId = 123456;
        long destinationId = 1;

        //act
        TransferInputDTO transferInputDTO = new TransferInputDTO(amount, currency, description, date, categoryId, destinationId);
        transferInputDTO.setAmount(amount);
        transferInputDTO.setCurrency(currency);
        transferInputDTO.setDescription(description);
        transferInputDTO.setDate(date);
        transferInputDTO.setCategoryId(categoryId);
        transferInputDTO.setDestinationAccountId(destinationId);

        //assert
        assertNotNull(transferInputDTO);
        assertEquals(amount, transferInputDTO.getAmount());
        assertEquals(currency, transferInputDTO.getCurrency());
        assertEquals(description, transferInputDTO.getDescription());
        assertEquals(date, transferInputDTO.getDate());
        assertEquals(categoryId, transferInputDTO.getCategoryId());
        assertEquals(destinationId, transferInputDTO.getDestinationAccountId());
    }
}