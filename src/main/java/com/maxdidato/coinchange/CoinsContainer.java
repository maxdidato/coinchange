package com.maxdidato.coinchange;

import java.io.*;
import java.util.Collection;
import java.util.Properties;

public class CoinsContainer {
    private final String filename;
    Properties coins = new Properties();

    public CoinsContainer(String filename) throws IOException {
        this.filename = filename;
    }

    public void removeCoins(Collection<Coin> optimalChangeFor) throws IOException {
        coins.load(new FileInputStream(this.filename));
        optimalChangeFor.forEach(c -> {
                    String denomination = String.valueOf(c.getDenomination());
                    String oldValue = coins.getProperty(denomination);
                    coins.put(denomination, String.valueOf(Integer.valueOf(oldValue) - 1));
                }

        );
        coins.store(new FileOutputStream(filename), null);
    }
}
