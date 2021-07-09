package switchtwentytwenty.project.applicationservices.implservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import switchtwentytwenty.project.applicationservices.iservices.ITransactionService;
import switchtwentytwenty.project.assemblers.TransactionAssembler;
import switchtwentytwenty.project.domain.domainservices.TransactionDomainService;
import switchtwentytwenty.project.domain.exceptions.InvalidAmountException;
import switchtwentytwenty.project.domain.factories.PaymentFactory;
import switchtwentytwenty.project.domain.factories.TransferFactory;
import switchtwentytwenty.project.domain.model.category.BaseCategory;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.domain.model.transaction.Payment;
import switchtwentytwenty.project.domain.model.transaction.Transaction;
import switchtwentytwenty.project.dto.account.AccountBalanceDTO;
import switchtwentytwenty.project.dto.account.AccountBalanceMapper;
import switchtwentytwenty.project.dto.transaction.PaymentInputDTO;
import switchtwentytwenty.project.dto.transaction.PaymentVOs;
import switchtwentytwenty.project.domain.model.transaction.Transfer;
import switchtwentytwenty.project.dto.transaction.*;
import switchtwentytwenty.project.repositories.AccountRepository;
import switchtwentytwenty.project.repositories.CategoryRepository;
import switchtwentytwenty.project.repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionAssembler transactionAssembler;

    /**
     * This method registers a payment from an account
     * @param paymentInputDTO DTO containing payment information
     * @param accountId Id of the account the payment is registered from
     * @return DTO with payment information
     */
    @Override
    @Transactional
    public TransactionOutputDTO registerPayment(PaymentInputDTO paymentInputDTO, long accountId) {
        existsAccount(accountId);
        PaymentVOs paymentVOs = transactionAssembler.toDomain(paymentInputDTO, accountId);
        CategoryName categoryName = getCategoryName(paymentVOs.getCategoryId());
        Payment aPayment = PaymentFactory.buildPayment(paymentVOs);
        Balance prePaymentBalance = getBalance(aPayment.getAccountId());
        validateTransaction(prePaymentBalance, aPayment.getAmount());
        Balance afterPaymentBalance = getBalanceAfterTransaction(prePaymentBalance, aPayment);
        Payment savedPayment = (Payment) transactionRepository.save(aPayment);
        return transactionAssembler.toOutputDTO(savedPayment, categoryName, afterPaymentBalance);
    }

    @Override
    @Transactional
    public TransactionOutputDTO transfer(TransferInputDTO transferInputDTO, long originAccountId) {
        existsAccount(originAccountId);
        TransferVOs transferVOs = transactionAssembler.toDomain(transferInputDTO, originAccountId);
        CategoryName categoryName = getCategoryName(transferVOs.getCategoryId());
        Transfer aTransfer = TransferFactory.buildTransfer(transferVOs);
        Balance preTransferBalance = getBalance(aTransfer.getAccountId());
        validateTransaction(preTransferBalance, aTransfer.getAmount());
        Balance afterTransferBalance = getBalanceAfterTransaction(preTransferBalance, aTransfer);
        Transfer savedTransfer = (Transfer) transactionRepository.save(aTransfer);

        registerTransferIntoDestinationAccount(transferInputDTO, originAccountId);

        return transactionAssembler.toOutputDTO(savedTransfer, categoryName, afterTransferBalance);
    }

    private void registerTransferIntoDestinationAccount(TransferInputDTO transferInputDTO, long originAccountId) {
        TransferVOs transferVOs = transactionAssembler.destinationAccountTransferToDomain(transferInputDTO, originAccountId);
        Transfer destinationTransfer = TransferFactory.buildTransfer(transferVOs);
        transactionRepository.save(destinationTransfer);
    }

    /**
     * This method obtains the categories name from its Id
     * @param categoryId category's Id
     * @return category's name
     */
    private CategoryName getCategoryName(CategoryId categoryId) {
        BaseCategory aCategory = getById(categoryId);
        return aCategory.getName();
    }

    /**
     * This method obtains the category from its Id
     * @param categoryId categoryId category's Id
     * @return category
     */
    private BaseCategory getById(CategoryId categoryId) {
        return categoryRepository.getCategory(categoryId);
    }

    /**
     * This method verifies that an account exists by its Id
     * @param accountId Id of the account
     */
    private void existsAccount(long accountId) {
        accountRepository.existsAccount(accountId);
    }

    /**
     * This method gets the account's balance
     * @param accountId Id of the account
     * @return balance
     */
    public Balance getBalance(AccountId accountId) {
        List<Transaction> transactions = transactionRepository.findAllByAccountId(accountId);
        InitialAmountValue initialAmountValue = accountRepository.getByAccountId(accountId);
        return calculateBalance(transactions, initialAmountValue);
    }

    /**
     * This method calculates the balance of an account
     * @param transactions List of transactions registered in the account
     * @param initialAmount Amount deposited in the account when it was created
     * @return balance
     */
    private Balance calculateBalance(List<Transaction> transactions, InitialAmountValue initialAmount) {
        double balance = initialAmount.getAmount();
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount().getAmount();
        }
        return new Balance(balance);
    }

    /**
     * This method validates if the account has enough money to complete the transaction
     * @param balance balance of the account
     * @param amount amount of the transaction
     */
    private void validateTransaction(Balance balance, TransactionAmount amount) {
        if (balance.getAmount() + amount.getAmount() <= 0) {
            throw new InvalidAmountException("The transaction cannot be completed. There's not enough money in your account.");
        }
    }

    /**
     * This method calculates the balance of an account after a transaction occured
     * @param preTransactionBalance balance of the account before the transaction
     * @param transaction registered transaction
     * @return balance of the account after the transaction
     */
    private Balance getBalanceAfterTransaction(Balance preTransactionBalance, Transaction transaction) {
        double balance = preTransactionBalance.getAmount() + transaction.getAmount().getAmount();
        return new Balance(balance);
    }

    @Override
    public AccountBalanceDTO getAccountBalance(long aAccountId){
        AccountId accountId = new AccountId(aAccountId);
        Balance accountBalance = getBalance(accountId);

        AccountBalanceMapper mapper = new AccountBalanceMapper();

        return mapper.toDTO(accountBalance);
    }

    @Override
    public TransactionListDTO getAccountTransactionsBetweenDates(long accountId, DateRangeDTO dateRangeDTO) {
        List<TransactionOutputDTO> listToReturn = new ArrayList<>();
        AccountId accountIdVO = new AccountId(accountId);
        List<Transaction> transactionList = transactionRepository.findAllByAccountId(accountIdVO);
        String startDate = dateRangeDTO.getStartDate();
        String endDate = dateRangeDTO.getEndDate();
        List<Transaction> transactionsBetweenDates = TransactionDomainService.getTransactionsBetweenDates(transactionList, startDate, endDate);

        for (Transaction transaction : transactionsBetweenDates) {
            CategoryId categoryId = transaction.getCategoryId();
            CategoryName categoryName = getCategoryName(categoryId);
            TransactionOutputDTO dto = TransactionsMapper.mapTransaction(transaction, categoryName);
            listToReturn.add(dto);
        }

        return new TransactionListDTO(listToReturn);
    }
}