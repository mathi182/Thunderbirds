package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignations;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/seat-assignations")
@Produces(MediaType.APPLICATION_JSON)
public class SeatAssignationsResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignSeat(SeatAssignations seatAssignations) {
        seatAssignations.assignSeat();
        return Response.created(URI.create("/seat-assignations/" + seatAssignations.getId())).entity(seatAssignations).build();
    }
}
