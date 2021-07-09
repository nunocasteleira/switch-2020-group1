package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TransactionDateJPATest {

    @Test
    void noArgsConstructor() {
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA();

        assertNotNull(transactionDateJPA);
    }

    @Test
    void testEquals() {
        String date = "01/02/2020";
        String otherDate = "02/02/2020";
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA(date);
        //noinspection UnnecessaryLocalVariable
        TransactionDateJPA transactionDateJPASame = transactionDateJPA;
        TransactionDateJPA transactionDateJPAOther = new TransactionDateJPA(date);
        TransactionDateJPA transactionDateJPADifferent = new TransactionDateJPA(otherDate);
        TransactionDateJPA nullTransactionDateJPA = null;
        OriginAccountIdJPA originAccountIdJPA = new OriginAccountIdJPA(5);

        assertSame(transactionDateJPA, transactionDateJPASame);
        assertEquals(transactionDateJPA, transactionDateJPASame);
        assertEquals(transactionDateJPA.hashCode(), transactionDateJPASame.hashCode());
        assertNotSame(transactionDateJPA, transactionDateJPAOther);
        assertEquals(transactionDateJPA, transactionDateJPAOther);
        assertEquals(transactionDateJPA.hashCode(), transactionDateJPAOther.hashCode());
        assertNotSame(transactionDateJPA, transactionDateJPADifferent);
        assertNotEquals(transactionDateJPA, transactionDateJPADifferent);
        assertNotEquals(transactionDateJPA.hashCode(), transactionDateJPADifferent.hashCode());
        assertNotEquals(0, transactionDateJPA.hashCode());
        assertNotEquals(transactionDateJPA, date);
        assertNotEquals(transactionDateJPA, nullTransactionDateJPA);
        assertNotEquals(transactionDateJPA, originAccountIdJPA);
    }

    @Test
    void getDate() {
        String expected = "01/02/2020 12:24";
        TransactionDateJPA transactionDateJPA = new TransactionDateJPA(expected);

        String result = transactionDateJPA.getDate();

        assertEquals(expected, result);
    }
}
