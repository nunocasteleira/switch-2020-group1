package switchtwentytwenty.project.dto.transaction;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.AccountId;

import static org.junit.jupiter.api.Assertions.*;

class TransactionOutputDTOTest {

    @Test
    void testNoArgsConstructor() {
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO();

        assertNotNull(transactionOutputDTO);
    }

    @Test
    void testAllArgs() {
        //arrange
        long transactionId = 1;
        long accountId = 2;
        long destinationAccountId = 3;
        double amount = 20;
        String currency = "EUR";
        String description = "Jantar Mexicano";
        String date = "12/12/1212 12:12";
        String destinationEntity = "Jantar Mexicano";
        int categoryId = 123456;
        String category = "Jantares";
        double balance = -10;

        //act
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(transactionId, accountId, destinationAccountId, amount, currency, description, date, destinationEntity, categoryId, category, balance);
        transactionOutputDTO.setTransactionId(transactionId);
        transactionOutputDTO.setAccountId(accountId);
        transactionOutputDTO.setDestinationAccountId(destinationAccountId);
        transactionOutputDTO.setAmount(amount);
        transactionOutputDTO.setCurrency(currency);
        transactionOutputDTO.setDescription(description);
        transactionOutputDTO.setDate(date);
        transactionOutputDTO.setDestinationEntity(destinationEntity);
        transactionOutputDTO.setCategoryId(categoryId);
        transactionOutputDTO.setCategoryName(category);
        transactionOutputDTO.setBalance(balance);

        //assert
        assertNotNull(transactionOutputDTO);
        assertEquals(transactionId, transactionOutputDTO.getTransactionId());
        assertEquals(accountId, transactionOutputDTO.getAccountId());
        assertEquals(destinationAccountId, transactionOutputDTO.getDestinationAccountId());
        assertEquals(amount, transactionOutputDTO.getAmount());
        assertEquals(currency, transactionOutputDTO.getCurrency());
        assertEquals(description, transactionOutputDTO.getDescription());
        assertEquals(date, transactionOutputDTO.getDate());
        assertEquals(destinationEntity, transactionOutputDTO.getDestinationEntity());
        assertEquals(categoryId, transactionOutputDTO.getCategoryId());
        assertEquals(category, transactionOutputDTO.getCategoryName());
        assertEquals(balance, transactionOutputDTO.getBalance());
    }

    @Test
    void createPaymentOutputDTOWithAllArguments() {
        long accountId = 4;
        double amount = 12.67;
        String currency = "GBP";
        String description = "Phone bill";
        String date = "12-09-2010 13:33";
        String destinationEntity = "Vodafone";
        Object categoryId = 2;
        long transactionId = 1;
        String categoryName = "Bills";
        double balance = 200.50;
        TransactionOutputDTO paymentOutputDTO = new TransactionOutputDTO(transactionId, accountId, amount, currency, description, date, destinationEntity, categoryId, categoryName, balance);

        assertNotNull(paymentOutputDTO);
    }

    @Test
    void createPaymentOutputDTOWithoutTransactionId() {
        long accountId = 4;
        double amount = 12.67;
        String currency = "GBP";
        String description = "Phone bill";
        String date = "12-09-2010 13:33";
        String destinationEntity = "Vodafone";
        Object categoryId = 2;
        String categoryName = "Bills";
        double balance = 200.50;
        TransactionOutputDTO paymentOutputDTO = new TransactionOutputDTO(accountId, amount, currency, description, date, destinationEntity, categoryId, categoryName, balance);

        assertNotNull(paymentOutputDTO);
    }

    @Test
    void ensureDTOAttributesAreValid() {
        long accountId = 4;
        double amount = 12.67;
        String currency = "GBP";
        String description = "Phone bill";
        String date = "12-09-2010 13:33";
        String destinationEntity = "Vodafone";
        Object categoryId = 2;
        long transactionId = 1;
        String categoryName = "Bills";
        double balance = 200.50;
        TransactionOutputDTO paymentOutputDTO = new TransactionOutputDTO(transactionId, accountId, amount, currency, description, date, destinationEntity, categoryId, categoryName, balance);

        long resultAccountId = paymentOutputDTO.getAccountId();
        double resultAmount = paymentOutputDTO.getAmount();
        String resultCurrency = paymentOutputDTO.getCurrency();
        String resultDescription = paymentOutputDTO.getDescription();
        String resultDate = paymentOutputDTO.getDate();
        String resultDestinationEntity = paymentOutputDTO.getDestinationEntity();
        Object resultCategoryId = paymentOutputDTO.getCategoryId();
        long resultTransactionId = paymentOutputDTO.getTransactionId();

        assertNotNull(paymentOutputDTO);
        assertEquals(accountId, resultAccountId);
        assertEquals(amount, resultAmount);
        assertEquals(currency, resultCurrency);
        assertEquals(description, resultDescription);
        assertEquals(date, resultDate);
        assertEquals(categoryId, resultCategoryId);
        assertEquals(destinationEntity, resultDestinationEntity);
        assertEquals(transactionId, resultTransactionId);
    }

