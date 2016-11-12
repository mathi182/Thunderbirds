package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;
import java.util.regex.Pattern;

import static ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext.EXISTENT_BOARDING_PASSENGER_HASH;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.buildUrl;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequest;
import static org.eclipse.jetty.http.HttpStatus.Code.*;
import static org.junit.Assert.*;

public class SeatAssignationsResourceRestTest {
    private static final UUID NON_EXISTENT_PASSENGER_HASH = UUID.randomUUID();
    private static final String VALID_MODE = "RANDOM";
    private static final String INVALID_MODE = "INVALID";

    SeatAssignationRequest seatAssignationRequest;

    @Before
    public void setUp() {
        seatAssignationRequest = new SeatAssignationRequest();
    }

    @Test
    public void givenAValidPassengerHashAndAValidMode_whenAssigningSeat_shouldAssignSeat() {
        seatAssignationRequest.passengerHash = EXISTENT_BOARDING_PASSENGER_HASH;
        seatAssignationRequest.mode = VALID_MODE;

        Response response;
        response = givenBaseRequest()
                .body(seatAssignationRequest)
                .when()
                .post(SeatAssignationsResource.PATH)
                .then()
                .statusCode(CREATED.getCode())
                .extract()
                .response();

        Boolean locationValidity = isLocationValid(response.getHeader("Location"));
        assertTrue(locationValidity);
        String seat = response.path("seat");
        assertNotNull(seat);
        assertNotEquals("", seat);
    }

    private boolean isLocationValid(String location) {
        String baseUrl = buildUrl(SeatAssignationsResource.PATH);
        baseUrl = baseUrl.replace("/", "\\/");
        Pattern pattern = Pattern.compile(baseUrl + "\\d+$");

        return pattern.matcher(location).matches();
    }

    @Test
    public void givenAnInvalidPassengerHashAndAValidMode_whenAssigningSeat_shouldReturnNotFound() {
        seatAssignationRequest.passengerHash = NON_EXISTENT_PASSENGER_HASH;
        seatAssignationRequest.mode = VALID_MODE;

        givenBaseRequest()
                .body(seatAssignationRequest)
                .when()
                .post(SeatAssignationsResource.PATH)
                .then()
                .statusCode(NOT_FOUND.getCode());
    }

    @Test
    public void givenAValidPassengerHashAndInvalidMode_whenAssigningSeat_shouldReturnBadRequest() {
        seatAssignationRequest.passengerHash = EXISTENT_BOARDING_PASSENGER_HASH;
        seatAssignationRequest.mode = INVALID_MODE;

        givenBaseRequest()
                .body(seatAssignationRequest)
                .when()
                .post(SeatAssignationsResource.PATH)
                .then()
                .statusCode(BAD_REQUEST.getCode());
    }

    @Ignore
    @Test
    public void givenNoMoreSeats_whenAssigningSeat_shouldReturnBadRequest() {
        seatAssignationRequest.passengerHash = EXISTENT_BOARDING_PASSENGER_HASH;
        seatAssignationRequest.mode = VALID_MODE;

        givenBaseRequest()
                .body(seatAssignationRequest)
                .when()
                .post(SeatAssignationsResource.PATH)
                .then()
                .statusCode(BAD_REQUEST.getCode());
    }
}