package switchtwentytwenty.project.domain.factories;

import lombok.NoArgsConstructor;
import switchtwentytwenty.project.domain.model.transaction.Payment;
import switchtwentytwenty.project.dto.transaction.PaymentVOs;

@NoArgsConstructor
public class PaymentFactory {

    public static Payment buildPayment(PaymentVOs paymentVOs) {
        return new Payment.Builder(paymentVOs.getAccountId(), paymentVOs.getAmount())
                .withDescription(paymentVOs.getDescription())
                .withTransactionDate(paymentVOs.getDate())
                .withDestinationEntity(paymentVOs.getDestinationEntity())
                .withCategoryId(paymentVOs.getCategoryId())
                .withTransactionId()
                .build();
    }
}
