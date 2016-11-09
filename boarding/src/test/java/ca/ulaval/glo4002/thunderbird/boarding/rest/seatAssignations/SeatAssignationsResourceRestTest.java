package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.SeatNotAvailableException;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequest;
import static junit.framework.TestCase.assertNotNull;
import static org.eclipse.jetty.http.HttpStatus.Code.*;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class SeatAssignationsResourceRestTest {
    private static final UUID EXISTENT_PASSENGER_HASH = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    private static final UUID NON_EXISTENT_PASSENGER_HASH = UUID.randomUUID();
    private static final String VALID_MODE = "RANDOM";
    private static final String INVALID_MODE = "INVALID";

    SeatAssignationRequest seatAssignationRequest;

    @Before
    public void setUp() {
        seatAssignationRequest = new SeatAssignationRequest();
    }

    @Ignore
    @Test
    public void givenAValidPassengerHashAndAValidMode_whenAssigningSeat_shouldAssignSeat() {
        //Should use passengerHash based on Passenger from boarding created by DevContext of boarding -> we still call it from Reservation here
        seatAssignationRequest.passengerHash = EXISTENT_PASSENGER_HASH;
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

        String seat = response.path("seat");
        assertNotNull(seat);
        assertNotEquals("", seat);
    }

    @Ignore
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

    @Ignore
    @Test
    public void givenAValidPassengerHashAndInvalidMode_whenAssigningSeat_shouldReturnBadRequest() {
        seatAssignationRequest.passengerHash = EXISTENT_PASSENGER_HASH;
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
        seatAssignationRequest.passengerHash = EXISTENT_PASSENGER_HASH;
        seatAssignationRequest.mode = VALID_MODE;
        Flight flightMock = mock(Flight.class);
        Mockito.when(flightMock.assignSeat(any(SeatAssignationStrategy.class))).thenThrow(new SeatNotAvailableException(""));

        givenBaseRequest()
                .body(seatAssignationRequest)
                .when()
                .post(SeatAssignationsResource.PATH)
                .then()
                .statusCode(BAD_REQUEST.getCode());
    }
}