package switchtwentytwenty.project.dto.account;


import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import switchtwentytwenty.project.domain.model.account.CashAccount;
import switchtwentytwenty.project.domain.model.account.PersonalBankAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountListDTO extends RepresentationModel<AccountListDTO> {
    @Getter
    private final List<CashAccount> cashAccountList;

    @Getter
    private final List<PersonalBankAccount> personalBankAccount;


    public AccountListDTO(List<PersonalBankAccount> personalBankAccount, List<CashAccount> cashAccount) {
        this.cashAccountList = new ArrayList<>(cashAccount);
        this.personalBankAccount = new ArrayList<>(personalBankAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; }

        if (!(o instanceof AccountListDTO)) {
            return false; }

        AccountListDTO that = (AccountListDTO) o;
        return Objects.equals(getCashAccountList(), that.getCashAccountList()) && Objects.equals(getPersonalBankAccount(), that.getPersonalBankAccount());
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCashAccountList(), getPersonalBankAccount());
    }
}
