package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.NoMoreSpaceInFlightException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.BAD_REQUEST;

@Provider
public class NoMoreSpaceInFlightExceptionMapper implements ExceptionMapper<NoMoreSpaceInFlightException> {
    @Override
    public Response toResponse(NoMoreSpaceInFlightException noMoreSpaceInFlightException) {
        return Response.status(BAD_REQUEST.getCode()).build();
    }
}
