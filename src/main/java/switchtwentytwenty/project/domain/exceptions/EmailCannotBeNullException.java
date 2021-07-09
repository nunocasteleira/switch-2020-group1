package switchtwentytwenty.project.domain.exceptions;


public class EmailCannotBeNullException extends IllegalArgumentException{
    private static final long serialVersionUID = 0;

    public EmailCannotBeNullException(String errorMessage) {
        super(errorMessage);
    }
}
