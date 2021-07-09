package switchtwentytwenty.project.datamodel.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class PersonNameJPA implements Serializable {
    private static final long serialVersionUID = 0;

    @Getter
    private String personName;

    public PersonNameJPA(String name) {
        this.personName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonNameJPA that = (PersonNameJPA) o;
        return Objects.equals(personName, that.personName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personName);
    }
}


