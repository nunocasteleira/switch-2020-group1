package switchtwentytwenty.project.domain.model.shared;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import switchtwentytwenty.project.domain.exceptions.EmailCannotBeNullException;
import switchtwentytwenty.project.domain.exceptions.InvalidEmailException;
import switchtwentytwenty.project.domain.model.interfaces.Id;

import java.util.Objects;
import java.util.regex.Pattern;

@NoArgsConstructor
public class Email implements Id {
    @Getter
    @Setter
    private String emailAddress;

    public Email(String emailAddress) {
        this.emailAddress = emailAddress;
        validate();
    }

    private void validate() {
        validateString();
        validateFormat();
    }

    /**
     * this method checks if the email is not null, empty and has a size != than 0
     */
    private void validateString() {
        if (this.emailAddress == null || this.emailAddress.isEmpty() || this.emailAddress.trim().length() == 0) {
            throw new EmailCannotBeNullException("The e-mail can not be null");
        }
    }

    /**
     * this method ensures that the e-mail has the @ sign and a dot after that
     */
    private void validateFormat() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(this.emailAddress).matches()) {
            throw new InvalidEmailException("E-mail Address is not valid.");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(emailAddress, email.emailAddress);
    }

    @Override
    public String toString() {
        return this.emailAddress;
    }
}
