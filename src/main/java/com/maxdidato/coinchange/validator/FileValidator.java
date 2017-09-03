package com.maxdidato.coinchange.validator;

import com.maxdidato.coinchange.model.Coin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import static java.lang.String.format;

public class FileValidator {

   public static void validateFile(String filename) throws IOException {
        checkIfFileExists(filename);
        Properties pros = new Properties();
        pros.load(new FileInputStream(filename));
        Arrays.stream(Coin.values()).forEach(c -> {
            try {
                if (getNumericValue(pros, c) < 0) {
                    throw new MalformedParametersException(format("The values can only be positive integers", filename));
                }
            } catch (NumberFormatException ex) {
                throw new MalformedParametersException(format("The file for coin storage '%s' is malformed", filename));
            }
        });
    }

    private static int getNumericValue(Properties pros, Coin c) {
        return Integer.parseInt(pros.getProperty(String.valueOf(c.getDenomination())));
    }

    private static void checkIfFileExists(String filename) throws FileNotFoundException {
        if (!Files.exists(Paths.get(filename))) {
            throw new FileNotFoundException(format("The file for coin storage '%s' doesn't exist", filename));
        }
    }
}