    @Test
    void ensureDTOAttributesAreSettable() {
        long accountId = 4;
        double amount = 12.67;
        String currency = "GBP";
        String description = "Phone bill";
        String date = "12-09-2010 13:33";
        String destinationEntity = "Vodafone";
        Object categoryId = 2;
        long transactionId = 1;
        String categoryName = "Category";
        double balance = 124.32;

        TransactionOutputDTO paymentOutputDTO = new TransactionOutputDTO();

        paymentOutputDTO.setAccountId(accountId);
        paymentOutputDTO.setAmount(amount);
        paymentOutputDTO.setCurrency(currency);
        paymentOutputDTO.setDescription(description);
        paymentOutputDTO.setDate(date);
        paymentOutputDTO.setDestinationEntity(destinationEntity);
        paymentOutputDTO.setCategoryId(categoryId);
        paymentOutputDTO.setTransactionId(transactionId);
        paymentOutputDTO.setCategoryName(categoryName);
        paymentOutputDTO.setBalance(balance);

        assertNotNull(paymentOutputDTO);
        assertEquals(accountId, paymentOutputDTO.getAccountId());
        assertEquals(amount, paymentOutputDTO.getAmount());
        assertEquals(currency, paymentOutputDTO.getCurrency());
        assertEquals(description, paymentOutputDTO.getDescription());
        assertEquals(date, paymentOutputDTO.getDate());
        assertEquals(categoryId, paymentOutputDTO.getCategoryId());
        assertEquals(destinationEntity, paymentOutputDTO.getDestinationEntity());
        assertEquals(categoryName, paymentOutputDTO.getCategoryName());
        assertEquals(balance, paymentOutputDTO.getBalance());
    }


