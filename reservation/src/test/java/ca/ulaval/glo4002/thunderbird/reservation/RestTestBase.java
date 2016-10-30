package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestTestBase {

    protected final static int EXISTANT_RESERVATION_NUMBER = DevContext.EXISTANT_RESERVATION_NUMBER;
    protected final static String EXISTANT_PASSENGER_HASH = DevContext.EXISTANT_PASSENGER_HASH;

    protected String buildUrl(String path) {
        return String.format("http://localhost:%d%s", RestTestSuite.TEST_SERVER_PORT, path);
    }

    protected RequestSpecification givenBaseRequest() {
        return given()
                .accept(ContentType.JSON)
                .port(RestTestSuite.TEST_SERVER_PORT)
                .contentType(ContentType.JSON);
    }

}
