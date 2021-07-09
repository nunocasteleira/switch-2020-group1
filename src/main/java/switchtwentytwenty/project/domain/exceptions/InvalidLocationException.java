package switchtwentytwenty.project.domain.exceptions;

public class InvalidLocationException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidLocationException(String errorMessage) {
        super(errorMessage);
    }
}
