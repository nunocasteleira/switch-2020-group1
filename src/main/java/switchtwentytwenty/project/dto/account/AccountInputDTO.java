package switchtwentytwenty.project.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class AccountInputDTO {
    @Getter
    @Setter
    private long familyId;
    @Getter
    @Setter
    private double initialAmount;
    @Getter
    @Setter
    private int currency;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String personId;
    @Getter
    @Setter
    private String provider;


    /**
     * Constructor method for FamilyCashAccountInputDTO object.
     *
     * @param familyId      id of the family
     * @param initialAmount initial cash amount in the account
     */
    public AccountInputDTO(long familyId, double initialAmount, int currency, String description) {
        this.familyId = familyId;
        this.initialAmount = initialAmount;
        this.currency = currency;
        this.description = description;
    }

    /**
     * this is the constructor for the personal bank account
     *
     * @param initialAmount initial amount for the account
     * @param currency      the currency
     * @param description   the intended description
     * @param provider      the bank where the account is alocated
     */
    public AccountInputDTO(double initialAmount, int currency, String description, String provider) {
        this.initialAmount = initialAmount;
        this.currency = currency;
        this.description = description;
        this.provider = provider;
    }

    /**
     * Constructor method for the DTO for the personal cash account.
     *
     * @param initialAmount initial amount the account will have.
     * @param currency      the currency.
     * @param description   the account description
     */
    public AccountInputDTO(double initialAmount, int currency, String description) {
        this.initialAmount = initialAmount;
        this.currency = currency;
        this.description = description;
    }
}
