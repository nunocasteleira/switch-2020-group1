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
public class ProfileInformationDTO extends RepresentationModel<ProfileInformationDTO> {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String mainEmailAddress;
    @Getter
    @Setter
    private String birthDate;
    @Getter
    @Setter
    private String vat;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private List<String> phoneNumbers;
    @Getter
    @Setter
    private List<String> emailAddresses;
    @Getter
    @Setter
    private long familyId;
    @Getter
    @Setter
    private String familyName;
    @Getter
    @Setter
    private boolean isAdmin;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfileInformationDTO)) {
            return false;
        }
        ProfileInformationDTO that = (ProfileInformationDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(mainEmailAddress, that.mainEmailAddress) && Objects.equals(birthDate, that.birthDate) && Objects.equals(vat, that.vat) && Objects.equals(address, that.address) && Objects.equals(phoneNumbers, that.phoneNumbers) && Objects.equals(emailAddresses, that.emailAddresses) && Objects.equals(familyId, that.familyId) && Objects.equals(familyName, that.familyName) && Objects.equals(isAdmin, that.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, mainEmailAddress, birthDate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);
    }
}
