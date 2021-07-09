package switchtwentytwenty.project.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import switchtwentytwenty.project.dto.authentication.JwtResponseDTO;
import switchtwentytwenty.project.dto.authentication.LoginRequestDTO;
import switchtwentytwenty.project.security.jwt.JwtUtils;
import switchtwentytwenty.project.security.service.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthControllerTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtUtils jwtUtils;
    @Mock
    UserDetailsImpl userDetails;
    @InjectMocks
    AuthController controller;

    @Test
    void ensureAuthControllerIsWorking() {
        assertNotNull(controller);
    }

    @Test
    void ensureResponseEntityIsOK() {
        LoginRequestDTO login = new LoginRequestDTO("rui@gmail.com", "password");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(token)).thenReturn(auth);

        Authentication authentication = authenticationManager.authenticate(token);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(jwtUtils.generateJwtToken(authentication)).thenReturn("token");

        String jwt = jwtUtils.generateJwtToken(authentication);

        when(userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList())).thenReturn(new ArrayList<>());

        List<String> role = new ArrayList<>();

        when(userDetails.getUsername()).thenReturn("rui@gmail.com");
        when(userDetails.getPersonName()).thenReturn("Rui");
        when(userDetails.getFamilyId()).thenReturn(1L);

        ResponseEntity<?> result = controller.authenticateUser(login);

        ResponseEntity<?> expected = ResponseEntity.ok(new JwtResponseDTO(jwt, userDetails.getUsername(), userDetails.getPersonName(), userDetails.getFamilyId(), role));

        assertNotNull(result);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void ensureResponseEntityIsReturningBadRequest() {
        LoginRequestDTO login = new LoginRequestDTO("rui@gmail.com", "password");

        ResponseEntity result = controller.authenticateUser(login);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

}