package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface IGetFamilyCategoriesTreeController {

    @GetMapping("/categories/{familyId}")
    ResponseEntity<Object> getFamilyCategoriesTree(@PathVariable("familyId") long familyId);
}
