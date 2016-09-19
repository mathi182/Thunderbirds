package ca.ulaval.glo4002.thunderbird.reservation;

import javax.swing.text.html.parser.Entity;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;

import static ca.ulaval.glo4002.thunderbird.reservation.Util.isStringNullOrEmpty;
import static sun.security.timestamp.TSResponse.BAD_REQUEST;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventsRessource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(Reservation reservation) {
        if(isReservationInvalid(reservation)) {
            return Response.status(BAD_REQUEST).build();
        }
        return Response.created(URI.create("/reservations/" + reservation.getReservationNumber())).build();
    }

    private boolean isReservationInvalid(Reservation reservation) {
        if (isStringNullOrEmpty(reservation.getFlightNumber())
                || isStringNullOrEmpty(reservation.getFlightDate())
                || reservation.getReservationNumber() == 0) {
            return true;
        }
        return false;
    }
}
