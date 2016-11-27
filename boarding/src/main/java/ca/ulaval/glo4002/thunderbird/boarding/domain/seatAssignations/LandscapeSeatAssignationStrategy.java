package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LandscapeSeatAssignationStrategy implements SeatAssignationStrategy {

    private Seat.SeatClass chosenSeatClass;
    private SeatFilter seatFilter;

    public LandscapeSeatAssignationStrategy(Seat.SeatClass seatClass) {
        if (seatClass.equals(Seat.SeatClass.ANY)) {
            seatFilter = new NoOperationSeatClassFilter();
        } else {
            seatFilter = new SeatClassSeatFilter(seatClass);
        }
        this.chosenSeatClass = seatClass;
    }

    //For tests
    public LandscapeSeatAssignationStrategy(Seat.SeatClass seatClass, SeatFilter seatFilter) {
        this.seatFilter = seatFilter;
        this.chosenSeatClass = seatClass;
    }

    @Override
    public Seat assignSeat(List<Seat> availableSeats) {
        List<Seat> filteredSeats = seatFilter.filter(availableSeats);
        if (filteredSeats.isEmpty()) {
            throw new NoMoreSeatAvailableException();
        }

        filteredSeats = filterBestViewSeat(filteredSeats);

        return chooseSeat(filteredSeats);
    }

    private List<Seat> filterBestViewSeat(List<Seat> availableSeats) {
        Seat currentBestSeat = availableSeats.get(0);
        List<Seat> seats = new ArrayList<>(Collections.singletonList(currentBestSeat));

        for (int i = 1; i < availableSeats.size(); i++) {
            Seat seat = availableSeats.get(i);
            if (seat.hasBetterViewThan(currentBestSeat)) {
                currentBestSeat = seat;

                seats.clear();
                seats.add(seat);
            } else if (seat.hasSameViewAs(currentBestSeat)) {
                seats.add(seat);
            }
        }

        return seats;
    }

    private Seat chooseSeat(List<Seat> seats) {
        if (seats.size() > 1) {
            CheapestSeatAssignationStrategy strategy = new CheapestSeatAssignationStrategy(chosenSeatClass);
            return strategy.assignSeat(seats);
        }
        return seats.get(0);
    }

}
