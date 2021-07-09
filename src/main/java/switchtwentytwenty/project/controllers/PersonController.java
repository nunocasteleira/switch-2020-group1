package switchtwentytwenty.project.controllers;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import switchtwentytwenty.project.applicationservices.iservices.IFamilyMemberService;
import switchtwentytwenty.project.applicationservices.iservices.IPersonAccountService;
import switchtwentytwenty.project.applicationservices.iservices.IPersonService;
import switchtwentytwenty.project.controllers.icontrollers.*;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.EmailCannotBeNullException;
import switchtwentytwenty.project.domain.exceptions.InvalidEmailException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.dto.account.CashAccountsOutputDTO;
import switchtwentytwenty.project.dto.person.FamilyMembersOutputDTO;
import switchtwentytwenty.project.dto.person.EmailListDTO;
import switchtwentytwenty.project.dto.person.ProfileInformationDTO;
import switchtwentytwenty.project.security.service.UserDetailsImpl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class PersonController implements IGetProfileInformationController, IAddEmailController, IRemoveEmailController, IGetCashAccountsController, IGetFamilyMembersWithCashAccounts {

    @Autowired
    private IPersonService personService;
    @Autowired
    private IFamilyMemberService familyMemberService;
    @Autowired
    private IPersonAccountService personAccountService;

    /**
     * This method obtains a user's profile information and hateoas links.
     *
     * @return profile information and hateoas links as a DTO.
     */

    @Override
    @GetMapping("/members/{personId}")
    public ResponseEntity<Object> getProfileInformation(@NonNull @PathVariable String personId) {
        ProfileInformationDTO aProfileInformationDTO;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String loggedPersonId = userDetails.getUsername();
            aProfileInformationDTO = familyMemberService.getProfileInformation(personId, loggedPersonId);
            long familyId = aProfileInformationDTO.getFamilyId();

            Link selfLink = linkTo(PersonController.class).slash(personId).withSelfRel();
            Link familyInformationLink = linkTo(FamilyController.class).slash("families").slash(familyId).withRel("familyInformation");
            Link accountListLink =
                    linkTo(AccountController.class).slash("accounts").slash(personId).withRel("accountList");

            aProfileInformationDTO.add(selfLink, familyInformationLink, accountListLink);
            return new ResponseEntity<>(aProfileInformationDTO, HttpStatus.OK);
        } catch (NullPointerException | AccessDeniedException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to add the emailAddress to the family member with the personId
     *
     * @param email string of the email address
     * @return True if the email is added successfully to the family member, false otherwise
     */
    @Override
    @PutMapping("/members/{personId}/emails")
    public ResponseEntity<Object> addEmail(@PathVariable String personId,
                                           @RequestBody String email) {
        EmailListDTO emailListDTO;
        try {
            emailListDTO = personService.addEmail(personId, email);
            Link selfLink = linkTo(PersonController.class).slash("members").slash(personId).slash("emails").withSelfRel();
            emailListDTO.add(selfLink);
            return new ResponseEntity<>(emailListDTO, HttpStatus.CREATED);
        } catch (EmailCannotBeNullException | InvalidEmailException | DuplicateObjectException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to remove the emailAddress from family member with the personId
     *
     * @param email string of the email address
     * @return a response entity that contains the HttpStatus and a body with either an emailListDTO
     *         and a self link or an error message
     */
    @Override
    @DeleteMapping("/members/{personId}/emails/{email}")
    public ResponseEntity<Object> removeEmail(@PathVariable String personId,
                                              @PathVariable String email) {
        EmailListDTO emailListDTO;
        try {
            emailListDTO = personService.removeEmail(personId, email);
            Link selfLink = linkTo(PersonController.class).slash("members").slash(personId).slash("emails").withSelfRel();
            emailListDTO.add(selfLink);
            return new ResponseEntity<>(emailListDTO, HttpStatus.OK);
        } catch (EmailCannotBeNullException | InvalidEmailException | ObjectDoesNotExistException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method allows to obtain the list of all cash accounts of a specific family member.
     *
     * @param personId family member's email address.
     * @return the list of cash accounts.
     */
    @Override
    @GetMapping("/members/{personId}/cashAccounts")
    public ResponseEntity<Object> getCashAccounts(@PathVariable String personId) {
        CashAccountsOutputDTO cashAccountsDTO = personAccountService.getCashAccounts(personId);
        Link selfLink = linkTo(PersonController.class).slash(personId).withSelfRel();
        Link accountListLink = linkTo(AccountController.class).slash("accounts").slash(personId).withRel("accountList");
        cashAccountsDTO.add(selfLink, accountListLink);
        return new ResponseEntity<>(cashAccountsDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("/families/{personId}/membersAccounts")
    public ResponseEntity<Object> getFamilyMembersWithCashAccounts(@PathVariable String personId) {
        FamilyMembersOutputDTO familyMembersDTO = personAccountService.getFamilyMembersWithCashAccounts(personId);
        Link selfLink = linkTo(FamilyController.class).slash(personId).withSelfRel();
        Link membersListLink = linkTo(PersonController.class).slash("members").slash(personId).withRel("membersList");
        familyMembersDTO.add(selfLink, membersListLink);
        return new ResponseEntity<>(familyMembersDTO, HttpStatus.OK);
    }
}