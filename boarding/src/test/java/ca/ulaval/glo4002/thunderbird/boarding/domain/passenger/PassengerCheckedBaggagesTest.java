package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.PassengerBaggagesCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PassengerCheckedBaggagesTest {
    private static final UUID HASH = UUID.randomUUID();
    private static final Seat.SeatClass SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final boolean NOT_VIP = false;
    private static final boolean VIP = true;
    private static final boolean IS_CHECKED_IN = false;
    private static final boolean IS_CHILD = false;

    private Flight flight = mock(Flight.class);
    private PassengerBaggagesCollection baggagesCollection = mock(PassengerBaggagesCollection.class);
    private Passenger passenger = new Passenger(HASH, SEAT_CLASS, NOT_VIP, IS_CHECKED_IN, IS_CHILD, flight, baggagesCollection);
    private Passenger vipPassenger = new Passenger(HASH, SEAT_CLASS, VIP, IS_CHECKED_IN, IS_CHILD, flight, baggagesCollection);

    @Test
    public void whenAddingCheckedBaggage_shouldAddInCheckedBaggages() {
        Baggage baggage = mock(Baggage.class);
        passenger.addBaggage(baggage);

        verify(baggagesCollection).addBaggage(baggage);
    }

    @Test
    public void givenCheckedBaggages_whenGettingAllBaggages_shouldReturnTheseCheckedBaggages() {
        Set<Baggage> expectedBaggages = new HashSet<>();
        willReturn(expectedBaggages).given(baggagesCollection).getBaggages();

        Set<Baggage> actualBaggages = passenger.getBaggages();

        verify(baggagesCollection).getBaggages();
        assertSame(expectedBaggages, actualBaggages);
    }

    @Test
    public void givenAPrice_whenCalculatingBaggagesPrice_shouldReturnThisPrice() {
        float expectedPrice = 100;
        willReturn(expectedPrice).given(baggagesCollection).calculateTotalPrice();

        float actualPrice = passenger.calculateBaggagesPrice();

        verify(baggagesCollection).calculateTotalPrice();
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void givenAPrice_whenCalculationBaggagesPriceForVip_shouldReturnThisPriceWithADiscount() {
        float expectedPriceWithoutDiscount = 100;
        willReturn(expectedPriceWithoutDiscount).given(baggagesCollection).calculateTotalPrice();

        float actualPrice = vipPassenger.calculateBaggagesPrice();

        float expectedPrice = expectedPriceWithoutDiscount * 0.95f;
        verify(baggagesCollection).calculateTotalPrice();
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }
}