package switchtwentytwenty.project.datamodel.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionJPA implements Serializable {
    private static final long serialVersionUID = 0;
    @Getter
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescriptionJPA)) {
            return false;
        }
        DescriptionJPA that = (DescriptionJPA) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
