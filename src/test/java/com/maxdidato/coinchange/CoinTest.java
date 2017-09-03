package com.maxdidato.coinchange;

import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class CoinTest {

    @Test
    public void when_a_valid_denomination_is_used_a_coin_instance_is_returned() {
        Arrays.stream(Coin.values()).forEach(c -> {
            assertThat(Coin.valueOf(c.getDenomination()), is(c));
        });
    }

    @Test(expected = InvalidParameterException.class)
    public void when_a_non_existent_denomination_is_used_then_an_exception_is_raised() {
        Coin.valueOf(12345);
    }


}