package ca.ulaval.glo4002.thunderbird.boarding.rest;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategyFactory;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepositoryProvider;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;

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
    private FlightRepository repository;

    @Context
    UriInfo uriInfo;

    public SeatAssignationsResource() {
        repository = new FlightRepositoryProvider().getFlightRepository();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignSeat(SeatAssignationRequest request) {
        Passenger passenger = new SeatAssignationRequestAssembler().getDomainPassenger(request);
        Flight flight = repository.getFlight(passenger.getFlightNumber(), passenger.getFlightDate());
        SeatAssignationStrategy.assignMode assignMode = new SeatAssignationRequestAssembler().getMode(request);
        SeatAssignationStrategy strategy = new SeatAssignationStrategyFactory().getStrategy(assignMode);

        Seat seat = flight.assignSeat(strategy);
        seat.take();
        
        TakenSeatAssembler assembler = new TakenSeatAssembler();
        TakenSeatDTO takenSeatDTO = assembler.fromDomain(seat);

        String seatAssignationsIdString = String.valueOf(new Random().nextInt());
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(seatAssignationsIdString).build();
        return Response.created(uri)
                .entity(takenSeatDTO)
                .build();
    }
}
