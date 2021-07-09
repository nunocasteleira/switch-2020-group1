package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import switchtwentytwenty.project.applicationservices.implservices.FamilyMemberService;
import switchtwentytwenty.project.applicationservices.implservices.PersonService;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.InvalidEmailException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.dto.person.EmailListDTO;
import switchtwentytwenty.project.dto.person.ProfileInformationDTO;
import switchtwentytwenty.project.security.service.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@ExtendWith(MockitoExtension.class)
@Profile("PersonControllerTest")
class PersonControllerTest {

    @Mock
    FamilyMemberService familyMemberService;

    @Mock
    PersonService personService;

    @Mock
    SecurityContextHolder securityContextHolder;

    @Mock
    UserDetailsImpl userDetails;

    @InjectMocks
    PersonController controller;


    @Test
    void getProfileInformationDTOFromAnExistingPerson() {
        //Arrange
        Link selfLink = linkTo(PersonController.class).slash("fabio@gmail.com").withSelfRel();

        Link familyInformationLink = linkTo(FamilyController.class).slash(12).withRel("accountList");

        Link accountListLink =
                linkTo(AccountController.class).slash("fabio@gmail.com").withRel("accountList");

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("fabio@gmail.com");

        when(familyMemberService.getProfileInformation("fabio@gmail.com", "fabio@gmail.com")).thenReturn(new ProfileInformationDTO("Fabio",
                "fabio@gmail.com", "09/11/1988", "227475987", "Rua das Flores Porto 4444-333",
                new ArrayList<>(), new ArrayList<>(), 12, "Maia", false).add(selfLink, familyInformationLink, accountListLink));

        //Act
        ResponseEntity<Object> profileInformationDTO =
                controller.getProfileInformation("fabio@gmail.com");

        //Assert
        assertNotNull(profileInformationDTO);
        assertEquals(HttpStatus.OK, profileInformationDTO.getStatusCode());
    }

    @Test
    void getProfileInformationDTOFromAnNonExistingPerson() {
        //Arrange
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>("Person does not exist.",
                HttpStatus.BAD_REQUEST);

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("gervasio@hotmail.com");

        when(familyMemberService.getProfileInformation("gervasio@hotmail.com", "gervasio@hotmail.com")).thenThrow(new ObjectDoesNotExistException("Person does not exist."));

        //Act
        ResponseEntity<Object> result = controller.getProfileInformation("gervasio@hotmail.com");

        //Assert
        assertEquals(expectedResponse.getStatusCode(), result.getStatusCode());
        assertEquals(expectedResponse.getBody(), result.getBody());
    }

//    @Test
//    void getProfileInformationDTOFromTwoExistingPersons() {
//        //Arrange
//        String expectedFirstPersonName = "Fabio";
//
//        Authentication auth = mock(Authentication.class);
//        SecurityContext securityContext = mock(SecurityContext.class);
//        when(securityContext.getAuthentication()).thenReturn(auth);
//        SecurityContextHolder.setContext(securityContext);
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
//        when(userDetails.getUsername()).thenReturn("fabio@gmail.com");
//
//        lenient().when(familyMemberService.getProfileInformation("fabio@gmail.com", "fabio@gmail.com")).thenReturn(new ProfileInformationDTO(expectedFirstPersonName,
//                "fabio@gmail.com", "09/11/1988", "227475987", "Rua das Flores Porto 4444-333",
//                new ArrayList<>(), new ArrayList<>(), 12, "Maia", false));
//
//        String expectedSecondPersonName = "Gervasio";
//
//        Authentication auth2 = mock(Authentication.class);
//        SecurityContext securityContext2 = mock(SecurityContext.class);
//        when(securityContext2.getAuthentication()).thenReturn(auth2);
//        SecurityContextHolder.setContext(securityContext2);
//        UserDetailsImpl userDetails2 = mock(UserDetailsImpl.class);
//        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails2);
//        when(userDetails2.getUsername()).thenReturn("gervasio@gmail.com");
//
//        lenient().when(familyMemberService.getProfileInformation("gervasio@gmail.com", "gervasio@gmail.com")).thenReturn(new ProfileInformationDTO(expectedSecondPersonName,
//                "gervasio@gmail.com", "09/11/1988", "227475985", "Rua das Flores Porto 4444-333",
//                new ArrayList<>(), new ArrayList<>(), 11, "Silva", true));
//
//        //Act
//        ResponseEntity<Object> firstProfileInformationDTO = controller.getProfileInformation( "fabio@gmail.com");
//        ResponseEntity<Object> secondProfileInformationDTO =
//                controller.getProfileInformation("gervasio@gmail.com");
//
//        //Assert
//        assertNotNull(firstProfileInformationDTO);
//        assertNotNull(secondProfileInformationDTO);
//        assertNotSame(firstProfileInformationDTO.getBody(), secondProfileInformationDTO.getBody());
//        assertEquals(firstProfileInformationDTO.getStatusCode(), secondProfileInformationDTO.getStatusCode());
//    }

