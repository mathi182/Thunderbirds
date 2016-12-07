package ca.ulaval.glo4002.thunderbird.boarding.application.seat;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategyFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategyFactory.AssignMode;
import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationAssembler;
import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationDTO;

public class SeatAssignationApplicationService {
    private final SeatAssignationAssembler seatAssignationRequestAssembler;
    private final SeatAssignationStrategyFactory seatAssignationStrategyFactory;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    public SeatAssignationApplicationService() {
        this.seatAssignationRequestAssembler = new SeatAssignationAssembler();
        this.seatAssignationStrategyFactory = new SeatAssignationStrategyFactory();
        this.flightRepository = ServiceLocator.resolve(FlightRepository.class);
        this.passengerRepository = ServiceLocator.resolve(PassengerRepository.class);
    }

    public Seat assignSeat(SeatAssignationDTO request) {
        Passenger passenger = passengerRepository.findByPassengerHash(request.passengerHash);

        Flight flight = getFlight(passenger);
        SeatAssignationStrategy strategy = getSeatAssignationStrategy(request, passenger);
        Seat seat = flight.assignBestSeat(strategy, passenger);

        flightRepository.saveFlight(flight);
        return seat;
    }

    private Flight getFlight(Passenger passenger) {
        return flightRepository.getFlight(passenger.getFlightNumber(), passenger.getFlightDate());
    }

    private SeatAssignationStrategy getSeatAssignationStrategy(SeatAssignationDTO request, Passenger passenger) {
        AssignMode assignMode = seatAssignationRequestAssembler.getMode(request);
        return seatAssignationStrategyFactory.getStrategy(assignMode);
    }
}