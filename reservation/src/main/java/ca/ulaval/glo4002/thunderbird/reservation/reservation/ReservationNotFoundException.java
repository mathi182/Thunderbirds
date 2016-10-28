package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ReservationNotFoundException extends WebApplicationException {
    private int reservationNumber;

    public ReservationNotFoundException(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    @Override
    public Response getResponse() {
        return Response.status(Status.NOT_FOUND).build();
    }
}
