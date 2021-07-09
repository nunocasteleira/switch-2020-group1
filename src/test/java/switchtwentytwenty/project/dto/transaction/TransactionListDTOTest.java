package switchtwentytwenty.project.dto.transaction;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionListDTOTest {

    @Test
    void createTransactionListDTOSuccessfully() {
        long transactionIdOne = 123;
        double amountOne = 34.45;
        String currencyOne = "EUR";
        String descriptionOne = "Transaction";
        String dateOne = "12/02/2020 15:56";
        String categoryNameOne = "Groceries";
        TransactionOutputDTO transactionOutputDTOOne = new TransactionOutputDTO(transactionIdOne, amountOne, currencyOne, descriptionOne, dateOne, categoryNameOne);

        long transactionIdTwo = 321;
        double amountTwo = 34.46;
        String currencyTwo = "USD";
        String descriptionTwo = "Transaction Two";
        String dateTwo = "12/02/2021 15:56";
        String categoryNameTwo = "Shopping";
        TransactionOutputDTO transactionOutputDTOTwo = new TransactionOutputDTO(transactionIdTwo, amountTwo, currencyTwo, descriptionTwo, dateTwo, categoryNameTwo);
        List<TransactionOutputDTO> dtoList = new ArrayList<>();
        dtoList.add(transactionOutputDTOOne);
        dtoList.add(transactionOutputDTOTwo);

        TransactionListDTO transactionListDTO = new TransactionListDTO(dtoList);

        assertNotNull(transactionListDTO);
        assertEquals(dtoList, transactionListDTO.getTransactionList());
    }
}