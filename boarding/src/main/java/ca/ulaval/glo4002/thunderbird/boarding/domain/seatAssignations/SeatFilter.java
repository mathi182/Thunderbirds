package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.Collection;
import java.util.List;

public interface SeatFilter {
    List<Seat> filter(Collection<Seat> seats);
}
