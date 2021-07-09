package switchtwentytwenty.project.dto.account;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import switchtwentytwenty.project.domain.model.shared.Balance;
import java.util.Objects;

public class AccountBalanceDTO extends RepresentationModel<AccountBalanceDTO> {

    @Getter
    private final Balance accountBalance;

    public AccountBalanceDTO(Balance accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountBalanceDTO)) {
            return false;
        }
        AccountBalanceDTO that = (AccountBalanceDTO) o;
        return getAccountBalance().equals(that.getAccountBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccountBalance());
    }
}
