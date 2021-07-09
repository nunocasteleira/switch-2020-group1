package switchtwentytwenty.project.domain.model.shared;

import lombok.Getter;
import switchtwentytwenty.project.domain.model.interfaces.ValueObject;
import switchtwentytwenty.project.utils.DateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TransactionDate implements ValueObject {
    @Getter
    private final LocalDateTime date;

    public TransactionDate(String date) {
        this.date = DateTime.parseDate(date);
    }

    public boolean isAfter(TransactionDate date) {
        return this.date.isAfter(date.date) || this.date.isEqual(date.date);
    }

    public boolean isBefore(TransactionDate date) {
        return this.date.isBefore(date.date.plusDays(1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDate)) {
            return false;
        }
        TransactionDate that = (TransactionDate) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.format(formatter);
    }
}

