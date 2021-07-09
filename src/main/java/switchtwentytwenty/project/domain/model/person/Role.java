package switchtwentytwenty.project.domain.model.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
public class Role {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }

    public Role(Integer id, ERole name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return name == role.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
