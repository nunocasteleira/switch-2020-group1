package switchtwentytwenty.project.applicationservices.implservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import switchtwentytwenty.project.applicationservices.iservices.IPersonService;
import switchtwentytwenty.project.datamodel.assembler.PersonDomainDataAssembler;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.InvalidEmailException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.dto.person.EmailListDTO;
import switchtwentytwenty.project.dto.person.EmailListMapper;
import switchtwentytwenty.project.repositories.PersonRepository;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

@Service
public class PersonService implements IPersonService {
    @Autowired
    IPersonRepositoryJPA iPersonRepositoryJPA;
    @Autowired
    PersonDomainDataAssembler personDomainDataAssembler;
    @Autowired
    private PersonRepository personRepository;


    @Override
    public EmailListDTO addEmail(String id, String email) {
        Email aPersonId = new Email(id);

        EmailListMapper mapper = new EmailListMapper();
        Person aPerson = personRepository.getByEmail(aPersonId);

        if (!personRepository.existsByEmailJPA(new Email(email))) {
            aPerson.addEmail(email);
            personRepository.savePerson(aPerson);
            return mapper.toDTO(aPerson);
        } else {
            throw new DuplicateObjectException("Email already exists");
        }
    }

    /**
     * Method to remove a secondary email from a person's email addresses
     *
     * @param id the person main email (their Id)
     * @param email the email that will be removed from the person's email addresses
     * @return an EmailListDTO containing the person's email addresses
     */

    @Override
    public EmailListDTO removeEmail(String id, String email) {
        if(id.equals(email)){
            throw new InvalidEmailException("Can't remove primary email");
        }

        Email aPersonId = new Email(id);

        EmailListMapper mapper = new EmailListMapper();
        Person aPerson = personRepository.getByEmail(aPersonId);

        if (!personRepository.existsByEmailJPA(new Email(email))) {
            aPerson.removeEmail(email);
            personRepository.savePerson(aPerson);
            return mapper.toDTO(aPerson);
        } else {
            throw new ObjectDoesNotExistException("Email does not exist");
        }
    }
}
