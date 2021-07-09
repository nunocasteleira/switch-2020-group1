package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipIdTest {

    @Test
    void ensureConstructorIsWorking(){
        int id = 1;
        RelationshipId relationshipId = new RelationshipId(id);
        int expectedId = relationshipId.getId();

        assertNotNull(relationshipId);
        assertEquals(id, expectedId);
    }

    @Test
    void testEqualsAndHashCode() {
        int id = 1;
        int idOther = 2;
        RelationshipId relationshipId = new RelationshipId(id);
        RelationshipId relationshipIdSame = relationshipId;
        RelationshipId relationshipIdEqual = new RelationshipId(id);
        RelationshipId relationshipIdOther = new RelationshipId(idOther);

        assertEquals(relationshipId, relationshipIdSame);
        assertSame(relationshipId, relationshipIdSame);
        assertTrue(relationshipId.equals(relationshipIdSame));
        assertEquals(relationshipId.hashCode(), relationshipIdSame.hashCode());
        assertEquals(relationshipId.getId(), relationshipIdSame.getId());
        assertEquals(relationshipId, relationshipIdEqual);
        assertNotSame(relationshipId, relationshipIdEqual);
        assertEquals(relationshipId.hashCode(), relationshipIdEqual.hashCode());
        assertEquals(relationshipId.getId(), relationshipIdEqual.getId());
        assertNotEquals(relationshipId, relationshipIdOther);
        assertNotEquals(relationshipId.hashCode(), relationshipIdOther.hashCode());
        assertNotEquals(relationshipId.getId(), relationshipIdOther.getId());
        assertFalse(relationshipId.equals(relationshipIdOther));
        assertNotEquals(relationshipId, id);
        assertNotEquals(null, relationshipId);
        assertNotEquals(0, relationshipId.hashCode());
    }
}