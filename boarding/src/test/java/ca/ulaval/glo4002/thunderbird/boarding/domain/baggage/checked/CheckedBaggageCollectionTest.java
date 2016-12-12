package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.CheckedBaggageCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.EconomicBaggageCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

public class CheckedBaggageCollectionTest {
    private static final float BAGGAGE_PRICE_SUM = 3;
    private static final float SECOND_BAGGAGE_PRICE = 3;

    private final Passenger passenger = mock(Passenger.class);
    private final Baggage firstBaggage = mock(Baggage.class);
    private final Baggage secondBaggage = mock(Baggage.class);

    private final CheckedBaggageCollection checkedBaggageCollection = new EconomicBaggageCollection(passenger);

    @Before
    public void setUp() {
        willReturn(1f).given(firstBaggage).getPrice();
        willReturn(2f).given(secondBaggage).getPrice();

        willReturn(SECOND_BAGGAGE_PRICE).given(secondBaggage).getBasePrice();
    }

    @Test
    public void whenAddingFirstBaggage_shouldBeFree() {
        checkedBaggageCollection.addBaggage(firstBaggage);

        verify(firstBaggage, never()).getBasePrice();
        verify(firstBaggage).setPrice(0.0f);
    }

    @Test
    public void whenAddingSecondBaggage_shouldNotBeFree() {
        checkedBaggageCollection.addBaggage(firstBaggage);
        checkedBaggageCollection.addBaggage(secondBaggage);

        verify(secondBaggage).getBasePrice();
        verify(secondBaggage).setPrice(SECOND_BAGGAGE_PRICE);
    }

    @Test
    public void whenGettingAllBaggages_shouldBeEmpty() {
        List<Baggage> baggages = checkedBaggageCollection.getBaggages();

        assertTrue(baggages.isEmpty());
    }

    @Test
    public void givenWeAddABaggage_whenRetrievingTheList_shouldNotBeEmpty() {
        checkedBaggageCollection.addBaggage(firstBaggage);

        List<Baggage> baggages = checkedBaggageCollection.getBaggages();

        assertFalse(baggages.isEmpty());
    }

    @Test
    public void givenTwoBaggages_whenCalculatingPrice_shouldReturnPriceSum() {
        checkedBaggageCollection.addBaggage(firstBaggage);
        checkedBaggageCollection.addBaggage(secondBaggage);

        float price = checkedBaggageCollection.calculatePrice();

        verify(firstBaggage).getPrice();
        verify(secondBaggage).getPrice();
        assertEquals(BAGGAGE_PRICE_SUM, price, 0.0f);
    }

    @Test(expected = BaggageAmountUnauthorizedException.class)
    public void givenMaximumNumberOfBaggages_whenAddingAnother_shouldThrowAnException() {
        checkedBaggageCollection.addBaggage(mock(Baggage.class));
        checkedBaggageCollection.addBaggage(mock(Baggage.class));
        checkedBaggageCollection.addBaggage(mock(Baggage.class));

        checkedBaggageCollection.addBaggage(mock(Baggage.class));
    }

    @Test(expected = BaggageAmountUnauthorizedException.class)
    public void givenMaximumNumberOfBaggagesForVip_whenAddingAnother_shouldThrowAnException() {
        checkedBaggageCollection.addBaggage(mock(Baggage.class));
        checkedBaggageCollection.addBaggage(mock(Baggage.class));
        checkedBaggageCollection.addBaggage(mock(Baggage.class));
        checkedBaggageCollection.addBaggage(mock(Baggage.class));

        checkedBaggageCollection.addBaggage(mock(Baggage.class));
    }
}