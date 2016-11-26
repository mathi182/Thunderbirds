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
    private static final UUID SOME_PASSENGER_HASH = new UUID(1L, 2L);
    private static final float BAGGAGE_PRICE_SUM = 3;

    private final EconomicCheckedBaggages economicCheckedBaggages = new EconomicCheckedBaggages(SOME_PASSENGER_HASH);
    private final Baggage baggageA = mock(Baggage.class);
    private final Baggage baggageB = mock(Baggage.class);

    @Before
    public void setUp() throws Exception {
        willReturn(1f).given(baggageA).getPrice();
        willReturn(2f).given(baggageB).getPrice();
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