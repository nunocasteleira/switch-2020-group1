package switchtwentytwenty.project.dto.transaction;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentVOsTest {

    @Test
    void createPaymentVOsWithNoArguments() {
        PaymentVOs paymentVOs = new PaymentVOs();

        assertNotNull(paymentVOs);
    }

    @Test
    void createPaymentVOsWithAllArguments() {
        long accountIdValue = 4;
        AccountId accountId = new AccountId(accountIdValue);
        double amountValue = -12.67;
        TransactionAmount transactionAmount = new TransactionAmount(amountValue, Currency.EUR);
        String descriptionValue = "Phone bill";
        Description description = new Description(descriptionValue);
        String dateValue = "12/09/2010 13:33";
        TransactionDate transactionDate = new TransactionDate(dateValue);
        String destinationEntityValue = "Vodafone";
        Description destinationEntity = new Description(destinationEntityValue);
        Object categoryIdValue = 2;
        CategoryId categoryId = new CategoryId(categoryIdValue);

        PaymentVOs paymentVOs = new PaymentVOs(accountId, transactionAmount, description, transactionDate, destinationEntity, categoryId);

        assertNotNull(paymentVOs);
    }

    @Test
    void ensureDTOAttributesAreValid() {
        long accountIdValue = 4;
        AccountId accountId = new AccountId(accountIdValue);
        double amountValue = -12.67;
        TransactionAmount transactionAmount = new TransactionAmount(amountValue, Currency.EUR);
        String descriptionValue = "Phone bill";
        Description description = new Description(descriptionValue);
        String dateValue = "12/09/2010 13:33";
        TransactionDate transactionDate = new TransactionDate(dateValue);
        String destinationEntityValue = "Vodafone";
        Description destinationEntity = new Description(destinationEntityValue);
        Object categoryIdValue = 2;
        CategoryId categoryId = new CategoryId(categoryIdValue);

        PaymentVOs paymentVOs = new PaymentVOs(accountId, transactionAmount, description, transactionDate, destinationEntity, categoryId);

        AccountId resultAccountId = paymentVOs.getAccountId();
        TransactionAmount resultAmount = paymentVOs.getAmount();
        Description resultDescription = paymentVOs.getDescription();
        TransactionDate resultDate = paymentVOs.getDate();
        Description resultDestinationEntity = paymentVOs.getDestinationEntity();
        CategoryId resultCategoryId = paymentVOs.getCategoryId();

        assertNotNull(paymentVOs);
        assertEquals(accountId, resultAccountId);
        assertEquals(transactionAmount, resultAmount);
        assertEquals(description, resultDescription);
        assertEquals(transactionDate, resultDate);
        assertEquals(categoryId, resultCategoryId);
        assertEquals(destinationEntity, resultDestinationEntity);
    }

    @Test
    void ensureDTOAttributesAreSettable() {
        long accountIdValue = 4;
        AccountId accountId = new AccountId(accountIdValue);
        double amountValue = -12.67;
        TransactionAmount transactionAmount = new TransactionAmount(amountValue, Currency.EUR);
        String descriptionValue = "Phone bill";
        Description description = new Description(descriptionValue);
        String dateValue = "12/09/2010 13:33";
        TransactionDate transactionDate = new TransactionDate(dateValue);
        String destinationEntityValue = "Vodafone";
        Description destinationEntity = new Description(destinationEntityValue);
        Object categoryIdValue = 2;
        CategoryId categoryId = new CategoryId(categoryIdValue);

        PaymentVOs paymentVOs = new PaymentVOs();

        paymentVOs.setAccountId(accountId);
        paymentVOs.setAccountId(accountId);
        paymentVOs.setAmount(transactionAmount);
        paymentVOs.setDescription(description);
        paymentVOs.setDate(transactionDate);
        paymentVOs.setDestinationEntity(destinationEntity);
        paymentVOs.setCategoryId(categoryId);

        assertNotNull(paymentVOs);
        assertEquals(accountId, paymentVOs.getAccountId());
        assertEquals(transactionAmount, paymentVOs.getAmount());
        assertEquals(description, paymentVOs.getDescription());
        assertEquals(transactionDate, paymentVOs.getDate());
        assertEquals(categoryId, paymentVOs.getCategoryId());
        assertEquals(destinationEntity, paymentVOs.getDestinationEntity());
    }


    @Test
    void equalsAndHashCode() {
        long accountIdValue = 4;
        AccountId accountId = new AccountId(accountIdValue);
        double amountValue = -12.67;
        TransactionAmount transactionAmount = new TransactionAmount(amountValue, Currency.EUR);
        String descriptionValue = "Phone bill";
        Description description = new Description(descriptionValue);
        String dateValue = "12/09/2010 13:33";
        TransactionDate transactionDate = new TransactionDate(dateValue);
        String destinationEntityValue = "Vodafone";
        Description destinationEntity = new Description(destinationEntityValue);
        Object categoryIdValue = 2;
        CategoryId categoryId = new CategoryId(categoryIdValue);

        AccountId accountId2 = new AccountId(5);
        TransactionAmount amount2 = new TransactionAmount(-12.67, Currency.BRL);
        Description description2 = new Description("Electricity bill");
        CategoryId categoryId2 = new CategoryId(5);

        //act
        PaymentVOs paymentVOs = new PaymentVOs(accountId, transactionAmount, description, transactionDate, destinationEntity, categoryId);
        PaymentVOs paymentVOsEqual = paymentVOs;
        PaymentVOs paymentVOsNull = null;
        PaymentVOs paymentVOsSame = new PaymentVOs(accountId, transactionAmount, description, transactionDate, destinationEntity, categoryId);
        PaymentVOs paymentVOsDifferentCategoryId = new PaymentVOs(accountId, transactionAmount, description, transactionDate, destinationEntity, categoryId2);
        PaymentVOs paymentVOsDifferentAccountId = new PaymentVOs(accountId2, transactionAmount, description, transactionDate, destinationEntity, categoryId);
        PaymentVOs paymentVOsDifferentAmount = new PaymentVOs(accountId, amount2, description, transactionDate, destinationEntity, categoryId);
        PaymentVOs paymentVOsDifferentDescription = new PaymentVOs(accountId, transactionAmount, description2, transactionDate, destinationEntity, categoryId);
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(amountValue, 2, descriptionValue, dateValue, destinationEntityValue, categoryIdValue);

        //assert
        assertEquals(paymentVOs, paymentVOsSame);
        assertNotSame(paymentVOs, paymentVOsSame);
        assertEquals(paymentVOs, paymentVOsEqual);
        assertEquals(paymentVOs.hashCode(), paymentVOsSame.hashCode());
        assertNotEquals(paymentVOs, paymentVOsNull);
        assertFalse(paymentVOs.equals(accountId));
        assertFalse(paymentVOs.equals(transactionAmount));
        assertNotEquals(paymentVOs, paymentVOsDifferentCategoryId);
        assertNotEquals(paymentVOs, paymentVOsDifferentAccountId);
        assertNotEquals(paymentVOs, paymentVOsDifferentAmount);
        assertNotEquals(paymentVOs, paymentVOsDifferentDescription);
        assertFalse(paymentVOs.equals(paymentVOsDifferentCategoryId));
        assertFalse(paymentVOs.equals(paymentVOsDifferentAccountId));
        assertFalse(paymentVOs.equals(paymentVOsDifferentAmount));
        assertFalse(paymentVOs.equals(paymentVOsDifferentDescription));
        assertNotEquals(paymentVOs.hashCode(), paymentVOsDifferentCategoryId.hashCode());
        assertNotEquals(paymentVOs.hashCode(), paymentVOsDifferentAccountId.hashCode());
        assertNotEquals(paymentVOs.hashCode(), paymentVOsDifferentAmount.hashCode());
        assertNotEquals(paymentVOs.hashCode(), paymentVOsDifferentDescription.hashCode());
        assertNotEquals(0, paymentVOs.hashCode());
        assertNotEquals(paymentVOs, accountId2);
        assertNotEquals(paymentVOs, amount2);
        assertNotEquals(paymentVOs, paymentInputDTO);
        assertFalse(paymentVOs.equals(paymentInputDTO));
    }

}
