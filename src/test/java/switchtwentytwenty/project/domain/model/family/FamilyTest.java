package switchtwentytwenty.project.domain.model.family;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import switchtwentytwenty.project.datamodel.family.FamilyJPA;
import switchtwentytwenty.project.datamodel.shared.EmailJPA;
import switchtwentytwenty.project.datamodel.shared.RelationshipIdJPA;
import switchtwentytwenty.project.datamodel.shared.RelationshipJPA;
import switchtwentytwenty.project.domain.exceptions.InvalidNameException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FamilyTest {

    @Test
    void createFamily_Successfully() {
        FamilyName familyName = new FamilyName("Silva");
        Email adminId = new Email("antonio@gmail.com");

        // act
        Family family = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();

        // assert
        assertNotNull(family);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"123", "Name.@"})
    void createFamily_Invalid_NullAndEmptyAndFormatName(String name) {
        try {
            // arrange
            FamilyName familyName = new FamilyName(name);
            Email adminId = new Email("antonio@gmail.com");
            // act
            new Family.FamilyBuilder(familyName, adminId);
        } catch (InvalidNameException exception) {
            // assert
            String message = "Invalid name.";
            assertEquals(message, exception.getMessage());
        }
    }

    @Test
    void validateFamily() {
        try {
            // arrange
            FamilyName familyName = null;
            Email adminId = null;
            RegistrationDate registrationDate = null;
            FamilyId familyId = null;
            // act
            new Family.FamilyBuilder(familyName, adminId)
                    .withRegistrationDate(registrationDate)
                    .withId(familyId)
                    .build();
        } catch (IllegalArgumentException exception) {
            // assert
            String message = "Invalid arguments.";
            assertEquals(message, exception.getMessage());
        }
    }

    @Test
    void hasId() {
        FamilyName familyName = new FamilyName("Silva");
        Email adminId = new Email("antonio@gmail.com");
        FamilyId familyId = new FamilyId(hashCode());
        Family family = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId(familyId)
                .build();

        boolean result = family.hasId(familyId);

        assertTrue(result);
    }

    @Test
    void hasDifferentId() {
        FamilyName familyName = new FamilyName("Silva");
        Email adminId = new Email("antonio@gmail.com");
        FamilyId familyId = new FamilyId(hashCode());
        FamilyId otherFamilyId = new FamilyId(1);
        Family family = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId(familyId)
                .build();

        boolean result = family.hasId(otherFamilyId);

        assertFalse(result);

    }

    @Test
    void testEquals_test1_DifferentObjects() {
        // arrange
        FamilyName familyName = new FamilyName("Silva");
        Email adminId = new Email("antonio@gmail.com");

        // act
        Family family = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();
        Family otherFamily = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();

        // assert
        assertNotSame(family, otherFamily);
        assertEquals(family, otherFamily);
    }

    @Test
    void testEquals_test2_SameObjects() {
        // arrange
        FamilyName familyName = new FamilyName("Silva");
        Email adminId = new Email("antonio@gmail.com");

        // act
        Family family = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();
        Family otherFamily = family;

        assertSame(family, otherFamily);
        assertEquals(family, otherFamily);
    }

    @Test
    void testEquals_test3_DifferentObjectsType() {
        // arrange
        FamilyName familyName = new FamilyName("Silva");
        Email adminId = new Email("antonio@gmail.com");

        // act
        Family family = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();
        String emailAddress = "tito@gmail.com";
        Email email = new Email(emailAddress);

        // assert
        assertNotEquals(family, email);
    }

    @Test
    void testEquals_test4_NotEqualObjects() {
        // arrange
        FamilyName familyName = new FamilyName("Silva");
        Email adminId = new Email("antonio@gmail.com");
        Email otherAdminId = new Email("maria@gmail.com");

        // act
        Family family = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();
        Family otherFamily = new Family.FamilyBuilder(familyName, otherAdminId)
                .withRegistrationDate()
                .withId()
                .build();

        // assert
        assertNotEquals(family, otherFamily);
    }

    @Test
    void testHashCode() {
        // arrange
        FamilyName familyName = new FamilyName("Silva");
        Email adminId = new Email("antonio@gmail.com");

        // act
        Family family = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();
        Family otherFamily = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();

        // assert
        assertNotSame(family, otherFamily);
        assertEquals(family.hashCode(), otherFamily.hashCode());
    }

    @Test
    void testHashCode_false() {
        // arrange
        FamilyName familyName = new FamilyName("Silva");
        Email adminId = new Email("antonio@gmail.com");
        Email otherAdminId = new Email("maria@gmail.com");

        // act
        Family family = new Family.FamilyBuilder(familyName, adminId)
                .withRegistrationDate()
                .withId()
                .build();
        Family otherFamily = new Family.FamilyBuilder(familyName, otherAdminId)
                .withRegistrationDate()
                .withId()
                .build();

        // assert
        assertNotEquals(family.hashCode(), otherFamily.hashCode());
    }

    @Test
    void checkIfRelationshipExistsNonExisting() {
        Email adminId = new Email("antonio@gmail.com");
        Email otherAdminId = new Email("maria@gmail.com");

        Family family = new Family.FamilyBuilder(new FamilyName("Bla"), adminId)
                .withRegistrationDate()
                .withId()
                .build();
        boolean result = family.checkIfRelationshipExists(adminId, otherAdminId);

        assertFalse(result);
    }

    @Test
    void checkIfRelationshipExistsAllTests() {
        Email adminId = new Email("antonio@gmail.com");
        Email email1 = new Email("maria@gmail.com");
        Email email2 = new Email("ze@gmail.com");

        Family family = new Family.FamilyBuilder(new FamilyName("Bla"), adminId)
                .withRegistrationDate()
                .withId()
                .build();

        Relationship relationship = new Relationship(1, email1, email2);
        family.addRelationship(relationship);


        boolean result1 = family.checkIfRelationshipExists(email1, email1);
        boolean result2 = family.checkIfRelationshipExists(email1, email2);
        boolean result3 = family.checkIfRelationshipExists(email1, adminId);
        boolean result4 = family.checkIfRelationshipExists(adminId, email2);


        assertFalse(result1);
        assertTrue(result2);
        assertFalse(result3);
        assertFalse(result4);
    }

    @Test
    void getRelationshipByIdSuccessfully() {
        Email adminId = new Email("tony@gmail.com");
        Email email1 = new Email("mary@gmail.com");
        Email email2 = new Email("john@gmail.com");

        Family family = new Family.FamilyBuilder(new FamilyName("Test Family"), adminId)
                .withRegistrationDate()
                .withId()
                .build();

        Relationship relationship = new Relationship(1, email1, email2);
        family.addRelationship(relationship);
        int relationshipId = relationship.getRelationshipId().getId();

        Relationship result = family.getRelationshipById(relationshipId);

        assertEquals(relationship, result);
    }

    @Test
    void failToGetRelationshipById() {
        Email adminId = new Email("tony@gmail.com");
        Email email1 = new Email("mary@gmail.com");
        Email email2 = new Email("john@gmail.com");

        Family family = new Family.FamilyBuilder(new FamilyName("Test Family"), adminId)
                .withRegistrationDate()
                .withId()
                .build();

        int nonexistentFamilyId = 0;

        assertThrows(ObjectDoesNotExistException.class, () -> family.getRelationshipById(nonexistentFamilyId));
    }
}