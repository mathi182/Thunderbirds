package ca.ulaval.glo4002.thunderbird.boarding.rest;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

public class TakenSeatAssembler {
    public TakenSeatDTO fromDomain(Seat seat) {
        TakenSeatDTO dto = new TakenSeatDTO();
        dto.seat = seat.getRow() + "-" + seat.getSeat();
        return dto;
    }
}
