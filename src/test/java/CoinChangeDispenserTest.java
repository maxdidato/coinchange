import com.maxdidato.coinchange.Coin;
import com.maxdidato.coinchange.CoinChangeDispenser;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.maxdidato.coinchange.Coin.ONE_POUND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CoinChangeDispenserTest {


    @Test
    public void when_pence_amount_is_equivalent_to_one_of_the_coin_denomination_it_returns_that_coin() {
        CoinChangeDispenser coinChangeDispenser = new CoinChangeDispenser();
        Arrays.stream(Coin.values()).forEach(c -> {
            Collection<Coin> actualChange = coinChangeDispenser.getOptimalChangeFor(c.getDenomination());
            Collection<Coin> expectedChange = Collections.singletonList(c);
            assertThat(actualChange,is(expectedChange));
        });
    }
}
