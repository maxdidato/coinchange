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

    public void removeCoins(Collection<Coin> coins) throws IOException, InsufficientCoinage {
        this.coins.load(new FileInputStream(this.filename));
        for (Coin c : coins) {
            String denomination = String.valueOf(c.getDenomination());
            String oldValue = this.coins.getProperty(denomination);
            String newValue = String.valueOf(Integer.valueOf(oldValue) - 1);
            if (Integer.valueOf(newValue) < 0) {
                throw new InsufficientCoinage(String.format("There are no more coins of denomination %s", denomination));
            }
            this.coins.put(denomination, newValue);
        }
        this.coins.store(new FileOutputStream(filename), null);
    }
}
