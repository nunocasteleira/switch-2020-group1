package switchtwentytwenty.project.domain.exceptions;

public class InvalidNameException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidNameException(String errorMessage) {
        super(errorMessage);
    }
}
