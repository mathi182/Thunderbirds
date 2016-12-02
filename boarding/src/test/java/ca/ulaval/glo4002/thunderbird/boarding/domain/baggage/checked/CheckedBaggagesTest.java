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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CheckedBaggagesTest {
    private static final float BAGGAGE_PRICE_SUM = 3;
    private static final int CHECKED_BAGGAGE_COST = 50;

    private final Passenger passenger = mock(Passenger.class);
    private final Baggage baggageA = mock(Baggage.class);
    private final Baggage baggageB = mock(Baggage.class);
    private final CheckedBaggages checkedBaggages = new EconomicCheckedBaggages(passenger);

    @Before
    public void setUp() {
        willReturn(1f).given(baggageA).getPrice();
        willReturn(2f).given(baggageB).getPrice();
    }

    @Test
    public void givenABaggage_whenWeValidate_shouldValidateWithTheRightLimits() {
        Baggage baggage = mock(Baggage.class);

        checkedBaggages.addBaggage(baggage);

        Length dimensionLimit = checkedBaggages.getDimensionLimit();
        Mass weightLimit = checkedBaggages.getWeightLimit();
        verify(baggage).validate(dimensionLimit, weightLimit);
    }

    @Test
    public void givenTwoBaggages_whenAddingTheseBaggages_shouldHaveFirstBaggageFree() {
        Baggage baggageA = mock(Baggage.class);
        Baggage baggageB = mock(Baggage.class);

        checkedBaggages.addBaggage(baggageA);
        checkedBaggages.addBaggage(baggageB);

        verify(baggageA).setPrice(0);
        verify(baggageB).setPrice(CHECKED_BAGGAGE_COST);
    }

    @Test
    public void givenAnEmptyCheckedBaggages_whenGettingAllBaggages_shouldBeEmpty() {
        List<Baggage> baggages = checkedBaggages.getBaggages();

        assertTrue(baggages.isEmpty());
    }

    @Test
    public void givenAnEmptyBaggageList_whenAddingABaggageAndRetrievingTheList_shouldNotBeEmpty() {
        checkedBaggages.addBaggage(mock(Baggage.class));

        List<Baggage> baggages = checkedBaggages.getBaggages();

        assertFalse(baggages.isEmpty());
    }

    @Test
    public void givenTwoBaggages_whenCalculatingPrice_shouldReturnPriceSum() {
        checkedBaggages.addBaggage(baggageA);
        checkedBaggages.addBaggage(baggageB);

        float price = checkedBaggages.calculatePrice();

        verify(baggageA).getPrice();
        verify(baggageB).getPrice();
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