package com.maxdidato.coinchange;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Optional;

public enum Coin {

    ONE_PENNY(1), TWO_PENCE(2), THREE_PENCE(3), FIVE_PENCE(5),
    TEN_PENCE(10), TWENTY_PENCE(20), FIFTY_PENCE(50), ONE_POUND(100);

    private int denomination;

    private Coin(int value) {
        this.denomination = value;
    }

    public int getDenomination() {
        return denomination;
    }

    public static Coin valueOf(int denomination) {
        Optional<Coin> foundCoin = Arrays.stream(Coin.values()).filter(c -> c.getDenomination() == denomination).findFirst();
        if (!foundCoin.isPresent()) {
            throw new InvalidParameterException(String.format("The coin with denomination %s does not exist", denomination));
        }
        return foundCoin.get();
    }

}
