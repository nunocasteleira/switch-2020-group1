package switchtwentytwenty.project.assemblers;

import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.dto.person.PersonVOs;

public class PersonAssembler {
    public PersonVOs toDomain(PersonInputDTO personInputDTO) {
        Email email = new Email(personInputDTO.getEmail());
        PersonName name = new PersonName(personInputDTO.getName());
        Address address = new Address(personInputDTO.getStreet(), personInputDTO.getLocation(),
                personInputDTO.getPostCode());
        BirthDate birthDate = new BirthDate(personInputDTO.getBirthDate());
        PersonVat vat = new PersonVat(personInputDTO.getVat());
        String phoneNumber = personInputDTO.getPhoneNumber();
        String password = personInputDTO.getPassword();

        return new PersonVOs(email, name, address, birthDate, vat, phoneNumber, password);
    }

    public PersonVOs toDomain(FamilyInputDTO familyInputDTO) {
        Email email = new Email(familyInputDTO.getEmail());
        PersonName name = new PersonName(familyInputDTO.getName());
        Address address = new Address(familyInputDTO.getStreet(), familyInputDTO.getLocation(),
                familyInputDTO.getPostCode());
        BirthDate birthDate = new BirthDate(familyInputDTO.getBirthDate());
        PersonVat vat = new PersonVat(familyInputDTO.getVat());
        String phoneNumber = familyInputDTO.getPhoneNumber();
        String password = familyInputDTO.getPassword();

        return new PersonVOs(email, name, address, birthDate, vat, phoneNumber, password);
    }
}
