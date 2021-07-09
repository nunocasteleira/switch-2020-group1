package switchtwentytwenty.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import switchtwentytwenty.project.applicationservices.iservices.IFamilyMemberService;
import switchtwentytwenty.project.applicationservices.iservices.IRelationshipService;
import switchtwentytwenty.project.controllers.icontrollers.*;
import switchtwentytwenty.project.domain.exceptions.*;
import switchtwentytwenty.project.dto.family.*;
import switchtwentytwenty.project.dto.person.FamilyMembersOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.repositories.PersonRepository;

import java.time.format.DateTimeParseException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FamilyController implements ICreateFamilyController, IAddFamilyMemberController, ICreateRelationshipController,
        IGetMembersAndRelationsController, IChangeRelationshipController, IGetRelationshipTypesController, IGetFamilyMembersController, IGetFamilyInformationController {
    private static final String FAMILIES = "families";
    @Autowired
    private IFamilyMemberService familyMemberService;
    @Autowired
    private IRelationshipService relationshipService;

    /**
     * This method allows to create a family.
     *
     * @param familyInputDTO data transfer object containing all relevant family and administrator
     *                       data.
     * @return id of the family.
     */
    @Override
    @PostMapping("/families")
    public ResponseEntity<Object> createFamily(@RequestBody @NonNull FamilyInputDTO familyInputDTO) {
        try {
            FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
            long familyId = familyOutputDTO.getFamilyId();
            Link selfLink = linkTo(FamilyController.class).slash(FAMILIES).slash(familyId).withSelfRel();
            familyOutputDTO.add(selfLink);
            return new ResponseEntity<>(familyOutputDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * this is a method that allow us to add a family member. First it will create the person and
     * the add the id to the family
     *
     * @param personInputDTO person data
     * @return the persons email
     */
    @Override
    @PostMapping("/families/{familyId}/members")
    public ResponseEntity<Object> addFamilyMember(@RequestBody @NonNull PersonInputDTO personInputDTO,
                                                  @PathVariable("familyId") long familyId) {
        try {
            AddFamilyMemberDTO familyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyId);
            String memberId = familyMemberDTO.getEmail();
            Link selfLink = linkTo(methodOn(PersonController.class).getProfileInformation(memberId)).withRel(memberId);
            familyMemberDTO.add(selfLink);
            return new ResponseEntity<>(familyMemberDTO, HttpStatus.CREATED);
        } catch (ObjectCanNotBeNullException | DuplicateObjectException | ObjectDoesNotExistException | EmailCannotBeNullException | InvalidEmailException | InvalidNameException | InvalidStreetException | InvalidLocationException | InvalidPostCodeException | InvalidVatNumberException | InvalidPhoneNumberException | InvalidDateException | DateTimeParseException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InvalidDataAccessApiUsageException exception) {
            return new ResponseEntity<>(PersonRepository.DUPLICATE_EMAIL, HttpStatus.BAD_REQUEST);
        } catch (AccessDeniedException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * @param familyId id of the family
     * @return boolean true, if the relationship was created successfully, false if not
     */
    @Override
    @PostMapping("/families/{familyId}/relationships")
    public ResponseEntity<Object> createRelationship(@RequestBody @NonNull RelationshipInputDTO inputDTO,
                                                     @PathVariable("familyId") long familyId) {
        try {
            RelationshipOutputDTO relationshipOutputDTO = relationshipService.createRelationship(familyId, inputDTO);
            Link selfLink = linkTo(methodOn(FamilyController.class).getListOfMembersAndTheirRelations(familyId)).withSelfRel();
            relationshipOutputDTO.add(selfLink);
            return new ResponseEntity<>(relationshipOutputDTO, HttpStatus.CREATED);

        } catch (NullPointerException | DuplicateObjectException | ObjectCanNotBeNullException | InvalidEmailException | InvalidRelationshipType | EmailCannotBeNullException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to get the existing relations inside a family
     *
     * @return a List of RelationshipOutputDTO, containing two ProfileInformationDTO and the
     *         relation between them
     */
    @Override
    @GetMapping("/families/{familyId}/relationships")
    public ResponseEntity<Object> getListOfMembersAndTheirRelations(@PathVariable("familyId") long familyId) {
        RelationshipListDTO listOfAllMembersAndRelations;
        try {
            listOfAllMembersAndRelations = relationshipService.getFamilyRelationshipList(familyId);
            for (RelationshipOutputDTO relationshipOutputDTO : listOfAllMembersAndRelations.getRelationshipList()) {
                String mainUserId = relationshipOutputDTO.getMainUser().getUserId();
                String otherUserId = relationshipOutputDTO.getOtherUser().getUserId();
                Link linkToMainUser =
                        linkTo(methodOn(PersonController.class).getProfileInformation(mainUserId)).withRel(mainUserId);
                Link linkToOtherUser =
                        linkTo(methodOn(PersonController.class).getProfileInformation(otherUserId)).withRel(otherUserId);
                relationshipOutputDTO.add(linkToMainUser);
                relationshipOutputDTO.add(linkToOtherUser);
            }
        } catch (ObjectDoesNotExistException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Link selfLink =
                linkTo(methodOn(FamilyController.class).getListOfMembersAndTheirRelations(familyId)).withSelfRel();
        listOfAllMembersAndRelations.add(selfLink);
        return new ResponseEntity<>(listOfAllMembersAndRelations, HttpStatus.OK);
    }

    /**
     * This method allows to obtain the list of all family members existing in specific family.
     *
     * @param familyId id of the family of which we want to obtain the list of members.
     * @return the list of family members.
     */
    @Override
    @GetMapping("/families/{familyId}/members")
    public ResponseEntity<Object> getFamilyMembers(@PathVariable("familyId") long familyId) {
        FamilyMembersOutputDTO familyMembersDTO = familyMemberService.getFamilyMembers(familyId);
        Link selfLink = linkTo(FamilyController.class).slash(FAMILIES).slash(familyId).withSelfRel();
        Link membersListLink = linkTo(PersonController.class).slash("members").slash(familyId).withRel("membersList");
        familyMembersDTO.add(selfLink, membersListLink);
        return new ResponseEntity<>(familyMembersDTO, HttpStatus.OK);
    }

    /**
     * Method to change a relationship in the family.
     *
     * @param familyId       - id of the family
     * @param relationshipId - id of the relationship
     * @return response entity with the RelationshipOutputDTO with changed relationship
     */
    @Override
    @PutMapping("/families/{familyId}/relationships/{relationshipId}")
    public ResponseEntity<Object> changeRelationship(@PathVariable("familyId") long familyId, @PathVariable("relationshipId") int relationshipId, int relationshipType) {
        try {
            RelationshipOutputDTO relationshipOutputDTO = relationshipService.changeRelationship(familyId, relationshipId, relationshipType);
            Link selfLink = linkTo(methodOn(FamilyController.class).getListOfMembersAndTheirRelations(familyId)).withSelfRel();
            relationshipOutputDTO.add(selfLink);
            return new ResponseEntity<>(relationshipOutputDTO, HttpStatus.OK);

        } catch (ObjectDoesNotExistException | InvalidRelationshipType exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Method to get the relationship types list.
     * @return RelationshipTypesListDTO object
     */
    @Override
    @GetMapping("/families/relationshipTypes")
    public ResponseEntity<Object> getRelationshipTypes() {
        RelationshipTypesListDTO relationshipTypesList = relationshipService.getRelationshipTypesListDTO();
        Link selfLink = linkTo(FamilyController.class).slash(FAMILIES).slash("relationshipTypes").withSelfRel();
        relationshipTypesList.add(selfLink);
        return new ResponseEntity<>(relationshipTypesList, HttpStatus.OK);
    }

    /**
     * Method to get the family information.
     * @param familyId id of the family
     * @return FamilyOutputDTO object
     */
    @Override
    @GetMapping("/families/{familyId}")
    public ResponseEntity<Object> getFamilyInformation(@PathVariable long familyId) {
        try{
            FamilyOutputDTO familyInformationDTO = familyMemberService.getFamilyInformation(familyId);
            Link selfLink = linkTo(FamilyController.class).slash(FAMILIES).slash(familyId).withSelfRel();
            familyInformationDTO.add(selfLink);
            return new ResponseEntity<>(familyInformationDTO, HttpStatus.OK);
        } catch (ObjectDoesNotExistException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}