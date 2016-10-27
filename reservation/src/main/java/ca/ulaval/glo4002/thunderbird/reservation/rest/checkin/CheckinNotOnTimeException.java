package ca.ulaval.glo4002.thunderbird.reservation.rest.checkin;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class CheckinNotOnTimeException extends WebApplicationException {
    @Override
    public Response getResponse() {
        return Response.status(Status.BAD_REQUEST).build();
    }
}
