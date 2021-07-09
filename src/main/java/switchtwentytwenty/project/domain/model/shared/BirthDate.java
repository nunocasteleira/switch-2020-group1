package switchtwentytwenty.project.domain.model.shared;

import switchtwentytwenty.project.domain.exceptions.InvalidDateException;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BirthDate implements ValueObject {
    private final LocalDate date;

    public BirthDate(String date) {
        this.date = parseDate(date);
        validateBirthDate();
    }

    /**
     * ensures that the date is not null or after the system date
     */
    private void validateBirthDate() {
        if (this.date.isAfter(LocalDate.now())) {
            throw new InvalidDateException("The birthDate is not valid.");
        }
    }

    /**
     * parses the string to a localDate
     *
     * @param birthDate string
     * @return the localDate
     */
    private LocalDate parseDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(birthDate, formatter);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}
