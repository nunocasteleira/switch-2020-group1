package switchtwentytwenty.project.domain.domainservices;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.domain.model.transaction.Payment;
import switchtwentytwenty.project.domain.model.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDomainServiceTest {

    @Test
    void getTransactionsBetweenDates() {
        List<Transaction> transactionList = new ArrayList<>();
        AccountId accountId = new AccountId(2);
        TransactionAmount amount = new TransactionAmount(-12.23, Currency.BRL);
        TransactionId transactionId = new TransactionId(1);
        Description description = new Description("Phone bill");
        TransactionDate date = new TransactionDate("20/06/2021 20:06");
        CategoryId categoryId = new CategoryId(3);
        Description destinationEntity = new Description("Vodafone");
        Payment payment = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(date)
                .withDestinationEntity(destinationEntity)
                .withCategoryId(categoryId)
                .withTransactionId(transactionId)
                .build();

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

        transactionList.add(payment);
        transactionList.add(payment1);
        transactionList.add(payment2);

        String startDate = "01/01/2009";
        String endDate = "01/01/2011";

        List<Transaction> expected = new ArrayList<>();
        expected.add(payment1);
        expected.add(payment2);

        List<Transaction> result = TransactionDomainService.getTransactionsBetweenDates(transactionList, startDate, endDate);
        int expectedLength = 2;
        assertEquals(expectedLength, result.size());
        assertEquals(expected, result);
    }
}