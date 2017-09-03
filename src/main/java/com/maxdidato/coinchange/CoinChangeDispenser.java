package com.maxdidato.coinchange;


import java.security.InvalidParameterException;
import java.util.*;

import static java.util.Arrays.*;

public class CoinChangeDispenser {
    public Collection<Coin> getOptimalChangeFor(int pence) {
        if ((pence < 0)||(pence > 100000)) {
            throw new InvalidParameterException(
                    String.format("Invalid parameter '%s'. The input must be a in 0..100000 range", pence));
        } else if (pence == 0) {
            return Collections.emptyList();
        } else {
            Coin change = Arrays.stream(Coin.values()).filter(c -> c.getDenomination() <= pence).findFirst().get();
            ArrayList<Coin> tempResult = new ArrayList<>();
            tempResult.add(change);
            tempResult.addAll(getOptimalChangeFor(pence - change.getDenomination()));
            return tempResult;
        }
    }
}
