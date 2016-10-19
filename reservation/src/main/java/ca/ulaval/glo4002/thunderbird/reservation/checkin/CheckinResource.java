package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.MissingInfoException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/checkins")
@Produces(MediaType.APPLICATION_JSON)
public class CheckinResource {
    private static final String PASSENGER_RESERVATION_NOT_FOUND_MESSAGE = "passenger reservation not found";
    private static final String PASSENGER_RESERVATION_NOT_VALID =
            "CheckinAgentId couldn't be completed. Verify if the reservation is complete, if time is correct or if the passenger hasn't been checked in before";


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkin(Checkin checkin) {
        if (!checkin.passengerExist()) {
            return Response.status(NOT_FOUND).entity(Entity.json(PASSENGER_RESERVATION_NOT_FOUND_MESSAGE)).build();
        }

        if (!checkin.isValid()) {
            return Response.status(BAD_REQUEST).entity(Entity.json(PASSENGER_RESERVATION_NOT_VALID)).build();
        }

        String checkinId = checkin.getCheckinId();
        checkin.save();

        checkin.completePassengerCheckin();
        return Response.created(URI.create("/checkins/" + checkinId)).build();
    }
}
