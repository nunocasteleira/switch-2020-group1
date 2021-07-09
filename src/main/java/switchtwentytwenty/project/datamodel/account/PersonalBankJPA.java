package switchtwentytwenty.project.datamodel.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import switchtwentytwenty.project.datamodel.shared.AmountJPA;
import switchtwentytwenty.project.datamodel.shared.DescriptionJPA;
import switchtwentytwenty.project.datamodel.shared.ProviderJPA;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class PersonalBankJPA extends AccountJPA {

    @Getter
    @Embedded
    private ProviderJPA provider;

    public PersonalBankJPA(AmountJPA initialAmountValueJPA, DescriptionJPA accountDescriptionJPA, ProviderJPA providerJPA) {
        super(initialAmountValueJPA, accountDescriptionJPA);
        this.provider = providerJPA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        PersonalBankJPA that = (PersonalBankJPA) o;
        return Objects.equals(provider, that.provider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), provider);
    }
}
