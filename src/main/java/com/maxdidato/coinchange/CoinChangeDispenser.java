package com.maxdidato.coinchange;


import java.util.ArrayList;
import java.util.Collection;

public class CoinChangeDispenser {
    public Collection<Coin> getOptimalChangeFor(int pence) {
        ArrayList<Coin> coinChange = new ArrayList<>();
        coinChange.add(Coin.valueOf(pence));
        return coinChange;
    }
}
