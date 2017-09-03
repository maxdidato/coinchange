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
    public void when_pence_amount_is_equivalent_to_one_of_the_coin_denomination_it_returns_that_coin() {
        Arrays.stream(Coin.values()).forEach(c -> {
            Collection<Coin> actualChange = coinChangeDispenser.getOptimalChangeFor(c.getDenomination());
            Collection<Coin> expectedChange = Collections.singletonList(c);
            assertThat(actualChange, is(expectedChange));
        });
    }

    @Test
    public void when_pence_amount_is_zero_it_trows_an_exception() {expectedEx.expect(InvalidParameterException.class);
        expectedEx.expectMessage("Invalid parameter '0'. The input must be a positive integer");
        coinChangeDispenser.getOptimalChangeFor(0);
    }

    @Test
    public void when_pence_amount_is_negative_it_trows_an_exception() {
        expectedEx.expect(InvalidParameterException.class);
        expectedEx.expectMessage("Invalid parameter '-4'. The input must be a positive integer");
        coinChangeDispenser.getOptimalChangeFor(-4);
    }
}
