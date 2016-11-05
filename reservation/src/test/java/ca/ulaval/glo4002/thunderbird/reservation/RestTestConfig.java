package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public final class RestTestConfig {

    public final static int EXISTENT_RESERVATION_NUMBER = DevContext.EXISTENT_RESERVATION_NUMBER;
    public final static UUID EXISTENT_PASSENGER_HASH = DevContext.EXISTENT_PASSENGER_HASH;

    public static String buildUrl(String path) {
        return String.format("http://localhost:%d%s", RestTestSuite.TEST_SERVER_PORT, path);
    }

    public static RequestSpecification givenBaseRequest() {
        return given()
                .accept(ContentType.JSON)
                .port(RestTestSuite.TEST_SERVER_PORT)
                .contentType(ContentType.JSON);
    }
}