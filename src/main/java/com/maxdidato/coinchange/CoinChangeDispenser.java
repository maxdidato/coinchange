package com.maxdidato.coinchange;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;

public class CoinChangeDispenser {
    public Collection<Coin> getOptimalChangeFor(int pence) {
        if (pence < 1){
            throw new InvalidParameterException(
                    String.format("Invalid parameter '%s'. The input must be a positive integer",pence));
        }
        ArrayList<Coin> coinChange = new ArrayList<>();
        coinChange.add(Coin.valueOf(pence));
        return coinChange;
    }
}
