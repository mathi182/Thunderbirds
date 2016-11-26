package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.baggageStrategies;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageType;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.priceStrategy.BaggagePriceStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.priceStrategy.CheckedBaggagePriceStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.CheckedBaggageValidationStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class CheckedBaggageStrategiesTest {

    private BaggageStrategies baggageStrategies;

    @Before
    public void setUp() throws Exception {
        baggageStrategies = BaggageStrategies.getFactory(BaggageType.CHECKED);
    }

    @Test
    public void whenCreatingPriceStrategy_shouldReturnCheckedBaggagePriceStrategy() {
        BaggagePriceStrategy strategy = baggageStrategies.createPriceStrategy();

        assertThat(strategy, instanceOf(CheckedBaggagePriceStrategy.class));
    }

    @Test
    public void whenCreatingValidationStrategy_shouldReturnCheckedBaggageValidationStrategy() {
        BaggageValidationStrategy strategy = baggageStrategies.createValidationStrategy();

        assertThat(strategy, instanceOf(CheckedBaggageValidationStrategy.class));
    }
}