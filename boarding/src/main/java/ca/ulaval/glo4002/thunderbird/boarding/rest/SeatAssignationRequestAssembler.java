package ca.ulaval.glo4002.thunderbird.boarding.rest;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;

public class SeatAssignationRequestAssembler {
    private static final String RANDOM_MODE = "RANDOM";

    public Passenger getDomainPassenger(SeatAssignationRequest request) {
        //TODO fetch passenger via API and repository, not via this class
        return Passenger.findByPassengerHash(request.passengerHash);
    }

    public SeatAssignationStrategy.assignMode getMode(SeatAssignationRequest request) {
        switch (request.mode){
            case RANDOM_MODE:
                return SeatAssignationStrategy.assignMode.RANDOM;
            default:
                throw new NoSuchStrategyException(request.mode);
        }
    }
}
