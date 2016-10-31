package ca.ulaval.glo4002.thunderbird.boarding.rest;

import ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class RestTestConfig {

    public final static int EXISTANT_RESERVATION_NUMBER = DevContext.EXISTANT_RESERVATION_NUMBER;
    public final static String EXISTANT_PASSENGER_HASH = DevContext.EXISTANT_PASSENGER_HASH;

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
