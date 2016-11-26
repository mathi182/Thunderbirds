package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageResponseBody;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static org.eclipse.jetty.http.HttpStatus.Code.OK;

public class BaggageAmountUnauthorizedExceptionMapper implements ExceptionMapper<BaggageAmountUnauthorizedException> {
    @Override
    public Response toResponse(BaggageAmountUnauthorizedException e) {
        RegisterBaggageResponseBody registerBaggageResponseBody = new RegisterBaggageResponseBody(false, "baggages amount authorized is reached");
        return Response.status(OK.getCode()).entity(registerBaggageResponseBody).build();
    }
}
