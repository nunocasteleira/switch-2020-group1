package switchtwentytwenty.project.domain.model.shared;

import switchtwentytwenty.project.domain.model.interfaces.ValueObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class RegistrationDate implements ValueObject {
    private final LocalDate date;

    /**
     * Constructor for the RegistrationDate object.
     */
    public RegistrationDate() {
        this.date = LocalDate.now();
    }

    public RegistrationDate(String date) {
        this.date = parseDate(date.trim());
    }

    private LocalDate parseDate(String registrationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        return LocalDate.parse(registrationDate, formatter);
    }

    /**
     * This method allows to obtain the registration date of the family.
     *
     * @return registration date of the family.
     */
    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistrationDate that = (RegistrationDate) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}
