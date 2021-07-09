package switchtwentytwenty.project.datamodel.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import switchtwentytwenty.project.datamodel.family.FamilyJPA;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipJPA implements Serializable {
    private static final long serialVersionUID = 0;

    @Id
    @Getter
    private RelationshipIdJPA relationshipId;
    @Embedded
    @Getter
    @Column(nullable = false, updatable = false)
    @AttributeOverride(name = "emailAddress", column = @Column(name = "main_user_id"))
    private EmailJPA mainUser;
    @Getter
    @Setter
    private int relationshipType;
    @Embedded
    @Getter
    @Column(nullable = false, updatable = false)
    @AttributeOverride(name = "emailAddress", column = @Column(name = "other_user_id"))
    private EmailJPA otherUser;

    @ManyToOne
    @JoinColumn(name = "family")
    private FamilyJPA family;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelationshipJPA that = (RelationshipJPA) o;
        return relationshipId.equals(that.relationshipId) &&
                relationshipType == that.relationshipType &&
                mainUser.equals(that.mainUser) &&
                otherUser.equals(that.otherUser) &&
                family.equals(that.family);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationshipId, mainUser, relationshipType, otherUser, family);
    }

}
