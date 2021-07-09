package switchtwentytwenty.project.assemblers;

import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.FamilyName;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyInputVOs;

public class FamilyAssembler {

    public FamilyInputVOs toDomain(FamilyInputDTO familyInputDTO) {
        FamilyName familyName = new FamilyName(familyInputDTO.getFamilyName());
        Email adminId = new Email(familyInputDTO.getEmail());

        return new FamilyInputVOs(familyName, adminId);
    }
}