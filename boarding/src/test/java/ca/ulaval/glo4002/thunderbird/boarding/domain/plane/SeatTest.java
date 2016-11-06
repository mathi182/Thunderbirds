package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class SeatTest {
    private static final int A_ROW = 1;
    private static final String A_SEAT = "A";
    private static final int A_LEGROOM = 54;
    private static final boolean HAS_WINDOW = true;
    private static final boolean HAS_CLEARVIEW = true;
    private static final double A_PRICE = 123.45;
    private static final Seat.seatClass A_CLASS = Seat.seatClass.ECONOMY;
    private static final boolean IS_EXIT_ROW = true;
    private Seat seat;

    @Before
    public void before() {
        seat = new Seat(A_ROW, A_SEAT, A_LEGROOM, HAS_WINDOW, HAS_CLEARVIEW, A_PRICE, A_CLASS, IS_EXIT_ROW, true);
    }

    @Test
    public void whenTakingASeat_ShouldNotBeAvailableAfterward() {
        seat.take();

        assertFalse(seat.isAvailable());
    }

    @Test
    public void givenANotTakenSeat_shouldBeAvailable() {
        assertTrue(seat.isAvailable());
    }

    @Test(expected = SeatAlreadyTakenException.class)
    public void givenATakenSeat_whenTaking_ShouldThrowSeatTakenException() {
        seat.take();

        seat.take();
    }

}
