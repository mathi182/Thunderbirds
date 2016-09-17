package ca.ulaval.glo4002.thunderbird.reservation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventsRessource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/reservation-created")
    public void reservationCreated() {
    }
}
