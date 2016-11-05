package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import org.junit.Test;

import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.EXISTENT_PASSENGER_HASH;
import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class CheckinResourceRestTest {
    private static final String AGENT_CHECKIN = "AGENT_ID";
    private static final UUID NON_EXISTENT_PASSENGER_HASH = new UUID(0L, 0L);

    @Test
    public void givenAnExistentPassenger_whenCheckin_shouldReturnCreated() {
        Checkin checkinExistentPassenger = new Checkin(EXISTENT_PASSENGER_HASH, AGENT_CHECKIN);

        givenBaseRequest()
                .body(checkinExistentPassenger)
                .when()
                .post(CheckinResource.PATH)
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    @Test
    public void givenAnNonExistentPassenger_whenCheckin_shouldReturnNotFound() {
        Checkin checkinNonExistentPassenger = new Checkin(NON_EXISTENT_PASSENGER_HASH, AGENT_CHECKIN);

        givenBaseRequest()
                .body(checkinNonExistentPassenger)
                .when()
                .post(CheckinResource.PATH)
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }

}