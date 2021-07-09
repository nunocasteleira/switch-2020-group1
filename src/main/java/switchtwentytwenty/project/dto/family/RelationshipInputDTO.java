package switchtwentytwenty.project.dto.family;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RelationshipInputDTO {
    @Getter
    @Setter
    private String mainUserId;

    @Getter
    @Setter
    private String otherUserId;

    @Getter
    @Setter
    private String relationshipType;
}
