package switchtwentytwenty.project.domain.model.shared;

import switchtwentytwenty.project.domain.exceptions.InvalidNameException;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class CategoryName implements ValueObject {
    private final String name;

    public CategoryName(String name) {
        checkStringName(name);
        this.name = name.toUpperCase();
        validate();

    }

    /**
     * Method to validate if the name and format name are valid.
     */
    private void validate() {
        if (!validateStringName()) {
            throw new InvalidNameException("Invalid Category Name.");
        }
    }

    /**
     * Method to validate if the name of the category is valid.
     *
     * @return true if valid name, false otherwise
     */
    private boolean validateStringName() {
        if (this.name.isEmpty() || this.name.trim().length() == 0) {
            return false;
        }
        return checkFormat(this.name);
    }

    /**
     * Method to check if the format of the category name is valid.
     * <p>
     * Extracted from https://stackoverflow.com/questions/336210/regular-expression-for-alphanumeric-and-underscores
     *
     * @param name name of the category
     * @return true if valid format name, false otherwise
     */
    private boolean checkFormat(String name) {
        String alphanumericRegex = "^[a-zA-Z0-9 !.,-]*$";
        Pattern pat = Pattern.compile(alphanumericRegex);

        return pat.matcher(name).matches();
    }

    private void checkStringName(String name) {
        if (name == null) {
            throw new InvalidNameException("Invalid Category Name.");
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
        CategoryName that = (CategoryName) o;
        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
