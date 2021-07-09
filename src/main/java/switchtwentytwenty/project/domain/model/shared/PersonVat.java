package switchtwentytwenty.project.domain.model.shared;

import switchtwentytwenty.project.domain.exceptions.InvalidVatNumberException;
import switchtwentytwenty.project.domain.exceptions.ObjectCanNotBeNullException;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.util.regex.Pattern;

public class PersonVat implements ValueObject {
    public final String vat;

    public PersonVat(String vat) {
        this.vat = vat;
        validate();
    }

    private void validate() {
        validateString();
        validateFormat();
    }

    /**
     * this method checks if the vat number is not null, empty and has a size != than 0
     */
    private void validateString() {
        if (this.vat == null || this.vat.isEmpty() || this.vat.trim().length() == 0) {
            throw new ObjectCanNotBeNullException("VAT is not valid.");
        }
    }

    /**
     * this method ensures that only 9 digits numbers are accepted, with the first one being 1, 2 or
     * 3
     */
    private void validateFormat() {
        String vatRegex = "^[123][0-9]{8}$";
        Pattern pattern = Pattern.compile(vatRegex);
        if (!pattern.matcher(this.vat).matches()) {
            throw new InvalidVatNumberException("VAT doesn't have a valid format.");
        }
    }

    @Override
    public String toString() {
        return this.vat;
    }
}
