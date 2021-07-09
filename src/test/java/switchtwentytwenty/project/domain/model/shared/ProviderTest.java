package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest {

    @Test
    void ensureProviderIsCreated()
    {
        String provider = "Bank";

        Provider result = new Provider(provider);

        assertNotNull(result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void ensureNullAndEmptyAreNotAccepted(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Provider(value));
    }

    @Test
    void ensureEquals()
    {
        String provider = "Bank";

        Provider result = new Provider(provider);
        Provider result2 = result;
        Provider different = new Provider("Else");

        assertEquals(result, result2);
        assertEquals(result, result);
        assertNotEquals(null, result);
        assertFalse(result.equals(null));
        assertNotEquals(result, different);
        assertEquals(result.hashCode(), result2.hashCode());
        assertNotEquals(result.hashCode(), different.hashCode());
        assertTrue(result.equals(result2));
    }

}