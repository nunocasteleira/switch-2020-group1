package switchtwentytwenty.project.domain.model.shared;

import switchtwentytwenty.project.domain.exceptions.InvalidPhoneNumberException;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class PhoneNumber implements ValueObject {
    private final String telephone;

    public PhoneNumber(String phoneNumber) {
        if (!validatePhoneNumber(phoneNumber)) {
            throw new InvalidPhoneNumberException("This phone number is not valid");
        }
        this.telephone = phoneNumber;

    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }

        return checkFormat(phoneNumber);
    }

    private boolean checkFormat(String phoneNumber) {
        String phoneNumberRegex = "^9[1236][0-9]{7}$|^2[1-9][0-9]{7}$";

        Pattern pat = Pattern.compile(phoneNumberRegex);
        return pat.matcher(phoneNumber).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(telephone, that.telephone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telephone);
    }

    @Override
    public String toString() {
        return telephone;
    }
}
