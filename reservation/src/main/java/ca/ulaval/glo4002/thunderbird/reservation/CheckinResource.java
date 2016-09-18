package ca.ulaval.glo4002.thunderbird.reservation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static ca.ulaval.glo4002.thunderbird.reservation.Util.empty;
import static ca.ulaval.glo4002.thunderbird.reservation.Util.isNullOrEmpty;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * Created by alexandre on 2016-09-17.
 */
@Path("/checkins")
@Produces(MediaType.APPLICATION_JSON)
public class CheckinResource {
    private ReservationRepository reservationRepository;
    private final String FIELDS_REQUIRED_MESSAGE = "by and passengerHas fields are required";
    private final String PASSENGER_RESERVATION_NOT_FOUND_MESSAGE = "passenger reservation not found";

    public CheckinResource() {
        this.reservationRepository = new ReservationRepository();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkin(Checkin checkin) {
        if (isNullOrEmpty(checkin.agentId) || isNullOrEmpty(checkin.passengerHash)) {
            return Response.status(BAD_REQUEST).entity(Entity.json(FIELDS_REQUIRED_MESSAGE)).build();
        }

        String passengerHash = checkin.passengerHash;
        if (!reservationRepository.passengerHasReservation(passengerHash)) {
            return Response.status(NOT_FOUND).entity(Entity.json(PASSENGER_RESERVATION_NOT_FOUND_MESSAGE)).build();
        }

        String checkinId = "checkinId";
        return Response.created(URI.create("/checkins/" + checkinId)).build();
    }
}
