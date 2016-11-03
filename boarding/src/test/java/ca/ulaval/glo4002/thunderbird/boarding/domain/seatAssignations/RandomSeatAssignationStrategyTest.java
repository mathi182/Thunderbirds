package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;

public class RandomSeatAssignationStrategyTest {
    private static final int A_RANDOM_NUMBER = 1;
    private RandomSeatAssignationStrategy strategy;
    private Random random;
    private List<Seat> seats;

    @Before
    public void before() {
        random = mock(Random.class);
        strategy = new RandomSeatAssignationStrategy(random);
        Seat seatA = mock(Seat.class);
        Seat seatB = mock(Seat.class);
        seats = new ArrayList<>(2);
        seats.add(seatA);
        seats.add(seatB);
    }

    @Test
    public void givenAListOfAvailableSeats_shouldChooseAccordingToRandom() {
        willReturn(A_RANDOM_NUMBER).given(random).nextInt(anyInt());

        Seat takenSeat = strategy.assignSeat(seats);

        assertSame(seats.get(A_RANDOM_NUMBER), takenSeat);
    }


}
