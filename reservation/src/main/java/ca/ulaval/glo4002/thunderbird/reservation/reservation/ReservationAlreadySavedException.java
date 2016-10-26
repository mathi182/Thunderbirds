package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ReservationAlreadySavedException extends WebApplicationException {
    private int reservationNumber;

    public ReservationAlreadySavedException(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    @Override
    public Response getResponse() {
        return Response.status(Status.BAD_REQUEST).build();
    }
}
