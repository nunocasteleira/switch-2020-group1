package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DestinationAccountIdJPATest {

    @Test
    void noArgsConstructor() {
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA();

        assertNotNull(destinationAccountIdJPA);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 100, Integer.MAX_VALUE})
    void getDestinationAccountIdNumber(int expected) {
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(expected);

        long result = destinationAccountIdJPA.getDestinationAccountIdNumber();

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 100, Integer.MAX_VALUE})
    void testEquals(int id) {
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(id);
        DestinationAccountIdJPA destinationAccountIdSame = destinationAccountIdJPA;
        DestinationAccountIdJPA destinationAccountIdJPAOther = new DestinationAccountIdJPA(id);
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA("Candy");
        DestinationAccountIdJPA destinationAccountIdNull = null;
        DestinationAccountIdJPA destinationAccountIdDifferent = new DestinationAccountIdJPA(50);

        assertEquals(destinationAccountIdJPA, destinationAccountIdSame);
        assertSame(destinationAccountIdJPA, destinationAccountIdSame);
        assertEquals(destinationAccountIdJPA.hashCode(), destinationAccountIdSame.hashCode());
        assertEquals(destinationAccountIdJPA, destinationAccountIdJPAOther);
        assertNotSame(destinationAccountIdJPA, destinationAccountIdJPAOther);
        assertEquals(destinationAccountIdJPA.hashCode(), destinationAccountIdJPAOther.hashCode());
        assertNotEquals(0, destinationAccountIdJPA.hashCode());
        assertNotEquals(destinationAccountIdJPA, destinationAccountIdNull);
        assertNotEquals(destinationAccountIdJPA, transactionDateJPA);
        assertNotEquals(destinationAccountIdJPA.hashCode(), transactionDateJPA.hashCode());
        assertEquals(destinationAccountIdJPA, destinationAccountIdJPA);
        assertSame(destinationAccountIdJPA, destinationAccountIdJPA);
        assertNotEquals(destinationAccountIdJPA, id);
        assertNotEquals(destinationAccountIdJPA, destinationAccountIdDifferent);
    }
}