package switchtwentytwenty.project.domain.exceptions;

public class ObjectDoesNotExistException extends NullPointerException {
    private static final long serialVersionUID = 0;

    public ObjectDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
