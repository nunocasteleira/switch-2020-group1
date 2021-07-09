package switchtwentytwenty.project.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class FamilyMemberOutputDTO extends RepresentationModel<FamilyMemberOutputDTO> {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilyMemberOutputDTO that = (FamilyMemberOutputDTO) o;
        return name.equals(that.name) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, email);
    }
}