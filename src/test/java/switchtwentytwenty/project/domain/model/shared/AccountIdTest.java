package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountIdTest {

    @Test
    void createAccountIdSuccessfully() {
        int id = hashCode();

        AccountId result = new AccountId(id);

        assertNotNull(result);
        assertEquals(id, result.getAccountIdNumber());
    }

    @Test
    void failToCreateAccountIdWithNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> new AccountId(-50));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 10, 100, Integer.MAX_VALUE})
    void testEquals(int accountIdInt) {
        AccountId accountId = new AccountId(accountIdInt);
        AccountId accountIdSame = accountId;
        AccountId accountIdOther = new AccountId(accountIdInt);
        int differentId = 50;
        AccountId accountIdDifferent = new AccountId(differentId);

        assertEquals(accountIdSame, accountId);
        assertSame(accountIdSame, accountId);
        assertEquals(accountIdSame.hashCode(), accountId.hashCode());
        assertEquals(accountIdOther, accountId);
        assertNotSame(accountIdOther, accountId);
        assertEquals(accountIdOther.hashCode(), accountId.hashCode());
        assertNotEquals(accountId, accountIdInt);
        assertNotEquals(0, accountId.hashCode());
        assertNotSame(accountId, accountIdDifferent);
        assertNotEquals(accountId, accountIdDifferent);
        assertNotEquals(accountId.hashCode(), accountIdDifferent.hashCode());
    }
}