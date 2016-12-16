package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.EconomicBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class EconomicBaggageCollectionTest {

    private static final int FREE_BAGGAGE_COUNT = 1;
    private static final Length DIMENSION_LIMIT = Length.fromCentimeters(158);
    private static final Mass WEIGHT_LIMIT = Mass.fromKilograms(23);;
    private EconomicBaggageCollection economicBaggageCollection;
    private Baggage baggage;
    private Passenger passenger;

    @Before
    public void setup() {
        baggage = mock(EconomicBaggage.class);
        passenger = mock(Passenger.class);
        willReturn(false).given(passenger).isVip();
        economicBaggageCollection = new EconomicBaggageCollection();
    }

    @Test
    public void shouldReturnRightValues() {
        assertEquals(WEIGHT_LIMIT, economicBaggageCollection.getWeightLimit());
        assertEquals(DIMENSION_LIMIT, economicBaggageCollection.getDimensionLimit());
        assertEquals(FREE_BAGGAGE_COUNT, economicBaggageCollection.getFreeBaggageCount());
    }

}
