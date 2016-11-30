package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class SeatAssignationStrategyFactoryTest {
    private SeatAssignationStrategyFactory factory;

    @Before
    public void before() {
        factory = new SeatAssignationStrategyFactory();
    }

    @Test
    public void givenModeRandom_whenGettingStrategy_shouldReturnRandomSeatStrategy() {
        SeatAssignationStrategy strategy = factory.getStrategy(SeatAssignationStrategy.AssignMode.RANDOM, Seat
                .SeatClass.ANY);

        assertThat(strategy, instanceOf(RandomSeatAssignationStrategy.class));
    }

    @Test
    public void givenModeCheapest_whenGettingStrategy_shouldReturnCheapestSeatStrategy() {
        SeatAssignationStrategy strategy = factory.getStrategy(SeatAssignationStrategy.AssignMode.CHEAPEST, Seat
                .SeatClass.ANY);

        assertThat(strategy, instanceOf(CheapestSeatAssignationStrategy.class));
    }

    @Test
    public void givenModeMostLegRoom_whenGettingStrategy_shouldReturnMostLegRoomSeatStrategy() {
        SeatAssignationStrategy strategy = factory.getStrategy(SeatAssignationStrategy.AssignMode.LEGS, Seat
                .SeatClass.ANY);

        assertThat(strategy, instanceOf(MostLegRoomSeatAssignationStrategy.class));
    }

    @Test
    public void givenModeBestView_whenGettingStrategy_shouldReturnMostLegRoomSeatStrategy(){
        SeatAssignationStrategy strategy = factory.getStrategy(SeatAssignationStrategy.AssignMode.LANDSCAPE, Seat
                .SeatClass.ANY);

        assertThat(strategy, instanceOf(LandscapeSeatAssignationStrategy.class));
    }
}
