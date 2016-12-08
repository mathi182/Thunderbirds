package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.expceptions.SeatAlreadyTakenException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SeatTest {
    private static final int ROW = 1;
    private static final String SEAT = "A";
    private static final int LEGROOM = 54;
    private static final boolean HAS_WINDOW = true;
    private static final boolean HAS_CLEAR_VIEW = true;
    private static final double PRICE = 123.45;
    private static final Seat.SeatClass SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final boolean IS_EXIT_ROW = true;

    private Seat seat;

    @Before
    public void before() {
        seat = new Seat(ROW, SEAT, LEGROOM, HAS_WINDOW, HAS_CLEAR_VIEW, PRICE, SEAT_CLASS, IS_EXIT_ROW, true);
    }

    @Test
    public void whenTakingASeat_ShouldNotBeAvailableAfterward() {
        seat.markAsUnavailable();

        assertFalse(seat.isAvailable());
    }

    @Test
    public void givenANotTakenSeat_shouldBeAvailable() {
        assertTrue(seat.isAvailable());
    }

    @Test(expected = SeatAlreadyTakenException.class)
    public void givenATakenSeat_whenTaking_ShouldThrowSeatTakenException() {
        seat.markAsUnavailable();

        seat.markAsUnavailable();
    }
}