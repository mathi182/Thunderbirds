package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepositoryProvider;

public class SeatAssignationRequestAssembler {
    private static final String RANDOM_MODE = "RANDOM";
    private static final String CHEAPEST_MODE = "CHEAPEST";
    private static final String LEGS_MODE = "LEGS";

    public Passenger getDomainPassenger(SeatAssignationRequest request) {
        PassengerRepository repository = new PassengerRepositoryProvider().getPassengerRepository();
        Passenger passenger = repository.getPassenger(request.passengerHash);
        return passenger;
    }

    public SeatAssignationStrategy.AssignMode getMode(SeatAssignationRequest request) {
        switch (request.mode){
            case RANDOM_MODE:
                return SeatAssignationStrategy.AssignMode.RANDOM;
            case CHEAPEST_MODE:
                return SeatAssignationStrategy.AssignMode.CHEAPEST;
            case LEGS_MODE:
                return SeatAssignationStrategy.AssignMode.LEGS;
            default:
                throw new NoSuchStrategyException(request.mode);
        }
    }
}
