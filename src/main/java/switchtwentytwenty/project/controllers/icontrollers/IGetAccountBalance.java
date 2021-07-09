package switchtwentytwenty.project.controllers.icontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;

public interface IGetAccountBalance {
    ResponseEntity<Object> getAccountBalance(@NonNull @PathVariable String personId , @PathVariable long accountId);
}
