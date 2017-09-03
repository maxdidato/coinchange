import com.maxdidato.coinchange.Coin;
import com.maxdidato.coinchange.CoinChangeDispenser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.maxdidato.coinchange.Coin.*;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CoinChangeDispenserTest {


    private CoinChangeDispenser coinChangeDispenser;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() {
        coinChangeDispenser = new CoinChangeDispenser();
    }

    @Test
    public void when_pence_amount_is_zero_it_returns_an_empty_collection() {
        assertThat(coinChangeDispenser.getOptimalChangeFor(0),is(emptyList()));
    }

    @Test
    public void when_pence_amount_is_greater_then_100000_it_returns_it_throws_an_exception() {
        assertThat(coinChangeDispenser.getOptimalChangeFor(0),is(emptyList()));
        expectedEx.expect(InvalidParameterException.class);
        expectedEx.expectMessage("Invalid parameter '100001'. The input must be a in 0..100000 range");
        coinChangeDispenser.getOptimalChangeFor(100001);
    }

    @Test
    public void when_pence_amount_is_negative_it_trows_an_exception() {
        expectedEx.expect(InvalidParameterException.class);
        expectedEx.expectMessage("Invalid parameter '-4'. The input must be a in 0..100000 range");
        coinChangeDispenser.getOptimalChangeFor(-4);
    }

    @Test
    public void when_pence_amount_is_equivalent_to_one_of_the_coin_denomination_it_returns_that_coin() {
        Arrays.stream(Coin.values()).forEach(c -> {
            Collection<Coin> actualChange = coinChangeDispenser.getOptimalChangeFor(c.getDenomination());
            Collection<Coin> expectedChange = Collections.singletonList(c);
            assertThat(actualChange, is(expectedChange));
        });
    }

    @Test
    public void when_a_pence_amount_is_passed_the_least_number_of_coins_is_returned(){
        Collection<Coin> actualChange = coinChangeDispenser.getOptimalChangeFor(245);
        Collection<Coin> expectedChange = Arrays.asList(ONE_POUND,ONE_POUND,TWENTY_PENCE,TWENTY_PENCE,FIVE_PENCE);
        assertThat(actualChange, is(expectedChange));

    }

    @Test
    public void it_accepts_numbers_up_to_1000000(){
        Collection<Coin> actualChange = coinChangeDispenser.getOptimalChangeFor(100000);
        assertThat(actualChange, is(Collections.nCopies(1000, ONE_POUND)));

    }


}
