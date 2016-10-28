package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class PassengerAlreadyCheckedInException extends WebApplicationException {
    private String passengerId;

    public PassengerAlreadyCheckedInException(String passengerId) {
        this.passengerId = passengerId;
    }

    @Override
    public Response getResponse() {
        return Response.status(Status.BAD_REQUEST).build();
    }
}
