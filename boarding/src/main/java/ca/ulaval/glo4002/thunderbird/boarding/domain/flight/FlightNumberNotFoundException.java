package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class FlightNumberNotFoundException extends WebApplicationException {
    private String flightNumber;

    public FlightNumberNotFoundException(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Override
    public Response getResponse() {
        return Response.status(Status.NOT_FOUND).build();
    }
}
