package ca.ulaval.glo4002.thunderbird.reservation.rest.passenger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class PassengerNotFoundException extends WebApplicationException {
    private String passengerHash;

    public PassengerNotFoundException(String passengerHash) {
        this.passengerHash = passengerHash;
    }

    @Override
    public Response getResponse() {
        return Response.status(Status.NOT_FOUND).build();
    }
}
