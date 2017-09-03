package com.maxdidato.coinchange;

import com.maxdidato.coinchange.testutils.CoinContainerHelper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static com.maxdidato.coinchange.Coin.*;
import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class CoinsContainerTest {


    public static final String FILE_NAME = "coin-inventory.properties";
    private CoinContainerHelper coinContainerHelper;
    private CoinsContainer coinsContainer;

    @Before
    public void setUp() throws IOException {
        coinsContainer = new CoinsContainer(FILE_NAME);
        coinContainerHelper = new CoinContainerHelper().withFileName(FILE_NAME);
    }

    @Test
    public void it_removes_one_pound_coins_from_the_count() throws IOException, InsufficientCoinage {
        coinContainerHelper.withOnePound(11).flush();
        coinsContainer.removeCoins(asList(ONE_POUND, ONE_POUND, ONE_POUND));
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getOnePound(), is(8));
    }

    @Test
    public void it_removes_fifty_cent_coins_from_the_count() throws IOException, InsufficientCoinage {
        coinContainerHelper.withFiftyPence(4).flush();
        coinsContainer.removeCoins(asList(FIFTY_PENCE, FIFTY_PENCE));
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getFiftyPence(), is(2));
    }

    @Test
    public void it_removes_twenty_pence_coins_from_the_count() throws IOException, InsufficientCoinage {
        coinContainerHelper.withTwentyPence(2).flush();
        coinsContainer.removeCoins(asList(TWENTY_PENCE));
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getTwentyPence(), is(1));
    }

    @Test
    public void it_removes_ten_pence_coins_from_the_count() throws IOException, InsufficientCoinage {
        coinContainerHelper.withTenPence(4).flush();
        coinsContainer.removeCoins(asList(TEN_PENCE));
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getTenPence(), is(3));
    }

    @Test
    public void it_removes_five_pence_coins_from_the_count() throws IOException, InsufficientCoinage {
        coinContainerHelper.withFivePence(10).flush();
        coinsContainer.removeCoins(asList(FIVE_PENCE, FIVE_PENCE, FIVE_PENCE, FIVE_PENCE));
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getFivePence(), is(6));
    }

    @Test
    public void it_removes_two_pence_coins_from_the_count() throws IOException, InsufficientCoinage {
        coinContainerHelper.withTwoPence(5).flush();
        coinsContainer.removeCoins(asList(TWO_PENCE, TWO_PENCE));
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getTwoPence(), is(3));
    }


    @Test
    public void it_removes_one_penny_coins_from_the_count() throws IOException, InsufficientCoinage {
        coinContainerHelper.withOnePenny(6).flush();
        coinsContainer.removeCoins(asList(ONE_PENNY, ONE_PENNY));
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getOnePenny(), is(4));
    }


    @Test
    public void when_a_collection_of_coins_is_passed_they_are_removed_from_the_count() throws IOException, InsufficientCoinage {
        coinContainerHelper.withOnePound(11).withFiftyPence(24).withTwentyPence(10).withTenPence(9)
                .withFivePence(100).withTwoPence(11).withOnePenny(10).flush();
        coinsContainer.removeCoins(asList(
                ONE_POUND, ONE_POUND, ONE_POUND, FIFTY_PENCE, FIFTY_PENCE, TWENTY_PENCE,
                TEN_PENCE, TEN_PENCE, TEN_PENCE, TEN_PENCE, TEN_PENCE,
                FIVE_PENCE, FIVE_PENCE, TWO_PENCE, TWO_PENCE, ONE_PENNY));
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getOnePound(), is(8));
        assertThat(coinContainerHelper.getFiftyPence(), is(22));
        assertThat(coinContainerHelper.getTwentyPence(), is(9));
        assertThat(coinContainerHelper.getTenPence(), is(4));
        assertThat(coinContainerHelper.getFivePence(), is(98));
        assertThat(coinContainerHelper.getTwoPence(), is(9));
        assertThat(coinContainerHelper.getOnePenny(), is(9));
    }

    @Test
    public void it_removes_coins_until_it_reaches_zero() throws IOException, InsufficientCoinage {
        coinContainerHelper.withOnePound(1).flush();
        coinsContainer.removeCoins(asList(ONE_POUND));
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getOnePound(), is(0));
    }

    @Test
    public void removing_a_particular_coin_doesnt_affect_the_others() throws IOException, InsufficientCoinage {
        coinContainerHelper.withOnePound(11).withFiftyPence(24).withTwentyPence(10).withTenPence(9)
                .withFivePence(100).withTwoPence(11).withOnePenny(10).flush();
        coinsContainer.removeCoins(asList(
                ONE_POUND, ONE_POUND, FIVE_PENCE, FIVE_PENCE, TWO_PENCE, TWO_PENCE));
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getOnePound(), is(9));
        assertThat(coinContainerHelper.getFiftyPence(), is(24));
        assertThat(coinContainerHelper.getTwentyPence(), is(10));
        assertThat(coinContainerHelper.getTenPence(), is(9));
        assertThat(coinContainerHelper.getFivePence(), is(98));
        assertThat(coinContainerHelper.getTwoPence(), is(9));
        assertThat(coinContainerHelper.getOnePenny(), is(10));
    }

    @Test
    public void when_passing_an_empty_list_the_count_is_not_affected() throws IOException, InsufficientCoinage {
        coinContainerHelper.withOnePound(11).withFiftyPence(24).withTwentyPence(10).withTenPence(9)
                .withFivePence(100).withTwoPence(11).withOnePenny(10).flush();
        coinsContainer.removeCoins(Collections.emptyList());
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getOnePound(), is(11));
        assertThat(coinContainerHelper.getFiftyPence(), is(24));
        assertThat(coinContainerHelper.getTwentyPence(), is(10));
        assertThat(coinContainerHelper.getTenPence(), is(9));
        assertThat(coinContainerHelper.getFivePence(), is(100));
        assertThat(coinContainerHelper.getTwoPence(), is(11));
        assertThat(coinContainerHelper.getOnePenny(), is(10));
    }
}