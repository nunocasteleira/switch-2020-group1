package switchtwentytwenty.project.domain.exceptions;

public class InvalidProviderException extends IllegalArgumentException {

    private static final long serialVersionUID = 0;

    public InvalidProviderException(String errorMessage) {
        super(errorMessage);
    }
}
