package switchtwentytwenty.project.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CategoryInputDTO {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Object parentId;

}
