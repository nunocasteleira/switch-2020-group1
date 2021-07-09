package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import switchtwentytwenty.project.dto.category.CategoryInputDTO;

public interface ICreateStandardCategoryController {

    ResponseEntity<Object> createStandardCategory(CategoryInputDTO inputDTO);
}
