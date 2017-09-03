package com.maxdidato.coinchange.testutils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Wither;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Setter
@Wither
@NoArgsConstructor
@AllArgsConstructor
public class CoinContainerHelper {

    private Properties prop = new Properties();
    private int onePound;
    private int fiftyPence;
    private int twentyPence;
    private int tenPence;
    private int fivePence;
    private int twoPence;
    private int onePenny;
    private String fileName;

    public void flush() throws IOException {
        prop.setProperty("100", String.valueOf(onePound));
        prop.setProperty("50", String.valueOf(fiftyPence));
        prop.setProperty("20", String.valueOf(twentyPence));
        prop.setProperty("10", String.valueOf(tenPence));
        prop.setProperty("5", String.valueOf(fivePence));
        prop.setProperty("2", String.valueOf(twoPence));
        prop.setProperty("1", String.valueOf(onePenny));
        fileName = "coin-inventory.properties";
        prop.store(new FileOutputStream(fileName), null);

    }

    public void load() throws IOException {
        InputStream input = new FileInputStream(fileName);
        prop.load(input);
    }

    public int getOnePound() {
        return Integer.valueOf(prop.getProperty("100"));
    }

    public int getFiftyPence() {
        return Integer.valueOf(prop.getProperty("50"));
    }

    public int getTwentyPence() {
        return Integer.valueOf(prop.getProperty("20"));
    }

    public int getTenPence() {
        return Integer.valueOf(prop.getProperty("10"));
    }

    public int getFivePence() {
        return Integer.valueOf(prop.getProperty("5"));
    }

    public int getTwoPence() {
        return Integer.valueOf(prop.getProperty("2"));
    }

    public int getOnePenny() {
        return Integer.valueOf(prop.getProperty("1"));
    }
}
