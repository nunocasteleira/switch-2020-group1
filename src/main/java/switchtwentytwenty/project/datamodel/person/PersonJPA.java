package switchtwentytwenty.project.datamodel.person;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import switchtwentytwenty.project.datamodel.shared.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Entity
public class PersonJPA implements Serializable {
    private static final long serialVersionUID = 0;
    @Getter
    @AttributeOverride(name = "emailAddress", column = @Column(name = "person_id"))
    @Column(unique = true)
    private EmailJPA id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long dataBaseId;
    @Embedded
    @Getter
    private PersonNameJPA name;
    @Embedded
    @Getter
    private AddressJPA address;
    @Embedded
    @Getter
    private BirthDateJPA birthdate;
    @Embedded
    @Getter
    private PersonVatJPA vat;
    @Getter
    private String password;
    @Getter
    private long familyId;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<OtherEmailJPA> emailAddresses;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    private List<PhoneNumberJPA> phoneNumbers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personJPA", cascade =
            CascadeType.ALL)
    @Getter
    @Setter
    private List<AccountIdJPA> accounts;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Getter
    @Setter
    private Set<RoleJPA> roles = new HashSet<>();

    public PersonJPA(EmailJPA id, PersonNameJPA name, AddressJPA addressJPA, BirthDateJPA birthdate,
                     PersonVatJPA vat, long familyId) {
        this.id = id;
        this.name = name;
        this.address = addressJPA;
        this.birthdate = birthdate;
        this.vat = vat;
        this.familyId = familyId;
    }

    public PersonJPA(EmailJPA id, PersonNameJPA name, AddressJPA addressJPA, BirthDateJPA birthdate,
                     PersonVatJPA vat, long familyId, String password, Set<RoleJPA> roles) {
        this.id = id;
        this.name = name;
        this.address = addressJPA;
        this.birthdate = birthdate;
        this.vat = vat;
        this.familyId = familyId;
        this.password = password;
        this.roles = new HashSet<>(roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonJPA)) {
            return false;
        }
        PersonJPA personJPA = (PersonJPA) o;
        return dataBaseId == personJPA.dataBaseId && id.equals(personJPA.id) && name.equals(personJPA.name) && address.equals(personJPA.address) && birthdate.equals(personJPA.birthdate) && vat.equals(personJPA.vat) && familyId == personJPA.familyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataBaseId, name, address, birthdate, vat, familyId);
    }
}