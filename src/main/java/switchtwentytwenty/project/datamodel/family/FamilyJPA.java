package switchtwentytwenty.project.datamodel.family;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import switchtwentytwenty.project.datamodel.shared.*;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class FamilyJPA implements Serializable {
    private static final long serialVersionUID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long familyId;
    @Embedded
    @Getter
    private FamilyNameJPA familyName;
    @Embedded
    @Getter
    private RegistrationDateJPA registrationDate;
    @Embedded
    @Getter
    private EmailJPA adminId;
    //@Embedded
    @Getter
    @Setter
    private long accountId;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "idFamilyPerson.familyJPA", cascade =
            CascadeType.ALL)
    @Getter
    private List<FamilyMembersJPA> familyMembers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "family", cascade =
            CascadeType.ALL)
    @Getter
    @Setter
    private List<RelationshipJPA> familyRelationships;

    public FamilyJPA(long familyId, FamilyNameJPA familyName,
                     RegistrationDateJPA registrationDate,
                     EmailJPA adminId, long accountIdJPA) {
        this.familyId = familyId;
        this.familyName = familyName;
        this.registrationDate = registrationDate;
        this.adminId = adminId;
        this.familyMembers = new ArrayList<>();
        this.familyRelationships = new ArrayList<>();
        this.accountId = accountIdJPA;
    }

    public void addFamilyMember(EmailJPA memberId) {
        FamilyMembersJPA familyMembersJPA = new FamilyMembersJPA(this, memberId);

        familyMembers.add(familyMembersJPA);
    }

    /**
     * Method to get a relationshipJPA in the FamilyJPA through its id.
     *
     * @param relationshipIdJPA - id of the relationship
     * @return Relationship object
     */
    public RelationshipJPA getRelationshipJPAById(RelationshipIdJPA relationshipIdJPA) {
        RelationshipJPA foundRelationshipJPA = null;
        for (RelationshipJPA relationshipJPA :
                this.familyRelationships) {
            if (relationshipJPA.getRelationshipId().equals(relationshipIdJPA)){
                foundRelationshipJPA = relationshipJPA;
            }
        }
        if (foundRelationshipJPA == null) {
            throw new ObjectDoesNotExistException("The relationship does not exist in the family.");
        }
        return foundRelationshipJPA;
    }
}