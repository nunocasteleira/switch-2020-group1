package switchtwentytwenty.project.datamodel.family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import switchtwentytwenty.project.datamodel.shared.EmailJPA;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "familyMember")
public class FamilyMembersJPA implements Serializable {
    private static final long serialVersionUID = 0;

    @Getter
    @EmbeddedId
    FamilyPersonIdJPA idFamilyPerson;

    public FamilyMembersJPA(FamilyJPA familyJPA, EmailJPA personId) {
        this.idFamilyPerson = new FamilyPersonIdJPA(familyJPA, personId);
    }

    public EmailJPA getPersonId() {
        return idFamilyPerson.getPersonId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilyMembersJPA that = (FamilyMembersJPA) o;
        return Objects.equals(idFamilyPerson, that.idFamilyPerson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFamilyPerson);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    static class FamilyPersonIdJPA implements Serializable {
        private static final long serialVersionUID = 0;

        @ManyToOne
        @JoinColumn(name = "familyId")
        private FamilyJPA familyJPA;
        @Getter
        @Column(nullable = false, updatable = false)
        private EmailJPA personId;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FamilyPersonIdJPA that = (FamilyPersonIdJPA) o;
            return Objects.equals(familyJPA, that.familyJPA) && Objects.equals(personId, that.personId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(familyJPA, personId);
        }
    }

}
