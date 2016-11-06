package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class MostLegRoomSeatAssignationStrategyTest {

    private static final int MOST_LEG_ROOM = 100;
    private static final int SECOND_MOST_LEG_ROOM = 90;
    private static final int THIRD_MOST_LEG_ROOM = 50;
    private MostLegRoomSeatAssignationStrategy strategy;
    private Seat cheapestEconomicMostLegRoomSeat = mock(Seat.class);
    private Seat economicMostLegRoomSeat = mock(Seat.class);
    private Seat economicSeat = mock(Seat.class);
    private Seat businessSeat = mock(Seat.class);

    private List<Seat> seatsWithUniqueMostLegRoom = new ArrayList<>(Arrays.asList(economicSeat,
            economicMostLegRoomSeat, businessSeat));

    @Before
    public void setUp() {
        willReturn(Seat.SeatClass.ECONOMY).given(economicMostLegRoomSeat).getSeatClass();
        willReturn(Seat.SeatClass.ECONOMY).given(economicSeat).getSeatClass();
        willReturn(Seat.SeatClass.BUSINESS).given(businessSeat).getSeatClass();
    }

    @Test
    public void givenAValidSeatsList_whenSelectingMostLegRoom_shouldReturnMostLegRoomFromAnyCLass() {
        willReturn(MOST_LEG_ROOM).given(economicMostLegRoomSeat).getLegRoom();
        willReturn(THIRD_MOST_LEG_ROOM).given(economicSeat).getLegRoom();
        willReturn(SECOND_MOST_LEG_ROOM).given(businessSeat).getLegRoom();
        strategy = new MostLegRoomSeatAssignationStrategy(Seat.SeatClass.ANY);

        Seat takenSeat = strategy.assignSeat(seatsWithUniqueMostLegRoom);

        int takenSeatLegRoom = takenSeat.getLegRoom();
        assertEquals(MOST_LEG_ROOM, takenSeatLegRoom);
    }

    @Test
    public void givenAValidSeatsList_whenSelectingMostLegRoomFromEconomic_shouldReturnMostLegRoomFromEconomicClass() {
        willReturn(MOST_LEG_ROOM).given(economicMostLegRoomSeat).getLegRoom();
        willReturn(SECOND_MOST_LEG_ROOM).given(economicSeat).getLegRoom();
        strategy = new MostLegRoomSeatAssignationStrategy(Seat.SeatClass.ECONOMY);

        Seat takenSeat = strategy.assignSeat(seatsWithUniqueMostLegRoom);

        int takenSeatLegRoom = takenSeat.getLegRoom();
        assertEquals(MOST_LEG_ROOM, takenSeatLegRoom);
    }

    @Test (expected = NoMoreSeatAvailableException.class)
    public void givenAValidSeatsListWithoutBusiness_whenSelectingMostLegRoomFromBusiness_shouldThrowNoMoreSeatAvailable() {
        List<Seat> seatsWithoutBusiness = new ArrayList<>(Arrays.asList(economicSeat, economicMostLegRoomSeat));
        strategy = new MostLegRoomSeatAssignationStrategy(Seat.SeatClass.BUSINESS);

        strategy.assignSeat(seatsWithoutBusiness);
    }

    @Test
    public void givenAValidSeatsList_whenSelectionMostLegRoomWithMultipleResult_shouldReturnSeatWithLowestPrice() {

    }
}
