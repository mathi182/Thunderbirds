package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path(PassengersResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class PassengersResource {
    public static final String PATH = "/passengers/";

    @GET
    @Path("{passenger_hash}")
    public PassengerDTO fetchPassenger(@PathParam("passenger_hash") UUID passenger_hash) {
        Passenger passenger = getPassenger(passenger_hash);
        PassengerAssembler passengerAssembler = new PassengerAssembler();

        return passengerAssembler.toDTO(passenger);
    }

    //TODO find a way to make this method private even when we test it
    private Passenger getPassenger(UUID passenger_hash) {
        return Passenger.findByPassengerHash(passenger_hash);
    }
}