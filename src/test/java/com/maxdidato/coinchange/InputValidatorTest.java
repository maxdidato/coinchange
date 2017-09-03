package com.maxdidato.coinchange;

import com.maxdidato.coinchange.validator.InputValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;

public class InputValidatorTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void when_pence_amount_is_greater_then_100000_it_returns_it_throws_an_exception() {
        expectedEx.expect(InvalidParameterException.class);
        expectedEx.expectMessage("Invalid parameter '100001'. The input must be a in 0..100000 range");
        InputValidator.validate(100001);
    }

    @Test
    public void when_pence_amount_is_negative_it_trows_an_exception() {
        expectedEx.expect(InvalidParameterException.class);
        expectedEx.expectMessage("Invalid parameter '-4'. The input must be a in 0..100000 range");
        InputValidator.validate(-4);
    }


}