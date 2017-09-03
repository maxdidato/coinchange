package com.maxdidato.coinchange.testutils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Wither;

import java.io.*;
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

    public String createFileWithMissingValues() throws IOException {
        prop.setProperty("2", String.valueOf(twoPence));
        prop.setProperty("1", String.valueOf(onePenny));
        String malformedFilename = "/tmp/malformed.properties";
        prop.store(new FileOutputStream(malformedFilename), null);
        return malformedFilename;
    }

    public String createFileWithNonNumericalEntries() throws IOException {
        prop.setProperty("100", String.valueOf(onePound));
        prop.setProperty("50", String.valueOf(fiftyPence));
        prop.setProperty("20", "nonnumeric");
        prop.setProperty("10", String.valueOf(tenPence));
        prop.setProperty("5", String.valueOf(fivePence));
        prop.setProperty("2", String.valueOf(twoPence));
        prop.setProperty("1", String.valueOf(onePenny));
        String malformedFilename = "/tmp/malformed.properties";
        prop.store(new FileOutputStream(malformedFilename), null);
        return malformedFilename;
    }

    public String createFileWithNegativeNumbers() throws IOException {
        prop.setProperty("100", String.valueOf(onePound));
        prop.setProperty("50", String.valueOf(fiftyPence));
        prop.setProperty("20", String.valueOf(twentyPence));
        prop.setProperty("10", String.valueOf(tenPence));
        prop.setProperty("5", "-1");
        prop.setProperty("2", String.valueOf(twoPence));
        prop.setProperty("1", String.valueOf(onePenny));
        String malformedFilename = "/tmp/malformed.properties";
        prop.store(new FileOutputStream(malformedFilename), null);
        return malformedFilename;
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
