package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IGetFamilyCategories {

     ResponseEntity<Object> getStandardAndFamilyCategories(@PathVariable("familyId") long familyId);
}
