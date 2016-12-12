package fixtures;

import ca.ulaval.glo4002.thunderbird.boarding.BoardingServer;
import ca.ulaval.glo4002.thunderbird.reservation.ReservationServer;
import io.restassured.specification.RequestSpecification;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

public class BaseRestFixture {
    protected RequestSpecification givenBoardingRequest() {
        return given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .port(BoardingServer.DEFAULT_PORT);
    }

    protected RequestSpecification givenReservationRequest() {
        return given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .port(ReservationServer.DEFAULT_PORT);
    }
}
