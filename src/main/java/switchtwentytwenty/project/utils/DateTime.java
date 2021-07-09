package switchtwentytwenty.project.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateTime {

    private DateTime() {
    }

    public static LocalDateTime parseDate(String date) {
        LocalDateTime dateTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy[ HH:mm]");
        TemporalAccessor temporalAccessor = formatter.parseBest(date, LocalDateTime::from, LocalDate::from);
        if (temporalAccessor instanceof LocalDateTime) {
            dateTime = (LocalDateTime) temporalAccessor;
        } else {
            dateTime = ((LocalDate) temporalAccessor).atStartOfDay();
        }
        return dateTime;
    }
}
