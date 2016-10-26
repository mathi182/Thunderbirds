package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class MissingCheckinInformationException extends WebApplicationException {
    private String field;

    public MissingCheckinInformationException(String field) {
        this.field = field;
    }

    @Override
    public Response getResponse() {
        return Response.status(Status.BAD_REQUEST).build();
    }
}
