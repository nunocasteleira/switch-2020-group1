package switchtwentytwenty.project.domain.model.shared;

import lombok.Getter;

import java.util.HashMap;

public enum Currency {
    EUR(1),
    USD(2),
    GBP(3),
    BRL(4);

    private static HashMap currencyMap = new HashMap<>();

    static {
        for (Currency currency : Currency.values()) {
            currencyMap.put(currency.currencyNumber, currency);
        }
    }

    @Getter
    private int currencyNumber;

    Currency(int currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    public static Currency convertNumberToCurrency(int currencyNumber) {
        if (currencyNumber > Currency.values().length || currencyNumber < Currency.values()[0].currencyNumber) {
            throw new IllegalArgumentException("Invalid currency.");
        }
        return (Currency) currencyMap.get(currencyNumber);
    }
}
