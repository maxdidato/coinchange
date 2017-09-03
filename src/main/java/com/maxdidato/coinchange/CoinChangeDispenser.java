package com.maxdidato.coinchange;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import static com.maxdidato.coinchange.InputValidator.validate;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public class CoinChangeDispenser {

    public Collection<Coin> getOptimalChangeFor(int pence) {
        validate(pence);
        if (pence == 0) {
            return Collections.emptyList();
        } else {
            Coin change = getBiggestCoinChangeFor(pence);
            int newPenceValue = pence - change.getDenomination();
            return concat(Stream.of(change), getOptimalChangeFor(newPenceValue).stream()).collect(toList());
        }
    }

    private Coin getBiggestCoinChangeFor(int pence) {
        return Arrays.stream(Coin.values()).filter(c -> c.getDenomination() <= pence).findFirst().get();
    }

}
