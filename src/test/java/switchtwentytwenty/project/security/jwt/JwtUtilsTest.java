package switchtwentytwenty.project.security.jwt;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import switchtwentytwenty.project.security.service.UserDetailsImpl;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    @InjectMocks
    JwtUtils jwtUtils;

    @Test
    void ensureNotNull() {
        assertNotNull(jwtUtils);
    }

    @Test
    void getEmailFromJwtToken() {
        String email = "email@gmail.com";
        String password = "password";
        String personName = "Jules Caesar";
        long familyId = 1L;
        UserDetailsImpl userDetails = new UserDetailsImpl(email, password, personName, familyId, Collections.EMPTY_SET);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "groupOneSecret");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 86400000);
        String token = jwtUtils.generateJwtToken(authentication);

        String result = jwtUtils.getEmailFromJwtToken(token);

        assertEquals(email, result);
    }

    @Test
    void validateJwtToken() {
        String email = "email@gmail.com";
        String password = "password";
        String personName = "Jules Caesar";
        long familyId = 1L;
        UserDetailsImpl userDetails = new UserDetailsImpl(email, password, personName, familyId, Collections.EMPTY_SET);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "groupOneSecret");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 86400000);
        String token = jwtUtils.generateJwtToken(authentication);

        boolean result = jwtUtils.validateJwtToken(token);

        assertTrue(result);
    }

    @Test
    void generateJwtToken() {
        String email = "email@gmail.com";
        String password = "password";
        String personName = "Jules Caesar";
        long familyId = 1L;
        UserDetailsImpl userDetails = new UserDetailsImpl(email, password, personName, familyId, Collections.EMPTY_SET);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "groupOneSecret");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 86400000);

        String result = jwtUtils.generateJwtToken(authentication);
        assertNotNull(result);
        assertNotEquals(Strings.EMPTY, result);
    }
}