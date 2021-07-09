package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface IGetStandardCategoriesTreeController {

    @GetMapping("/categories/standard")
    ResponseEntity<Object> getStandardCategoriesTree();
}
