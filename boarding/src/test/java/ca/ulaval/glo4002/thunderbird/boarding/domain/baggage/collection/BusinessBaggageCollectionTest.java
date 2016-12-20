package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.BusinessBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class BusinessBaggageCollectionTest {

    private static final int FREE_BAGGAGE_COUNT = 2;
    private static final Length DIMENSION_LIMIT = Length.fromCentimeters(158);
    private static final Mass WEIGHT_LIMIT = Mass.fromKilograms(30);
    private static final float FREE = 0;
    private static final float BAGGAGE_PRICE = 20;
    private static final float DELTA = 0.01f;
    private BusinessBaggageCollection businessBaggageCollection;
    private Baggage baggage;

    @Before
    public void setup() {
        baggage = mock(BusinessBaggage.class);
        Passenger passenger = mock(Passenger.class);
        willReturn(false).given(passenger).isVip();
        businessBaggageCollection = new BusinessBaggageCollection(passenger);
        willReturn(BAGGAGE_PRICE).given(baggage).getBasePrice();
        businessBaggageCollection.addBaggage(baggage);
        businessBaggageCollection.addBaggage(baggage);
    }

    @Test
    public void shouldReturnRightValues() {
        assertEquals(WEIGHT_LIMIT, businessBaggageCollection.getWeightLimit());
        assertEquals(DIMENSION_LIMIT, businessBaggageCollection.getDimensionLimit());
        assertEquals(FREE_BAGGAGE_COUNT, businessBaggageCollection.getFreeBaggageCount());
    }

    @Test
    public void givenCollectionWithMoreThanOneBaggage_whenCalculingTotalCost_shouldReturnAppropriatePrice() {
        businessBaggageCollection.addBaggage(baggage);

        float actualPrice = businessBaggageCollection.calculateTotalCost();

        assertEquals(BAGGAGE_PRICE, actualPrice, DELTA);
    }

    @Test
    public void givenCollectionWithOnlyOneBaggage_whenCalculatingTotalCost_shouldBeFree() {
        float actualPrice = businessBaggageCollection.calculatePrice();

        assertEquals(FREE, actualPrice, DELTA);
    }
}
