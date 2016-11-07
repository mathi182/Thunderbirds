package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.List;

public class CheapestSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.SeatClass classType;

    public CheapestSeatAssignationStrategy() {
        classType = Seat.SeatClass.ANY;
    }

    public CheapestSeatAssignationStrategy(Seat.SeatClass classType) {
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
            if (isSeatGoodClass(seat)) {
                if (cheapestSeat == null) {
                    cheapestSeat = seat;
                } else if (seat.getPrice() < cheapestSeat.getPrice()) {
                    cheapestSeat = seat;
                }
            }
        }

        return cheapestSeat;
    }

    private boolean isSeatGoodClass(Seat seat) {
        return classType.equals(Seat.SeatClass.ANY) || seat.getSeatClass().equals(classType);
    }
}
