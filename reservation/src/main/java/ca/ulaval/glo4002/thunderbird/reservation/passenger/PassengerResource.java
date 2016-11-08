package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

@Path(PassengerResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class PassengerResource {

    public static final String PATH = "/passengers/";

    @GET
    @Path("{passenger_hash}")
    public Passenger fetchPassenger(@PathParam("passenger_hash") UUID passenger_hash){
        Passenger passenger = getPassenger(passenger_hash);
        return passenger;
    }

    //TODO find a way to make this method private even when we test it
    public Passenger getPassenger(UUID passenger_hash){
        return Passenger.findByPassengerHash(passenger_hash);
    }
}
