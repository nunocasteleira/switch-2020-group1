package switchtwentytwenty.project.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateTimeTest {

    @Test
    void getLocalDateTimeFromStringWithCorrectStringFormat() {
        String inputDate = "12/09/2021 13:00";
        LocalDateTime expectedLocalDateTime = LocalDateTime.of(2021, 9, 12, 13, 0);

        LocalDateTime resultLocalDateTime = DateTime.parseDate(inputDate);

        assertEquals(expectedLocalDateTime, resultLocalDateTime);
    }

    @Test
    void getLocalDateTimeFromStringWithTimeFirstAndDateAfter() {
        String inputDate = "13:00 12/09/2021";

        assertThrows(DateTimeParseException.class, () -> DateTime.parseDate(inputDate));
    }

    @Test
    void getLocalDateTimeFromStringWithReversedDateFormat() {
        String inputDate = "2021/09/12 13:00";

        assertThrows(DateTimeParseException.class, () -> DateTime.parseDate(inputDate));
    }

    @Test
    void getLocalDateTimeFromStringWithtwoDigitYear() {
        String inputDate = "12/09/21 13:00";

        assertThrows(DateTimeParseException.class, () -> DateTime.parseDate(inputDate));
    }
}
