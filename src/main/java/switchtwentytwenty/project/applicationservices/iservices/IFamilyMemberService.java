package switchtwentytwenty.project.applicationservices.iservices;

import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.person.FamilyMembersOutputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.dto.person.ProfileInformationDTO;

public interface IFamilyMemberService {
    AddFamilyMemberDTO addFamilyMember(PersonInputDTO personInputDTO,
                                       long familyId);


    FamilyOutputDTO createFamily(FamilyInputDTO familyInputDTO);

    ProfileInformationDTO getProfileInformation(String personId, String loggedPersonId);

    FamilyMembersOutputDTO getFamilyMembers(long familyId);

    FamilyOutputDTO getFamilyInformation(long familyId);
}
