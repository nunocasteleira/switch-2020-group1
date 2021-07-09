package switchtwentytwenty.project.applicationservices.implservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import switchtwentytwenty.project.applicationservices.iservices.IFamilyMemberService;
import switchtwentytwenty.project.assemblers.FamilyAssembler;
import switchtwentytwenty.project.assemblers.PersonAssembler;
import switchtwentytwenty.project.domain.domainservices.FamilyDomainService;
import switchtwentytwenty.project.domain.factories.FamilyFactory;
import switchtwentytwenty.project.domain.factories.PersonFactory;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.person.Role;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.dto.family.*;
import switchtwentytwenty.project.dto.person.*;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.PersonRepository;
import switchtwentytwenty.project.repositories.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class FamilyMemberService implements IFamilyMemberService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private FamilyDomainService familyDomainService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FamilyOutputMapper familyOutputMapper;

    /**
     * this methods will allow us to create a new person and add it's id to the family
     *
     * @param personInputDTO persons data
     * @param familyIdString the administrator family
     * @return the person id
     */
    @Override
    public AddFamilyMemberDTO addFamilyMember(PersonInputDTO personInputDTO,
                                              long familyIdString) {
        FamilyId familyId = new FamilyId(familyIdString);
        Email adminId = new Email(personInputDTO.getAdminId());
        checkIfAdminBelongsToFamily(familyId, adminId);
        PersonVOs personVOs = new PersonAssembler().toDomain(personInputDTO);
        personVOs.setPassword(encoder.encode(personInputDTO.getPassword()));
        Person person = PersonFactory.buildPerson(personVOs, familyId);
        promoteToFamilyMember(person);
        addMember(person, familyId);
        return new FamilyMemberMapper().toDTO(person);
    }

    /**
     * Method to check if Admin belongs to the logged Family in the App.
     *
     * @param familyId id of the family
     * @param adminId  admin id (Email)
     */
    private void checkIfAdminBelongsToFamily(FamilyId familyId, Email adminId) {
        Family family = familyRepository.getDatabaseSavedFamily(familyId);
        familyDomainService.checkFamilyAdmin(family, adminId);
    }

    /**
     * This method ensures that the person is created with the proper family id and, at the same
     * time, is added to the list of member of the family
     *
     * @param person   person to add
     * @param familyId family to add the person
     */
    private void addMember(Person person, FamilyId familyId) {
        personRepository.addPerson(person);
        familyRepository.addFamilyMember(person.getId(), familyId);
    }

    /**
     * This method allows to create a family.
     *
     * @param familyInputDTO data transfer object containing all relevant family and administrator
     *                       data.
     * @return familyOutputDTO data transfer object containing all relevant family data.
     */
    @Override
    public FamilyOutputDTO createFamily(FamilyInputDTO familyInputDTO) {
        FamilyInputVOs familyInputVOs = new FamilyAssembler().toDomain(familyInputDTO);
        PersonVOs personVOs = new PersonAssembler().toDomain(familyInputDTO);
        personVOs.setPassword(encoder.encode(familyInputDTO.getPassword()));
        FamilyFactory familyFactory = new FamilyFactory(familyInputVOs, personVOs);
        Family family = familyFactory.getFamily();
        Person administrator = familyFactory.getAdministrator();
        promoteToFamilyMember(administrator);
        promoteToFamilyAdministrator(administrator);
        Family savedFamily = familyRepository.save(family);
        administrator.setFamilyId(savedFamily.getFamilyId());
        addMember(administrator, savedFamily.getFamilyId());
        return new FamilyOutputMapper().toDTO(savedFamily);
    }

    /**
     * This method obtains the profile information DTO object that contains the primitive attributes
     * of an user
     *
     * @param personId id of the user whose information we want to get
     * @return DTO containing the primitive attributes of the user
     */
    @Override
    public ProfileInformationDTO getProfileInformation(String personId, String loggedPersonId) {
        Email aPersonId = new Email(personId);
        Person aPerson = personRepository.getByEmail(aPersonId);
        Email aLoggedPersonId = new Email(loggedPersonId);
        Person loggedPerson = personRepository.getByEmail(aLoggedPersonId);
        checkAuthorizationForPerson(aPerson, loggedPerson);
        FamilyId aFamilyId = aPerson.getFamilyId();
        Family aFamily = familyRepository.getDatabaseSavedFamily(aFamilyId);
        ProfileInformationMapper mapper = new ProfileInformationMapper();
        return mapper.mapProfileInformation(aPerson, aFamily);
    }

    /**
     * Method to check authorization for logged Person to see the member profile.
     *
     * @param person       person who's profile is requested
     * @param loggedPerson person logged in the App
     */
    private void checkAuthorizationForPerson(Person person, Person loggedPerson) {
        FamilyId personFamilyId = person.getFamilyId();
        FamilyId loggedPersonFamilyId = loggedPerson.getFamilyId();
        Family family_Person = familyRepository.getDatabaseSavedFamily(personFamilyId);
        Family family_LoggedPerson = familyRepository.getDatabaseSavedFamily(loggedPersonFamilyId);
        if (!family_Person.equals(family_LoggedPerson)) {
            throw new AccessDeniedException("Access denied: this person is not allowed to see the requested profile.");
        }
    }

    private void promoteToFamilyMember(Person person) {
        Set<Role> roles;
        Set<Role> previousRoles = person.getRoles();
        if (previousRoles.isEmpty()) {
            roles = new HashSet<>();
        } else {
            roles = previousRoles;
        }
        ERole eRoleFamilyMember = ERole.ROLE_FAMILY_MEMBER;
        Role roleFamilyMember = roleRepository.getByName(eRoleFamilyMember);
        roles.add(roleFamilyMember);
        person.setRoles(roles);
    }

    private void promoteToFamilyAdministrator(Person person) {
        Set<Role> roles;
        Set<Role> previousRoles = person.getRoles();
        if (previousRoles.isEmpty()) {
            roles = new HashSet<>();
        } else {
            roles = previousRoles;
        }
        ERole eRoleFamilyAdministrator = ERole.ROLE_FAMILY_ADMIN;
        Role roleFamilyAdministrator = roleRepository.getByName(eRoleFamilyAdministrator);
        roles.add(roleFamilyAdministrator);
        person.setRoles(roles);
    }

    /**
     * This method allows to obtain the list of all family members existing in specific family.
     *
     * @param familyId id of the family.
     * @return DTO with the list of family members id's (email).
     */
    @Override
    public FamilyMembersOutputDTO getFamilyMembers(long familyId) {
        FamilyId aFamilyId = new FamilyId(familyId);
        List<Email> emailFamilyMembers = familyRepository.getFamilyMembers(aFamilyId);
        List<Person> familyMembers = personRepository.getFamilyMembers(emailFamilyMembers);

        FamilyMemberMapper familyMemberMapper = new FamilyMemberMapper();
        return familyMemberMapper.toDTO(familyMembers);
    }

    /**
     * Method to obtain the family information given its id.
     *
     * @param familyId id of the family
     * @return FamilyOutputDTO object
     */
    @Override
    public FamilyOutputDTO getFamilyInformation(long familyId) {
        FamilyId aFamilyId = new FamilyId(familyId);
        Family family = familyRepository.getDatabaseSavedFamily(aFamilyId);
        return familyOutputMapper.toDTO(family);
    }
}
