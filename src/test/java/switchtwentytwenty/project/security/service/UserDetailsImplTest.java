package switchtwentytwenty.project.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import switchtwentytwenty.project.domain.factories.PersonFactory;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.person.PersonVOs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    @Test
    void build() {
        FamilyId familyId = new FamilyId(1L);
        Email email = new Email("bla@gmail.com");
        PersonName personName = new PersonName("bla");
        Address address = new Address("la la la", "la", "4433-001");
        BirthDate birthDate = new BirthDate("03/07/1990");
        PersonVat vat = new PersonVat("222222888");
        String password = "password";
        Set<Role> roles = new HashSet<>();
        ERole roleFamilyAdmin = ERole.ROLE_FAMILY_ADMIN;
        Role createdRole = new Role(roleFamilyAdmin);
        roles.add(createdRole);
        PersonVOs personVOs = new PersonVOs(email, personName, address, birthDate, vat, "914444555");
        Person person = PersonFactory.buildPerson(personVOs, familyId);
        person.setPassword(password);
        person.setRoles(roles);
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        UserDetailsImpl user = UserDetailsImpl.build(person);

        assertNotNull(user);
        assertEquals(email.getEmailAddress(), user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(familyId.getFamilyId(), user.getFamilyId());
        assertEquals(authorities, user.getAuthorities());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    @Test
    void testEquals() {
        FamilyId familyId = new FamilyId(1L);
        String emailAddress = "bla@gmail.com";
        String differentEmailAddress = "blabla@gmail.com";
        Email email = new Email(emailAddress);
        PersonName personName = new PersonName("bla");
        Address address = new Address("la la la", "la", "4433-001");
        BirthDate birthDate = new BirthDate("03/07/1990");
        PersonVat vat = new PersonVat("222222888");
        String password = "password";
        Set<Role> emptyRoles = new HashSet<>();
        Set<Role> roles = new HashSet<>();
        ERole roleFamilyAdmin = ERole.ROLE_FAMILY_ADMIN;
        Role createdRole = new Role(roleFamilyAdmin);
        roles.add(createdRole);
        PersonVOs personVOs = new PersonVOs(email, personName, address, birthDate, vat, "914444555");
        Person person = PersonFactory.buildPerson(personVOs, familyId);
        person.setPassword(password);
        person.setRoles(roles);
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        UserDetailsImpl userWithBuilder = UserDetailsImpl.build(person);
        UserDetailsImpl userWithBuilderSame = userWithBuilder;
        UserDetailsImpl userWithoutBuilder = new UserDetailsImpl(email.getEmailAddress(), password, personName.toString(), familyId.getFamilyId(), authorities);
        UserDetailsImpl userWithoutBuilderDifferentUsername = new UserDetailsImpl(differentEmailAddress, password, personName.toString(), familyId.getFamilyId(), authorities);

        assertEquals(userWithBuilder, userWithoutBuilder);
        assertEquals(userWithBuilder.hashCode(), userWithoutBuilder.hashCode());
        assertNotSame(userWithBuilder, userWithoutBuilder);
        assertEquals(userWithBuilder, userWithBuilderSame);
        assertSame(userWithBuilder, userWithBuilderSame);
        assertNotEquals(userWithBuilder, userWithoutBuilderDifferentUsername);
        assertNotEquals(userWithBuilder, person);
        assertNotEquals(0, userWithBuilder.hashCode());
    }
}