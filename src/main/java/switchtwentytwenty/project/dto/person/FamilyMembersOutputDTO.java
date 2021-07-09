package switchtwentytwenty.project.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class FamilyMembersOutputDTO extends RepresentationModel<FamilyMembersOutputDTO> {

    @Getter
    @Setter
    private List<FamilyMemberOutputDTO> familyMembers;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilyMembersOutputDTO that = (FamilyMembersOutputDTO) o;
        return Objects.equals(familyMembers, that.familyMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), familyMembers);
    }
}
