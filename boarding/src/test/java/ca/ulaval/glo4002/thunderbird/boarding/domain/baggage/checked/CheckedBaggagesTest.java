package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat.SeatClass.ECONOMY;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CheckedBaggagesTest {
    private static final float BAGGAGE_PRICE_SUM = 3;
    private static final int CHECKED_BAGGAGE_COST = 50;
    private static final int ECONOMIC_WEIGHT_LIMIT_IN_GRAMS = 23000;
    private static final int ECONOMIC_DIMENSION_LIMIT_IN_MM = 1580;

    private final Baggage baggageA = mock(Baggage.class);
    private final Baggage baggageB = mock(Baggage.class);
    private final CheckedBaggages economicCheckedBaggages = CheckedBaggagesFactory.getCheckedBaggages(ECONOMY);

    @Before
    public void setUp() {
        willReturn(1f).given(baggageA).getPrice();
        willReturn(2f).given(baggageB).getPrice();
    }

    @Test
    public void givenABaggage_whenWeValidate_shouldValidateWithTheRightLimits() {
        Baggage baggage = mock(Baggage.class);

        economicCheckedBaggages.addBaggage(baggage);

        verify(baggage).validate(ECONOMIC_DIMENSION_LIMIT_IN_MM, ECONOMIC_WEIGHT_LIMIT_IN_GRAMS);
    }

    @Test
    public void givenTwoBaggages_whenAddingTheseBaggages_shouldHaveFirstBaggageFree() {
        Baggage baggageA = mock(Baggage.class);
        Baggage baggageB = mock(Baggage.class);

        economicCheckedBaggages.addBaggage(baggageA);
        economicCheckedBaggages.addBaggage(baggageB);

        verify(baggageA).setPrice(0);
        verify(baggageB).setPrice(CHECKED_BAGGAGE_COST);
    }

    @Test
    public void givenAnEmptyCheckedBaggages_whenGettingAllBaggages_shouldBeEmpty() {
        List<Baggage> baggages = economicCheckedBaggages.getBaggages();

        assertTrue(baggages.isEmpty());
    }

    @Test
    public void givenAnEmptyBaggageList_whenAddingABaggageAndRetrievingTheList_shouldNotBeEmpty() {
        economicCheckedBaggages.addBaggage(mock(Baggage.class));

        List<Baggage> baggages = economicCheckedBaggages.getBaggages();

        assertFalse(baggages.isEmpty());
    }

    @Test
    public void givenTwoBaggages_whenCalculatingPrice_shouldReturnPriceSum() {
        economicCheckedBaggages.addBaggage(baggageA);
        economicCheckedBaggages.addBaggage(baggageB);

        float price = economicCheckedBaggages.calculatePrice();

        verify(baggageA).getPrice();
        verify(baggageB).getPrice();
        assertEquals(BAGGAGE_PRICE_SUM, price, 0.0f);
    }

    @Test(expected = BaggageAmountUnauthorizedException.class)
    public void givenMaximumNumberOfBaggages_whenAddingAnother_shouldThrowAnException() {
        economicCheckedBaggages.addBaggage(mock(Baggage.class));
        economicCheckedBaggages.addBaggage(mock(Baggage.class));
        economicCheckedBaggages.addBaggage(mock(Baggage.class));

        economicCheckedBaggages.addBaggage(mock(Baggage.class));
    }
}