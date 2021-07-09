package switchtwentytwenty.project.security.jwt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthEntryPointJwtTest {

    @Test
    void ensureNotNull() {
        AuthEntryPointJwt entryPointJwt = new AuthEntryPointJwt();
        assertNotNull(entryPointJwt);
    }
}
