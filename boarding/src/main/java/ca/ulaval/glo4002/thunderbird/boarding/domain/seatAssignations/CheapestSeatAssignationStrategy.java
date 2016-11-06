package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.List;

public class CheapestSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.seatClass classType;

    public CheapestSeatAssignationStrategy() {
        classType = Seat.seatClass.ANY;
    }

    public CheapestSeatAssignationStrategy(Seat.seatClass classType) {
        this.classType = classType;
    }

    @Override
    public Seat assignSeat(List<Seat> availableSeats) {
        Seat cheapestSeat = findCheapestSeat(availableSeats);

        if (cheapestSeat == null) {
            throw new NoMoreSeatAvailableException();
        }

        return cheapestSeat;
    }

    private Seat findCheapestSeat(List<Seat> availableSeats) {
        Seat cheapestSeat = null;

        for (Seat seat : availableSeats) {
            if (classType == Seat.seatClass.ANY || seat.getPriceClass() == classType) {
                if (cheapestSeat == null) {
                    cheapestSeat = seat;
                } else if (seat.getPrice() < cheapestSeat.getPrice()) {
                    cheapestSeat = seat;
                }
            }
        }

        return cheapestSeat;
    }
}
