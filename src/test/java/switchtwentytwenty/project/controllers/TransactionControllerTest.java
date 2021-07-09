package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.applicationservices.implservices.TransactionService;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.dto.transaction.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("TransactionControllerTest")
class TransactionControllerTest {
    @Mock
    TransactionService transactionService;
    @Mock
    PaymentInputDTO paymentInputDTO;
    @Mock
    TransferInputDTO transferInputDTO;
    @InjectMocks
    TransactionController transactionController;

    @Test
    void paymentIsRegisteredFromFamilyCashAccountWithTheRightInformation() {
        //Arrange
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(1, 20, "GBP", "Electricity bill", "19/06/2021 12:54", "EDP", 2, "categoryName", 300);
        when(transactionService.registerPayment(paymentInputDTO, 1)).thenReturn(transactionOutputDTO);

        //Act
        ResponseEntity<Object> registeredPayment = transactionController.registerPayment(paymentInputDTO, 1);

        //Assert
        assertNotNull(registeredPayment);
        assertNotNull(registeredPayment.getBody());
        assertEquals(HttpStatus.CREATED, registeredPayment.getStatusCode());
    }

    @Test
    void paymentIsNotRegisteredIfAccountIdIsFromANonexistentAccount() {
        //Arrange
        when(transactionService.registerPayment(paymentInputDTO, 1)).thenThrow(ObjectDoesNotExistException.class);

        //Act
        ResponseEntity<Object> registeredPayment = transactionController.registerPayment(paymentInputDTO, 1);

        //Assert
        assertNotNull(registeredPayment);
        assertEquals(HttpStatus.BAD_REQUEST, registeredPayment.getStatusCode());
    }

    /*@Test
    void transfer_Successfully() {
        //arrange
        TransactionOutputDTO transferOutputDTO = new TransactionOutputDTO(1, 2, 20, "EUR", "Electricity bill", "19/06/2021 12:54", 2, "categoryName");
        when(transactionService.transfer(transferInputDTO, 1, 2)).thenReturn(transferOutputDTO);

        //act
        ResponseEntity<Object> result = transactionController.transfer(transferInputDTO, 2, 1);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void transfer_Invalid() {
        //arrange
        when(transactionService.transfer(transferInputDTO, 1, 2)).thenThrow(ObjectDoesNotExistException.class);

        //act
        ResponseEntity<Object> result = transactionController.transfer(transferInputDTO, 2, 1);

        //assert
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }*/

/*
    @Test
    void getAccountTransactionsBetweenDates() {
        long accountId = 1;
        String startDate = "01/01/2020";
        String endDate = "01/01/2021";
        DateRangeDTO dateRangeDTO = new DateRangeDTO(startDate, endDate);
        List<TransactionOutputDTO> transactionOutputDTOList = new ArrayList<>();
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(1, 20, "GBP", "Electricity bill", "19/06/2021 12:54", "EDP", 2, "categoryName", 300);
        TransactionOutputDTO transferOutputDTO = new TransactionOutputDTO(1, 2, 20, "EUR", "Electricity bill", "19/06/2021 12:54", 2, "categoryName");
        transactionOutputDTOList.add(transactionOutputDTO);
        transactionOutputDTOList.add(transferOutputDTO);
        TransactionListDTO transactionListDTO = new TransactionListDTO(transactionOutputDTOList);

        when(transactionService.getAccountTransactionsBetweenDates(accountId, dateRangeDTO))
                .thenReturn(transactionListDTO);
        ResponseEntity<Object> result = transactionController.getAccountTransactionsBetweenDates(accountId, startDate, endDate);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(transactionListDTO, result.getBody());
    }
*/

    @Test
    void getAccountTransactionsBetweenDatesUnsuccessfully() {
        long accountId = 1;
        String startDate = "01/01/2020";
        String endDate = "01/01/2021";
        DateRangeDTO dateRangeDTO = new DateRangeDTO(startDate, endDate);

        when(transactionService.getAccountTransactionsBetweenDates(accountId, dateRangeDTO)).thenThrow(NullPointerException.class);
        ResponseEntity<Object> result = transactionController.getAccountTransactionsBetweenDates(accountId, startDate, endDate);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}
