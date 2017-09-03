package com.maxdidato.coinchange;

import com.maxdidato.coinchange.testutils.CoinContainerHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import static com.maxdidato.coinchange.Coin.*;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ChangeWithLimitedCoinsTest {
    /*
        We reexecute the same tests executed for getOptimalChangeFor.
        The requirement clearly says that two different methods are required, getOptimalChangeFor and getChangeFor.
        The latter is an extension of the first one plus a check on available coins.
        It would be possible to test that getChangeFor calls getOptimalChangeFor so that all the tests are "valid"
        for getChangeFor. However that will not protect from future implementation on getChangeFor that could break
        it. For this reason the same tests will be executed for getChangeFor
        */
    private CoinChangeDispenser coinChangeDispenser;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private CoinsContainer coinsContainer;
    public static final String FILE_NAME = "coin-inventory.properties";
    private CoinContainerHelper coinContainerHelper;

    @Before
    public void setUp() throws IOException {
        coinsContainer = new CoinsContainer("coin-inventory.properties");
        coinChangeDispenser = new CoinChangeDispenser(coinsContainer);
        coinContainerHelper = new CoinContainerHelper().withFileName(FILE_NAME);
        //Setup "unlimited coins" as default. The status of the container is overridden in test that needs to
        coinContainerHelper.withOnePound(1000).withFiftyPence(1000).withTwentyPence(1000).withTenPence(1000)
                .withFivePence(1000).withTwoPence(1000).withOnePenny(1000).flush();
    }

    @Test
    public void when_pence_amount_is_zero_it_returns_an_empty_collection() throws InsufficientCoinage {
        assertThat(coinChangeDispenser.getChangeFor(0), is(emptyList()));
    }

    @Test
    public void when_pence_amount_is_greater_then_100000_it_returns_it_throws_an_exception() throws InsufficientCoinage {
        expectedEx.expect(InvalidParameterException.class);
        expectedEx.expectMessage("Invalid parameter '100001'. The input must be a in 0..100000 range");
        coinChangeDispenser.getChangeFor(100001);
    }

    @Test
    public void when_pence_amount_is_negative_it_trows_an_exception() throws InsufficientCoinage {
        expectedEx.expect(InvalidParameterException.class);
        expectedEx.expectMessage("Invalid parameter '-4'. The input must be a in 0..100000 range");
        coinChangeDispenser.getChangeFor(-4);
    }

    @Test
    public void when_pence_amount_is_equivalent_to_one_of_the_coin_denomination_it_returns_that_coin() {
        Arrays.stream(Coin.values()).forEach(c -> {
            Collection<Coin> actualChange = null;
            try {
                actualChange = coinChangeDispenser.getChangeFor(c.getDenomination());
            } catch (InsufficientCoinage insufficientCoinage) {
                insufficientCoinage.printStackTrace();
            }
            Collection<Coin> expectedChange = Collections.singletonList(c);
            assertThat(actualChange, is(expectedChange));
        });
    }

    @Test
    public void when_a_pence_amount_of_25_is_passed_2_coins_are_returned() throws InsufficientCoinage {
        Collection<Coin> actualChange = coinChangeDispenser.getChangeFor(25);
        Collection<Coin> expectedChange = Arrays.asList(TWENTY_PENCE, FIVE_PENCE);
        assertThat(actualChange, is(expectedChange));
    }

    @Test
    public void when_a_pence_amount_of_27_is_passed_3_coins_are_returned() throws InsufficientCoinage {
        Collection<Coin> actualChange = coinChangeDispenser.getChangeFor(27);
        Collection<Coin> expectedChange = Arrays.asList(TWENTY_PENCE, FIVE_PENCE, TWO_PENCE);
        assertThat(actualChange, is(expectedChange));
    }

    @Test
    public void when_a_pence_amount_of_127_is_passed_4_coins_are_returned() throws InsufficientCoinage {
        Collection<Coin> actualChange = coinChangeDispenser.getChangeFor(127);
        Collection<Coin> expectedChange = Arrays.asList(ONE_POUND, TWENTY_PENCE, FIVE_PENCE, TWO_PENCE);
        assertThat(actualChange, is(expectedChange));
    }

    @Test
    public void when_a_pence_amount_of_145_is_passed_5_coins_are_returned() throws InsufficientCoinage {
        Collection<Coin> actualChange = coinChangeDispenser.getChangeFor(146);
        Collection<Coin> expectedChange = Arrays.asList(ONE_POUND, TWENTY_PENCE, TWENTY_PENCE, FIVE_PENCE, ONE_PENNY);
        assertThat(actualChange, is(expectedChange));
    }

    @Test
    public void when_a_pence_amount_of_185_is_passed_6_coins_are_returned() throws InsufficientCoinage {
        Collection<Coin> actualChange = coinChangeDispenser.getChangeFor(148);
        Collection<Coin> expectedChange = Arrays.asList(ONE_POUND, TWENTY_PENCE, TWENTY_PENCE, FIVE_PENCE, TWO_PENCE, ONE_PENNY);
        assertThat(actualChange, is(expectedChange));
    }

    @Test
    public void when_a_pence_amount_of_187_is_passed_7_coins_are_returned() throws InsufficientCoinage {
        Collection<Coin> actualChange = coinChangeDispenser.getChangeFor(188);
        Collection<Coin> expectedChange = Arrays.asList(ONE_POUND, FIFTY_PENCE, TWENTY_PENCE, TEN_PENCE, FIVE_PENCE, TWO_PENCE, ONE_PENNY);
        assertThat(actualChange, is(expectedChange));
    }

    @Test
    public void it_accepts_numbers_up_to_1000000() throws InsufficientCoinage {
        Collection<Coin> actualChange = coinChangeDispenser.getChangeFor(100000);
        assertThat(actualChange, is(Collections.nCopies(1000, ONE_POUND)));
    }

    @Test
    public void once_the_change_is_erogated_the_remaining_coins_are_updated() throws IOException, InsufficientCoinage {
        coinContainerHelper.withOnePound(11).withFiftyPence(24).withTwentyPence(10).withTenPence(9)
                .withFivePence(100).withTwoPence(11).withOnePenny(10).flush();

        coinChangeDispenser.getChangeFor(27);
        coinContainerHelper.load();
        assertThat(coinContainerHelper.getTwentyPence(), is(9));
        assertThat(coinContainerHelper.getFivePence(), is(99));
        assertThat(coinContainerHelper.getTwoPence(), is(10));
    }

    @Test
    public void when_calculating_change_if_no_coins_are_left_an_exception_is_raised() throws IOException, InsufficientCoinage {
        expectedEx.expect(InsufficientCoinage.class);
        expectedEx.expectMessage("There are no more coins of denomination 20");
        //The result for this would be (ONE_POUND, TWENTY_PENCE, TWENTY_PENCE, FIVE_PENCE, ONE_PENNY
        //but only one TWENTY_PENCE is there
        coinContainerHelper.withOnePound(11).withFiftyPence(24).withTwentyPence(1).withTenPence(9)
                .withFivePence(100).withTwoPence(11).withOnePenny(10).flush();
        coinChangeDispenser.getChangeFor(146);
    }


}
