package switchtwentytwenty.project.domain.exceptions;

public class InvalidPhoneNumberException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidPhoneNumberException(String errorMessage) {
        super(errorMessage);
    }
}
