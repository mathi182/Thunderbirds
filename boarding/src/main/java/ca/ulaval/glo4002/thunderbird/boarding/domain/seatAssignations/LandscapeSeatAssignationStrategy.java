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
        availableSeats = filterSeatsByClass(availableSeats);
        Seat bestSeat = findBestViewSeat(availableSeats);

        return bestSeat;
    }

    private List<Seat> filterSeatsByClass(List<Seat> availableSeats) {
        availableSeats.removeIf(seat -> !isSeatGoodClass(seat));

        return availableSeats;
    }

    private Seat findBestViewSeat(List<Seat> availableSeats) {
        if (availableSeats.isEmpty()) {
            throw new NoMoreSeatAvailableException();
        }

        Seat bestSeat = availableSeats.get(0);

        for (Seat seat : availableSeats) {
            bestSeat = bestSeat.bestSeatViewBetween(seat);
        }

        return bestSeat;
    }

    private boolean isSeatGoodClass(Seat seat) {
        return classType.equals(Seat.SeatClass.ANY) || seat.getSeatClass().equals(classType);
    }
}
