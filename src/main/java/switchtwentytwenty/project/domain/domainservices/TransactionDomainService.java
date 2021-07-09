package switchtwentytwenty.project.domain.domainservices;

import org.springframework.stereotype.Service;
import switchtwentytwenty.project.domain.model.shared.TransactionDate;
import switchtwentytwenty.project.domain.model.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionDomainService {

    private TransactionDomainService() {
    }

    public static List<Transaction> getTransactionsBetweenDates(List<Transaction> transactionList, String startDate, String endDate) {
        List<Transaction> transactionsBetweenDates = new ArrayList<>();
        TransactionDate transactionStartDate = new TransactionDate(startDate);
        TransactionDate transactionEndDate = new TransactionDate(endDate);
        for (Transaction transaction : transactionList) {
            boolean isBetweenDates = transaction.isAfter(transactionStartDate) && transaction.isBefore(transactionEndDate);
            if (isBetweenDates) {
                transactionsBetweenDates.add(transaction);
            }
        }
        return transactionsBetweenDates;
    }
}
