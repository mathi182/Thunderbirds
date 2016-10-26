package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class CheckinAlreadySavedException extends WebApplicationException {
    private String checkinId;

    public CheckinAlreadySavedException(String checkinId) {
        this.checkinId = checkinId;
    }

    @Override
    public Response getResponse() {
        return Response.status(Status.BAD_REQUEST).build();
    }
}
