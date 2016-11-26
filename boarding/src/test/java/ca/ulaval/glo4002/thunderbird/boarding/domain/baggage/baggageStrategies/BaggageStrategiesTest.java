package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.baggageStrategies;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class BaggageStrategiesTest {
    @Test
    public void givenChecked_whenGetStrategy_shouldReturnCheckedBaggageToolkit() {
        BaggageStrategies baggageStrategies = BaggageStrategies.getFactory(BaggageType.CHECKED);

        assertThat(baggageStrategies, instanceOf(CheckedBaggageStrategies.class));
    }

}