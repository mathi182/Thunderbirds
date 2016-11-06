package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.List;

public interface SeatAssignationStrategy {
    enum assignMode { RANDOM, CHEAPEST }

    Seat assignSeat(List<Seat> availableSeats);
}
