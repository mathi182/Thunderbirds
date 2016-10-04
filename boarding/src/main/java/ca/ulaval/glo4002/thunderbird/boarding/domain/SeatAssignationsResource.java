package ca.ulaval.glo4002.thunderbird.boarding.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/seat-assignations")
@Produces(MediaType.APPLICATION_JSON)
public class SeatAssignationsResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignSeat(SeatAssignations seatAssignations){
        //System.out.print(seatAssignations.passengerHash + seatAssignations.mode);
        SeatAssignationsResponse responseContent = new SeatAssignationsResponse("12-A");
        return Response.ok(seatAssignations,MediaType.APPLICATION_JSON).build();
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

    private class SeatAssignationsResponse {

        @JsonProperty("seat") String seat;

        SeatAssignationsResponse(String seat){
            this.seat = seat;
        }
    }
}
