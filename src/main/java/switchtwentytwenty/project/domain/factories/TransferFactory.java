package switchtwentytwenty.project.domain.factories;

import lombok.NoArgsConstructor;
import switchtwentytwenty.project.domain.model.transaction.Transfer;
import switchtwentytwenty.project.dto.transaction.TransferVOs;

@NoArgsConstructor
public class TransferFactory {

    public static Transfer buildTransfer(TransferVOs transferVOs) {
        return new Transfer.TransferBuilder(transferVOs.getOriginAccountId(), transferVOs.getDestinationAccountId(), transferVOs.getAmount())
                .withDescription(transferVOs.getDescription())
                .withTransactionDate(transferVOs.getDate())
                .withCategoryId(transferVOs.getCategoryId())
                .withTransactionId()
                .build();
    }
}