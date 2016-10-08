package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventsResource {

    @POST
    @Path("/reservation-created")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(Reservation reservation) {
        if (reservation.isValid()) {
            reservation.save();
            return Response.created(URI.create("/reservations/" + reservation.getReservationNumber())).build();
        }
        return Response.status(BAD_REQUEST).build();
    }

}