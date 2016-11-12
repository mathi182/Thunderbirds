package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.LuggageAmountAuthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.RegisterLuggageResponseBody;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static org.eclipse.jetty.http.HttpStatus.Code.OK;

public class BaggageAmountAuthorizedExceptionMapper implements ExceptionMapper<LuggageAmountAuthorizedException> {
    @Override
    public Response toResponse(LuggageAmountAuthorizedException e) {
        RegisterLuggageResponseBody registerLuggageResponseBody = new RegisterLuggageResponseBody(false, "baggages amount authorized is reached");
        return Response.status(OK.getCode()).entity(registerLuggageResponseBody).build();
    }
}
