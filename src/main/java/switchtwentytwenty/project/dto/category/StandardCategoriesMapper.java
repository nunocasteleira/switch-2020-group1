package switchtwentytwenty.project.dto.category;

import java.util.List;

public class StandardCategoriesMapper {


    public StandardCategoriesDTO mapStandardCategories(List<CategoryDTO> categoryDTOs ) {

        return new StandardCategoriesDTO(categoryDTOs);
    }
}
