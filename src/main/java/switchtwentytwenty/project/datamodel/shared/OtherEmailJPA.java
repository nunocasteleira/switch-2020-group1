package switchtwentytwenty.project.datamodel.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import switchtwentytwenty.project.datamodel.person.PersonJPA;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(OtherEmailJPA.OtherEmailId.class)
public class OtherEmailJPA implements Serializable{
    private static final long serialVersionUID = 0;
    @Id
    @ManyToOne
    @JoinColumn(name = "main_email", nullable = false)
    @Setter
    private PersonJPA person;

    @Id
    @Getter
    @Setter
    private String otherEmail;


    @NoArgsConstructor
    public static class OtherEmailId implements Serializable {
        private static final long serialVersionUID = 0;

        private PersonJPA person;
        private String otherEmail;

        public OtherEmailId(PersonJPA person, String otherEmail) {
            this.person = person;
            this.otherEmail = otherEmail;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            OtherEmailId that = (OtherEmailId) o;
            return Objects.equals(person, that.person) && Objects.equals(otherEmail, that.otherEmail);
        }

        @Override
        public int hashCode() {
            return Objects.hash(person, otherEmail);
        }
    }
}
