package switchtwentytwenty.project.domain.model.shared;

import switchtwentytwenty.project.domain.exceptions.InvalidNameException;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class PersonName implements ValueObject {
    private final String name;

    public PersonName(String name) {
        this.name = name;
        validate();
    }

    private void validate() {
        validatePersonName();
        checkFormat();
    }

    /**
     * this method checks if the name is not null, empty and has a size != than 0
     */
    private void validatePersonName() {
        if (this.name == null || this.name.isEmpty() || this.name.trim().length() == 0) {
            throw new InvalidNameException("Invalid name");
        }
    }

    /**
     * this method check if the name is in the proper format, like with no numbers
     */
    private void checkFormat() {
        String alphaRegex = "^[-'a-zA-ZÀ-ÖØ-öø-ÿ\\s]*$"; //with spaces and special chars

        Pattern pat = Pattern.compile(alphaRegex);
        if (!pat.matcher(this.name).matches()) {
            throw new InvalidNameException("Invalid name");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonName that = (PersonName) o;
        return name.equals(that.name);
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

