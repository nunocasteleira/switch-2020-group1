package switchtwentytwenty.project.domain.exceptions;

public class InvalidRelationshipType extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public InvalidRelationshipType(String errorMessage) {
        super(errorMessage);
    }
}
