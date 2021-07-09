package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import switchtwentytwenty.project.dto.category.CategoryInputDTO;

public interface ICreateFamilyCategoryController {

    ResponseEntity<Object> createFamilyCategory(@RequestBody CategoryInputDTO categoryInputDTO, @PathVariable long familyId);
}
