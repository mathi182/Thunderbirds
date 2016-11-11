package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategyFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.EconomyCheckedBaggageValidationStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class BaggageValidationStrategyFactoryTest {
    BaggageValidationStrategyFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new BaggageValidationStrategyFactory();
    }

    @Test
    public void givenEconomyMode_whenGettingStrategy_shouldReturnEconomyCheckedBaggageValidationStrategy() {
        BaggageValidationStrategy strategy = factory.getStrategy(BaggageValidationStrategy.ValidationMode.ECONOMY);

        assertThat(strategy, instanceOf(EconomyCheckedBaggageValidationStrategy.class));
    }
}