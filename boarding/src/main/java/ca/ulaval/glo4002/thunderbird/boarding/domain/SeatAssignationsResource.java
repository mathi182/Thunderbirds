package ca.ulaval.glo4002.thunderbird.boarding.domain;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/seat-assignations")
@Produces(MediaType.APPLICATION_JSON)
public class SeatAssignationsResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignSeat(SeatAssignations seatAssignations) {
        try {
            
            return Response.created(URI.create("/seat-assignations/" + seatAssignations.getId())).entity(seatAssignations).build();
        } catch (PassengerNotFoundException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    private static class SeatHashMode {

        String passengerHash;
        String mode;

        @JsonCreator
        SeatHashMode(@JsonProperty("passenger_hash") String passengerHash,
                     @JsonProperty("mode") String mode) {
            this.passengerHash = passengerHash;
            this.mode = mode;
        }
    }
}
