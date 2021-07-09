package switchtwentytwenty.project.domain.model.shared;


import lombok.Getter;
import switchtwentytwenty.project.domain.exceptions.InvalidLocationException;
import switchtwentytwenty.project.domain.exceptions.InvalidPostCodeException;
import switchtwentytwenty.project.domain.exceptions.InvalidStreetException;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.util.regex.Pattern;

public class Address implements ValueObject {
    @Getter
    private final String street;
    @Getter
    private final String location;
    @Getter
    private final String postalCode;

    public Address(String street, String location, String postalCode) {
        this.street = street;
        this.postalCode = postalCode;
        this.location = location;
        validate(street, location, postalCode);
    }

    private void validate(String street, String location, String postalCode) {
        validateStreet(street);
        validateLocation(location);
        validatePostalCode(postalCode);
    }

    /**
     * this method checks if the street is not null, empty and has a size != than 0
     *
     * @param street street
     */
    private void validateStreet(String street) {
        if (street == null || street.isEmpty() || !checkFormatStreet(street)) {
            throw new InvalidStreetException("Invalid Street address");
        }
    }

    private boolean checkFormatStreet(String street) {
        String streetRegex = "^[-'a-zA-ZÀ-ÖØ-öø-ÿ-º\\s,.]+[-'a-zA-ZÀ-ÖØ-öø-ÿ-º-[0-9]\\s,.]*$"; //with spaces and special chars

        Pattern pat = Pattern.compile(streetRegex);
        return pat.matcher(street).matches();
    }

    /**
     * this method checks if the location is not null, empty and has a size != than 0
     *
     * @param location location
     */
    private void validateLocation(String location) {
        if (location == null || location.isEmpty() || !checkFormatLocation(location)) {
            throw new InvalidLocationException("Invalid Location");
        }
    }

    private boolean checkFormatLocation(String location) {
        String locationRegex = "^[-'a-zA-ZÀ-ÖØ-öø-ÿ\\s]*$"; //with spaces and special chars

        Pattern pat = Pattern.compile(locationRegex);
        return pat.matcher(location).matches();
    }

    /**
     * this method checks if the postal code is not null, empty and has a size != than 0
     *
     * @param postalCode postal code
     */
    private void validatePostalCode(String postalCode) {
        if (postalCode == null || postalCode.isEmpty() || !checkFormatPostalCode(postalCode)) {
            throw new InvalidPostCodeException("Invalid Postal Code");
        }
    }

    /**
     * ensures that postal code starts with a number between 1-9 and has de format 4digits-3digits
     *
     * @param postalCode postal code
     * @return true if it is in the intended format, false if its not
     */
    private boolean checkFormatPostalCode(String postalCode) {
        String postalCodeRegex = "^[1-9][0-9]{3}-[0-9]{3}$";

        Pattern pat = Pattern.compile(postalCodeRegex);
        return pat.matcher(postalCode).matches();
    }

    @Override
    public String toString() {
        return street + ", " +location + ", " + postalCode;
    }
}
