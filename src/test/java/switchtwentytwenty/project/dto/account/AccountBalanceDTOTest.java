package switchtwentytwenty.project.dto.account;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AccountBalanceDTOTest {

    @Test
    void getBalanceSuccessfully() {
        //arrange

        Balance balance = new Balance(20);

        //Act
        AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO(balance);

        Balance expected = accountBalanceDTO.getAccountBalance();

        AccountBalanceDTO expectedDTO = new AccountBalanceDTO(expected);

        //Assert
        assertNotNull(accountBalanceDTO);
        assertEquals(expectedDTO, accountBalanceDTO);
    }


    @Test
    void testEquals() {
        //Arrange
        Balance balance = new Balance(20);

        Balance balance1 = new Balance(21);

        //Act
        AccountBalanceDTO accountListDTO = new AccountBalanceDTO(balance);
        AccountBalanceDTO accountListDTOSame = accountListDTO;
        AccountBalanceDTO accountListDTOOther = new AccountBalanceDTO(balance);
        AccountBalanceDTO accountListDTODiff = new AccountBalanceDTO(balance1);


        //Assert
        assertNotNull(accountListDTO);
        assertEquals(accountListDTO, accountListDTOSame);
        assertSame(accountListDTO, accountListDTOSame);
        assertEquals(accountListDTO.hashCode(), accountListDTOSame.hashCode());
        assertNotEquals(0, accountListDTO.hashCode());
        assertEquals(accountListDTO, accountListDTOOther);
        assertNotSame(accountListDTO, accountListDTOOther);
        assertEquals(accountListDTO.hashCode(), accountListDTOOther.hashCode());
        assertNotEquals(accountListDTO, balance);
        assertNotEquals(accountListDTO, balance1);
        assertNotEquals(accountListDTO,accountListDTODiff);
    }
}
