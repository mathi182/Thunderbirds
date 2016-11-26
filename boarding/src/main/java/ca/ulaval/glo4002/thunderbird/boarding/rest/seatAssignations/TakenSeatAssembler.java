package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seat.Seat;

public class TakenSeatAssembler {
    public TakenSeatDTO fromDomain(Seat seat) {
        TakenSeatDTO dto = new TakenSeatDTO();
        dto.seat = seat.getRow() + "-" + seat.getSeatName();
        return dto;
    }
}