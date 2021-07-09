package switchtwentytwenty.project.dto.person;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.person.PersonDTO;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {

    @Test
    void ensurePersonDTOIsWorkingProperly(){
        Email email = new Email("test@gmail.com");
        PersonName name = new PersonName("teste");
        Address address = new Address("Rua torta", "Direito", "4444-001");
        BirthDate birthDate = new BirthDate("03/04/1990");
        PersonVat vat = new PersonVat("232222444");

        PersonDTO personDTO = new PersonDTO(email, name, address, birthDate, vat);

        assertNotNull(personDTO);
        assertEquals(email, personDTO.getId());
        assertEquals(name, personDTO.getName());
        assertEquals(address, personDTO.getAddress());
        assertEquals(birthDate, personDTO.getBirthdate());
        assertEquals(vat, personDTO.getVat());
    }
}