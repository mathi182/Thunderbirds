package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;

import java.util.Random;

public class SeatAssignationStrategyFactory {

    public SeatAssignationStrategy getStrategy(SeatAssignationStrategy.AssignMode mode, Seat.SeatClass seatClass, boolean isAChild) {
        switch (mode) {
            case RANDOM:
                return new RandomSeatAssignationStrategy(new Random(), isAChild);
            case CHEAPEST:
                return new CheapestSeatAssignationStrategy(seatClass, isAChild);
            case LANDSCAPE:
                return new LandscapeSeatAssignationStrategy(seatClass);
            case LEGS:
                return new MostLegRoomSeatAssignationStrategy(seatClass, isAChild);
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}
