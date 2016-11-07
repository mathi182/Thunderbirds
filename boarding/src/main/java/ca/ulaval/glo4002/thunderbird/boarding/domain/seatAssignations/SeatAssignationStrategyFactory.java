package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoSuchStrategyException;

import java.util.Random;

public class SeatAssignationStrategyFactory {

    public SeatAssignationStrategy getStrategy(SeatAssignationStrategy.AssignMode mode) {
        switch (mode) {
            case RANDOM:
                return new RandomSeatAssignationStrategy(new Random());
            case CHEAPEST:
                return new CheapestSeatAssignationStrategy();
            case LEGS:
                return new MostLegRoomSeatAssignationStrategy();
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}
