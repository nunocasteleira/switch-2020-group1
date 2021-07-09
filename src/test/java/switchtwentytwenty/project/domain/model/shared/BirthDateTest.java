package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import switchtwentytwenty.project.domain.exceptions.InvalidDateException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class BirthDateTest {

    @Test
    void createBirthDateTwentiethSeptemberNinetyThree() {
        //Arrange
        String date = "20/09/1993";
        //Act
        BirthDate birthDate = new BirthDate(date);
        //Assert
        assertNotNull(birthDate);
    }

    @Test
    void createBirthDateNinthNovemberEightyEight() {
        //Arrange
        String date = "09/11/1988";

        //Act
        BirthDate birthDate = new BirthDate(date);
        //Assert
        assertNotNull(birthDate);
    }

    @Test
    void createBirthDateToday() {
        //Arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = LocalDate.now().format(formatter);
        //Act
        BirthDate birthDate = new BirthDate(date);
        //Assert
        assertNotNull(birthDate);
    }

    @Test
    void createBirthDateTomorrow() {
        //Arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = LocalDate.now().plusDays(1).format(formatter);
        //Assert
        assertThrows(InvalidDateException.class, () -> new BirthDate(date));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "3-12-5132"})
    void createInvalidBirthDate(String candidate) {
        assertThrows(DateTimeParseException.class, () -> new BirthDate(candidate));
    }

    @Test
    void createNullBirthDate() {
        //Assert
        assertThrows(NullPointerException.class, () -> new BirthDate(null));
    }

    @Test
    void testToString() {
        //Arrange
        String expected = "20/09/1993";
        BirthDate birthDate = new BirthDate(expected);
        //Act
        String result = birthDate.toString();
        //Assert
        assertEquals(expected, result);
    }
}
