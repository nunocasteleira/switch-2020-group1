package switchtwentytwenty.project.dto.family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import switchtwentytwenty.project.domain.model.shared.Email;
import switchtwentytwenty.project.domain.model.shared.FamilyName;

@AllArgsConstructor
public class FamilyInputVOs {

    @Getter
    private final FamilyName familyName;
    @Getter
    private final Email email;
}
