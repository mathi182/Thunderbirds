package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignations;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;

@Path(SeatAssignationsResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class SeatAssignationsResource {

    public static final String PATH = "/seat-assignations/";

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignSeat(SeatAssignations seatAssignations) {
        seatAssignations.assignSeat();
        String seatAssignationsIdString = Integer.toString(seatAssignations.getId());

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(seatAssignationsIdString).build();
        return Response.created(uri)
                .entity(seatAssignations)
                .build();
    }
}
