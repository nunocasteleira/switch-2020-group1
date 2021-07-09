package switchtwentytwenty.project.domain.model.account;

import lombok.Getter;
import switchtwentytwenty.project.domain.model.interfaces.Entity;
import switchtwentytwenty.project.domain.model.shared.AccountId;
import switchtwentytwenty.project.domain.model.shared.Description;
import switchtwentytwenty.project.domain.model.shared.InitialAmountValue;
import switchtwentytwenty.project.domain.model.shared.Provider;

public class PersonalBankAccount extends Account implements Entity {

    @Getter
    private Provider provider;

    /**
     * this is the constructor for the personal bank account
     * @param initialAmountValue this is the initial amount for the account and
     * @param accountDescription
     * @param provider
     */
    public PersonalBankAccount(InitialAmountValue initialAmountValue, Description accountDescription, Provider provider) {
        this.accountId = new AccountId(0);
        this.initialAmountValue = initialAmountValue;
        this.accountDescription = accountDescription;
        this.provider = provider;
        validateEntryData();
    }

    private void validateEntryData(){
        if(this.initialAmountValue == null || this.accountDescription == null || this.provider == null){
            throw new IllegalArgumentException("Make sure all required data is filled");
        }
    }

    @Override
    public boolean hasId(AccountId id) {
        return false;
    }
}
