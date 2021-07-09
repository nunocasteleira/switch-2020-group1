package switchtwentytwenty.project.domain.exceptions;

public class InvalidDateException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidDateException(String errorMessage) {
        super(errorMessage);
    }
}
