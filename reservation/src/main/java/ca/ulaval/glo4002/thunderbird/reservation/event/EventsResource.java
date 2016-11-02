package ca.ulaval.glo4002.thunderbird.reservation.event;

import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationsResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Path(EventsResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class EventsResource {

    public static final String PATH = "/events/";
    public static final String RESERVATION_CREATED = "reservation-created";

    @POST
    @Path(RESERVATION_CREATED)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(Reservation reservation) {
        reservation.save();
        String reservationNumber = Integer.toString(reservation.getReservationNumber());

        UriBuilder uriBuilder = UriBuilder.fromUri(ReservationsResource.PATH);
        URI uri = uriBuilder.path(reservationNumber).build();
        return Response.created(uri).build();
    }
}
