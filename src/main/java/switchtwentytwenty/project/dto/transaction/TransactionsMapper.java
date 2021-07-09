package switchtwentytwenty.project.dto.transaction;

import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.domain.model.transaction.Transaction;

public class TransactionsMapper {
    private TransactionsMapper() {
    }

    public static TransactionOutputDTO mapTransaction(Transaction transaction, CategoryName category) {
        long transactionId = transaction.getTransactionId().getTransactionIdValue();
        double amount = transaction.getAmount().getAmount();
        String currency = transaction.getAmount().getCurrency().toString();
        String description = transaction.getDescription().getAccountDescription();
        String date = transaction.getDate().toString();
        String categoryName = category.toString();

        return new TransactionOutputDTO(transactionId, amount, currency, description, date, categoryName);
    }
}
