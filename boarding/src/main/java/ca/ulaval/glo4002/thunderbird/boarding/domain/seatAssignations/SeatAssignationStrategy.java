package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seat.Seat;

import java.util.List;

public interface SeatAssignationStrategy {
    enum AssignMode { RANDOM, CHEAPEST, LEGS , LANDSCAPE}

    Seat assignSeat(List<Seat> availableSeats);
}
