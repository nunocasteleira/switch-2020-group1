package switchtwentytwenty.project.dto.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeDTOTest {

    @Test
    void createDateRangeDTOSuccessfully() {
        String startDate = "12/01/2020";
        String endDate = "13/01/2020";
        DateRangeDTO dateRangeDTO = new DateRangeDTO(startDate, endDate);

        assertNotNull(dateRangeDTO);
        assertEquals(startDate, dateRangeDTO.getStartDate());
        assertEquals(endDate, dateRangeDTO.getEndDate());
    }

    @Test
    void setStartDate() {
        String startDate = "12/01/2020";
        String endDate = "13/01/2020";
        DateRangeDTO dateRangeDTO = new DateRangeDTO();
        dateRangeDTO.setStartDate(startDate);
        dateRangeDTO.setEndDate(endDate);

        assertNotNull(dateRangeDTO);
        assertEquals(startDate, dateRangeDTO.getStartDate());
        assertEquals(endDate, dateRangeDTO.getEndDate());
    }

    @Test
    void testEquals() {
        String startDate = "12/01/2020";
        String differentStartDate = "13/01/2020";
        String endDate = "14/01/2020";
        String differentEndDate = "15/01/2020";
        DateRangeDTO dateRangeDTO = new DateRangeDTO(startDate, endDate);
        //noinspection UnnecessaryLocalVariable
        DateRangeDTO dateRangeDTOSame = dateRangeDTO;
        DateRangeDTO dateRangeDTOOther = new DateRangeDTO(startDate, endDate);
        DateRangeDTO dateRangeDTODifferentStart = new DateRangeDTO(differentStartDate, endDate);
        DateRangeDTO dateRangeDTODifferentEnd = new DateRangeDTO(startDate, differentEndDate);

        assertEquals(dateRangeDTO, dateRangeDTOSame);
        assertSame(dateRangeDTO, dateRangeDTOSame);
        assertEquals(dateRangeDTO.hashCode(), dateRangeDTOSame.hashCode());
        assertEquals(dateRangeDTO, dateRangeDTOOther);
        assertEquals(dateRangeDTO.hashCode(), dateRangeDTOOther.hashCode());
        assertNotEquals(0, dateRangeDTO.hashCode());
        assertNotEquals(dateRangeDTO, dateRangeDTODifferentStart);
        assertNotEquals(dateRangeDTO.hashCode(), dateRangeDTODifferentStart.hashCode());
        assertNotEquals(dateRangeDTO, dateRangeDTODifferentEnd);
        assertNotEquals(dateRangeDTO.hashCode(), dateRangeDTODifferentEnd.hashCode());
        assertNotEquals(dateRangeDTO, startDate);
    }
}