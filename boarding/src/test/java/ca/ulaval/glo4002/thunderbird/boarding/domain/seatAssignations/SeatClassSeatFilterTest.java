package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class SeatClassSeatFilterTest {
    private static final Seat.SeatClass A_SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final Seat.SeatClass ANOTHER_SEAT_CLASS = Seat.SeatClass.BUSINESS;
    private SeatClassSeatFilter seatFilter;

    @Before
    public void before() {
        seatFilter = new SeatClassSeatFilter(A_SEAT_CLASS);
    }

    @Test
    public void givenAnEmptyList_whenFiltering_shouldReturnAnEmptyList() {
        List<Seat> seats = new ArrayList<>();

        List<Seat> filteredSeats = seatFilter.filter(seats);

        assertTrue(filteredSeats.isEmpty());
    }

    @Test
    public void givenAList_whenFiltering_shouldReturnANewList() {
        List<Seat> seats = new ArrayList<>();
        Seat seat = mock(Seat.class);
        willReturn(A_SEAT_CLASS).given(seat).getSeatClass();
        seats.add(seat);

        List<Seat> filteredSeats = seatFilter.filter(seats);

        assertNotSame(seats, filteredSeats);
    }

    @Test
    public void givenAList_whenFiltering_shouldRemoveSeatsWithWrongSeatClass() {
        Seat correctSeatClassSeat = mock(Seat.class);
        Seat wrongSeatClassSeat = mock(Seat.class);
        willReturn(A_SEAT_CLASS).given(correctSeatClassSeat).getSeatClass();
        willReturn(ANOTHER_SEAT_CLASS).given(wrongSeatClassSeat).getSeatClass();
        List<Seat> seats = Arrays.asList(correctSeatClassSeat, wrongSeatClassSeat);

        List<Seat> filteredSeats = seatFilter.filter(seats);

        assertEquals(1, filteredSeats.size());
        assertEquals(A_SEAT_CLASS, filteredSeats.get(0).getSeatClass());
    }


}
