package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggages;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PassengerCheckedBaggagesTest {
    private static final UUID HASH = UUID.randomUUID();
    private static final Seat.SeatClass SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final Instant FLIGHT_DATE = Instant.now();
    private static final String FLIGHT_NUMBER = "QK-918";
    private static final boolean NOT_VIP = false;
    private static final boolean VIP = true;

    private CheckedBaggages checkedBaggages = mock(CheckedBaggages.class);
    private Passenger passenger = new Passenger(HASH, SEAT_CLASS, FLIGHT_DATE, FLIGHT_NUMBER, NOT_VIP, checkedBaggages);
    private Passenger vipPassenger = new Passenger(HASH, SEAT_CLASS, FLIGHT_DATE, FLIGHT_NUMBER, VIP, checkedBaggages);

    @Test
    public void whenAddingCheckedBaggage_shouldAddInCheckedBaggages() {
        Baggage baggage = mock(Baggage.class);
        passenger.addBaggage(baggage);

        verify(checkedBaggages).addBaggage(baggage);
    }

    @Test
    public void givenCheckedBaggages_whenGettingAllBaggages_shouldReturnTheseCheckedBaggages() {
        List<Baggage> expectedBaggages = new ArrayList<>();
        willReturn(expectedBaggages).given(checkedBaggages).getBaggages();

        List<Baggage> actualBaggages = passenger.getBaggages();

        verify(checkedBaggages).getBaggages();
        assertSame(expectedBaggages, actualBaggages);
    }

    @Test
    public void givenAPrice_whenCalculatingBaggagesPrice_shouldReturnThisPrice() {
        float expectedPrice = 100;
        willReturn(expectedPrice).given(checkedBaggages).calculatePrice();

        float actualPrice = passenger.calculateBaggagesPrice();

        verify(checkedBaggages).calculatePrice();
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void givenAPrice_whenCalculationBaggagesPriceForVip_shouldReturnThisPriceWithADiscount() {
        float expectedPriceWithoutDiscount = 100;
        willReturn(expectedPriceWithoutDiscount).given(checkedBaggages).calculatePrice();

        float actualPrice = vipPassenger.calculateBaggagesPrice();

        float expectedPrice = expectedPriceWithoutDiscount * 0.95f;
        verify(checkedBaggages).calculatePrice();
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }
}