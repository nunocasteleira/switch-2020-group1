package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DestinationEntityJPATest {

    @Test
    void getDestinationEntity() {
        String destinationEntity = "Indaqua";
        DestinationEntityJPA destinationEntityJPA = new DestinationEntityJPA(destinationEntity);
        String result = destinationEntityJPA.getDestinationEntity();

        assertEquals(destinationEntity, result);
    }

    @Test
    void testEquals() {
        String destinationEntity1 = "EDP";
        String destinationEntity2 = "Mercado";
        DestinationEntityJPA accountDescriptionJPA1 = new DestinationEntityJPA(destinationEntity1);
        //noinspection UnnecessaryLocalVariable
        DestinationEntityJPA accountDescriptionJPA1a = accountDescriptionJPA1;
        DestinationEntityJPA accountDescriptionJPA1b = new DestinationEntityJPA(destinationEntity1);
        DestinationEntityJPA accountDescriptionJPA2 = new DestinationEntityJPA(destinationEntity2);

        assertEquals(accountDescriptionJPA1, accountDescriptionJPA1a);
        assertEquals(accountDescriptionJPA1, accountDescriptionJPA1b);
        assertSame(accountDescriptionJPA1, accountDescriptionJPA1a);
        assertNotSame(accountDescriptionJPA1, accountDescriptionJPA1b);
        assertEquals(accountDescriptionJPA1.hashCode(), accountDescriptionJPA1a.hashCode());
        assertEquals(accountDescriptionJPA1.getDestinationEntity(), accountDescriptionJPA1a.getDestinationEntity());
        assertEquals(accountDescriptionJPA1.hashCode(), accountDescriptionJPA1b.hashCode());
        assertEquals(accountDescriptionJPA1.getDestinationEntity(), accountDescriptionJPA1b.getDestinationEntity());
        assertNotEquals(accountDescriptionJPA1, accountDescriptionJPA2);
        assertNotEquals(accountDescriptionJPA1.hashCode(), accountDescriptionJPA2.hashCode());
        assertNotEquals(accountDescriptionJPA1.getDestinationEntity(), accountDescriptionJPA2.getDestinationEntity());
        assertNotEquals(0, accountDescriptionJPA1.hashCode());
        assertNotEquals(destinationEntity1, accountDescriptionJPA1);
        assertNotEquals(null, accountDescriptionJPA1);
        assertFalse(accountDescriptionJPA1.equals(null));
        assertFalse(accountDescriptionJPA1.equals(accountDescriptionJPA2));
        assertTrue(accountDescriptionJPA1.equals(accountDescriptionJPA1b));
    }

    @Test
    void testNoArgsConstructor() {
        DestinationEntityJPA destinationEntityJPA = new DestinationEntityJPA();

        assertNotNull(destinationEntityJPA);
    }
}
