package switchtwentytwenty.project.domain.exceptions;

public class ObjectCanNotBeNullException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public ObjectCanNotBeNullException(String errorMessage) {
        super(errorMessage);
    }
}
