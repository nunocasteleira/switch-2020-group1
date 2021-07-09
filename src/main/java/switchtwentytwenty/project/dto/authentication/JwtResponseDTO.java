package switchtwentytwenty.project.dto.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class JwtResponseDTO {
    @Getter
    @Setter
    private String token;
    @Getter
    @Setter
    private String type = "Bearer";
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String personName;
    @Getter
    @Setter
    private long familyId;
    @Getter
    @Setter
    private List<String> role;

    public JwtResponseDTO(String token, String email, String personName, long familyId, List<String> role) {
        this.token = token;
        this.email = email;
        this.personName = personName;
        this.familyId = familyId;
        this.role = new ArrayList<>(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JwtResponseDTO that = (JwtResponseDTO) o;
        return familyId == that.familyId && Objects.equals(token, that.token) && Objects.equals(type, that.type) && Objects.equals(email, that.email) && Objects.equals(personName, that.personName) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, type, email, personName, familyId, role);
    }
}
