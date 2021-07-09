package switchtwentytwenty.project.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Valid
public class TransferInputDTO {
    @Getter
    @Setter
    @NotBlank
    private double amount;
    @Getter
    @Setter
    @NotBlank
    private int currency;
    @Getter
    @Setter
    @NotBlank
    private String description;
    @Getter
    @Setter
    @NotBlank
    private String date;
    @Getter
    @Setter
    @NotBlank
    private Object categoryId;
    @Getter
    @Setter
    @NotBlank
    private long destinationAccountId;
}