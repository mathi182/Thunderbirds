package ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.validationStrategy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class LuggageValidationStrategyFactoryTest {
    LuggageValidationStrategyFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new LuggageValidationStrategyFactory();
    }

    @Test
    public void givenEconomyMode_whenGettingStrategy_shouldReturnEconomyCheckedLuggageValidationStrategy() {
        LuggageValidationStrategy strategy = factory.getStrategy(LuggageValidationStrategy.ValidationMode.ECONOMY);

        assertThat(strategy, instanceOf(EconomyCheckedLuggageValidationStrategy.class));
    }
}