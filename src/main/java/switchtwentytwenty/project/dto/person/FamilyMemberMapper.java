package switchtwentytwenty.project.dto.person;

import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FamilyMemberMapper {

    public AddFamilyMemberDTO toDTO(Person person) {
        String email = person.getId().getEmailAddress();
        String name = person.getName().toString();
        String street = person.getAddress().getStreet();
        String location = person.getAddress().getLocation();
        String postalCode = person.getAddress().getPostalCode();
        String birthDate = person.getBirthdate().toString();
        String vat = person.getVat().toString();
        long familyId = person.getFamilyId().getFamilyId();
        String phoneNumber = person.getPhoneNumbers().toString();
        long databaseId = person.getDatabaseId();
        Set<Role> roles = person.getRoles();

        return new AddFamilyMemberDTO(email, databaseId, name, street, location, postalCode, birthDate, vat, familyId, phoneNumber, roles);
    }

    public FamilyMembersOutputDTO toDTO(List<Person> familyMembersWithCashAccounts) {
        List<FamilyMemberOutputDTO> familyMembersWithCashAccountsDTO = new ArrayList<>();
        for (Person familyMember : familyMembersWithCashAccounts) {
            FamilyMemberOutputDTO familyMemberOutputDTO = familyMemberToOutputDTO(familyMember);
            familyMembersWithCashAccountsDTO.add(familyMemberOutputDTO);
        }
        return new FamilyMembersOutputDTO(familyMembersWithCashAccountsDTO);
    }

    private FamilyMemberOutputDTO familyMemberToOutputDTO(Person familyMember) {
        String name = familyMember.getName().toString();
        String email = familyMember.getId().toString();
        return new FamilyMemberOutputDTO(name, email);
    }
}