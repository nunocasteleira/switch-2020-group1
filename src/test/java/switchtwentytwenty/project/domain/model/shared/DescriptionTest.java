package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.exceptions.InvalidDescriptionException;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionTest {

    @Test
    void createAccountDescriptionSuccessfully() {
        String description = "Family cash account.";

        Description accountDescription = new Description(description);

        assertNotNull(accountDescription);
        assertEquals(description, accountDescription.getAccountDescription());
    }

    @Test
    void failToCreateAccountDescription_Null() {
        String description = null;

        assertThrows(InvalidDescriptionException.class, () -> new Description(description));
    }

    @Test
    void failToCreateAccountDescription_Empty() {
        String description = "";

        assertThrows(InvalidDescriptionException.class, () -> new Description(description));
    }

    @Test
    void testEqualsAndHashCode() {
        String description1 = "Family cash account.";
        String description2 = "Other cash account.";

        Description accountDescription1 = new Description(description1);
        Description accountDescription1a = accountDescription1;
        Description accountDescription1b = new Description(description1);
        Description accountDescription2 = new Description(description2);

        assertNotNull(accountDescription1);
        assertNotNull(accountDescription1a);
        assertNotNull(accountDescription2);
        assertEquals(accountDescription1, accountDescription1a);
        assertEquals(accountDescription1, accountDescription1b);
        assertSame(accountDescription1, accountDescription1a);
        assertNotSame(accountDescription1, accountDescription1b);
        assertNotEquals(accountDescription1, accountDescription2);
        assertEquals(accountDescription1.getAccountDescription(), accountDescription1a.getAccountDescription());
        assertEquals(accountDescription1.hashCode(), accountDescription1a.hashCode());
        assertEquals(accountDescription1.getAccountDescription(), accountDescription1b.getAccountDescription());
        assertEquals(accountDescription1.hashCode(), accountDescription1b.hashCode());
        assertNotEquals(accountDescription1.getAccountDescription(), accountDescription2.getAccountDescription());
        assertNotEquals(accountDescription1.hashCode(), accountDescription2.hashCode());
        assertTrue(accountDescription1.equals(accountDescription1a));
        assertFalse(accountDescription1.equals(accountDescription2));
        assertFalse(accountDescription1.equals(null));
        assertFalse(accountDescription1.equals(description1));
        assertNotEquals(accountDescription1, description2);
        assertNotEquals(0, accountDescription1.getAccountDescription());
    }
}