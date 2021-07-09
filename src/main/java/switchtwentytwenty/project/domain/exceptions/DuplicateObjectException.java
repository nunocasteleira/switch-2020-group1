package switchtwentytwenty.project.domain.exceptions;

public class DuplicateObjectException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public DuplicateObjectException(String errorMessage) {
        super(errorMessage);
    }
}
