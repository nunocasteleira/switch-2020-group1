package switchtwentytwenty.project.datamodel.account;

import lombok.NoArgsConstructor;
import switchtwentytwenty.project.datamodel.shared.AmountJPA;
import switchtwentytwenty.project.datamodel.shared.DescriptionJPA;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class CashAccountJPA extends AccountJPA {

    /**
     * Constructor method for CashAccount object.
     *
     * @param initialAmountValueJPA initial cash value JPA value object
     * @param accountDescriptionJPA account description JPA value object
     */
    public CashAccountJPA(AmountJPA initialAmountValueJPA, DescriptionJPA accountDescriptionJPA) {
        super(initialAmountValueJPA, accountDescriptionJPA);
    }
}
