package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageResponseBody;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.OK;

@Provider
public class BaggageWeightInvalidExceptionMapper implements ExceptionMapper<BaggageWeightInvalidException> {
    @Override
    public Response toResponse(BaggageWeightInvalidException e) {
        RegisterBaggageResponseBody registerBaggageResponseBody = new RegisterBaggageResponseBody(false, "weightInG refused");
        return Response.status(OK.getCode()).entity(registerBaggageResponseBody).build();
    }
}
