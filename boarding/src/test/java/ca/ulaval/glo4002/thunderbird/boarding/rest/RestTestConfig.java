package ca.ulaval.glo4002.thunderbird.boarding.rest;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class RestTestConfig {
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
