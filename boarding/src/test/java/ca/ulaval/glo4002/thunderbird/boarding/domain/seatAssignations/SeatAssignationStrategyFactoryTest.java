package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoSuchStrategyException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class SeatAssignationStrategyFactoryTest {
    private static final String RANDOM_MODE = "RANDOM";
    private static final String INVALID_MODE = "FOOBAR";
    private SeatAssignationStrategyFactory factory;

    @Before
    public void before() {
        factory = new SeatAssignationStrategyFactory();
    }

    @Test
    public void givenModeRandom_shouldReturnRandomSeatStrategy() {
        SeatAssignationStrategy strategy = factory.getStrategy(SeatAssignationStrategy.assignMode.RANDOM);

        assertThat(strategy, instanceOf(RandomSeatAssignationStrategy.class));
    }
}
