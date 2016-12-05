package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomSeatAssignationStrategy implements SeatAssignationStrategy {

    private Random random;
    private boolean childSeat;

    public RandomSeatAssignationStrategy(Random random, boolean childSeat) {
        this.random = random;
        this.childSeat = childSeat;
    }

    @Override
    public Seat findAvailableSeat(List<Seat> availableSeats) {
        if(childSeat){
            availableSeats = removeExitRowSeats(availableSeats);
        }

        int chosenSeat = random.nextInt(availableSeats.size());

        return availableSeats.get(chosenSeat);
    }

    private List<Seat> removeExitRowSeats(List<Seat> availableSeats){
        List<Seat> seats;
        seats = availableSeats
                .stream()
                .filter(seat -> !seat.isExitRow())
                .collect(Collectors.toList());

        if(seats.size() == 0){
            throw new NoMoreSeatAvailableException();
        }
        return seats;
    }

}
