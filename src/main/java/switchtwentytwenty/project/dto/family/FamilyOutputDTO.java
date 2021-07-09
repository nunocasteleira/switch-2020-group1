package switchtwentytwenty.project.dto.family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class FamilyOutputDTO extends RepresentationModel<FamilyOutputDTO> {

    @Getter
    @Setter
    private long familyId;
    @Getter
    @Setter
    private String familyName;
    @Getter
    @Setter
    private String adminId;
    @Getter
    @Setter
    private String registrationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilyOutputDTO that = (FamilyOutputDTO) o;
        return familyName.equals(that.familyName) && adminId.equals(that.adminId) && registrationDate.equals(that.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), familyName, adminId, registrationDate);
    }
}