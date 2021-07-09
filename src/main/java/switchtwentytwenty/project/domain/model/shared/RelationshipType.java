package switchtwentytwenty.project.domain.model.shared;

import switchtwentytwenty.project.domain.exceptions.InvalidRelationshipType;

import java.util.HashMap;

public enum RelationshipType {
    SPOUSE(1),
    PARTNER(2),
    PARENT(3),
    CHILD(4),
    SIBLING(5),
    GRANDPARENT(6),
    GRANDCHILD(7),
    UNCLE_AUNT(8),
    NEPHEW_NIECE(9),
    COUSIN(10);

    private int numericValue;

    /**
     * The relationshipTypeMap attribute initializes a hashMap. A hashMap stores items in "key/value" pairs.
     */
    private static HashMap relationshipTypeMap = new HashMap<>();

    /**
     * This static initialization block fills the hashMap with the family relationship types and their corresponding numeric value.
     */
    static {
        for (RelationshipType relationshipType : RelationshipType.values()) {
            relationshipTypeMap.put(relationshipType.numericValue, relationshipType);
        }
    }

    /**
     * Constructor method for the FamilyRelationshipType Enum
     * @param numericValue that corresponds to a relationship type
     */
    RelationshipType(int numericValue) {
        this.numericValue = numericValue;
    }

    /**
     * This method obtains the relationship type by inputting its numeric value
     * @param numericValue of the relationship type
     * @return The relationship type that corresponds to the numeric value
     */
    public static RelationshipType valueOf(int numericValue) {
        if (numericValue > RelationshipType.values().length || numericValue < RelationshipType.values()[0].numericValue) {
            throw new InvalidRelationshipType("Invalid relationship type.");
        }
        return (RelationshipType) relationshipTypeMap.get(numericValue);
    }

    /**
     * This method obtains the numeric value of the relationship type
     * @return numeric value
     */
    public int getNumericValue(){
        return this.numericValue;
    }

    @Override
    public String toString() {
        return name();
    }
}
