package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDateTest {

    @Test
    void createTransactionDateSuccessfullyWithSameDate() {
        LocalDateTime expectedDate = LocalDateTime.of(2010, 9, 12, 14, 34);

        TransactionDate resultDate = new TransactionDate("12/09/2010 14:34");

        assertNotNull(resultDate);
        assertEquals(expectedDate, resultDate.getDate());
    }

    @Test
    void createTransactionDateInvalidDifferentDate() {
        LocalDateTime expectedDate = LocalDateTime.of(2010, 9, 12, 14, 34);

        TransactionDate resultDate = new TransactionDate("12/09/2010 13:35");

        assertNotNull(resultDate);
        assertNotEquals(expectedDate, resultDate.getDate());
    }

    @Test
    void testEqualsWithDifferentObjects() {
        TransactionDate transactionDate = new TransactionDate("12/09/2010 13:34");
        TransactionDate otherTransactionDate = new TransactionDate("12/09/2010 13:34");
        TransactionDate nullTransactionDate = null;
        PersonName personName = new PersonName("some");

        assertNotSame(transactionDate, otherTransactionDate);
        assertEquals(transactionDate, otherTransactionDate);
        assertNotEquals(transactionDate, nullTransactionDate);
        assertNotEquals(transactionDate, personName);
    }

    @Test
    void testEqualsWithSameObjects() {
        TransactionDate transactionDate = new TransactionDate("12/09/2010 13:34");
        //noinspection UnnecessaryLocalVariable
        TransactionDate otherTransactionDate = transactionDate;

        assertSame(transactionDate, otherTransactionDate);
        assertEquals(transactionDate, otherTransactionDate);
    }

    @Test
    void testEqualsWithDifferentObjectsType() {
        String emailAddress = "tito@gmail.com";
        TransactionDate transactionDate = new TransactionDate("12/09/2010 13:34");
        Email email = new Email(emailAddress);

        assertNotEquals(transactionDate, email);
    }

    @Test
    void testEqualsWithNotEqualObjects() {
        LocalDateTime date = LocalDateTime.of(2020, 3, 2, 13, 34);
        TransactionDate transactionDate = new TransactionDate("02/03/2020 13:32");

        assertNotEquals(date, transactionDate.getDate());
    }

    @Test
    void testHashCode() {
        TransactionDate transactionDate = new TransactionDate("02/03/2020 13:32");
        TransactionDate otherTransactionDate = new TransactionDate("02/03/2020 13:32");

        assertNotSame(transactionDate, otherTransactionDate);
        assertEquals(transactionDate.hashCode(), otherTransactionDate.hashCode());
        assertNotEquals(0, transactionDate.hashCode());
    }

    @Test
    void testHashCodeFalse() {
        LocalDateTime date = LocalDateTime.of(2020, 3, 2, 12, 34);
        TransactionDate transactionDate = new TransactionDate("12/09/2010 13:34");

        assertNotEquals(date.hashCode(), transactionDate.getDate().hashCode());
    }

    @Test
    void toStringTest() {
        String expected = "02/03/2020 12:34";
        TransactionDate transactionDate = new TransactionDate(expected);

        String result = transactionDate.toString();

        assertEquals(expected, result);
    }

    @Test
    void equalsSameDateDifferentObjects() {
        String date = "02/03/2020 12:34";
        TransactionDate transactionDate = new TransactionDate(date);
        TransactionDate otherTransactionDate = new TransactionDate(date);

        assertNotSame(transactionDate, otherTransactionDate);
        assertEquals(transactionDate, otherTransactionDate);
    }

    @Test
    void equalsDifferentDateDifferentObjects() {
        String firstDate = "02/03/2020 12:55";
        String secondDate = "03/03/2020 12:55";
        TransactionDate transactionDate = new TransactionDate(firstDate);
        TransactionDate otherTransactionDate = new TransactionDate(secondDate);

        assertNotSame(transactionDate, otherTransactionDate);
        assertNotEquals(transactionDate, otherTransactionDate);
    }

    @Test
    void isAfter() {
        String originalDate = "02/03/2020 12:55";
        String afterDate = "03/03/2020 12:55";
        TransactionDate transactionDate = new TransactionDate(originalDate);
        TransactionDate afterTransactionDate = new TransactionDate(afterDate);

        boolean result = afterTransactionDate.isAfter(transactionDate);

        assertTrue(result);
    }

    @Test
    void isBefore() {
        String originalDate = "02/03/2020 12:55";
        String beforeDate = "03/03/2020 12:55";
        TransactionDate transactionDate = new TransactionDate(originalDate);
        TransactionDate beforeTransactionDate = new TransactionDate(beforeDate);

        boolean result = transactionDate.isBefore(beforeTransactionDate);

        assertTrue(result);
    }

    @Test
    void isAfterSameDate() {
        String originalDate = "02/03/2020 12:55";
        String afterDate = "02/03/2020 12:55";
        TransactionDate transactionDate = new TransactionDate(originalDate);
        TransactionDate afterTransactionDate = new TransactionDate(afterDate);

        boolean result = afterTransactionDate.isAfter(transactionDate);

        assertTrue(result);
    }

    @Test
    void isNotAfterDifferentDate() {
        String originalDate = "02/03/2020 12:55";
        String notAfterDate = "01/03/2020 12:55";
        TransactionDate transactionDate = new TransactionDate(originalDate);
        TransactionDate afterTransactionDate = new TransactionDate(notAfterDate);

        boolean result = afterTransactionDate.isAfter(transactionDate);

        assertFalse(result);
    }

    @Test
    void isBeforeSameDate() {
        String originalDate = "02/03/2020 12:55";
        String beforeDate = "02/03/2020 12:55";
        TransactionDate transactionDate = new TransactionDate(originalDate);
        TransactionDate beforeTransactionDate = new TransactionDate(beforeDate);

        boolean result = transactionDate.isBefore(beforeTransactionDate);

        assertTrue(result);
    }

    @Test
    void isNotAfter() {
        String originalDate = "02/03/2020 12:55";
        String notAfterDate = "02/03/2019 12:54";
        TransactionDate transactionDate = new TransactionDate(originalDate);
        TransactionDate afterTransactionDate = new TransactionDate(notAfterDate);

        boolean result = afterTransactionDate.isAfter(transactionDate);

        assertFalse(result);
    }

    @Test
    void isNotBefore() {
        String originalDate = "02/03/2020 12:55";
        String notBeforeDate = "02/03/2021 12:55";
        TransactionDate transactionDate = new TransactionDate(originalDate);
        TransactionDate notBeforeTransactionDate = new TransactionDate(notBeforeDate);

        boolean result = notBeforeTransactionDate.isBefore(transactionDate);

        assertFalse(result);
    }
}

