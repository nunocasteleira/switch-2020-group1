package switchtwentytwenty.project.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class CashAccountsOutputDTO extends RepresentationModel<CashAccountsOutputDTO> {

    @Getter
    @Setter
    private List<AccountOutputDTO> cashAccounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CashAccountsOutputDTO that = (CashAccountsOutputDTO) o;
        return cashAccounts.equals(that.cashAccounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cashAccounts);
    }
}
