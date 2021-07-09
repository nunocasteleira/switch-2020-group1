package switchtwentytwenty.project.domain.exceptions;

public class InvalidEmailException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidEmailException(String errorMessage) {
        super(errorMessage);
    }
}
