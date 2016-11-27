package ca.ulaval.glo4002.thunderbird.reservation.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.BAD_REQUEST;

@Provider
public class InvalidResourceExceptionMapper implements ExceptionMapper<InvalidResourceException> {
    @Override
    public Response toResponse(InvalidResourceException exception) {
        return Response.status(BAD_REQUEST.getCode()).entity(exception.getMessage()).build();
    }
}