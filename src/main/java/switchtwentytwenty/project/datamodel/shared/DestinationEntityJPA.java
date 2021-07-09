package switchtwentytwenty.project.datamodel.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DestinationEntityJPA implements Serializable {
    private static final long serialVersionUID = 0;
    @Getter
    @Column
    private String destinationEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DestinationEntityJPA)) {
            return false;
        }
        DestinationEntityJPA that = (DestinationEntityJPA) o;
        return destinationEntity.equals(that.destinationEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinationEntity);
    }
}
