package switchtwentytwenty.project.domain.model.family;

import lombok.Getter;
import lombok.Setter;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.interfaces.AggregateRoot;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Family implements AggregateRoot<FamilyId> {

    @Getter
    @Setter
    private FamilyId familyId;
    @Getter
    private final FamilyName familyName;
    @Getter
    private final RegistrationDate registrationDate;
    @Getter
    private final Email adminId;
    @Setter
    private List<Email> familyMembers = new ArrayList<>();
    @Setter
    private List<Relationship> familyRelationships = new ArrayList<>();
    @Getter
    private AccountId cashAccountId = new AccountId(0);

    private Family(FamilyBuilder builder) {
        this.familyName = builder.name;
        this.registrationDate = builder.date;
        this.adminId = builder.adminId;
        this.familyId = builder.id;
    }

    @Override
    public boolean hasId(FamilyId familyId) {
        return this.familyId.equals(familyId);
    }

    public List<Email> getFamilyMembers() {
        return new ArrayList<>(familyMembers);
    }

    /**
     * This method obtains the list of relationships between the administrator and other family
     * members
     *
     * @return list of relationships of that family
     */
    public List<Relationship> getRelationshipsList() {
        return new ArrayList<>(familyRelationships);
    }

    /**
     * This method adds a new relationship to the list of family relationships
     *
     * @param relationship x
     */
    public void addRelationship(Relationship relationship) {
        this.familyRelationships.add(relationship);
    }

    /**
     * This method checks if a person exists in a given family
     *
     * @param personId Id of the person we want to check
     * @return boolean true, if the person exists in the family, false if he/she doesn't
     */
    public boolean checkIfPersonExistsInFamily(Email personId) {
        boolean result = false;
        List<Email> list = this.familyMembers;
        for (int i = 0; i < list.size() && !result; i++) {
            Email familyMemberId = list.get(i);
            result = personId.equals(familyMemberId);
        }
        return result;
    }

    /**
     * This method checks if a relationship exists between two family members.
     *
     * @param mainUserId  id of the family member - the relationship is in relation to him/her
     * @param otherUserId id of the other family member
     * @return boolean true, if there is already a relationship between the two family members,
     *         false there isn't
     */
    public boolean checkIfRelationshipExists(Email mainUserId, Email otherUserId) {
        boolean result = false;
        List<Relationship> relationships = this.familyRelationships;
        for (int i = 0; i < relationships.size() && !result; i++) {
            Relationship relationship = relationships.get(i);
            result =
                    relationship.getMainUserId().equals(mainUserId) && relationship.getOtherUserId().equals(otherUserId);
        }
        return result;
    }

    /**
     * Method to verify if family has a Cash Account.
     *
     * @return true if has, false if account id is null
     */
    public boolean hasCashAccount() {
        AccountId nullAccountId = new AccountId(0);
        return !this.cashAccountId.equals(nullAccountId);
    }

    /**
     * Method to set the Cash Account Id in the Family.
     *
     * @param cashAccountId Cash Account Id
     */
    public void setAccountId(AccountId cashAccountId) {
        this.cashAccountId = cashAccountId;
    }

    /**
     * Method to get a relationship through its id.
     * @param relationshipId - id of the relationship
     * @return Relationship object
     */
    public Relationship getRelationshipById(int relationshipId) {
        RelationshipId relationshipIdVO = new RelationshipId(relationshipId);
        Relationship foundRelationship = null;
        for (Relationship relationship:
                this.familyRelationships) {
            if(relationship.getRelationshipId().equals(relationshipIdVO)){
                foundRelationship = relationship;
            }
        }
        if(foundRelationship == null){
            throw new ObjectDoesNotExistException("The relationship does not exist in the family.");
        }
        return foundRelationship;
    }

    /**
     * Method to check is a member is admin of this family.
     * @param adminId id of the member (Email)
     * @return true if admin, false if not
     */
    public boolean isAdmin(Email adminId) {
        return this.adminId.equals(adminId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Family family = (Family) o;
        return familyName.equals(family.familyName) && registrationDate.equals(family.registrationDate) && adminId.equals(family.adminId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(familyName, getRegistrationDate(), adminId);
    }

    public static class FamilyBuilder {
        private final FamilyName name;
        private final Email adminId;
        private RegistrationDate date;
        private FamilyId id;

        public FamilyBuilder(FamilyName familyName, Email personId) {
            this.name = familyName;
            this.adminId = personId;
        }

        public FamilyBuilder withRegistrationDate() {
            this.date = new RegistrationDate();
            return this;
        }

        public FamilyBuilder withRegistrationDate(RegistrationDate registrationDate) {
            this.date = registrationDate;
            return this;
        }

        public FamilyBuilder withId() {
            this.id = new FamilyId(0);
            return this;
        }

        public FamilyBuilder withId(FamilyId familyId) {
            this.id = familyId;
            return this;
        }

        public Family build() {
            Family family = new Family(this);
            validateFamily();
            return family;
        }

        private void validateFamily() {
            if (validateName()) {
                throw new IllegalArgumentException("Invalid arguments.");
            }
        }

        /**
         * This method allows to validate the name of the family.
         *
         * @return boolean indicating the success or failure of the validation of name.
         */
        private boolean validateName() {
            return this.name == null;
        }

    }
}