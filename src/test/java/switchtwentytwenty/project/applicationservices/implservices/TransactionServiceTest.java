package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.assemblers.TransactionAssembler;
import switchtwentytwenty.project.domain.exceptions.InvalidAmountException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.category.BaseCategory;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.domain.model.transaction.Payment;
import switchtwentytwenty.project.domain.model.transaction.Transaction;
import switchtwentytwenty.project.domain.model.transaction.Transfer;
import switchtwentytwenty.project.dto.transaction.*;
import switchtwentytwenty.project.repositories.AccountRepository;
import switchtwentytwenty.project.repositories.CategoryRepository;
import switchtwentytwenty.project.repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Profile("TransactionServiceTest")
class TransactionServiceTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    TransactionAssembler transactionAssembler;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    TransactionRepository transactionRepository;
    @InjectMocks
    TransactionService transactionService;

    @Test
    void paymentIsRegisteredFromAccountWithTheRightInformation() {
        //Arrange
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", 2);
        long accountId = 1;
        PaymentVOs paymentVOs = new PaymentVOs(new AccountId(accountId), new TransactionAmount(-20, Currency.USD), new Description("Electricity bill"), new TransactionDate("19/06/2021 12:54"), new Description("EDP"), new CategoryId(2));
        Payment payment = new Payment.Builder(paymentVOs.getAccountId(), paymentVOs.getAmount())
                .withDescription(paymentVOs.getDescription())
                .withTransactionDate(paymentVOs.getDate())
                .withDestinationEntity(paymentVOs.getDestinationEntity())
                .withCategoryId(paymentVOs.getCategoryId())
                .withTransactionId()
                .build();
        Payment savedPayment = new Payment.Builder(paymentVOs.getAccountId(), paymentVOs.getAmount())
                .withDescription(paymentVOs.getDescription())
                .withTransactionDate(paymentVOs.getDate())
                .withDestinationEntity(paymentVOs.getDestinationEntity())
                .withCategoryId(paymentVOs.getCategoryId())
                .withTransactionId(new TransactionId(3))
                .build();
        CategoryName categoryName = new CategoryName("BILLS");
        StandardCategory category = new StandardCategory(categoryName);
        Balance balance = new Balance(150.00);
        InitialAmountValue initialAmountValue = new InitialAmountValue(150.00, Currency.GBP);
        List<Transaction> transactions = new ArrayList<>();
        TransactionOutputDTO expectedOutputDTO = new TransactionOutputDTO(3, accountId, -20, "GBP", "Electricity bill", "19/06/2021 12:54", "EDP", 2, "BILLS", balance.getAmount());

        doNothing().when(accountRepository).existsAccount(accountId);
        when(transactionAssembler.toDomain(paymentInputDTO, accountId)).thenReturn(paymentVOs);
        when(categoryRepository.getCategory(new CategoryId(2))).thenReturn(category);
        when(transactionRepository.findAllByAccountId(isA(AccountId.class))).thenReturn(transactions);
        when(accountRepository.getByAccountId(isA(AccountId.class))).thenReturn(initialAmountValue);
        when(transactionRepository.save(payment)).thenReturn(savedPayment);
        when(transactionAssembler.toOutputDTO(isA(Payment.class), isA(CategoryName.class), isA(Balance.class))).thenReturn(expectedOutputDTO);

        //Act
        TransactionOutputDTO resultDTO = transactionService.registerPayment(paymentInputDTO, accountId);

        //Assert
        assertNotNull(resultDTO);
        assertEquals(expectedOutputDTO, resultDTO);
    }

    @Test
    void paymentIsNotRegisteredIfBalanceIsLowerThenPaymentAmount() {
        //Arrange
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", 2);
        long accountId = 1;
        PaymentVOs paymentVOs = new PaymentVOs(new AccountId(accountId), new TransactionAmount(-20, Currency.USD), new Description("Electricity bill"), new TransactionDate("19/06/2021 12:54"), new Description("EDP"), new CategoryId(2));
        Payment payment = new Payment.Builder(paymentVOs.getAccountId(), paymentVOs.getAmount())
                .withDescription(paymentVOs.getDescription())
                .withTransactionDate(paymentVOs.getDate())
                .withDestinationEntity(paymentVOs.getDestinationEntity())
                .withCategoryId(paymentVOs.getCategoryId())
                .withTransactionId()
                .build();
        Payment savedPayment = new Payment.Builder(paymentVOs.getAccountId(), paymentVOs.getAmount())
                .withDescription(paymentVOs.getDescription())
                .withTransactionDate(paymentVOs.getDate())
                .withDestinationEntity(paymentVOs.getDestinationEntity())
                .withCategoryId(paymentVOs.getCategoryId())
                .withTransactionId(new TransactionId(3))
                .build();
        CategoryName categoryName = new CategoryName("BILLS");
        StandardCategory category = new StandardCategory(categoryName);
        Balance balance = new Balance(150.00);
        InitialAmountValue initialAmountValue = new InitialAmountValue(10, Currency.GBP);
        List<Transaction> transactions = new ArrayList<>();

        doNothing().when(accountRepository).existsAccount(accountId);
        when(transactionAssembler.toDomain(paymentInputDTO, accountId)).thenReturn(paymentVOs);
        when(categoryRepository.getCategory(new CategoryId(2))).thenReturn(category);
        when(transactionRepository.findAllByAccountId(isA(AccountId.class))).thenReturn(transactions);
        when(accountRepository.getByAccountId(isA(AccountId.class))).thenReturn(initialAmountValue);

        assertThrows(InvalidAmountException.class, () -> transactionService.registerPayment(paymentInputDTO, accountId));
    }

    @Test
    void paymentIsNotRegisteredIfAccountIdIsFromANonexistentAccount() {
        //Arrange
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", 2);
        long accountId = 1;

        doThrow(ObjectDoesNotExistException.class).when(accountRepository).existsAccount(accountId);

        //Act and assert
        assertThrows(ObjectDoesNotExistException.class, () -> transactionService.registerPayment(paymentInputDTO, accountId));
    }

    @Test
    void paymentIsNotRegisteredFromAccountWithWrongCategoryId() {
        //Arrange
        PaymentInputDTO paymentInputDTO = new PaymentInputDTO(20, 3, "Electricity bill", "19/06/2021 12:54", "EDP", 2);
        long accountId = 1;
        PaymentVOs paymentVOs = new PaymentVOs(new AccountId(accountId), new TransactionAmount(-20, Currency.USD), new Description("Electricity bill"), new TransactionDate("19/06/2021 12:54"), new Description("EDP"), new CategoryId(2));

        doNothing().when(accountRepository).existsAccount(accountId);
        when(transactionAssembler.toDomain(paymentInputDTO, accountId)).thenReturn(paymentVOs);
        doThrow(ObjectDoesNotExistException.class).when(categoryRepository).getCategory(isA(CategoryId.class));

        //Act and assert
        assertThrows(ObjectDoesNotExistException.class, () -> transactionService.registerPayment(paymentInputDTO, accountId));
    }

