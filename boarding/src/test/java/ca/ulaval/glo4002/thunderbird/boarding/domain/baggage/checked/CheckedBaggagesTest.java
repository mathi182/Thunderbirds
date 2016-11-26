package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CheckedBaggagesTest {
    private static final float BAGGAGE_PRICE_SUM = 3;

    private final Baggage baggageA = mock(Baggage.class);
    private final Baggage baggageB = mock(Baggage.class);

    private static final UUID SOME_PASSENGER_HASH = new UUID(1L, 2L);
    private static final int CHECKED_BAGGAGE_COST = CheckedBaggages.CHECKED_BAGGAGE_COST;
    private static final int FREE_BAGGAGE_COST = 0;

    private static final int DIMENSION_LIMIT_IN_MM = CheckedBaggagesFactory.ECONOMIC_DIMENSION_LIMIT_IN_MM;
    private static final int WEIGHT_LIMIT_IN_GRAMS = CheckedBaggagesFactory.ECONOMIC_WEIGHT_LIMIT_IN_GRAMS;
    private static final int FREE_CHECKED_BAGGAGE = CheckedBaggagesFactory.ECONOMIC_FREE_CHECKED_BAGGAGE;

    private final CheckedBaggages economicCheckedBaggages = new CheckedBaggages(SOME_PASSENGER_HASH,
            FREE_CHECKED_BAGGAGE, WEIGHT_LIMIT_IN_GRAMS, DIMENSION_LIMIT_IN_MM);

    @Before
    public void setUp() throws Exception {
        willReturn(1f).given(baggageA).getPrice();
        willReturn(2f).given(baggageB).getPrice();
    }

    @Test
    public void givenABaggage_whenValidate_shouldValidateWithTheRightLimits() {
        Baggage baggage = mock(Baggage.class);

        economicCheckedBaggages.addBaggage(baggage);

        verify(baggage).validate(DIMENSION_LIMIT_IN_MM, WEIGHT_LIMIT_IN_GRAMS);
    }

    @Test
    public void givenTwoBaggages_whenAddingTheseBaggages_shouldHaveOneFreeBaggage() {
        Baggage baggageA = mock(Baggage.class);
        Baggage baggageB = mock(Baggage.class);

        economicCheckedBaggages.addBaggage(baggageA);
        economicCheckedBaggages.addBaggage(baggageB);

        verify(baggageA).setPrice(FREE_BAGGAGE_COST);
        verify(baggageB).setPrice(CHECKED_BAGGAGE_COST);
    }

    @Test
    public void whenGetBaggages_shouldBeEmpty() {
        List<Baggage> baggages = economicCheckedBaggages.getBaggages();

        assertTrue(baggages.isEmpty());
    }

    @Test
    public void whenAddABaggage_whenGetBaggages_shouldNotBeEmpty() {
        economicCheckedBaggages.addBaggage(mock(Baggage.class));

        List<Baggage> baggages = economicCheckedBaggages.getBaggages();

        assertFalse(baggages.isEmpty());
    }

    @Test
    public void givenTwoBaggages_whenCalculatePrice_shouldReturnPriceSum() {
        economicCheckedBaggages.addBaggage(baggageA);
        economicCheckedBaggages.addBaggage(baggageB);

        float price = economicCheckedBaggages.calculatePrice();

        verify(baggageA).getPrice();
        verify(baggageB).getPrice();
        assertEquals(BAGGAGE_PRICE_SUM, price, 0.0f);
    }

    @Test(expected = BaggageAmountUnauthorizedException.class)
    public void givenMaximumNumberOfBaggages_whenAddAnOther_shouldThrowAnException() {
        economicCheckedBaggages.addBaggage(mock(Baggage.class));
        economicCheckedBaggages.addBaggage(mock(Baggage.class));
        economicCheckedBaggages.addBaggage(mock(Baggage.class));

        economicCheckedBaggages.addBaggage(mock(Baggage.class));
    }
}