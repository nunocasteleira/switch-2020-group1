package switchtwentytwenty.project.dto.account;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class AccountOutputDTO extends RepresentationModel<AccountOutputDTO> {
    @Getter
    @Setter
    private long accountId;
    @Getter
    @Setter
    private double initialAmount;
    @Getter
    @Setter
    private String currency;
    @Getter
    @Setter
    private String accountDescription;
    @Getter
    @Setter
    private String provider;

    public AccountOutputDTO(long accountId, double initialAmount, String eur, String description) {
        this.accountId = accountId;
        this.initialAmount = initialAmount;
        this.currency = eur;
        this.accountDescription = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountOutputDTO)) {
            return false;
        }
        AccountOutputDTO that = (AccountOutputDTO) o;
        return accountId == that.accountId && Double.compare(that.initialAmount, initialAmount) == 0 && Objects.equals(currency, that.currency) && Objects.equals(accountDescription, that.accountDescription) && Objects.equals(provider, that.provider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountId, initialAmount, currency, accountDescription, provider);
    }
}