/*    @Test
    void transfer_Successfully() {
        //arrange
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "Electricity bill", "19/06/2021 12:54", 2);
        long originAccountId = 1;
        long destinationAccountId = 2;
        TransferVOs transferVOs = new TransferVOs(new AccountId(originAccountId), new AccountId(destinationAccountId), new TransactionAmount(-20, Currency.USD), new Description("Electricity bill"), new TransactionDate("19/06/2021 12:54"), new CategoryId(2));
        Transfer transfer = new Transfer.TransferBuilder(transferVOs.getOriginAccountId(), transferVOs.getDestinationAccountId(), transferVOs.getAmount())
                .withDescription(transferVOs.getDescription())
                .withTransactionDate(transferVOs.getDate())
                .withCategoryId(transferVOs.getCategoryId())
                .withTransactionId()
                .build();
        Transfer savedTransfer = new Transfer.TransferBuilder(transferVOs.getOriginAccountId(), transferVOs.getDestinationAccountId(), transferVOs.getAmount())
                .withDescription(transferVOs.getDescription())
                .withTransactionDate(transferVOs.getDate())
                .withCategoryId(transferVOs.getCategoryId())
                .withTransactionId(new TransactionId(3))
                .build();
        CategoryName categoryName = new CategoryName("BILLS");
        StandardCategory category = new StandardCategory(categoryName);
        TransactionOutputDTO expected = new TransactionOutputDTO(3, originAccountId, 2, 20, "EUR", "Electricity bill", "19/06/2021 12:54", 2, categoryName.toString());

        doNothing().when(accountRepository).existsAccount(originAccountId);
        when(transactionAssembler.toDomain(transferInputDTO, originAccountId, destinationAccountId)).thenReturn(transferVOs);
        when(categoryRepository.getCategory(new CategoryId(2))).thenReturn(category);
        when(transactionRepository.save(transfer)).thenReturn(savedTransfer);
        when(transactionAssembler.toOutputDTO(isA(Transfer.class), isA(CategoryName.class))).thenReturn(expected);

        //act
        TransactionOutputDTO result = transactionService.transfer(transferInputDTO, originAccountId, destinationAccountId);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void transfer_Invalid_NonexistentAccount() {
        //arrange
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "Electricity bill", "19/06/2021 12:54", 2);
        long originAccountId = 1;
        long destinationAccountId = 2;

        doThrow(ObjectDoesNotExistException.class).when(accountRepository).existsAccount(originAccountId);

        //act && assert
        assertThrows(ObjectDoesNotExistException.class, () -> transactionService.transfer(transferInputDTO, originAccountId, destinationAccountId));
    }

    @Test
    void transfer_Invalid_WrongCategoryId() {
        //arrange
        TransferInputDTO transferInputDTO = new TransferInputDTO(20, 2, "Electricity bill", "19/06/2021 12:54", 2);
        long originAccountId = 1;
        long destinationAccountId = 2;
        TransferVOs transferVOs = new TransferVOs(new AccountId(originAccountId), new AccountId(destinationAccountId), new TransactionAmount(-20, Currency.USD), new Description("Electricity bill"), new TransactionDate("19/06/2021 12:54"), new CategoryId(2));

        doNothing().when(accountRepository).existsAccount(originAccountId);
        when(transactionAssembler.toDomain(transferInputDTO, originAccountId, destinationAccountId)).thenReturn(transferVOs);
        doThrow(ObjectDoesNotExistException.class).when(categoryRepository).getCategory(isA(CategoryId.class));

        //act && assert
        assertThrows(ObjectDoesNotExistException.class, () -> transactionService.transfer(transferInputDTO, originAccountId, destinationAccountId));
    }*/

    @Test
    void getAccountTransactionsBetweenDates() {
        long accountId = 1;
        String startDate = "01/01/2020";
        String endDate = "01/01/2021";
        DateRangeDTO dateRangeDTO = new DateRangeDTO(startDate, endDate);

        List<Transaction> transactionList = new ArrayList<>();
        PaymentVOs paymentVOs = new PaymentVOs(new AccountId(accountId), new TransactionAmount(-20, Currency.USD), new Description("Electricity bill"), new TransactionDate("18/06/2020 12:54"), new Description("EDP"), new CategoryId(2));
        Payment payment = new Payment.Builder(paymentVOs.getAccountId(), paymentVOs.getAmount())
                .withDescription(paymentVOs.getDescription())
                .withTransactionDate(paymentVOs.getDate())
                .withDestinationEntity(paymentVOs.getDestinationEntity())
                .withCategoryId(paymentVOs.getCategoryId())
                .withTransactionId()
                .build();
        transactionList.add(payment);
        long destinationAccountId = 2;
        TransferVOs transferVOs = new TransferVOs(new AccountId(accountId), new AccountId(destinationAccountId), new TransactionAmount(-20, Currency.USD), new Description("Electricity bill"), new TransactionDate("19/06/2020 12:54"), new CategoryId(2));
        Transfer transfer = new Transfer.TransferBuilder(transferVOs.getOriginAccountId(), transferVOs.getDestinationAccountId(), transferVOs.getAmount())
                .withDescription(transferVOs.getDescription())
                .withTransactionDate(transferVOs.getDate())
                .withCategoryId(transferVOs.getCategoryId())
                .withTransactionId()
                .build();
        transactionList.add(transfer);

        when(transactionRepository.findAllByAccountId(new AccountId(accountId)))
                .thenReturn(transactionList);
        String categoryName = "Shopping";
        BaseCategory category = new StandardCategory(new CategoryName(categoryName));
        when(categoryRepository.getCategory(isA(CategoryId.class))).thenReturn(category);

        List<TransactionOutputDTO> expectedList = new ArrayList<>();
        TransactionOutputDTO transactionOne = new TransactionOutputDTO(payment.getTransactionId().getTransactionIdValue(), payment.getAmount().getAmount(), payment.getAmount().getCurrency().toString(), payment.getDescription().getAccountDescription(), payment.getDate().toString(), categoryName.toUpperCase());
        TransactionOutputDTO transactionTwo = new TransactionOutputDTO(transfer.getTransactionId().getTransactionIdValue(), transfer.getAmount().getAmount(), transfer.getAmount().getCurrency().toString(), transfer.getDescription().getAccountDescription(), transfer.getDate().toString(), categoryName.toUpperCase());
        expectedList.add(transactionOne);
        expectedList.add(transactionTwo);
        TransactionListDTO expected = new TransactionListDTO(expectedList);

        TransactionListDTO result = transactionService.getAccountTransactionsBetweenDates(accountId, dateRangeDTO);

        assertNotNull(result);
        assertEquals(expected, result);
    }
}