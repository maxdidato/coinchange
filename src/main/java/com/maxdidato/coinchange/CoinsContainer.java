package com.maxdidato.coinchange;

import com.maxdidato.coinchange.exception.InsufficientCoinage;
import com.maxdidato.coinchange.model.Coin;
import com.maxdidato.coinchange.validator.FileValidator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import static com.maxdidato.coinchange.validator.FileValidator.validateFile;
import static java.lang.String.format;

public class CoinsContainer {
    private final String filename;
    Properties coins = new Properties();

    public CoinsContainer(String filename) throws IOException {
        validateFile(filename);
        this.filename = filename;
    }

    public void removeCoins(Collection<Coin> coins) throws IOException, InsufficientCoinage {
        readProperties();
        for (Coin coin : coins) {
            String denomination = String.valueOf(coin.getDenomination());
            String oldValue = this.coins.getProperty(denomination);
            String newValue = String.valueOf(Integer.valueOf(oldValue) - 1);
            if (Integer.valueOf(newValue) < 0) {
                throw new InsufficientCoinage(format("There are no more coins of denomination %s", denomination));
            }
            this.coins.put(denomination, newValue);
        }
        flush();
    }

    private void readProperties() throws IOException {
        this.coins.load(new FileInputStream(this.filename));
    }

    private void flush() throws IOException {
        this.coins.store(new FileOutputStream(filename), null);
    }

}
