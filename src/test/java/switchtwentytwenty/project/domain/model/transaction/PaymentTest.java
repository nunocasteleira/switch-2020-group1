package switchtwentytwenty.project.domain.model.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import switchtwentytwenty.project.datamodel.shared.OriginAccountIdJPA;
import switchtwentytwenty.project.domain.model.shared.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    AccountId accountId;
    TransactionAmount amount;
    TransactionId transactionId;
    Description description;
    TransactionDate date;
    CategoryId categoryId;
    Description destinationEntity;

    @BeforeEach
    void initialize() {
        accountId = new AccountId(2);
        amount = new TransactionAmount(-12.23, Currency.BRL);
        transactionId = new TransactionId(1);
        description = new Description("Phone bill");
        date = new TransactionDate("20/06/2021 20:06");
        categoryId = new CategoryId(3);
        destinationEntity = new Description("Vodafone");
    }

    @Test
    void paymentIsCreatedSuccessfullyWithTransactionId() {
        Payment payment = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withDestinationEntity(destinationEntity)
                .withCategoryId(categoryId)
                .withTransactionId(transactionId)
                .build();

        assertNotNull(payment);
    }

    @Test
    void paymentIsCreatedSuccessfullyWithoutTransactionId() {
        Payment payment = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withDestinationEntity(destinationEntity)
                .withCategoryId(categoryId)
                .withTransactionId()
                .build();

        assertNotNull(payment);
    }

    @Test
    void ensurePersonAttributesAreCorrect() {
        AccountId expectedAccountId = new AccountId(2);
        AccountId notExpectedAccountId = new AccountId(3);
        TransactionAmount expectedAmount = new TransactionAmount(-12.23, Currency.BRL);
        TransactionAmount notExpectedAmount = new TransactionAmount(-12.24, Currency.BRL);
        TransactionId expectedTransactionId = new TransactionId(1);
        TransactionId unexpectedTransactionId = new TransactionId(4);
        Description expectedDescription = new Description("Phone bill");
        Description unexpectedDescription = new Description("Shopping");
        TransactionDate expectedDate = new TransactionDate("20/06/2021 20:06");
        TransactionDate unexpectedDate = new TransactionDate("20/06/2020 20:06");
        CategoryId expectedCategoryId = new CategoryId(3);
        CategoryId unexpectedCategoryId = new CategoryId(6);
        Description expectedDestinationEntity = new Description("Vodafone");
        Description unexpectedDestinationEntity = new Description("Shop Z");

        Payment payment = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withDestinationEntity(destinationEntity)
                .withCategoryId(categoryId)
                .withTransactionId(transactionId)
                .build();

        AccountId resultAccountId = payment.getAccountId();
        TransactionAmount resultAmount = payment.getAmount();
        TransactionId resultTransactionId = payment.getTransactionId();
        Description resultDescription = payment.getDescription();
        TransactionDate resultDate= payment.getDate();
        CategoryId resultCategoryId = payment.getCategoryId();
        Description resultDestinationEntity = payment.getDestinationEntity();

        assertEquals(expectedAccountId, resultAccountId);
        assertEquals(expectedAmount, resultAmount);
        assertEquals(expectedTransactionId, resultTransactionId);
        assertEquals(expectedDescription, resultDescription);
        assertEquals(expectedDate, resultDate);
        assertEquals(expectedCategoryId, resultCategoryId);
        assertEquals(expectedDestinationEntity, resultDestinationEntity);

        assertNotEquals(notExpectedAmount, resultAmount);
        assertNotEquals(notExpectedAccountId, resultAccountId);
        assertNotEquals(unexpectedTransactionId, resultTransactionId);
        assertNotEquals(unexpectedDescription, resultDescription);
        assertNotEquals(unexpectedDate, resultDate);
        assertNotEquals(unexpectedCategoryId, resultCategoryId);
        assertNotEquals(unexpectedDestinationEntity, resultDestinationEntity);
    }

    @Test
    void hasId() {
        TransactionId paymentId = new TransactionId(1);
        Payment payment = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withDestinationEntity(destinationEntity)
                .withCategoryId(categoryId)
                .withTransactionId(paymentId)
                .build();

        boolean result = payment.hasId(paymentId);

        assertTrue(result);
    }

    @Test
    void setTransactionId() {
        TransactionId paymentId = new TransactionId(1);
        Payment payment = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withDestinationEntity(destinationEntity)
                .withCategoryId(categoryId)
                .withTransactionId()
                .build();

        payment.setTransactionId(paymentId);

        TransactionId resultId = payment.getTransactionId();

        assertEquals(paymentId, resultId);
    }

    @Test
    void testEquals() {
        AccountId accountId1 = new AccountId(3);
        TransactionAmount amount1 = new TransactionAmount(-25.78, Currency.GBP);
        Description description1 = new Description("Electricity bill");
        TransactionDate transactionDate1 = new TransactionDate("12/09/2010 14:00");
        CategoryId categoryId1 = new CategoryId(2);
        Description destinationEntity1 = new Description("EDP");
        TransactionId transactionId1 = new TransactionId(12);
        Payment payment1 = new Payment.Builder(accountId1, amount1)
                .withDescription(description1)
                .withTransactionDate(transactionDate1)
                .withDestinationEntity(destinationEntity1)
                .withCategoryId(categoryId1)
                .withTransactionId(transactionId1)
                .build();

        AccountId accountId2 = new AccountId(1);
        TransactionAmount amount2 = new TransactionAmount(-14.98, Currency.EUR);
        Description description2 = new Description("Water bill");
        TransactionDate transactionDate2 = new TransactionDate("13/08/2009 16:00");
        CategoryId categoryId2 = new CategoryId(4);
        Description destinationEntity2 = new Description("Indaqua");
        TransactionId transactionId2 = new TransactionId(12);

        Payment payment2 = new Payment.Builder(accountId2, amount2)
                .withDescription(description2)
                .withTransactionDate(transactionDate2)
                .withDestinationEntity(destinationEntity2)
                .withCategoryId(categoryId2)
                .withTransactionId(transactionId2)
                .build();

        //noinspection UnnecessaryLocalVariable
        Payment paymentJPA1a = payment1;
        Payment paymentJPA1b = new Payment.Builder(accountId1, amount1)
                .withDescription(description1)
                .withTransactionDate(transactionDate1)
                .withDestinationEntity(destinationEntity1)
                .withCategoryId(categoryId1)
                .withTransactionId(transactionId1)
                .build();

        assertEquals(payment1.getCategoryId(), paymentJPA1b.getCategoryId());
        assertEquals(payment1.isPayment(), paymentJPA1b.isPayment());
        assertEquals(payment1.getAccountId(), paymentJPA1b.getAccountId());
        assertEquals(payment1.getAmount(), paymentJPA1b.getAmount());
        assertEquals(payment1.getDate(), paymentJPA1b.getDate());
        assertEquals(payment1.getDescription(), paymentJPA1b.getDescription());
        assertEquals(payment1.getTransactionId(), paymentJPA1b.getTransactionId());
        assertEquals(payment1, paymentJPA1a);
        assertEquals(payment1, paymentJPA1b);
        assertSame(payment1, paymentJPA1a);
        assertNotSame(payment1, paymentJPA1b);
        assertEquals(payment1.hashCode(), paymentJPA1a.hashCode());
        assertNotEquals(0, payment2.hashCode());
        assertNotEquals(0, payment1.getTransactionId());
        assertEquals(payment1.getDestinationEntity(), paymentJPA1a.getDestinationEntity());
        assertEquals(payment1.hashCode(), paymentJPA1b.hashCode());
        assertEquals(payment1.getDestinationEntity(), paymentJPA1b.getDestinationEntity());
        assertNotEquals(payment1, payment2);
        assertNotEquals(payment1.hashCode(), payment2.hashCode());
        assertNotEquals(payment1.getDestinationEntity(), payment2.getDestinationEntity());
        assertNotEquals(0, payment1.hashCode());
        assertNotEquals(null, payment1);
        assertFalse(payment1.equals(null));
        assertFalse(payment1.equals(payment2));
        assertTrue(payment1.equals(paymentJPA1b));
    }

    @Test
    void isAfter() {
        AccountId accountId1 = new AccountId(3);
        TransactionAmount amount1 = new TransactionAmount(-25.78, Currency.GBP);
        Description description1 = new Description("Electricity bill");
        TransactionDate transactionDate1 = new TransactionDate("12/09/2010 14:00");
        CategoryId categoryId1 = new CategoryId(2);
        Description destinationEntity1 = new Description("EDP");
        TransactionId transactionId1 = new TransactionId(12);
        Payment payment1 = new Payment.Builder(accountId1, amount1)
                .withDescription(description1)
                .withTransactionDate(transactionDate1)
                .withDestinationEntity(destinationEntity1)
                .withCategoryId(categoryId1)
                .withTransactionId(transactionId1)
                .build();

        AccountId accountId2 = new AccountId(1);
        TransactionAmount amount2 = new TransactionAmount(-14.98, Currency.EUR);
        Description description2 = new Description("Water bill");
        TransactionDate transactionDate2 = new TransactionDate("13/08/2009 16:00");
        CategoryId categoryId2 = new CategoryId(4);
        Description destinationEntity2 = new Description("Indaqua");
        TransactionId transactionId2 = new TransactionId(12);

        Payment payment2 = new Payment.Builder(accountId2, amount2)
                .withDescription(description2)
                .withTransactionDate(transactionDate2)
                .withDestinationEntity(destinationEntity2)
                .withCategoryId(categoryId2)
                .withTransactionId(transactionId2)
                .build();

        boolean result = payment1.isAfter(payment2.getDate());

        assertTrue(result);
    }

    @Test
    void isBefore() {
        AccountId accountId1 = new AccountId(3);
        TransactionAmount amount1 = new TransactionAmount(-25.78, Currency.GBP);
        Description description1 = new Description("Electricity bill");
        TransactionDate transactionDate1 = new TransactionDate("12/09/2010 14:00");
        CategoryId categoryId1 = new CategoryId(2);
        Description destinationEntity1 = new Description("EDP");
        TransactionId transactionId1 = new TransactionId(12);
        Payment payment1 = new Payment.Builder(accountId1, amount1)
                .withDescription(description1)
                .withTransactionDate(transactionDate1)
                .withDestinationEntity(destinationEntity1)
                .withCategoryId(categoryId1)
                .withTransactionId(transactionId1)
                .build();

        AccountId accountId2 = new AccountId(1);
        TransactionAmount amount2 = new TransactionAmount(-14.98, Currency.EUR);
        Description description2 = new Description("Water bill");
        TransactionDate transactionDate2 = new TransactionDate("13/08/2009 16:00");
        CategoryId categoryId2 = new CategoryId(4);
        Description destinationEntity2 = new Description("Indaqua");
        TransactionId transactionId2 = new TransactionId(12);

        Payment payment2 = new Payment.Builder(accountId2, amount2)
                .withDescription(description2)
                .withTransactionDate(transactionDate2)
                .withDestinationEntity(destinationEntity2)
                .withCategoryId(categoryId2)
                .withTransactionId(transactionId2)
                .build();

        boolean result = payment2.isBefore(payment1.getDate());

        assertTrue(result);
    }

    @Test
    void isNotAfter() {
        AccountId accountId1 = new AccountId(3);
        TransactionAmount amount1 = new TransactionAmount(-25.78, Currency.GBP);
        Description description1 = new Description("Electricity bill");
        TransactionDate transactionDate1 = new TransactionDate("12/09/2010 14:00");
        CategoryId categoryId1 = new CategoryId(2);
        Description destinationEntity1 = new Description("EDP");
        TransactionId transactionId1 = new TransactionId(12);
        Payment payment1 = new Payment.Builder(accountId1, amount1)
                .withDescription(description1)
                .withTransactionDate(transactionDate1)
                .withDestinationEntity(destinationEntity1)
                .withCategoryId(categoryId1)
                .withTransactionId(transactionId1)
                .build();

        AccountId accountId2 = new AccountId(1);
        TransactionAmount amount2 = new TransactionAmount(-14.98, Currency.EUR);
        Description description2 = new Description("Water bill");
        TransactionDate transactionDate2 = new TransactionDate("13/08/2009 16:00");
        CategoryId categoryId2 = new CategoryId(4);
        Description destinationEntity2 = new Description("Indaqua");
        TransactionId transactionId2 = new TransactionId(12);

        Payment payment2 = new Payment.Builder(accountId2, amount2)
                .withDescription(description2)
                .withTransactionDate(transactionDate2)
                .withDestinationEntity(destinationEntity2)
                .withCategoryId(categoryId2)
                .withTransactionId(transactionId2)
                .build();

        boolean result = payment2.isAfter(payment1.getDate());

        assertFalse(result);
    }

    @Test
    void isNotBefore() {
        AccountId accountId1 = new AccountId(3);
        TransactionAmount amount1 = new TransactionAmount(-25.78, Currency.GBP);
        Description description1 = new Description("Electricity bill");
        TransactionDate transactionDate1 = new TransactionDate("12/09/2010 14:00");
        CategoryId categoryId1 = new CategoryId(2);
        Description destinationEntity1 = new Description("EDP");
        TransactionId transactionId1 = new TransactionId(12);
        Payment payment1 = new Payment.Builder(accountId1, amount1)
                .withDescription(description1)
                .withTransactionDate(transactionDate1)
                .withDestinationEntity(destinationEntity1)
                .withCategoryId(categoryId1)
                .withTransactionId(transactionId1)
                .build();

        AccountId accountId2 = new AccountId(1);
        TransactionAmount amount2 = new TransactionAmount(-14.98, Currency.EUR);
        Description description2 = new Description("Water bill");
        TransactionDate transactionDate2 = new TransactionDate("13/08/2009 16:00");
        CategoryId categoryId2 = new CategoryId(4);
        Description destinationEntity2 = new Description("Indaqua");
        TransactionId transactionId2 = new TransactionId(12);

        Payment payment2 = new Payment.Builder(accountId2, amount2)
                .withDescription(description2)
                .withTransactionDate(transactionDate2)
                .withDestinationEntity(destinationEntity2)
                .withCategoryId(categoryId2)
                .withTransactionId(transactionId2)
                .build();

        boolean result = payment1.isBefore(payment2.getDate());

        assertFalse(result);
    }

    @Test
    void TransferHasId(){
        Transfer transfer = new Transfer.TransferBuilder(new AccountId(1), new AccountId(2), new TransactionAmount(200, Currency.GBP))
                .withDescription(new Description("transfer"))
                .withTransactionDate(new TransactionDate("11/09/1999"))
                .withCategoryId(new CategoryId(1))
                .withTransactionId()
                .build();

        TransactionId transactionId = transfer.getTransactionId();

        boolean result = transfer.hasId(transactionId);

        assertTrue(result);
    }

    @Test
    void TransferHasDifferentId(){
        Transfer transfer = new Transfer.TransferBuilder(new AccountId(1), new AccountId(2), new TransactionAmount(200, Currency.GBP))
                .withDescription(new Description("transfer"))
                .withTransactionDate(new TransactionDate("11/09/1999"))
                .withCategoryId(new CategoryId(1))
                .withTransactionId()
                .build();

        TransactionId transactionId = new TransactionId(90);

        boolean result = transfer.hasId(transactionId);

        assertFalse(result);
    }
}
