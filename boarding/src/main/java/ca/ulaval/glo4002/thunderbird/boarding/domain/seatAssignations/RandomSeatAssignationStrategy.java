package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.List;
import java.util.Random;

public class RandomSeatAssignationStrategy implements SeatAssignationStrategy {

    private Random random;

    public RandomSeatAssignationStrategy(Random random) {
        this.random = random;
    }

    @Override
    public Seat assignSeat(List<Seat> availableSeats) {
        int chosenSeat = random.nextInt(availableSeats.size());

        return availableSeats.get(chosenSeat);
    }

}
