package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;
import static javax.ws.rs.core.Response.status;

public class SeatNotAvailableException extends WebApplicationException {
    private String flightNumber;
    private String flightDate;
    private String seat;

    public SeatNotAvailableException(String flightNumber, String flightDate, String seat) {
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.seat = seat;
    }

    @Override
    public Response getResponse() {
        return status(Status.BAD_REQUEST).build();
    }
}