    @Test
    void ensureEmailIsAdded() {
        //Arrange
        String id = "fabio@gmail.com";
        String emailAddress = "joao@gmail.com";
        HttpStatus expected = HttpStatus.CREATED;

        List<String> dtoList = new ArrayList<>();
        dtoList.add("joao@gmail.com");
        EmailListDTO expectedList = new EmailListDTO(dtoList);

        given(personService.addEmail(id, emailAddress)).willReturn(new EmailListDTO(dtoList));

        //Act
        ResponseEntity<Object> result = controller.addEmail(id, emailAddress);

        //Assert
        assertNotNull(result);
        assertEquals(expected, result.getStatusCode());
        assertEquals(expectedList, result.getBody());
    }

    @Test
    void ensureRepeatedEmailIsNotAdded() {
        //Arrange
        String id = "fabio@gmail.com";
        String emailAddress = "fabio@gmail.com";
        ResponseEntity<Object> expected = new ResponseEntity<>("Email already exists",
                HttpStatus.BAD_REQUEST);

        when(personService.addEmail(id, emailAddress)).thenThrow(new DuplicateObjectException("Email already exists"));

        //Act
        ResponseEntity<Object> result = controller.addEmail(id, emailAddress);
        //Assert
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void ensureEmailIsRemoved() {
        //Arrange
        String id = "fabio@gmail.com";
        String emailAddress = "fabio2@gmail.com";
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add(id);
        emailAddresses.add(emailAddress);
        EmailListDTO emailListDTO = new EmailListDTO(emailAddresses);

        ResponseEntity<Object> expected = new ResponseEntity<>(emailListDTO, HttpStatus.OK);

        when(personService.removeEmail(id, emailAddress)).thenReturn(emailListDTO);

        //Act
        ResponseEntity<Object> result = controller.removeEmail(id, emailAddress);
        //Assert
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void ensurePrimaryEmailIsNotRemoved() {
        //Arrange
        String id = "fabio@gmail.com";
        String emailAddress = "fabio@gmail.com";
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add(id);
        emailAddresses.add(emailAddress);
        EmailListDTO emailListDTO = new EmailListDTO(emailAddresses);

        ResponseEntity<Object> expected = new ResponseEntity<>("Can't remove primary email", HttpStatus.BAD_REQUEST);

        when(personService.removeEmail(id, emailAddress)).thenThrow(new InvalidEmailException("Can't remove primary email"));

        //Act
        ResponseEntity<Object> result = controller.removeEmail(id, emailAddress);
        //Assert
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void ensureEmailIsNotRemovedWhenDoesNotExist() {
        //Arrange
        String id = "fabio@gmail.com";
        String emailAddress = "fabio2@gmail.com";
        ResponseEntity<Object> expected = new ResponseEntity<>("Email does not exist",
                HttpStatus.BAD_REQUEST);

        when(personService.removeEmail(id, emailAddress)).thenThrow(new ObjectDoesNotExistException("Email does not exist"));

        //Act
        ResponseEntity<Object> result = controller.removeEmail(id, emailAddress);
        //Assert
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }
}
