package switchtwentytwenty.project.dto.family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import switchtwentytwenty.project.domain.model.shared.*;

import java.util.List;

@AllArgsConstructor
public class FamilyOutputVOs {

    @Getter
    private final FamilyName familyName;
    @Getter
    private final Email email;
    @Getter
    private final RegistrationDate registrationDate;
    @Getter
    private final FamilyId familyId;
    @Getter
    private final AccountId accountId;
    @Getter
    private final List<Email> familyMembers;
    @Getter
    private final List<Relationship> familyRelationships;
}
