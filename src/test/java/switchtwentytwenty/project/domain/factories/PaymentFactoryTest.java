package switchtwentytwenty.project.domain.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.domain.model.transaction.Payment;
import switchtwentytwenty.project.dto.transaction.PaymentVOs;

import static org.junit.jupiter.api.Assertions.*;

class PaymentFactoryTest {
    AccountId accountId;
    TransactionAmount amount;
    Description description;
    TransactionDate transactionDate;
    Description destinationEntity;
    CategoryId categoryId;
    PaymentVOs paymentVOs;

    @BeforeEach
    void initialize(){
        accountId = new AccountId(3);
        amount = new TransactionAmount(-245.66,Currency.BRL);
        description = new Description("Compras");
        transactionDate = new TransactionDate("12/09/2019 13:32");
        destinationEntity = new Description("Mercado");
        categoryId = new CategoryId(2);

        paymentVOs = new PaymentVOs(accountId, amount, description, transactionDate,destinationEntity, categoryId);
    }

    @Test
    void buildPayment() {
        Payment payment = PaymentFactory.buildPayment(paymentVOs);

        assertNotNull(payment);
    }

    @Test
    void ensurePaymentIsDoneWithTheRightVOs(){
        Payment expected = new Payment.Builder(accountId, amount)
                .withDescription(description)
                .withTransactionDate(transactionDate)
                .withDestinationEntity(destinationEntity)
                .withCategoryId(categoryId)
                .withTransactionId()
                .build();

        Payment resultPayment = PaymentFactory.buildPayment(paymentVOs);

        assertEquals(expected.getAmount(), resultPayment.getAmount());
        assertEquals(expected.getAccountId(), resultPayment.getAccountId());
        assertEquals(expected.getDescription(), resultPayment.getDescription());
        assertEquals(expected.getDate(), resultPayment.getDate());
        assertEquals(expected.getDestinationEntity(), resultPayment.getDestinationEntity());
        assertEquals(expected.getCategoryId(), resultPayment.getCategoryId());
        assertNotEquals(description.hashCode(), resultPayment.hashCode());
    }

    @Test
    void testNoArgsConstructor(){
        PaymentFactory paymentFactory = new PaymentFactory();

        assertNotNull(paymentFactory);
    }
}
