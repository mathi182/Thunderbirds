package ca.ulaval.glo4002.thunderbird.boarding.rest.Checkin;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

@Path("/passengers/{passenger_hash}/checkin")
@Produces(MediaType.APPLICATION_JSON)
public class CheckinRessource {
    @Context
    UriInfo uriInfo;

    private PassengerRepository repository;

    public CheckinRessource(){
        this.repository = ServiceLocator.resolve(PassengerRepository.class);
    }

    public CheckinRessource(PassengerRepository repository){
        this.repository = repository;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkinPassenger(@PathParam("passenger_hash") UUID passengerHash){
        Passenger passenger = repository.getPassenger(passengerHash);
        passenger.checkIn();
        repository.savePassenger(passenger);
        return Response.ok().build();
    }
}
