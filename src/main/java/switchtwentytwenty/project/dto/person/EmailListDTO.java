package switchtwentytwenty.project.dto.person;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class EmailListDTO extends RepresentationModel<EmailListDTO> {

    @Getter
    private final List<String> emailAddresses;

    public EmailListDTO(List<String> emailAddresses) {
        this.emailAddresses = new ArrayList<>(emailAddresses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; }

        if (o == null || getClass() != o.getClass()) {
            return false; }

        EmailListDTO that = (EmailListDTO) o;
        return Objects.equals(emailAddresses, that.emailAddresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), emailAddresses);
    }
}
