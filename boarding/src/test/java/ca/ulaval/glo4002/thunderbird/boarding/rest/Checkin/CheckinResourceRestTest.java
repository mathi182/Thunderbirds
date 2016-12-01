package ca.ulaval.glo4002.thunderbird.boarding.rest.Checkin;

import org.junit.Test;
import sun.misc.UUDecoder;

import javax.ws.rs.core.Response;
import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext.EXISTENT_BOARDING_PASSENGER;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

public class CheckinResourceRestTest {
    private static final UUID VALID_PASSENGER_HASH = EXISTENT_BOARDING_PASSENGER.getHash();
    private static final UUID INVALIDE_PASSENGER_HASH = UUID.randomUUID();

    @Test
    public void givenAValidPassengerHash_whenCheckingIn_ShouldReturnOK() {
        givenBaseRequest()
                .when().post(String.format("/passengers/%s/checkin", VALID_PASSENGER_HASH))
                .then().statusCode(OK.getStatusCode());
    }

    @Test
    public void givenAnInvalidPassengerHash_whenCheckingIn_ShouldReturnNotFound(){
        givenBaseRequest()
                .when().post(String.format("/passengers/%s/checkin", INVALIDE_PASSENGER_HASH))
                .then().statusCode(NOT_FOUND.getStatusCode());
    }
}
