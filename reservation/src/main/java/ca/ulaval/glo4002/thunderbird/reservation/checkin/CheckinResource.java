package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.Instant;

@Path(CheckinResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class CheckinResource {

    public static final String PATH = "/checkins/";

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkin(Checkin checkin) {
        checkin.completePassengerCheckin(Instant.now());
        checkin.save();
        String checkinId = checkin.getCheckinId();

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(checkinId).build();
        return Response.created(uri).build();
    }
}
