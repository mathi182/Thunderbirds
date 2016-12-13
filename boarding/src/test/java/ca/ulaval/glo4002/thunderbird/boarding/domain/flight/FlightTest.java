package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FlightTest {
    private static final String FLIGHT_NUMBER = "QK-918";
    private static final Instant FLIGHT_DATE = Instant.ofEpochMilli(123456);

    private final Seat seat = mock(Seat.class);
    private final Plane plane = mock(Plane.class);
    private final List<Seat> seats = Arrays.asList(seat);
    private final FlightId flightId = new FlightId(FLIGHT_NUMBER, FLIGHT_DATE);
    private final SeatAssignationStrategy strategy = mock(SeatAssignationStrategy.class);
    private final Passenger passenger = mock(Passenger.class);

    private Flight flight = new Flight(flightId, plane);

    @Before
    public void setUp() {
        willReturn(seats).given(plane).getSeats();
        flight = new Flight(flightId, plane);
    }

    @Test
    public void givenABestSeat_whenAssigningBestSeat_shouldAssignAndReturnThisSeat() {
        Seat expectedSeat = mock(Seat.class);
        willReturn(expectedSeat).given(strategy).findBestSeat(seats, passenger);

        Seat actualSeat = flight.reserveSeat(strategy, passenger);

        verify(strategy).findBestSeat(seats, passenger);
        assertEquals(expectedSeat, actualSeat);
    }
}