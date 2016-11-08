package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.EXISTENT_PASSENGER_HASH;
import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.*;

public class CheckinResourceRestTest {
    private static final String AGENT_CHECKIN = "AGENT_ID";
    private static final UUID NON_EXISTENT_PASSENGER_HASH = new UUID(0L, 0L);

    @Test
    public void givenAnExistentPassenger_whenCheckin_shouldReturnCreated() {
        Map<String, Object> requestBody = getJsonAsMap(EXISTENT_PASSENGER_HASH, AGENT_CHECKIN);

        givenBaseRequest()
                .body(requestBody)
                .when()
                .post(CheckinResource.PATH)
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    @Test
    public void givenAnNonExistentPassenger_whenCheckin_shouldReturnNotFound() {
        Map<String, Object> requestBody = getJsonAsMap(NON_EXISTENT_PASSENGER_HASH, AGENT_CHECKIN);

        givenBaseRequest()
                .body(requestBody)
                .when()
                .post(CheckinResource.PATH)
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenNoPassengerHash_whenCheckin_shouldReturnBadRequest() {
        Map<String, Object> requestBody = getJsonAsMap(null, AGENT_CHECKIN);

        givenBaseRequest()
                .body(requestBody)
                .when()
                .post(CheckinResource.PATH)
                .then()
                .statusCode(BAD_REQUEST.getStatusCode());
    }

    private Map<String, Object> getJsonAsMap(UUID passengerHash, String by) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        
        jsonAsMap.put("passenger_hash", passengerHash);
        jsonAsMap.put("by", by);

        return jsonAsMap;
    }
}