    @Test
    void equalsAndHashCode() {
        long accountId1 = 4;
        double amount1 = 12.67;
        String currency1 = "GBP";
        String description1 = "Phone bill";
        String date1 = "12-09-2010 13:33";
        String destinationEntity1 = "Vodafone";
        Object categoryId1 = 2;
        long transactionId1 = 1;
        String categoryName = "Bills";
        double balance = 200.50;

        long accountId2 = 5;
        double amount2 = 12.68;
        String description2 = "Shopping";

        long transactionId2 = 7;

        //act
        TransactionOutputDTO outputDTO = new TransactionOutputDTO(transactionId1, accountId1, amount1, currency1, description1, date1, destinationEntity1, categoryId1, categoryName, balance);
        TransactionOutputDTO outputDTOEqual = outputDTO;
        TransactionOutputDTO outputDTONull = null;
        TransactionOutputDTO outputDTOSame = new TransactionOutputDTO(transactionId1, accountId1, amount1, currency1, description1, date1, destinationEntity1, categoryId1, categoryName, balance);
        TransactionOutputDTO outputDTODifferentId = new TransactionOutputDTO(transactionId2, accountId1, amount1, currency1, description1, date1, destinationEntity1, categoryId1, categoryName, balance);
        TransactionOutputDTO outputDTODifferentAccountId = new TransactionOutputDTO(transactionId1, accountId2, amount1, currency1, description1, date1, destinationEntity1, categoryId1, categoryName, balance);
        TransactionOutputDTO outputDTODifferentAmount = new TransactionOutputDTO(transactionId1, accountId1, amount2, currency1, description1, date1, destinationEntity1, categoryId1, categoryName, balance);
        TransactionOutputDTO outputDTODifferentDescription = new TransactionOutputDTO(transactionId1, accountId1, amount2, currency1, description2, date1, destinationEntity1, categoryId1, categoryName, balance);
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(amount1, 3, description1, date1, destinationEntity1, categoryId1);

        //assert
        assertEquals(outputDTO, outputDTOSame);
        assertNotSame(outputDTO, outputDTOSame);
        assertEquals(outputDTO, outputDTOEqual);
        assertEquals(outputDTO.hashCode(), outputDTOSame.hashCode());
        assertNotEquals(outputDTO, outputDTONull);
        assertFalse(outputDTO.equals(transactionId1));
        assertFalse(outputDTO.equals(accountId1));
        assertFalse(outputDTO.equals(amount1));
        assertFalse(outputDTO.equals(currency1));
        assertEquals(outputDTO, outputDTODifferentId);
        assertNotEquals(outputDTO, outputDTODifferentAccountId);
        assertNotEquals(outputDTO, outputDTODifferentAmount);
        assertNotEquals(outputDTO, outputDTODifferentDescription);
        assertTrue(outputDTO.equals(outputDTODifferentId));
        assertFalse(outputDTO.equals(outputDTODifferentAccountId));
        assertFalse(outputDTO.equals(outputDTODifferentAmount));
        assertFalse(outputDTO.equals(outputDTODifferentDescription));
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentId.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentAccountId.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentAmount.hashCode());
        assertNotEquals(outputDTO.hashCode(), outputDTODifferentDescription.hashCode());
        assertNotEquals(0, outputDTO.hashCode());
        assertNotEquals(outputDTO, accountId2);
        assertNotEquals(outputDTO, amount2);
        assertNotEquals(outputDTO, paymentInputDTO);
        assertFalse(outputDTO.equals(paymentInputDTO));
    }

    @Test
    void testGetterAndSetter() {
        //arrange
        long accountId = 2;
        long destinationAccountId = 3;
        double amount = 20;
        String currency = "EUR";
        String description = "Jantar Mexicano";
        String date = "12/12/1212 12:12";
        int categoryId = 123456;
        String category = "Jantares";
        double balance = -10;

        //act
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(accountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);
        transactionOutputDTO.setAccountId(accountId);
        transactionOutputDTO.setDestinationAccountId(destinationAccountId);
        transactionOutputDTO.setAmount(amount);
        transactionOutputDTO.setCurrency(currency);
        transactionOutputDTO.setDescription(description);
        transactionOutputDTO.setDate(date);
        transactionOutputDTO.setCategoryId(categoryId);
        transactionOutputDTO.setCategoryName(category);
        transactionOutputDTO.setBalance(balance);

        //assert
        assertNotNull(transactionOutputDTO);
        assertEquals(accountId, transactionOutputDTO.getAccountId());
        assertEquals(destinationAccountId, transactionOutputDTO.getDestinationAccountId());
        assertEquals(amount, transactionOutputDTO.getAmount());
        assertEquals(currency, transactionOutputDTO.getCurrency());
        assertEquals(description, transactionOutputDTO.getDescription());
        assertEquals(date, transactionOutputDTO.getDate());
        assertEquals(categoryId, transactionOutputDTO.getCategoryId());
        assertEquals(category, transactionOutputDTO.getCategoryName());
        assertEquals(balance, transactionOutputDTO.getBalance());
    }

    @Test
    void testGetterAndSetter_withTransactionId() {
        //arrange
        long transactionId = 1;
        long accountId = 2;
        long destinationAccountId = 3;
        double amount = 20;
        String currency = "EUR";
        String description = "Jantar Mexicano";
        String date = "12/12/1212 12:12";
        int categoryId = 123456;
        String category = "Jantares";
        double balance = -10;

        //act
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(transactionId, accountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);
        transactionOutputDTO.setTransactionId(transactionId);
        transactionOutputDTO.setAccountId(accountId);
        transactionOutputDTO.setDestinationAccountId(destinationAccountId);
        transactionOutputDTO.setAmount(amount);
        transactionOutputDTO.setCurrency(currency);
        transactionOutputDTO.setDescription(description);
        transactionOutputDTO.setDate(date);
        transactionOutputDTO.setCategoryId(categoryId);
        transactionOutputDTO.setCategoryName(category);
        transactionOutputDTO.setBalance(balance);

        //assert
        assertNotNull(transactionOutputDTO);
        assertEquals(transactionId, transactionOutputDTO.getTransactionId());
        assertEquals(accountId, transactionOutputDTO.getAccountId());
        assertEquals(destinationAccountId, transactionOutputDTO.getDestinationAccountId());
        assertEquals(amount, transactionOutputDTO.getAmount());
        assertEquals(currency, transactionOutputDTO.getCurrency());
        assertEquals(description, transactionOutputDTO.getDescription());
        assertEquals(date, transactionOutputDTO.getDate());
        assertEquals(categoryId, transactionOutputDTO.getCategoryId());
        assertEquals(category, transactionOutputDTO.getCategoryName());
        assertEquals(balance, transactionOutputDTO.getBalance());
    }

    @Test
    void testEquals_test1_DifferentObjects() {
        //arrange
        long accountId = 2;
        long destinationAccountId = 3;
        double amount = 20;
        String currency = "EUR";
        String description = "Jantar Mexicano";
        String date = "12/12/1212 12:12";
        int categoryId = 123456;
        String category = "Jantares";
        double balance = -10;

        //act
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(accountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);
        TransactionOutputDTO otherTransactionOutputDTO = new TransactionOutputDTO(accountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);

        //assert
        assertNotSame(transactionOutputDTO, otherTransactionOutputDTO);
        assertEquals(transactionOutputDTO, otherTransactionOutputDTO);
    }

    @Test
    void testEquals_test2_SameObjects() {
        //arrange
        long accountId = 2;
        long destinationAccountId = 3;
        double amount = 20;
        String currency = "EUR";
        String description = "Jantar Mexicano";
        String date = "12/12/1212 12:12";
        int categoryId = 123456;
        String category = "Jantares";
        double balance = -10;

        //act
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(accountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);
        TransactionOutputDTO otherTransactionOutputDTO = transactionOutputDTO;

        //assert
        assertSame(transactionOutputDTO, otherTransactionOutputDTO);
        assertEquals(transactionOutputDTO, otherTransactionOutputDTO);
    }

    @Test
    void testEquals_test3_DifferentObjectsType() {
        //arrange
        long accountId = 2;
        long destinationAccountId = 3;
        double amount = 20;
        String currency = "EUR";
        String description = "Jantar Mexicano";
        String date = "12/12/1212 12:12";
        int categoryId = 123456;
        String category = "Jantares";
        double balance = -10;

        //act
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(accountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);
        AccountId otherOriginId = new AccountId(accountId);

        //assert
        assertNotEquals(transactionOutputDTO, otherOriginId);
    }

    @Test
    void testEquals_test4_NotEqualObjects() {
        //arrange
        long accountId = 2;
        long otherAccountId = 1;
        long destinationAccountId = 3;
        double amount = 20;
        String currency = "EUR";
        String description = "Jantar Mexicano";
        String date = "12/12/1212 12:12";
        int categoryId = 123456;
        String category = "Jantares";
        double balance = -10;

        //act
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(accountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);
        TransactionOutputDTO otherTransactionOutputDTO = new TransactionOutputDTO(otherAccountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);

        //assert
        assertNotEquals(transactionOutputDTO, otherTransactionOutputDTO);
    }

    @Test
    void testHashCode() {
        //arrange
        long accountId = 2;
        long destinationAccountId = 3;
        double amount = 20;
        String currency = "EUR";
        String description = "Jantar Mexicano";
        String date = "12/12/1212 12:12";
        int categoryId = 123456;
        String category = "Jantares";
        double balance = -10;

        //act
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(accountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);
        TransactionOutputDTO otherTransactionOutputDTO = new TransactionOutputDTO(accountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);

        //assert
        assertNotSame(transactionOutputDTO, otherTransactionOutputDTO);
        assertEquals(transactionOutputDTO.hashCode(), otherTransactionOutputDTO.hashCode());
    }

    @Test
    void testHashCode_false() {
        //arrange
        long accountId = 2;
        long otherAccountId = 1;
        long destinationAccountId = 3;
        double amount = 20;
        String currency = "EUR";
        String description = "Jantar Mexicano";
        String date = "12/12/1212 12:12";
        int categoryId = 123456;
        String category = "Jantares";
        double balance = -10;

        //act
        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(accountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);
        TransactionOutputDTO otherTransactionOutputDTO = new TransactionOutputDTO(otherAccountId, destinationAccountId, amount, currency, description, date, categoryId, category, balance);

        //assert
        assertNotEquals(transactionOutputDTO.hashCode(), otherTransactionOutputDTO.hashCode());
    }

    @Test
    void createTransactionOutputDTOSuccessfully() {
        long transactionId = 123;
        double amount = 34.45;
        String currency = "EUR";
        String description = "Transaction";
        String date = "12/02/2020 15:56";
        String categoryName = "Groceries";

        TransactionOutputDTO transactionOutputDTO = new TransactionOutputDTO(transactionId, amount, currency, description, date, categoryName);

        assertNotNull(transactionOutputDTO);
        assertEquals(transactionId, transactionOutputDTO.getTransactionId());
        assertEquals(amount, transactionOutputDTO.getAmount());
        assertEquals(currency, transactionOutputDTO.getCurrency());
        assertEquals(description, transactionOutputDTO.getDescription());
        assertEquals(date, transactionOutputDTO.getDate());
        assertEquals(categoryName, transactionOutputDTO.getCategoryName());
    }
}