package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.measurementsStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class BaggageValidationStrategyFactoryTest {
    MeasurementsStrategyFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new MeasurementsStrategyFactory();
    }

    @Test
    public void givenEconomyMode_whenGettingStrategy_shouldReturnEconomyCheckedBaggageValidationStrategy() {
        BaggageValidationStrategy strategy = factory.getStrategy(Seat.SeatClass.ECONOMY);

        assertThat(strategy, instanceOf(EconomyCheckedBaggageValidationStrategy.class));
    }
}