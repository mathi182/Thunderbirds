package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.expceptions.SeatAlreadyTakenException;
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
    private static final Seat.SeatClass A_CLASS = Seat.SeatClass.ECONOMY;
    private static final boolean IS_EXIT_ROW = true;
    private static final int SMALLER_LEG_ROOM = 30;
    private static final int MOST_LEG_ROOM = 100;
    private static final double A_CHEAPER_PRICE = 100;
    private static final double A_MORE_EXPENSIVE_PRICE = 200;
    private boolean hasMoreRoom;
    private boolean hasSameAmount;
    private Seat seat;
    private Seat seatToCompareWith;
    private Seat seatComparing;
    private boolean hasLowerPrice;

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

    @Test
    public void givenSmallerLegRoom_whenCheckingIfSeatHasMoreRoomSpace_shouldReturnTrue() {
        hasMoreRoom = seat.hasMoreLegRoomThan(SMALLER_LEG_ROOM);

        assertTrue(hasMoreRoom);
    }

    @Test
    public void givenSameLegRoom_whenCheckingIfSeatHasMoreRoomSpace_shouldReturnFalse() {
        hasMoreRoom = seat.hasMoreLegRoomThan(A_LEGROOM);

        assertFalse(hasMoreRoom);
    }

    @Test
    public void givenMoreLegRoom_whenCheckingIfSeatHasMoreRoomSpace_shouldReturnFalse() {
        hasMoreRoom = seat.hasMoreLegRoomThan(MOST_LEG_ROOM);

        assertFalse(hasMoreRoom);
    }

    @Test
    public void givenSameLegRoom_whenCheckinIfSeatHasSameAmountOfLegRoom_shouldReturnTrue() {
        hasSameAmount = seat.hasSameAmountOfLegRoom(A_LEGROOM);

        assertTrue(hasSameAmount);
    }

    @Test
    public void givenMoreLegRoom_whenCheckinIfSeatHasSameAmountOfLegRoom_shouldReturnFalse() {
        hasSameAmount = seat.hasSameAmountOfLegRoom(MOST_LEG_ROOM);

        assertFalse(hasSameAmount);
    }

    @Test
    public void givenSmallerLegRoom_whenCheckinIfSeatHasSameAmountOfLegRoom_shouldReturnFalse() {
        hasSameAmount = seat.hasSameAmountOfLegRoom(SMALLER_LEG_ROOM);

        assertFalse(hasSameAmount);
    }

    @Test
    public void givenCheaperSeat_whenIfSeatHasLowerPrice_shouldReturnFalse() {
        seatToCompareWith = new Seat(A_ROW, A_SEAT, A_LEGROOM, HAS_WINDOW, HAS_CLEARVIEW, A_CHEAPER_PRICE, A_CLASS,
                IS_EXIT_ROW, true);
        ;

        hasLowerPrice = seat.hasLowerPriceThan(seatToCompareWith);

        assertFalse(hasLowerPrice);
    }

    @Test
    public void givenMostExpensiveSeat_whenIfSeatHasLowerPrice_shouldReturnTrue() {
        seatToCompareWith = new Seat(A_ROW, A_SEAT, A_LEGROOM, HAS_WINDOW, HAS_CLEARVIEW, A_MORE_EXPENSIVE_PRICE,
                A_CLASS, IS_EXIT_ROW, true);

        hasLowerPrice = seat.hasLowerPriceThan(seatToCompareWith);

        assertTrue(hasLowerPrice);
    }

    @Test
    public void givenSamePriceSeat_whenIfSeatHasLowerPrice_shouldReturnFalse() {
        seatToCompareWith = new Seat(A_ROW, A_SEAT, A_LEGROOM, HAS_WINDOW, HAS_CLEARVIEW, A_PRICE, A_CLASS,
                IS_EXIT_ROW, true);

        hasLowerPrice = seat.hasLowerPriceThan(seatToCompareWith);

        assertFalse(hasLowerPrice);
    }

    @Test
    public void givenSameViewSeat_whenSeatHasSameView_shouldReturnTrue(){
        seatToCompareWith = new Seat(A_ROW, A_SEAT, A_LEGROOM, HAS_WINDOW, HAS_CLEARVIEW, A_PRICE, A_CLASS, IS_EXIT_ROW, true);

        boolean hasSameView = seat.hasSameViewAs(seatToCompareWith);

        assertTrue(hasSameView);
    }

    @Test
    public void givenDifferentHasWindowSeat_whenSeatHasSameView_shouldReturnFalse(){
        seatToCompareWith = new Seat(A_ROW, A_SEAT, A_LEGROOM, !HAS_WINDOW, HAS_CLEARVIEW, A_PRICE, A_CLASS, IS_EXIT_ROW, true);

        boolean hasSameView = seat.hasSameViewAs(seatToCompareWith);

        assertFalse(hasSameView);
    }

    @Test
    public void givenDifferentHasClearViewSeat_whenSeatHasSameView_shouldReturnFalse(){
        seatToCompareWith = new Seat(A_ROW, A_SEAT, A_LEGROOM, HAS_WINDOW, !HAS_CLEARVIEW, A_PRICE, A_CLASS, IS_EXIT_ROW, true);

        boolean hasSameView = seat.hasSameViewAs(seatToCompareWith);

        assertFalse(hasSameView);
    }

    @Test
    public void givenSameViewSeat_whenComparingView_shouldReturnFalse(){
        seatToCompareWith = new Seat(A_ROW, A_SEAT, A_LEGROOM, HAS_WINDOW, HAS_CLEARVIEW, A_PRICE, A_CLASS, IS_EXIT_ROW, true);

        boolean hasBetterViewThan = seat.hasBetterViewThan(seatToCompareWith);

        assertFalse(hasBetterViewThan);
    }

    @Test public void givenSeatWithNoWindow_whenComparingView_shouldReturnTrue(){
        seatToCompareWith = new Seat(A_ROW, A_SEAT, A_LEGROOM, !HAS_WINDOW, HAS_CLEARVIEW, A_PRICE, A_CLASS, IS_EXIT_ROW, true);

        boolean hasBetterViewThan = seat.hasBetterViewThan(seatToCompareWith);

        assertTrue(hasBetterViewThan);
    }

    @Test public void givenSeatWithNoWindow_whenComparingViewWithNoWindowSeat_shouldReturnFalse(){
        seatComparing = new Seat(A_ROW, A_SEAT, A_LEGROOM, !HAS_WINDOW, HAS_CLEARVIEW, A_PRICE, A_CLASS, IS_EXIT_ROW, true);

        boolean hasBetterViewThan = seatComparing.hasBetterViewThan(seat);

        assertFalse(hasBetterViewThan);
    }

    @Test public void givenSeatWithNoClearView_whenComparingView_shouldReturnTrue(){
        seatToCompareWith = new Seat(A_ROW, A_SEAT, A_LEGROOM, HAS_WINDOW, !HAS_CLEARVIEW, A_PRICE, A_CLASS, IS_EXIT_ROW, true);

        boolean hasBetterViewThan = seat.hasBetterViewThan(seatToCompareWith);

        assertTrue(hasBetterViewThan);
    }

    @Test public void givenSeatWithNoClearView_whenComparingViewWithNoClearViewSeat_shouldReturnFalse(){
        seatComparing = new Seat(A_ROW, A_SEAT, A_LEGROOM, HAS_WINDOW, !HAS_CLEARVIEW, A_PRICE, A_CLASS, IS_EXIT_ROW, true);

        boolean hasBetterViewThan = seatComparing.hasBetterViewThan(seat);

        assertFalse(hasBetterViewThan);
    }
}
