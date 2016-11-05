package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageResponseBody;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.OK;

@Provider
public class BaggageDimensionInvalidExceptionMapper implements ExceptionMapper<BaggageDimensionInvalidException> {
    @Override
    public Response toResponse(BaggageDimensionInvalidException e) {
        RegisterBaggageResponseBody registerBaggageResponseBody = new RegisterBaggageResponseBody(false, "dimensions refused");
        return Response.status(OK.getCode()).entity(registerBaggageResponseBody).build();
    }
}
