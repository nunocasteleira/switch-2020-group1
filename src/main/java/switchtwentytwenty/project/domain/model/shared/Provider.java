package switchtwentytwenty.project.domain.model.shared;

import lombok.Getter;
import lombok.ToString;
import switchtwentytwenty.project.domain.exceptions.InvalidProviderException;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;
import java.util.Objects;

@ToString
public class Provider implements ValueObject {

    @Getter
    private final String accountProvider;

    /**
     * This is the constructor for the account provider
     * @param accountProvider the bank where the account is allocated
     */
    public Provider(String accountProvider) {
        this.accountProvider = accountProvider;
        validateProvider(accountProvider);
    }


    /**
     * this method ensures that the provider can not be null or empty
     * @param provider the bank where the account is allocated
     */
    private void validateProvider(String provider) {
        if (provider == null || provider.isEmpty()) {
            throw new InvalidProviderException("Please enter the provider name");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Provider provider1 = (Provider) o;
        return Objects.equals(accountProvider, provider1.accountProvider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountProvider);
    }
}
