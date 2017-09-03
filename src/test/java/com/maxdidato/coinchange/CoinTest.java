package com.maxdidato.coinchange;

import com.maxdidato.coinchange.model.Coin;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.Arrays;

import static com.maxdidato.coinchange.model.Coin.*;
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

    @Test
    public void the_denomination_of_one_pound_is_100() {
        assertThat(ONE_POUND.getDenomination(), is(100));
    }

    @Test
    public void the_denomination_of_fifty_pence_is_50() {
        assertThat(FIFTY_PENCE.getDenomination(), is(50));
    }

    @Test
    public void the_denomination_of_twenty_pence_is_20() {
        assertThat(TWENTY_PENCE.getDenomination(), is(20));
    }

    @Test
    public void the_denomination_of_ten_pence_is_10() {
        assertThat(TEN_PENCE.getDenomination(), is(10));
    }

    @Test
    public void the_denomination_of_five_pence_is_5() {
        assertThat(FIVE_PENCE.getDenomination(), is(5));
    }

    @Test
    public void the_denomination_of_two_pence_is_2() {
        assertThat(TWO_PENCE.getDenomination(), is(2));
    }

    @Test
    public void the_denomination_of_one_penny_is_1() {
        assertThat(ONE_PENNY.getDenomination(), is(1));
    }
}