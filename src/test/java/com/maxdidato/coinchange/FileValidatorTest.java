package com.maxdidato.coinchange;

import com.maxdidato.coinchange.exception.InsufficientCoinage;
import com.maxdidato.coinchange.testutils.CoinContainerHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.MalformedParametersException;

import static com.maxdidato.coinchange.validator.FileValidator.validateFile;


public class FileValidatorTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    @Test
    public void if_the_file_doesnt_exist_an_exception_is_raised() throws IOException, InsufficientCoinage {
        expectedEx.expect(FileNotFoundException.class);
        expectedEx.expectMessage("The file for coin storage 'non existing file' doesn't exist");
        validateFile("non existing file");
    }

    @Test
    public void if_the_file_has_missing_values_an_exception_is_raised() throws IOException, InsufficientCoinage {
        CoinContainerHelper coinContainerHelper = new CoinContainerHelper();
        String fileWithMissingValues = coinContainerHelper.createFileWithMissingValues();
        expectedEx.expect(MalformedParametersException.class);
        expectedEx.expectMessage("The file for coin storage '" + fileWithMissingValues + "' is malformed");
        new CoinsContainer(fileWithMissingValues);
    }

    @Test
    public void if_the_file_has_non_numeric_values_for_entries_an_exception_is_raised() throws IOException, InsufficientCoinage {
        CoinContainerHelper coinContainerHelper = new CoinContainerHelper();
        String fileWithMissingValues = coinContainerHelper.createFileWithNonNumericalEntries();
        expectedEx.expect(MalformedParametersException.class);
        expectedEx.expectMessage("The file for coin storage '" + fileWithMissingValues + "' is malformed");
        new CoinsContainer(fileWithMissingValues);
    }

    @Test
    public void if_the_file_has_negative_values_for_entries_an_exception_is_raised() throws IOException, InsufficientCoinage {
        CoinContainerHelper coinContainerHelper = new CoinContainerHelper();
        String fileWithMissingValues = coinContainerHelper.createFileWithNegativeNumbers();
        expectedEx.expect(MalformedParametersException.class);
        expectedEx.expectMessage("The values can only be positive integers");
        new CoinsContainer(fileWithMissingValues);
    }

}