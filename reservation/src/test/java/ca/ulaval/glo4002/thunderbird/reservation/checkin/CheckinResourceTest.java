package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import org.junit.Before;
import org.junit.Test;
import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.EXISTANT_PASSENGER_HASH;
import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class CheckinResourceTest {
    public static final String CHECKIN_ID = "checkinId";
    private static final String AGENT_CHECKIN = "AGENT_ID";
    private static final String INEXISTANT_PASSENGER_HASH = "HASH";
    private Checkin checkinExistantPassenger;

    @Before
    public void setUp() throws Exception {
        checkinExistantPassenger = new Checkin(EXISTANT_PASSENGER_HASH, AGENT_CHECKIN);
    }

    @Test
    public void givenAnExistingPassenger_whenCheckin_shouldReturnCreated() {
        givenBaseRequest().body(checkinExistantPassenger)
                .when()
                .post(CheckinResource.PATH)
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    @Test
    public void givenAnInexistingPassenger_whenCheckin_shouldReturnNotFound() {
        Checkin checkinInexistantPassenger = new Checkin(INEXISTANT_PASSENGER_HASH, AGENT_CHECKIN);
        givenBaseRequest().body(checkinInexistantPassenger)
                .when()
                .post(CheckinResource.PATH)
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }

    // TODO :
//    @Test
//    public void whenCheckin_shouldCompletePassengerCheckin() {
//        checkinResource.checkin(checkin);
//
//        verify(checkin).completePassengerCheckin(any());
//        verify(checkin).save();
//    }

    // TODO:
//    @Test
//    public void whenCheckin_locationShouldBeAdequate() {
//        String expectedLocation = "/checkins/" + CHECKIN_ID;
//
//        Response response =  givenBaseRequest()
//                .body(checkinExistantPassenger)
//                .post(CheckinResource.PATH)
//                .then()
//    }
}