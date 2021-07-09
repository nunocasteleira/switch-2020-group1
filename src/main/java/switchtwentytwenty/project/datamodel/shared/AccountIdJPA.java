package switchtwentytwenty.project.datamodel.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import switchtwentytwenty.project.datamodel.person.PersonJPA;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personalAccount")
public class AccountIdJPA implements Serializable {
    private static final long serialVersionUID = 0;

    @ManyToOne
    @JoinColumn(name = "personId")
    @Getter
    private PersonJPA personJPA;
    @Id
    @Getter
    private long accountId;
}
