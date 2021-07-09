package switchtwentytwenty.project.dto.family;

import org.springframework.stereotype.Component;
import switchtwentytwenty.project.domain.model.family.Family;

@Component
public class FamilyOutputMapper {

    /**
     * Method to convert family to FamilyOutputDTO.
     * @param family family object
     * @return FamilyOutputDTO object
     */
    public FamilyOutputDTO toDTO(Family family) {
        long familyId = family.getFamilyId().getFamilyId();
        String name = family.getFamilyName().toString();
        String adminId = family.getAdminId().toString();
        String registrationDate = family.getRegistrationDate().toString();

        return new FamilyOutputDTO(familyId, name, adminId, registrationDate);
    }
}
