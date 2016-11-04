package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoSuchStrategyException;

import java.util.Random;

public class SeatAssignationStrategyFactory {
    private static final String RANDOM_MODE = "RANDOM";

    public SeatAssignationStrategy getStrategy(SeatAssignationStrategy.assignMode mode) {
        switch (mode) {
            case RANDOM:
                return new RandomSeatAssignationStrategy(new Random());
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}
