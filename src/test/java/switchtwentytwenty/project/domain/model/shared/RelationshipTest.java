package switchtwentytwenty.project.domain.model.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.Main;
import switchtwentytwenty.project.domain.exceptions.EmailCannotBeNullException;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipTest {
    Main main;
    Email person1Id;
    Email person2Id;
    Email person3Id;

    @BeforeEach
    void initialize() {
        main = new Main();
        person1Id = new Email("person1@gmail.com");
        person2Id = new Email("person2@gmail.com");
        person3Id = new Email("person3@gmail.com");
    }

    @Test
    void createRelationship_Successfully() {
        RelationshipType relationshipType = RelationshipType.GRANDCHILD;

        Relationship result = new Relationship(relationshipType, person1Id,
                person2Id);
        assertNotNull(result);
    }

    @Test
    void createFamilyRelationshipWithValidRelationshipTypeAndPersonId() {
        RelationshipType relationshipType = RelationshipType.GRANDCHILD;
        Relationship first = new Relationship(relationshipType, person1Id,
                person2Id);
        Relationship second = new Relationship(relationshipType, person1Id, person2Id);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(0, first.hashCode());
    }

    @Test
    void createFamilyRelationship_Unsuccessfully_NullPerson1Id() {
        RelationshipType relationshipType = RelationshipType.SIBLING;

        assertThrows(EmailCannotBeNullException.class,
                () -> new Relationship(relationshipType, null, person2Id));
    }

    @Test
    void createFamilyRelationship_Unsuccessfully_NullPerson2Id() {
        RelationshipType relationshipType = RelationshipType.SIBLING;

        assertThrows(EmailCannotBeNullException.class,
                () -> new Relationship(relationshipType, person1Id, null));
    }

    @Test
    void createFamilyRelationship_Successfully_ValidRelationshipNumericValue() {
        int relationshipType = 8;

        Relationship result = new Relationship(relationshipType, person1Id,
                person2Id);
        assertNotNull(result);
    }

    @Test
    void createFamilyRelationship_Unsuccessfully_ValidRelationshipNumericValue_NullPerson2Id() {
        int relationshipType = 6;
        assertThrows(IllegalArgumentException.class,
                () -> new Relationship(relationshipType, person1Id, null));
    }

    @Test
    void createFamilyRelationship_Unsuccessfully_InvalidRelationshipNumericValue() {
        int relationshipType = 15;

        assertThrows(IllegalArgumentException.class,
                () -> new Relationship(relationshipType, person2Id, person1Id));
    }

    @Test
    void getMainUserId_Successfully() {
        int relationshipType = 5;

        Relationship relationship = new Relationship(relationshipType, person1Id,
                person2Id);

        Email mainUserId = relationship.getMainUserId();

        assertEquals(mainUserId, person1Id);
    }

    @Test
    void getOtherUserId_Successfully() {
        int relationshipType = 5;

        Relationship relationship = new Relationship(relationshipType, person1Id,
                person2Id);

        Email otherUserId = relationship.getOtherUserId();

        assertEquals(otherUserId, person2Id);
    }

    @Test
    void getRelationshipType_Successfully() {
        int relationshipType = 1;

        Relationship relationship = new Relationship(relationshipType, person1Id,
                person2Id);
        RelationshipType expectedRelationshipType = RelationshipType.SPOUSE;
        RelationshipType actualRelationshipType = relationship.getFamilyRelationshipType();

        assertEquals(expectedRelationshipType, actualRelationshipType);
    }

    @Test
    void isParent_True() {
        int relationshipType = 3;

        Relationship relationship = new Relationship(relationshipType, person1Id, person2Id);
        boolean result = relationship.isParent(person1Id);

        assertTrue(result);
    }

    @Test
    void isParent_False() {
        int relationshipType = 4;

        Relationship relationship = new Relationship(relationshipType, person1Id, person2Id);
        boolean result = relationship.isParent(person1Id);

        assertFalse(result);
    }

    @Test
    void isParent_NotMainUser() {
        int relationshipType = 4;

        Relationship relationship = new Relationship(relationshipType, person1Id, person2Id);
        boolean result = relationship.isParent(person2Id);

        assertFalse(result);
    }

    @Test
    void relationshipsAreEqual() {
        int relationshipType = 4;

        Relationship relationship1 = new Relationship(relationshipType, person1Id, person2Id);
        Relationship relationship2 = new Relationship(relationshipType, person1Id, person2Id);

        assertEquals(relationship1, relationship2);
    }

    @Test
    void relationshipsAreNotEqual() {
        int relationshipType = 4;

        Relationship relationship1 = new Relationship(relationshipType, person1Id, person2Id);
        Relationship relationship2 = new Relationship(relationshipType, person1Id, person3Id);

        assertNotEquals(relationship1, relationship2);
    }

    @Test
    void relationshipsAreNotEqual_RelationshipIsNull() {
        int relationshipType = 4;

        Relationship relationship1 = new Relationship(relationshipType, person1Id, person2Id);

        assertNotEquals(null, relationship1);
        assertFalse(relationship1.equals(null));
    }


    @Test
    void relationships_ensureEqualsIsOk() {
        int relationshipType1 = 4;
        int relationshipType2 = 10;
        Relationship relationship1 = new Relationship(relationshipType1, person1Id, person2Id);
        Relationship relationship2 = new Relationship(relationshipType1, person2Id, person1Id);
        Relationship relationship3 = new Relationship(relationshipType1, person1Id, person3Id);
        Relationship relationship4 = new Relationship(relationshipType1, person3Id, person1Id);
        Relationship relationship5 = new Relationship(relationshipType2, person1Id, person2Id);

        assertFalse(relationship1.equals(null));
        assertFalse(relationship1.equals(relationshipType1));
        assertNotEquals(null, relationship1);
        assertNotEquals(relationshipType1, relationship1);
        assertFalse(relationship1.equals(relationship2));
        assertFalse(relationship1.equals(relationship3));
        assertFalse(relationship1.equals(relationship4));
        assertFalse(relationship1.equals(relationship5));

    }

    @Test
    void setIdDatabaseIsOk(){
        int relationshipType1 = 4;
        int expected = 10;
        Relationship relationship = new Relationship(relationshipType1, person1Id, person2Id);

        relationship.setRelationshipId(new RelationshipId(expected));
        assertEquals(10L, relationship.getRelationshipId().getId());
    }
}
