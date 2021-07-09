package switchtwentytwenty.project.dto.account;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.shared.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountBalanceMapperTest {

    AccountBalanceMapper mapper = new AccountBalanceMapper();

    @Test
    void toDTOTest() {
        //Arrange
        Balance balance = new Balance(20);

        //Act
        AccountBalanceDTO accountBalanceDTO = mapper.toDTO(balance);

        //Assert
        assertNotNull(accountBalanceDTO);
    }

    @Test
    void toDTONullCashAccountTest() {

        //Assert
        assertThrows(ObjectDoesNotExistException.class,
                () -> mapper.toDTO(null));
    }
}
