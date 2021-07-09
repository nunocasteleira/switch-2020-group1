package switchtwentytwenty.project.domain.exceptions;

public class InvalidAmountException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidAmountException(String errorMessage) {
        super(errorMessage);
    }
}