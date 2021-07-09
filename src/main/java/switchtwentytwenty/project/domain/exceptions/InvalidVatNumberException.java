package switchtwentytwenty.project.domain.exceptions;

public class InvalidVatNumberException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidVatNumberException(String errorMessage) {
        super(errorMessage);
    }
}
