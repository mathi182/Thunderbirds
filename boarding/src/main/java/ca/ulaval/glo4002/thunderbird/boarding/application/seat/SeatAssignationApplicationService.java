package ca.ulaval.glo4002.thunderbird.boarding.application.seat;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategyFactory;
import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationRequest;
import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationRequestAssembler;


public class SeatAssignationApplicationService {

    private final SeatAssignationRequestAssembler seatAssignationRequestAssembler;
    private FlightRepository repository;

    public SeatAssignationApplicationService() {
        this.repository = ServiceLocator.resolve(FlightRepository.class);
        seatAssignationRequestAssembler = new SeatAssignationRequestAssembler();
    }

    public Seat assignSeat(SeatAssignationRequest request) {
        Flight flight = getFlight(request);
        SeatAssignationStrategy strategy = getSeatAssignationStrategy(request);
        Seat seat = flight.findAvailableSeat(strategy);
        seat.markAsUnavailable();
        return seat;
    }

    private Flight getFlight(SeatAssignationRequest request) {
        Passenger passenger = seatAssignationRequestAssembler.getDomainPassenger(request);
        return repository.getFlight(passenger.getFlightNumber(), passenger.getFlightDate());
    }

    private SeatAssignationStrategy getSeatAssignationStrategy(SeatAssignationRequest request) {
        SeatAssignationStrategy.AssignMode assignMode = seatAssignationRequestAssembler.getMode(request);
        return new SeatAssignationStrategyFactory().getStrategy(assignMode, request.seatClass);
    }
}
