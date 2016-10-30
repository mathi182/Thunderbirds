package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import org.junit.Test;

import static ca.ulaval.glo4002.thunderbird.reservation.TestConfig.EXISTANT_PASSENGER_HASH;
import static ca.ulaval.glo4002.thunderbird.reservation.TestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class CheckinResourceRestTest {

    private static final String AGENT_CHECKIN = "AGENT_ID";
    private static final String INEXISTANT_RESERVATION_NUMBER = "HASH";

    @Test
    public void givenAnExistingPassenger_whenCheckin_shouldCreateCheckin() {
        givenBaseRequest().body(new Checkin(EXISTANT_PASSENGER_HASH, AGENT_CHECKIN))
                .when()
                    .post("/checkins")
                .then()
                    .statusCode(CREATED.getStatusCode());
    }

    @Test
    public void givenAnInexistingPassenger_whenCheckin_shouldReturnNotFound() {
        givenBaseRequest().body(new Checkin(INEXISTANT_RESERVATION_NUMBER, AGENT_CHECKIN))
                .when()
                    .post("/checkins")
                .then()
                    .statusCode(NOT_FOUND.getStatusCode());
    }

}