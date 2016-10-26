package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/checkins")
@Produces(MediaType.APPLICATION_JSON)
public class CheckinResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkin(Checkin checkin) {
        checkin.completePassengerCheckin();
        checkin.save();

        String checkinId = checkin.getCheckinId();
        return Response.created(URI.create("/checkins/" + checkinId)).build();
    }
}
