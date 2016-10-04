package ca.ulaval.glo4002.thunderbird.reservation;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Myriam Moar on 2016-09-25.
 */
@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationRessources {
    @GET
    @Path("/{reservation_number}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getReservation(@PathParam("reservation_number") int reservationNumber) {
        throw new NotImplementedException();
    }
}
