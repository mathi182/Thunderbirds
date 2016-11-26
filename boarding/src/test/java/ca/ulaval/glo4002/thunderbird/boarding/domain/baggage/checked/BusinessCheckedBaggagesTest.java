package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BusinessCheckedBaggagesTest {
    private static final UUID SOME_PASSENGER_HASH = new UUID(1L, 2L);
    private static final int CHECKED_BAGGAGE_COST = CheckedBaggages.CHECKED_BAGGAGE_COST;
    private static final int FREE_BAGGAGE_COST = 0;

    private static final int DIMENSION_LIMIT_IN_MM = BusinessCheckedBaggages.DIMENSION_LIMIT_IN_MM;
    private static final int WEIGHT_LIMIT_IN_GRAMS = BusinessCheckedBaggages.WEIGHT_LIMIT_IN_GRAMS;

    private final BusinessCheckedBaggages businessCheckedBaggages = new BusinessCheckedBaggages(SOME_PASSENGER_HASH);

    @Test
    public void givenABaggage_whenValidate_shouldValidateWithTheRightLimits() {
        Baggage baggage = mock(Baggage.class);

        businessCheckedBaggages.addBaggage(baggage);

        verify(baggage).validate(DIMENSION_LIMIT_IN_MM, WEIGHT_LIMIT_IN_GRAMS);
    }

    @Test
    public void givenThreeBaggages_whenAddTheseBaggages_shouldHaveTwoFreeBaggage() {
        Baggage baggageA = mock(Baggage.class);
        Baggage baggageB = mock(Baggage.class);
        Baggage baggageC = mock(Baggage.class);

        businessCheckedBaggages.addBaggage(baggageA);
        businessCheckedBaggages.addBaggage(baggageB);
        businessCheckedBaggages.addBaggage(baggageC);

        verify(baggageA).setPrice(FREE_BAGGAGE_COST);
        verify(baggageB).setPrice(FREE_BAGGAGE_COST);
        verify(baggageC).setPrice(CHECKED_BAGGAGE_COST);
    }
}