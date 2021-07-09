package switchtwentytwenty.project.datamodel.shared;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class EmailJPA implements Serializable {
    private static final long serialVersionUID = 0;
    @Getter
    private String emailAddress;

    public EmailJPA(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailJPA emailJPA = (EmailJPA) o;
        return Objects.equals(emailAddress, emailJPA.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress);
    }
}
