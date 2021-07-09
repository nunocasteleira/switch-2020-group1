package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipIdJPATest {

    @Test
    void ensureNoArgsConstructorIsWorking(){
        int relationshipId = 1;
        RelationshipIdJPA relationshipIdJPA = new RelationshipIdJPA();
        relationshipIdJPA.setId(relationshipId);

        assertNotNull(relationshipIdJPA);
        assertEquals(relationshipId, relationshipIdJPA.getId());
    }

    @Test
    void testEqualsAndHashCode(){
        int relationshipId = 1;
        int otherRelationshipId = 2;
        RelationshipIdJPA relationshipIdJPA = new RelationshipIdJPA(relationshipId);
        RelationshipIdJPA relationshipIdJPASame = relationshipIdJPA;
        RelationshipIdJPA relationshipIdJPAEqual = new RelationshipIdJPA(relationshipId);
        RelationshipIdJPA relationshipIdJPAOther = new RelationshipIdJPA(otherRelationshipId);

        assertEquals(relationshipIdJPA, relationshipIdJPASame);
        assertSame(relationshipIdJPA, relationshipIdJPASame);
        assertEquals(relationshipIdJPA, relationshipIdJPAEqual);
        assertNotSame(relationshipIdJPA, relationshipIdJPAEqual);
        assertEquals(relationshipIdJPA.getId(), relationshipIdJPASame.getId());
        assertEquals(relationshipIdJPA.hashCode(), relationshipIdJPASame.hashCode());
        assertEquals(relationshipIdJPA.getId(), relationshipIdJPAEqual.getId());
        assertEquals(relationshipIdJPA.hashCode(), relationshipIdJPAEqual.hashCode());
        assertNotEquals(relationshipIdJPA,relationshipIdJPAOther);
        assertNotEquals(relationshipIdJPA.getId(), relationshipIdJPAOther.getId());
        assertNotEquals(relationshipIdJPA.hashCode(), relationshipIdJPAOther.hashCode());
        assertNotEquals(0, relationshipIdJPA.hashCode());
        assertNotEquals(null, relationshipIdJPA);
        assertNotEquals(relationshipIdJPA, relationshipId);
        assertFalse(relationshipIdJPA.equals(relationshipIdJPAOther));
        assertTrue(relationshipIdJPA.equals(relationshipIdJPAEqual));
    }
}