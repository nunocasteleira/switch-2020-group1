package switchtwentytwenty.project.datamodel.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import switchtwentytwenty.project.datamodel.person.PersonJPA;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(PhoneNumberJPA.OtherPhoneId.class)
public class PhoneNumberJPA implements Serializable {
    private static final long serialVersionUID = 0;
    @Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    @Setter
    private PersonJPA person;

    @Id
    @Getter
    @Setter
    private String phoneNumber;

    @NoArgsConstructor
    public static class OtherPhoneId implements Serializable {
        private static final long serialVersionUID = 0;

        private PersonJPA person;
        private String phoneNumber;

        public OtherPhoneId(PersonJPA person, String phoneNumber) {
            this.person = person;
            this.phoneNumber = phoneNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            OtherPhoneId that = (OtherPhoneId) o;
            return Objects.equals(person, that.person) && Objects.equals(phoneNumber, that.phoneNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(person, phoneNumber);
        }
    }
}
