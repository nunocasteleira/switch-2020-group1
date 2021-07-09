package switchtwentytwenty.project.dto.person;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.person.EmailListDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmailListDTOTest {

    @Test
    void getEmailList() {

        //Arrange
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");

        //Act
        EmailListDTO emailListDTO = new EmailListDTO(emailAddresses);
        List<String> actualEmailAddresses = emailListDTO.getEmailAddresses();


        //Assert
        assertNotNull(emailListDTO);
        assertEquals(actualEmailAddresses, emailAddresses);
    }

    @Test
    void testEquals() {
        //Arrange
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");

        List<String> emailAddressesDiff = new ArrayList<>();
        emailAddressesDiff.add("abcd@hotmail.com");
        emailAddressesDiff.add("defh@gmail.com");

        //Act
        EmailListDTO emailListDTO = new EmailListDTO(emailAddresses);
        EmailListDTO emailListDTOSame = emailListDTO;
        EmailListDTO emailListDTOOther = new EmailListDTO(emailAddresses);
        EmailListDTO emailListDTODifferent = new EmailListDTO(emailAddressesDiff);

        //Assert
        assertNotNull(emailListDTO);
        assertEquals(emailListDTO, emailListDTOSame);
        assertSame(emailListDTO, emailListDTOSame);
        assertEquals(emailListDTO.hashCode(), emailListDTOSame.hashCode());
        assertNotEquals(0, emailListDTO.hashCode());
        assertEquals(emailListDTO, emailListDTOOther);
        assertNotSame(emailListDTO, emailListDTOOther);
        assertEquals(emailListDTO.hashCode(), emailListDTOOther.hashCode());
        assertNotEquals(emailListDTO, emailAddresses);
        assertNotEquals(emailListDTO,emailAddressesDiff);
        assertNotEquals(emailListDTO,emailListDTODifferent);

    }
}
