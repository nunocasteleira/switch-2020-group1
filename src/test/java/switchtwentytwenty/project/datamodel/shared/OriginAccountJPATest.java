package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class OriginAccountJPATest {
    @ParameterizedTest
    @ValueSource(ints = {1, 100, Integer.MAX_VALUE})
    void testEquals(int id) {
        OriginAccountIdJPA originAccountIdJPA = new OriginAccountIdJPA(id);
        //noinspection UnnecessaryLocalVariable
        OriginAccountIdJPA originAccountIdJPASame = originAccountIdJPA;
        OriginAccountIdJPA originAccountIdOther = new OriginAccountIdJPA(id);
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("Candy");
        OriginAccountIdJPA originAccountIdNull = null;
        OriginAccountIdJPA originAccountIdDifferent = new OriginAccountIdJPA(50);

        assertEquals(originAccountIdJPA, originAccountIdJPASame);
        assertSame(originAccountIdJPA, originAccountIdJPASame);
        assertEquals(originAccountIdJPA.hashCode(), originAccountIdJPASame.hashCode());
        assertEquals(originAccountIdJPA, originAccountIdOther);
        assertNotSame(originAccountIdJPA, originAccountIdOther);
        assertEquals(originAccountIdJPA.hashCode(), originAccountIdOther.hashCode());
        assertNotEquals(0, originAccountIdJPA.hashCode());
        assertNotEquals(originAccountIdJPA, originAccountIdNull);
        assertNotEquals(originAccountIdJPA, transactionDateJPA);
        assertNotEquals(originAccountIdJPA.hashCode(), transactionDateJPA.hashCode());
        assertEquals(originAccountIdJPA, originAccountIdJPA);
        assertSame(originAccountIdJPA, originAccountIdJPA);
        assertNotEquals(originAccountIdJPA, id);
        assertNotEquals(originAccountIdJPA, originAccountIdDifferent);
    }

    @Test
    void noArgsConstructor() {
        OriginAccountIdJPA originAccountIdJPA = new OriginAccountIdJPA();

        assertNotNull(originAccountIdJPA);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 100, Integer.MAX_VALUE})
    void getAccountIdNumber(int expected) {
        OriginAccountIdJPA originAccountIdJPA = new OriginAccountIdJPA(expected);

        long result = originAccountIdJPA.getAccountIdNumber();

        assertEquals(expected, result);
    }
}
