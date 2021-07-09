package switchtwentytwenty.project.domain.model.shared;

import lombok.Getter;
import lombok.Setter;
import switchtwentytwenty.project.domain.exceptions.EmailCannotBeNullException;

import java.util.Objects;

public class Relationship {
    private final Email mainUserId;
    private final Email otherUserId;
    @Setter
    private final RelationshipType relationshipType;
    @Getter
    @Setter
    private RelationshipId relationshipId;

    /**
     * Constructor method for the family relationship object.
     *
     * @param relationshipType The type of relationship to be created.
     * @param mainUserId       corresponds to the Id of the family member. The relationship will be built in relation to him/her.
     * @param otherUserId      corresponds to the Id of the family member with whom person1 will have a relationship.
     */
    public Relationship(RelationshipType relationshipType, Email mainUserId, Email otherUserId) {
        this.relationshipType = relationshipType;
        this.mainUserId = mainUserId;
        this.otherUserId = otherUserId;
        this.relationshipId = new RelationshipId(hashCode());
        validateFamilyRelationship(mainUserId, otherUserId);
    }

    /**
     * Validates if the users id's are not null, throws exception if any of them are.
     *
     * @param mainUserId    corresponds to the Id of the family member. The relationship will be built in relation to him/her.
     * @param otherUserId   corresponds to the Id of the family member with whom person1 will have a relationship.
     */
    private void validateFamilyRelationship(Email mainUserId, Email otherUserId) {
        if (mainUserId == null || otherUserId == null) {
            throw new EmailCannotBeNullException("Invalid arguments");
        }
    }

    /**
     * Constructor method for the family relationship object.
     *
     * @param relationshipType in a numeric value
     * @param mainUserId       corresponds to the Id of the family member. The relationship will be built in relation to him/her.
     * @param otherUserId      corresponds to the Id of the family member with whom the person1 wants to build the relationship.
     */
    public Relationship(int relationshipType, Email mainUserId, Email otherUserId) {
        this.relationshipType = RelationshipType.valueOf(relationshipType);
        this.mainUserId = mainUserId;
        this.otherUserId = otherUserId;
        this.relationshipId = new RelationshipId(hashCode());
        validateFamilyRelationship(mainUserId, otherUserId);
    }

    /**
     * This method returns the id of the person in a particular relationship.
     *
     * @return int id of the family member
     */
    public Email getMainUserId() {
        return this.mainUserId;
    }

    /**
     * This method returns the id of the second person in a particular relationship
     *
     * @return int id of the family member
     */
    public Email getOtherUserId() {
        return this.otherUserId;
    }

    /**
     * This method obtains the relationship type of a particular relationship.
     *
     * @return relationship type
     */
    public RelationshipType getFamilyRelationshipType() {
        return this.relationshipType;
    }

    /**
     * Validates if a family member is a parent.
     *
     * @param parentId  corresponds to the Id of the family member that is to be checked if it is a parent.
     * @return
     */
    public boolean isParent(Email parentId) {
        return parentId == mainUserId && this.relationshipType == RelationshipType.PARENT;
    }

    /**
     * This equals method compares two relationships (by class, personId and relationshipType)
     *
     * @param obj the other object
     * @return boolean true, if the two relationships are equal according to the method's standards
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Relationship that = (Relationship) obj;
        return mainUserId.getEmailAddress() == that.mainUserId.getEmailAddress() && otherUserId.getEmailAddress() == that.otherUserId.getEmailAddress() && relationshipType.getNumericValue() == that.relationshipType.getNumericValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationshipType, mainUserId, otherUserId);
    }
}