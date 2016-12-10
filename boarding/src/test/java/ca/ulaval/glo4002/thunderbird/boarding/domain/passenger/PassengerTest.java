package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.PassengerBaggagesCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PassengerTest {
    private static final UUID HASH = UUID.randomUUID();
    private static final Seat.SeatClass SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final boolean VIP = true;
    private static final boolean IS_CHECKED_IN = true;
    private static final boolean IS_CHILD = false;

    private Flight flight = mock(Flight.class);
    private PassengerBaggagesCollection passengerCollection = mock(PassengerBaggagesCollection.class);
    private Passenger passenger = new Passenger(HASH, SEAT_CLASS, VIP, IS_CHECKED_IN, IS_CHILD, flight, passengerCollection);

    @Test
    public void shouldReturnRightValues() {
        assertEquals(HASH, passenger.getHash());
        assertEquals(SEAT_CLASS, passenger.getSeatClass());
        assertEquals(VIP, passenger.isVip());
        assertEquals(IS_CHECKED_IN, passenger.isCheckedIn());
        assertEquals(IS_CHILD, passenger.isChild());
        assertSame(flight, passenger.getFlight());
    }

    @Test
    public void givenFlightReturnASeat_whenAssigningSeat_shouldAssignSeatToPassenger() {
        Seat bestSeat = mock(Seat.class);
        SeatAssignationStrategy strategy = mock(SeatAssignationStrategy.class);
        willReturn(bestSeat).given(flight).reserveSeat(strategy, passenger);

        passenger.assignSeat(strategy);

        verify(flight).reserveSeat(strategy, passenger);
        assertSame(bestSeat, passenger.getSeat());
    }
}