package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.exceptions.LuggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.RegisterLuggageResponseBody;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.OK;

@Provider
public class LuggageWeightInvalidExceptionMapper implements ExceptionMapper<LuggageWeightInvalidException> {
    @Override
    public Response toResponse(LuggageWeightInvalidException e) {
        RegisterLuggageResponseBody registerLuggageResponseBody = new RegisterLuggageResponseBody(false, "weight " +
                "invalid");
        return Response.status(OK.getCode()).entity(registerLuggageResponseBody).build();
    }
}
