package ca.ulaval.glo4002.thunderbird.boarding.rest;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;

public class SeatAssignationRequestAssembler {

    public Passenger getDomainPassenger(SeatAssignationRequest request) {
        return Passenger.findByPassengerHash(request.passengerHash);
    }

    public String getMode(SeatAssignationRequest request) {
        return request.mode;
    }
}
