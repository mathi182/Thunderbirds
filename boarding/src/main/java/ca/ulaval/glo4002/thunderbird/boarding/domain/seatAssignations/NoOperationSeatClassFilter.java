package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NoOperationSeatClassFilter implements SeatFilter {
    @Override
    public List<Seat> filter(Collection<Seat> seats) {
        return new ArrayList<>(seats);
    }
}
