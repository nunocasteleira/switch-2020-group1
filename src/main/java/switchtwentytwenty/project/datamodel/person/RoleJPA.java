package switchtwentytwenty.project.datamodel.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoleJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(length = 20)
    @Getter
    @Setter
    private String name;

    public RoleJPA(String name) {
        this.name = name;
    }
}
