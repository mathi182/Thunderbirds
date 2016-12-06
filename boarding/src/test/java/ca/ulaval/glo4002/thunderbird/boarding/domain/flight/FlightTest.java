package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.SeatNotAvailableException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FlightTest {
    private static final String A_FLIGHT_NUMBER = "QK-918";
    private static final Instant A_FLIGHT_DATE = Instant.ofEpochMilli(123456);
    private List<Seat> seats;
    private Seat seat;
    private Flight flight;
    private Plane plane;
    private SeatAssignationStrategy strategy;
    private Passenger passenger;

    @Before
    public void before() {
        plane = mock(Plane.class);
        seat = mock(Seat.class);
        seats = new ArrayList<>();
        seats.add(seat);
        flight = new Flight(A_FLIGHT_NUMBER, A_FLIGHT_DATE, plane, seats);
        strategy = mock(SeatAssignationStrategy.class);
        passenger = mock(Passenger.class);
    }

    @Test(expected = SeatNotAvailableException.class)
    public void givenAllSeatsTaken_shouldThrowSeatNotAvailable() {
        willReturn(false).given(seat).isAvailable();
        willReturn(false).given(passenger).isAChild();

        flight.findAvailableSeat(strategy, passenger.isAChild());
    }

    @Test
    public void givenASeatsAvailable_shouldUseStrategy() {
        willReturn(true).given(seat).isAvailable();
        willReturn(false).given(passenger).isAChild();

        flight.findAvailableSeat(strategy, passenger.isAChild());

        verify(strategy).findAvailableSeat(anyListOf(Seat.class));
    }

    @Test(expected = SeatNotAvailableException.class)
    public void givenASeatListWithOnlyExitRowSeats_whenFindingAvailableSeat_ShouldThrowSeatsNotAvailable(){
        willReturn(true).given(seat).isExitRow();
        willReturn(true).given(seat).isAvailable();
        willReturn(true).given(passenger).isAChild();

        flight.findAvailableSeat(strategy, passenger.isAChild());
    }

    @Test
    public void givenASeatListWithNoExitRowSeats_whenFindingAvailableSeat_shouldCallStrategyWithOneSeat(){
        willReturn(false).given(seat).isExitRow();
        willReturn(true).given(seat).isAvailable();
        willReturn(true).given(passenger).isAChild();
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);

        flight.findAvailableSeat(strategy,passenger.isAChild());

        verify(strategy).findAvailableSeat(captor.capture());
        int expectedSize = 1;
        assertEquals(expectedSize,captor.getValue().size());
    }
}
