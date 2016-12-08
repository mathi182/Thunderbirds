package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FlightTest {
    private static final String A_FLIGHT_NUMBER = "QK-918";
    private static final Instant A_FLIGHT_DATE = Instant.ofEpochMilli(123456);

    private final Plane plane = mock(Plane.class);
    private final List<Seat> seats = new ArrayList<>();
    private final FlightId flightId = new FlightId(A_FLIGHT_NUMBER, A_FLIGHT_DATE);
    private final Flight flight = new Flight(flightId, plane, seats);
    private final SeatAssignationStrategy strategy = mock(SeatAssignationStrategy.class);
    private final Passenger passenger = mock(Passenger.class);

    @Test
    public void givenABestSeat_whenAssigningBestSeat_shouldAssignAndReturnThisSeat() {
        Seat expectedSeat = mock(Seat.class);
        willReturn(expectedSeat).given(strategy).findBestSeat(seats, passenger);

        Seat actualSeat = flight.assignBestSeat(strategy,passenger);

        verify(strategy).findBestSeat(seats, passenger);
        verify(expectedSeat).markAsUnavailable();
        assertEquals(expectedSeat, actualSeat);
    }
}