package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.Random;

public class SeatAssignationStrategyFactory {
    public enum AssignMode {
        RANDOM,
        CHEAPEST,
        LANDSCAPE,
        LEGS
    }

    public SeatAssignationStrategy getStrategy(AssignMode mode, Seat.SeatClass seatClass) {
        switch (mode) {
            case RANDOM:
                return new RandomSeatAssignationStrategy(new Random());
            case CHEAPEST:
                return new CheapestSeatAssignationStrategy(seatClass);
            case LANDSCAPE:
                return new LandscapeSeatAssignationStrategy(seatClass);
            case LEGS:
                return new MostLegRoomSeatAssignationStrategy(seatClass);
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}
