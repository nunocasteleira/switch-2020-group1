package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TransactionIdTest {

    @Test
    void ensureIdIsEqual() {
        long expected = new Random().nextInt();
        TransactionId transactionId = new TransactionId(expected);
        long result = transactionId.getTransactionIdValue();

        assertEquals(expected, result);
    }

    @Test
    void ensureTransactionIdIsEqual() {
        long id = new Random().nextInt();

        TransactionId transactionId = new TransactionId(id);

        assertTrue(transactionId.equals(transactionId));
    }

    @Test
    void ensureTransactionIdIsNotEqual() {
        int id = new Random().nextInt();

        TransactionId aTransactionId = new TransactionId(id);
        TransactionId otherTransactionId = null;

        assertFalse(aTransactionId.equals(otherTransactionId));
    }

    @Test
    void ensureTransactionIdsAreEqual() {
        int id = new Random().nextInt();
        //noinspection UnnecessaryLocalVariable
        int sameId = id;

        TransactionId aTransactionId = new TransactionId(id);
        TransactionId sameTransactionId = new TransactionId(sameId);

        assertEquals(aTransactionId, sameTransactionId);
    }

    @Test
    void ensureTransactionIdsAreNotEqual() {
        int id = new Random().nextInt();
        int otherId = new Random().nextInt();

        TransactionId aTransactionId = new TransactionId(id);
        TransactionId otherTransactionId = new TransactionId(otherId);

        assertNotEquals(aTransactionId, otherTransactionId);
    }


    @Test
    void ensureHashCodeEquals() {

        int id = new Random().nextInt();
        int sameId = id;

        TransactionId aTransactionId = new TransactionId(id);
        TransactionId sameTransactionId = new TransactionId(sameId);

        assertEquals(aTransactionId.hashCode(), sameTransactionId.hashCode());
    }

    @Test
    void ensureHashCodeNotEquals() {

        int id = new Random().nextInt();
        int otherId = new Random().nextInt();

        TransactionId aTransactionId = new TransactionId(id);
        TransactionId otherTransactionId = new TransactionId(otherId);

        assertNotEquals(aTransactionId.hashCode(), otherTransactionId.hashCode());
    }

    @Test
    void ensureGetIdIsNotEqual() {
        int id = new Random().nextInt();
        int randomId = new Random().nextInt();
        Object result;

        TransactionId aTransactionId = new TransactionId(randomId);
        result = aTransactionId.getTransactionIdValue();

        assertNotEquals(id, result);
    }

    @Test
    void ensureTransactionIdIsNotEqualToCategoryId() {
        int id = new Random().nextInt();

        TransactionId aTransactionId = new TransactionId(id);
        CategoryId aCategoryId = new CategoryId(4);

        assertFalse(aTransactionId.equals(aCategoryId));
    }
}
