package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.Seat.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;

import java.util.Random;

public class SeatAssignationStrategyFactory {

    public SeatAssignationStrategy getStrategy(SeatAssignationStrategy.AssignMode mode, Seat.SeatClass seatClass) {
        switch (mode) {
            case RANDOM:
                return new RandomSeatAssignationStrategy(new Random());
            case CHEAPEST:
                return new CheapestSeatAssignationStrategy(seatClass);
            case LEGS:
                return new MostLegRoomSeatAssignationStrategy(seatClass);
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}
