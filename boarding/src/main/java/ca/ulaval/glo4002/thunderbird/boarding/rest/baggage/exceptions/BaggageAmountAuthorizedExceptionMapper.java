package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountAuthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageResponseBody;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static org.eclipse.jetty.http.HttpStatus.Code.OK;

public class BaggageAmountAuthorizedExceptionMapper implements ExceptionMapper<BaggageAmountAuthorizedException> {
    @Override
    public Response toResponse(BaggageAmountAuthorizedException e) {
        RegisterBaggageResponseBody registerBaggageResponseBody = new RegisterBaggageResponseBody(false, "baggages amount authorized is reached");
        return Response.status(OK.getCode()).entity(registerBaggageResponseBody).build();
    }
}
