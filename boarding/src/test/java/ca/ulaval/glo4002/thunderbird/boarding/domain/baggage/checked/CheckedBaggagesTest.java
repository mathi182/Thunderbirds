package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

public class CheckedBaggagesTest {
    private static final float BAGGAGE_PRICE_SUM = 3;
    private static final float SECOND_BAGGAGE_PRICE = 3;

    private final Passenger passenger = mock(Passenger.class);
    private final Baggage firstBaggage = mock(Baggage.class);
    private final Baggage secondBaggage = mock(Baggage.class);

    private final CheckedBaggages checkedBaggages = new EconomicCheckedBaggages(passenger);
    private final Length dimensionLimit = checkedBaggages.getDimensionLimit();
    private final Mass weightLimit = checkedBaggages.getWeightLimit();

    @Before
    public void setUp() {
        willReturn(1f).given(firstBaggage).getPrice();
        willReturn(2f).given(secondBaggage).getPrice();

        willReturn(SECOND_BAGGAGE_PRICE).given(secondBaggage).getBasePrice(dimensionLimit, weightLimit);
    }

    @Test
    public void whenAddingFirstBaggage_shouldBeFree() {
        checkedBaggages.addBaggage(firstBaggage);

        verify(firstBaggage, never()).getBasePrice(dimensionLimit, weightLimit);
        verify(firstBaggage).setPrice(0.0f);
    }

    @Test
    public void whenAddingSecondBaggage_shouldNotBeFree() {
        checkedBaggages.addBaggage(firstBaggage);
        checkedBaggages.addBaggage(secondBaggage);

        verify(secondBaggage).getBasePrice(dimensionLimit, weightLimit);
        verify(secondBaggage).setPrice(SECOND_BAGGAGE_PRICE);
    }

    @Test
    public void whenGettingAllBaggages_shouldBeEmpty() {
        List<Baggage> baggages = checkedBaggages.getBaggages();

        assertTrue(baggages.isEmpty());
    }

    @Test
    public void givenWeAddABaggage_whenRetrievingTheList_shouldNotBeEmpty() {
        checkedBaggages.addBaggage(firstBaggage);

        List<Baggage> baggages = checkedBaggages.getBaggages();

        assertFalse(baggages.isEmpty());
    }

    @Test
    public void givenTwoBaggages_whenCalculatingPrice_shouldReturnPriceSum() {
        checkedBaggages.addBaggage(firstBaggage);
        checkedBaggages.addBaggage(secondBaggage);

        float price = checkedBaggages.calculatePrice();

        verify(firstBaggage).getPrice();
        verify(secondBaggage).getPrice();
        assertEquals(BAGGAGE_PRICE_SUM, price, 0.0f);
    }

    @Test(expected = BaggageAmountUnauthorizedException.class)
    public void givenMaximumNumberOfBaggages_whenAddingAnother_shouldThrowAnException() {
        checkedBaggages.addBaggage(mock(Baggage.class));
        checkedBaggages.addBaggage(mock(Baggage.class));
        checkedBaggages.addBaggage(mock(Baggage.class));

        checkedBaggages.addBaggage(mock(Baggage.class));
    }

    @Test(expected = BaggageAmountUnauthorizedException.class)
    public void givenMaximumNumberOfBaggagesForVip_whenAddingAnother_shouldThrowAnException() {
        checkedBaggages.addBaggage(mock(Baggage.class));
        checkedBaggages.addBaggage(mock(Baggage.class));
        checkedBaggages.addBaggage(mock(Baggage.class));
        checkedBaggages.addBaggage(mock(Baggage.class));

        checkedBaggages.addBaggage(mock(Baggage.class));
    }
}