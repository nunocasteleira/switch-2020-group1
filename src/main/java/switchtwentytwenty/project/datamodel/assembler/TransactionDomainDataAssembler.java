package switchtwentytwenty.project.datamodel.assembler;

import org.springframework.stereotype.Service;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.datamodel.transaction.PaymentJPA;
import switchtwentytwenty.project.datamodel.transaction.TransferJPA;
import switchtwentytwenty.project.domain.model.transaction.Payment;
import switchtwentytwenty.project.domain.model.transaction.Transfer;

@Service
public class TransactionDomainDataAssembler {

    public PaymentJPA toData(Payment aPayment) {
        OriginAccountIdJPA accountIdJPA = new OriginAccountIdJPA(aPayment.getAccountId().getAccountIdNumber());
        AmountJPA amountJPA = new AmountJPA(aPayment.getAmount().getAmount(), aPayment.getAmount().getCurrency().getCurrencyNumber());
        DescriptionJPA descriptionJPA = new DescriptionJPA(aPayment.getDescription().getAccountDescription());
        TransactionDateJPA dateJPA = new TransactionDateJPA(aPayment.getDate().toString());
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(aPayment.getCategoryId().getId());
        DestinationEntityJPA destinationEntityJPA = new DestinationEntityJPA(aPayment.getDestinationEntity().getAccountDescription());

        return new PaymentJPA(accountIdJPA, amountJPA, descriptionJPA, dateJPA, categoryIdJPA, destinationEntityJPA);
    }

    public TransferJPA toData(Transfer aTransfer) {
        OriginAccountIdJPA originAccountIdJPA = new OriginAccountIdJPA(aTransfer.getAccountId().getAccountIdNumber());
        DestinationAccountIdJPA destinationAccountIdJPA = new DestinationAccountIdJPA(aTransfer.getDestinationAccountId().getAccountIdNumber());
        AmountJPA amountJPA = new AmountJPA(aTransfer.getAmount().getAmount(), aTransfer.getAmount().getCurrency().getCurrencyNumber());
        DescriptionJPA descriptionJPA = new DescriptionJPA(aTransfer.getDescription().getAccountDescription());
        TransactionDateJPA dateJPA = new TransactionDateJPA(aTransfer.getDate().toString());
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(aTransfer.getCategoryId().getId());

        return new TransferJPA(originAccountIdJPA, destinationAccountIdJPA, amountJPA, descriptionJPA, dateJPA, categoryIdJPA);
    }
}
