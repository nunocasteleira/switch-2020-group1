package switchtwentytwenty.project.datamodel.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import switchtwentytwenty.project.datamodel.shared.AmountJPA;
import switchtwentytwenty.project.datamodel.shared.DescriptionJPA;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor
public abstract class AccountJPA {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    @Getter
    @Embedded
    private AmountJPA initialAmountValue;
    @Getter
    @Embedded
    private DescriptionJPA accountDescription;


    /**
     * Constructor method for CashAccount object.
     *
     * @param initialAmountValueJPA initial cash value JPA value object
     * @param accountDescriptionJPA account description JPA value object
     */
    public AccountJPA(AmountJPA initialAmountValueJPA, DescriptionJPA accountDescriptionJPA) {
        this.accountId = 0;
        this.initialAmountValue = initialAmountValueJPA;
        this.accountDescription = accountDescriptionJPA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountJPA)) {
            return false;
        }
        AccountJPA that = (AccountJPA) o;
        return accountId == that.accountId && initialAmountValue.equals(that.initialAmountValue) && accountDescription.equals(that.accountDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, initialAmountValue, accountDescription);
    }
}
