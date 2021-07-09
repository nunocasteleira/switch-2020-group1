package switchtwentytwenty.project.domain.exceptions;

public class InvalidDescriptionException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidDescriptionException(String errorMessage) {
        super(errorMessage);
    }
}