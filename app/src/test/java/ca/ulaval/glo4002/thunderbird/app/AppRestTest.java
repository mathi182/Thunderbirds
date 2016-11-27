package ca.ulaval.glo4002.thunderbird.app;

import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.BaggageResource;
import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationRequest;
import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationsResource;
import ca.ulaval.glo4002.thunderbird.reservation.checkin.CheckinResource;
import ca.ulaval.glo4002.thunderbird.reservation.event.EventsResource;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationsResource;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class AppRestTest {
    private static final String VALID_MODE = "random";
    private static final UUID NONEXISTENT_PASSENGER_HASH = UUID.randomUUID();
    private final int INVALID_RESERVATION_NUMBER = 14253;
    private final int BOARDING_PORT = 8888;
    private final int RESERVATION_PORT = 8787;

    @Test
    public void givenAnInvalidReservation_whenCreatingReservation_shouldReturnBadRequest(){
        given()
            .accept(ContentType.JSON)
            .port(RESERVATION_PORT)
            .contentType(ContentType.JSON)
            .body("")
        .when()
            .post(EventsResource.PATH + EventsResource.RESERVATION_CREATED)
        .then()
            .assertThat()
            .statusCode(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenANonexistentReservationNumber_whenFetchingReservation_shouldReturnNotFound(){
        given()
            .accept(ContentType.JSON)
            .port(RESERVATION_PORT)
            .contentType(ContentType.JSON)
        .when()
            .get(ReservationsResource.PATH + INVALID_RESERVATION_NUMBER)
        .then()
            .assertThat()
            .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenAnInvalidCheckIn_whenCheckingIn_shouldReturnBadRequest(){
        given()
            .accept(ContentType.JSON)
            .port(RESERVATION_PORT)
            .contentType(ContentType.JSON)
            .body("")
        .when()
            .post(CheckinResource.PATH)
        .then()
            .assertThat()
            .statusCode(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void givenANonexistentPassengerHash_whenAssigningSeat_shouldReturnNotFound(){
        SeatAssignationRequest seatAssignationRequest = new SeatAssignationRequest();
        seatAssignationRequest.passengerHash = NONEXISTENT_PASSENGER_HASH;
        seatAssignationRequest.mode = VALID_MODE;

        given()
            .accept(ContentType.JSON)
            .port(BOARDING_PORT)
            .contentType(ContentType.JSON)
            .body(seatAssignationRequest)
        .when()
            .post(SeatAssignationsResource.PATH)
        .then()
            .assertThat()
            .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenANonexistentPassengerHash_whenRegisteringABaggage_shouldReturnNotFound(){
        given()
            .accept(ContentType.JSON)
            .port(BOARDING_PORT)
            .contentType(ContentType.JSON)
            .body("")
        .when()
            .post(BaggageResource.PATH + NONEXISTENT_PASSENGER_HASH + BaggageResource.BAGGAGES)
        .then()
            .assertThat()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}