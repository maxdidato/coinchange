package com.maxdidato.coinchange;

import com.maxdidato.coinchange.exception.InsufficientCoinage;
import com.maxdidato.coinchange.model.Coin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.maxdidato.coinchange.validator.InputValidator.validate;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public class CoinChangeDispenser {

    private final CoinsContainer coinsContainer;

    public CoinChangeDispenser(CoinsContainer coinsContainer) {
        this.coinsContainer = coinsContainer;
    }

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

    public Collection<Coin> getChangeFor(int pence) throws InsufficientCoinage {
        Collection<Coin> optimalChangeFor = getOptimalChangeFor(pence);
        try {
            coinsContainer.removeCoins(optimalChangeFor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return optimalChangeFor;
    }

    public static void main(String[] args) throws IOException {
        //This implement a rough command line ui to the coin changer.
        //No validation is implemented here as this is for testing purposes
        System.out.println("WELCOME TO COIN CHANGE. PRESS Q ANY TIME TO QUIT");
        while (true) {
            CoinsContainer coinsContainer = new CoinsContainer("coin-inventory.properties");
            CoinChangeDispenser coinChangeDispenser = new CoinChangeDispenser(coinsContainer);
            System.out.println("How many pence you want to change?");
            Scanner scanner = new Scanner(System.in);
            String pence = scanner.nextLine();
            if (pence.toLowerCase().equals("q")) {
                break;
            }
            System.out.println("THE OPTIMAL CHANGE IS\n");
            int intPence = Integer.parseInt(pence);
            System.out.println(coinChangeDispenser.getOptimalChangeFor(intPence));
            try {
                coinChangeDispenser.getChangeFor(intPence);
                System.out.println("WE WERE ABLE TO DISPENSE THE COINS:\n");
            } catch (InsufficientCoinage insufficientCoinage) {
                System.out.println(insufficientCoinage.getMessage());
            }
            System.out.println("COINS COUNT:");
            System.out.println(coinsContainer.coins);
        }


    }
}

