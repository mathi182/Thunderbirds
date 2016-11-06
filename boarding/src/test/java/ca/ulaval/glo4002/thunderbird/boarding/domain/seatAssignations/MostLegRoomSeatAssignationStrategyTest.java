package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class MostLegRoomSeatAssignationStrategyTest {

    private static final int MOST_LEG_ROOM = 100;
    private MostLegRoomSeatAssignationStrategy strategy;
    private Seat cheapestMostLegRoomSeat = mock(Seat.class);
    private Seat normalMostLegRoomSeat = mock(Seat.class);
    private Seat regularSeat = mock(Seat.class);

    private List<Seat> seatsWithUniqueMostLegRoom = new ArrayList<>(Arrays.asList(normalMostLegRoomSeat, regularSeat));
    private List<Seat> seatsWithMultipleMostLegRoom = new ArrayList<>(Arrays.asList(cheapestMostLegRoomSeat, normalMostLegRoomSeat, regularSeat));


    @Test
    public void givenAValidSeatsList_whenSelectingMostClearance_shouldReturnMostClearanceFromAnyCLass() {
        willReturn(MOST_LEG_ROOM).given(normalMostLegRoomSeat).getLegRoom();
        strategy = new MostLegRoomSeatAssignationStrategy(Seat.seatClass.ANY);

        Seat takenSeat = strategy.assignSeat(seatsWithUniqueMostLegRoom);
        int takenSeatLegRoom = takenSeat.getLegRoom();

        assertEquals(MOST_LEG_ROOM, takenSeatLegRoom);
    }
}
