package switchtwentytwenty.project.domain.exceptions;

public class InvalidStreetException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidStreetException(String errorMessage) {
        super(errorMessage);
    }
}
