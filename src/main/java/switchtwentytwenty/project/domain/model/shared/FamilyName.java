package switchtwentytwenty.project.domain.model.shared;

import switchtwentytwenty.project.domain.exceptions.InvalidNameException;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class FamilyName implements ValueObject {
    private final String name;

    /**
     * Constructor for the FamilyName object.
     *
     * @param name name of the family to which the family will be registered.
     */
    public FamilyName(String name) {
        this.name = name;
        validate();
    }

    /**
     * This method allows to validate the family name.
     */
    private void validate() {
        validateString();
        validateFormat();
    }

    /**
     * This method allows to validate that the name is not null, empty and has a size different than
     * zero.
     */
    private void validateString() {
        if (this.name == null || this.name.isEmpty()) {
            throw new InvalidNameException("Invalid name.");
        }
    }

    /**
     * This method allows to validate that the name has spaces and special chars.
     */
    private void validateFormat() {
        String nameRegex = "^[-'a-zA-ZÀ-ÖØ-öø-ÿ\\s]*$";
        Pattern pattern = Pattern.compile(nameRegex);
        if (!pattern.matcher(this.name).matches()) {
            throw new InvalidNameException("Invalid name.");
        }
    }

    /**
     * This method allows to obtain the name of the family.
     *
     * @return name of the family.
     */
    public String getPersonName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilyName that = (FamilyName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
