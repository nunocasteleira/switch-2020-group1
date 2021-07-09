package switchtwentytwenty.project.security.jwt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenFilterTest {
    @Test
    void ensureNotNull() {
        AuthTokenFilter authTokenFilter = new AuthTokenFilter();
        assertNotNull(authTokenFilter);
    }

}