package switchtwentytwenty.project.domain.exceptions;

public class InvalidPostCodeException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidPostCodeException(String errorMessage) {
        super(errorMessage);
    }
}
