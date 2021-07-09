package switchtwentytwenty.project.dto.family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import switchtwentytwenty.project.domain.model.person.Role;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class AddFamilyMemberDTO extends RepresentationModel<AddFamilyMemberDTO> {

    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private long databaseId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String street;
    @Getter
    @Setter
    private String location;
    @Getter
    @Setter
    private String postCode;
    @Getter
    @Setter
    private String birthdate;
    @Getter
    @Setter
    private String vat;
    @Getter
    @Setter
    private long familyId;
    @Getter
    @Setter
    private String phoneNumbers;
    @Getter
    @Setter
    private Set<Role> roles;

    public AddFamilyMemberDTO(String email, String name, String street, String location, String postCode, String birthdate, String vat, long familyId, String phoneNumber, Set<Role> roles){
        this.email = email;
        this.name = name;
        this.street = street;
        this.location = location;
        this.postCode = postCode;
        this.birthdate = birthdate;
        this.vat = vat;
        this.familyId = familyId;
        this.phoneNumbers = phoneNumber;
        this.roles = new HashSet<>(roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AddFamilyMemberDTO that = (AddFamilyMemberDTO) o;
        return familyId == that.familyId && email.equals(that.email) && name.equals(that.name) && street.equals(that.street) && location.equals(that.location) && postCode.equals(that.postCode) && birthdate.equals(that.birthdate) && vat.equals(that.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, name, street, location, postCode, birthdate, vat, familyId);
    }
}
