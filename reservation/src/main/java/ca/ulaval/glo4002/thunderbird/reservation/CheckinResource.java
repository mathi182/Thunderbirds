package ca.ulaval.glo4002.thunderbird.reservation;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by alexandre on 2016-09-17.
 */
@Path("/checkins")
@Produces(MediaType.APPLICATION_JSON)
public class CheckinResource {
    private ReservationRepository reservationRepository;

    public CheckinResource() {
        this.reservationRepository = new ReservationRepository();
    }

    @POST
    public Response checkin(Checkin checkin) {
        if (checkin.getBy() == null || checkin.getPassenger_hash() == null) {
            return Response.status(400).entity(new StringBuilder("by and passengerHas fields are required")).build();
        }

        String passengerHash = checkin.getPassenger_hash();
        if (!reservationRepository.passengerHasReservation(passengerHash)) {
            return Response.status(404).entity(new StringBuilder("passenger reservation not found")).build();
        }

        String checkinId = "checkinId";
        return Response.created(URI.create("/checkins/" + checkinId)).build();
    }
}
