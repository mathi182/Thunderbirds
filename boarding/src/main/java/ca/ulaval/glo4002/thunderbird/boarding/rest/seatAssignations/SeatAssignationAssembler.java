package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;

public class SeatAssignationAssembler {
    private static final String RANDOM_MODE = "RANDOM";
    private static final String CHEAPEST_MODE = "CHEAPEST";
    private static final String LANDSCAPE_MODE = "LANDSCAPE";
    private static final String LEGS_MODE = "LEGS";

    public Passenger getDomainPassenger(SeatAssignationDTO request) {
        PassengerRepository repository = ServiceLocator.resolve(PassengerRepository.class);
        return repository.getPassenger(request.passengerHash);
    }

    public SeatAssignationStrategy.AssignMode getMode(SeatAssignationDTO request) {
        switch (request.mode) {
            case RANDOM_MODE:
                return SeatAssignationStrategy.AssignMode.RANDOM;
            case CHEAPEST_MODE:
                return SeatAssignationStrategy.AssignMode.CHEAPEST;
            case LANDSCAPE_MODE:
                return SeatAssignationStrategy.AssignMode.LANDSCAPE;
            case LEGS_MODE:
                return SeatAssignationStrategy.AssignMode.LEGS;
            default:
                throw new NoSuchStrategyException(request.mode);
        }
    }
}