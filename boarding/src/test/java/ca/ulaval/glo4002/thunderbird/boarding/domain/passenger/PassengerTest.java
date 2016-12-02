package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggages;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;

public class PassengerTest {
    private static final UUID HASH = UUID.randomUUID();
    private static final Seat.SeatClass SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final Instant FLIGHT_DATE = Instant.now();
    private static final String FLIGHT_NUMBER = "QK-918";
    private static final boolean VIP = true;
    private static final boolean IS_CHECKED_IN = true;
    private static final boolean IS_NOT_CHECKED_IN = false;

    private CheckedBaggages checkedBaggages = mock(CheckedBaggages.class);
    private Passenger passenger = new Passenger(HASH, SEAT_CLASS, FLIGHT_DATE, FLIGHT_NUMBER, VIP, IS_CHECKED_IN, checkedBaggages);

    @Test
    public void shouldReturnRightValues() {
        assertEquals(HASH, passenger.getHash());
        assertEquals(SEAT_CLASS, passenger.getSeatClass());
        assertEquals(FLIGHT_DATE, passenger.getFlightDate());
        assertEquals(FLIGHT_NUMBER, passenger.getFlightNumber());
        assertEquals(VIP, passenger.isVip());
        assertEquals(IS_CHECKED_IN, passenger.isCheckedIn());
    }

    @Test
    public void givenANonCheckedInPassenger_whenCheckingIn_shouldBeCheckedIn(){
        Passenger nonCheckedInPassenger = new Passenger(HASH, SEAT_CLASS, FLIGHT_DATE, FLIGHT_NUMBER, VIP, IS_NOT_CHECKED_IN);

        nonCheckedInPassenger.checkin();

        assertTrue(nonCheckedInPassenger.isCheckedIn());
    }
}