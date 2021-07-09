package switchtwentytwenty.project.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import switchtwentytwenty.project.domain.model.person.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final String username;

    @JsonIgnore
    private final String password;

    private final long familyId;

    private final String personName;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String email, String password, String personName, long familyId,
                           Collection<? extends GrantedAuthority> authorities) {
        this.username = email;
        this.password = password;
        this.personName = personName;
        this.familyId = familyId;
        this.authorities = new ArrayList<>(authorities);
    }

    public static UserDetailsImpl build(Person person) {
        List<GrantedAuthority> authorities = person.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                person.getId().getEmailAddress(),
                person.getPassword(),
                person.getName().toString(),
                person.getFamilyId().getFamilyId(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getPersonName() {
        return personName;
    }

    public long getFamilyId() {
        return familyId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, personName, familyId, authorities);
    }
}
