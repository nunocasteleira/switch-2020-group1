package switchtwentytwenty.project.applicationservices.iservices;

import switchtwentytwenty.project.dto.person.EmailListDTO;

public interface IPersonService {

    EmailListDTO addEmail(String id, String email);

    EmailListDTO removeEmail(String id, String email);
}
