package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SeatClassSeatFilter implements SeatFilter {
    private final Seat.SeatClass chosenSeatClass;

    public SeatClassSeatFilter(Seat.SeatClass chosenSeatClass) {
        this.chosenSeatClass = chosenSeatClass;
    }

    @Override
    public List<Seat> filter(Collection<Seat> seats) {
        return seats
                .stream()
                .filter(seat -> seat.getSeatClass().equals(chosenSeatClass))
                .collect(Collectors.toList());
    }
}
