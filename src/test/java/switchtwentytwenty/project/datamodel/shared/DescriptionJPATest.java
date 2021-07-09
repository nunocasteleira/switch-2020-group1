package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionJPATest {

    @Test
    void getDescription() {
        String description = "Family cash account.";
        DescriptionJPA accountDescriptionJPA = new DescriptionJPA(description);
        String result;

        result = accountDescriptionJPA.getDescription();

        assertEquals(description, result);
    }

    @Test
    void testEquals() {
        String description1 = "Faria's family cash account.";
        String description2 = "Machado's family cash account.";
        DescriptionJPA accountDescriptionJPA1 = new DescriptionJPA(description1);
        DescriptionJPA accountDescriptionJPA1a = accountDescriptionJPA1;
        DescriptionJPA accountDescriptionJPA1b = new DescriptionJPA(description1);
        DescriptionJPA accountDescriptionJPA2 = new DescriptionJPA(description2);

        assertEquals(accountDescriptionJPA1, accountDescriptionJPA1a);
        assertEquals(accountDescriptionJPA1, accountDescriptionJPA1b);
        assertSame(accountDescriptionJPA1, accountDescriptionJPA1a);
        assertNotSame(accountDescriptionJPA1, accountDescriptionJPA1b);
        assertEquals(accountDescriptionJPA1.hashCode(), accountDescriptionJPA1a.hashCode());
        assertEquals(accountDescriptionJPA1.getDescription(), accountDescriptionJPA1a.getDescription());
        assertEquals(accountDescriptionJPA1.hashCode(), accountDescriptionJPA1b.hashCode());
        assertEquals(accountDescriptionJPA1.getDescription(), accountDescriptionJPA1b.getDescription());
        assertNotEquals(accountDescriptionJPA1, accountDescriptionJPA2);
        assertNotEquals(accountDescriptionJPA1.hashCode(), accountDescriptionJPA2.hashCode());
        assertNotEquals(accountDescriptionJPA1.getDescription(), accountDescriptionJPA2.getDescription());
        assertNotEquals(0, accountDescriptionJPA1.hashCode());
        assertNotEquals(description1, accountDescriptionJPA1);
        assertNotEquals(null, accountDescriptionJPA1);
        assertFalse(accountDescriptionJPA1.equals(null));
        assertFalse(accountDescriptionJPA1.equals(accountDescriptionJPA2));
        assertTrue(accountDescriptionJPA1.equals(accountDescriptionJPA1b));
    }

    @Test
    void testNoArgsConstructor() {
        DescriptionJPA accountDescriptionJPA = new DescriptionJPA();

        assertNotNull(accountDescriptionJPA);
    }

}