package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProviderJPATest {

    @Test
    void ensureProviderIsCreatedAndGetterIsWorking() {
        String provider = "Bank";

        ProviderJPA providerJPA = new ProviderJPA(provider);

        assertNotNull(providerJPA);
        assertEquals(provider, providerJPA.getProvider());
    }

    @Test
    void ensureEqualsAndHashCode() {
        String provider = "Bank";

        ProviderJPA providerJPA = new ProviderJPA(provider);
        ProviderJPA same = providerJPA;
        ProviderJPA equals = new ProviderJPA(provider);
        ProviderJPA zero = null;
        ProviderJPA different = new ProviderJPA("else");

        assertEquals(providerJPA, same);
        assertEquals(providerJPA, equals);
        assertFalse(providerJPA.equals(zero));
        assertEquals(providerJPA.hashCode(), same.hashCode());
        assertNotEquals(providerJPA.hashCode(), different.hashCode());
        assertNotEquals(providerJPA, different);
        assertNotEquals(provider, providerJPA);
    }

}