package ca.ulaval.glo4002.thunderbird.reservation.rest.event;

import ca.ulaval.glo4002.thunderbird.reservation.rest.reservation.Reservation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventsResource {

    @POST
    @Path("/reservation-created")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(Reservation reservation) {
        reservation.save();
        return Response.created(URI.create("/reservations/" + reservation.getReservationNumber())).build();
    }

}
