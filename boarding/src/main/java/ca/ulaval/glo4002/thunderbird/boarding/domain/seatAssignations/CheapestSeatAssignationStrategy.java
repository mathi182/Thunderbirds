package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.List;

public class CheapestSeatAssignationStrategy implements SeatAssignationStrategy {

    private SeatFilter seatFilter;

    public CheapestSeatAssignationStrategy(Seat.SeatClass classType) {
        if (classType.equals(Seat.SeatClass.ANY)) {
            seatFilter = new NoOperationSeatClassFilter();
        } else {
            seatFilter = new SeatClassSeatFilter(classType);
        }
    }

    //For tests
    public CheapestSeatAssignationStrategy(SeatFilter seatFilter) {
        this.seatFilter = seatFilter;
    }

    @Override
    public Seat assignSeat(List<Seat> availableSeats) {
        List<Seat> filteredSeats = seatFilter.filter(availableSeats);

        if (filteredSeats.size() == 0) {
            throw new NoMoreSeatAvailableException();
        }

        return findCheapestSeat(filteredSeats);
    }

    private Seat findCheapestSeat(List<Seat> availableSeats) {
        Seat cheapestSeat = availableSeats.get(0);

        for (Seat seat : availableSeats) {
            if (seat.hasLowerPriceThan(cheapestSeat)) {
                cheapestSeat = seat;
            }
        }
        return cheapestSeat;
    }
}
