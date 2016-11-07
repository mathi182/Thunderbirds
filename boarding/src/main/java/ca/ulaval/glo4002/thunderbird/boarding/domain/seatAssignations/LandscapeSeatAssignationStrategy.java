package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.List;

public class LandscapeSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.SeatClass classType;

    public LandscapeSeatAssignationStrategy() {
        classType = Seat.SeatClass.ANY;
    }

    public LandscapeSeatAssignationStrategy(Seat.SeatClass seatClass) {
        this.classType = seatClass;
    }

    @Override
    public Seat assignSeat(List<Seat> availableSeats) {
        Seat bestViewSeat = findBestViewSeat(availableSeats);

        if (bestViewSeat == null) {
            throw new NoMoreSeatAvailableException();
        }

        return bestViewSeat;
    }

    private Seat findBestViewSeat(List<Seat> availableSeats) {
        Seat bestViewSeat = null;

        for (Seat seat : availableSeats) {
            if (isSeatGoodClass(seat)) {
                if (bestViewSeat == null) {
                    bestViewSeat = seat;
                }
                bestViewSeat = seat.hasBestViewBetween(bestViewSeat);
            }
        }
    }

    private boolean isSeatGoodClass(Seat seat) {
        return classType.equals(Seat.SeatClass.ANY) || seat.getSeatClass().equals(classType);
    }
}
