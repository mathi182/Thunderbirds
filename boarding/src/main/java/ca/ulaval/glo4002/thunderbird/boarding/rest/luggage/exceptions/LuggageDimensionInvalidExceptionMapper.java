package ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.exceptions.LuggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.luggage.RegisterLuggageResponseBody;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.eclipse.jetty.http.HttpStatus.Code.OK;

@Provider
public class LuggageDimensionInvalidExceptionMapper implements ExceptionMapper<LuggageDimensionInvalidException> {
    @Override
    public Response toResponse(LuggageDimensionInvalidException e) {
        RegisterLuggageResponseBody registerLuggageResponseBody = new RegisterLuggageResponseBody(false, "dimensions " +
                "invalid");
        return Response.status(OK.getCode()).entity(registerLuggageResponseBody).build();
    }
}
