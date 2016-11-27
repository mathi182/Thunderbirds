package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.mock;

public class NoOperationSeatClassFilterTest {
    private SeatFilter seatFilter;

    @Before
    public void before() {
        seatFilter = new NoOperationSeatClassFilter();
    }

    @Test
    public void givenAnEmptyCollection_whenFiltering_shouldReturnAnEmptyList() {
        List<Seat> seats = Collections.emptyList();

        List<Seat> filteredSeats = seatFilter.filter(seats);

        assertTrue(filteredSeats.isEmpty());
    }

    @Test
    public void givenAList_whenFiltering_shouldReturnANewList() {
        List<Seat> seats = Collections.singletonList(mock(Seat.class));

        List<Seat> filteredSeats = seatFilter.filter(seats);

        assertNotSame(seats, filteredSeats);
    }

    @Test
    public void givenAList_whenFiltering_shouldNotModifyTheList() {
        List<Seat> seats = Arrays.asList(mock(Seat.class), mock(Seat.class));

        List<Seat> filteredSeats = seatFilter.filter(seats);

        assertEquals(seats, filteredSeats);
    }
}
