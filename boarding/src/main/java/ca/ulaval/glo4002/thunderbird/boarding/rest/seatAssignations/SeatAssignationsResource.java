package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategyFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Random;

@Path(SeatAssignationsResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class SeatAssignationsResource {
    public static final String PATH = "/seat-assignations/";

    @Context
    UriInfo uriInfo;

    private FlightRepository repository;

    public SeatAssignationsResource() {
        repository = ServiceLocator.resolve(FlightRepository.class);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignSeat(SeatAssignationRequest request) {
        Flight flight = getFlight(request);
        SeatAssignationStrategy strategy = getSeatAssignationStrategy(request);

        Seat seat = flight.assignSeat(strategy);
        seat.take();

        TakenSeatDTO takenSeatDTO = convertSeatToDTO(seat);

        String seatAssignationsIdString = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(seatAssignationsIdString).build();
        return Response.created(uri)
                .entity(takenSeatDTO)
                .build();
    }

    private Flight getFlight(SeatAssignationRequest request) {
        SeatAssignationRequestAssembler seatAssignationRequestAssembler = new SeatAssignationRequestAssembler();
        Passenger passenger = seatAssignationRequestAssembler.getDomainPassenger(request);
        return repository.getFlight(passenger.getFlightNumber(), passenger.getFlightDate());
    }

    private SeatAssignationStrategy getSeatAssignationStrategy(SeatAssignationRequest request) {
        SeatAssignationRequestAssembler seatAssignationRequestAssembler = new SeatAssignationRequestAssembler();
        SeatAssignationStrategy.AssignMode assignMode = seatAssignationRequestAssembler.getMode(request);
        return new SeatAssignationStrategyFactory().getStrategy(assignMode, request.seatClass);
    }

    private TakenSeatDTO convertSeatToDTO(Seat seat) {
        TakenSeatAssembler assembler = new TakenSeatAssembler();
        return assembler.fromDomain(seat);
    }
}