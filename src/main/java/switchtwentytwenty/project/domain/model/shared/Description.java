package switchtwentytwenty.project.domain.model.shared;

import lombok.Getter;
import switchtwentytwenty.project.domain.exceptions.InvalidDescriptionException;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.util.Objects;

public class Description implements ValueObject {
    @Getter
    private String accountDescription;

    /**
     * Constructor method for Value Object AccountDescription.
     *
     * @param accountDescription String description for account
     */
    public Description(String accountDescription) {
        this.accountDescription = accountDescription;
        validateDescription(accountDescription);
    }

    /**
     * Method to validate description for the Account.
     *
     * @param description String description for account
     */
    private void validateDescription(String description) {
        if (description == null || description.isEmpty() || description.trim().length() == 0) {
            throw new InvalidDescriptionException("Invalid description.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Description)) {
            return false;
        }
        Description that = (Description) o;
        return Objects.equals(accountDescription, that.accountDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountDescription);
    }
}
