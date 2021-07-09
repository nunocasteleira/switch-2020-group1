package switchtwentytwenty.project.security.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.factories.PersonFactory;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.person.PersonVOs;
import switchtwentytwenty.project.repositories.PersonRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("UserDetailsServiceImplTest")
class UserDetailsServiceImplTest {
    @Mock
    PersonRepository personRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsernameCorrectly() {
        Email email = new Email("bla@gmail.com");
        PersonName personName = new PersonName("bla");
        Address address = new Address("la la la", "la", "4433-001");
        BirthDate birthDate = new BirthDate("03/07/1990");
        PersonVat vat = new PersonVat("222222888");
        FamilyId familyId = new FamilyId(1L);

        PersonVOs personVOs = new PersonVOs(email, personName, address, birthDate, vat, "914444555");
        Person person = PersonFactory.buildPerson(personVOs, familyId);
        UserDetailsImpl expected = UserDetailsImpl.build(person);

        when(personRepository.getByEmail(email)).thenReturn(person);

        UserDetails result = userDetailsService.loadUserByUsername(email.getEmailAddress());

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void loadUserByUsernameThrowsErrorWhenUserDoesntExist() {
        String emailAddress = "nonexistinguser@gmail.com";
        Email email = new Email(emailAddress);
        when(personRepository.getByEmail(email)).thenThrow(IllegalArgumentException.class);

        assertThrows(ObjectDoesNotExistException.class, () -> userDetailsService.loadUserByUsername(emailAddress));
    }
}