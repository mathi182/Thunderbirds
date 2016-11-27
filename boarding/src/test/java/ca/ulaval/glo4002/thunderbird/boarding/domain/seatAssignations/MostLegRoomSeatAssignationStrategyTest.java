package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.mock;

public class MostLegRoomSeatAssignationStrategyTest {

    private static final int MOST_LEG_ROOM = 100;
    private static final int SECOND_MOST_LEG_ROOM = 90;
    private static final int THIRD_MOST_LEG_ROOM = 50;
    private MostLegRoomSeatAssignationStrategy strategy;
    private Seat economicMostLegRoomSeat = mock(Seat.class);
    private Seat economicCheapestSeat = mock(Seat.class);
    private Seat businessSeat = mock(Seat.class);
    private SeatFilter seatFilter;

    private List<Seat> seats = new ArrayList<>(Arrays.asList(economicMostLegRoomSeat, economicCheapestSeat,
            businessSeat));

    @Before
    public void setUp() {
        willReturn(Seat.SeatClass.ECONOMY).given(economicMostLegRoomSeat).getSeatClass();
        willReturn(Seat.SeatClass.ECONOMY).given(economicCheapestSeat).getSeatClass();
        willReturn(Seat.SeatClass.BUSINESS).given(businessSeat).getSeatClass();
        willReturn(MOST_LEG_ROOM).given(economicMostLegRoomSeat).getLegRoom();
        willReturn(THIRD_MOST_LEG_ROOM).given(businessSeat).getLegRoom();
        seatFilter = mock(SeatFilter.class);
    }

    @Test
    public void givenAValidSeatsList_whenSelectingMostLegRoom_shouldReturnMostLegRoomFromAnyCLass() {
        willReturn(SECOND_MOST_LEG_ROOM).given(economicCheapestSeat).getLegRoom();
        willReturn(true).given(economicMostLegRoomSeat).hasMoreLegRoomThan(any(Seat.class));
        willReturn(seats).given(seatFilter).filter(anyCollectionOf(Seat.class));
        strategy = new MostLegRoomSeatAssignationStrategy(Seat.SeatClass.ANY, seatFilter);

        Seat takenSeat = strategy.assignSeat(seats);

        assertEquals(economicMostLegRoomSeat, takenSeat);
    }

    @Test(expected = NoMoreSeatAvailableException.class)
    public void
    givenAValidSeatsListWithoutBusiness_whenSelectingMostLegRoomFromBusiness_shouldThrowNoMoreSeatAvailable() {
        List<Seat> seatsWithoutBusiness = new ArrayList<>(Arrays.asList(economicCheapestSeat, economicMostLegRoomSeat));
        willReturn(Collections.emptyList()).given(seatFilter).filter(anyCollectionOf(Seat.class));
        strategy = new MostLegRoomSeatAssignationStrategy(Seat.SeatClass.BUSINESS, seatFilter);

        strategy.assignSeat(seatsWithoutBusiness);
    }

    @Test
    public void givenAValidSeatsList_whenSelectionMostLegRoomWithMultipleResult_shouldReturnSeatWithLowestPrice() {
        willReturn(MOST_LEG_ROOM).given(economicCheapestSeat).getLegRoom();
        willReturn(true).given(economicCheapestSeat).hasLowerPriceThan(any(Seat.class));
        willReturn(true).given(economicMostLegRoomSeat).hasMoreLegRoomThan(any(Seat.class));
        willReturn(false).given(economicCheapestSeat).hasMoreLegRoomThan(any(Seat.class));
        willReturn(true).given(economicCheapestSeat).hasSameAmountOfLegRoomAs(any(Seat.class));
        willReturn(seats).given(seatFilter).filter(anyCollectionOf(Seat.class));
        strategy = new MostLegRoomSeatAssignationStrategy(Seat.SeatClass.ANY, seatFilter);

        Seat takenSeat = strategy.assignSeat(seats);

        assertEquals(economicCheapestSeat, takenSeat);
    }
}
