package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import org.junit.Test;

import static ca.ulaval.glo4002.thunderbird.reservation.TestConfig.EXISTANT_RESERVATION_NUMBER;
import static ca.ulaval.glo4002.thunderbird.reservation.TestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.equalTo;

public class ReservationsResourceRestTest {

    private static final int INEXISTANT_RESERVATION_NUMBER = 666;

    @Test
    public void givenAnExistingReservationNumber_whenFetchingReservation_shouldReturnReservation() {
        givenBaseRequest()
                .when()
                    .get("/reservations/{reservation_number}", EXISTANT_RESERVATION_NUMBER)
                .then()
                    .statusCode(OK.getStatusCode())
                    .body("reservation_number", equalTo(EXISTANT_RESERVATION_NUMBER));
    }

    @Test
    public void givenAnInexistingReservationNumber_whenFetchingReservation_shouldReturnNotFound() {
        givenBaseRequest()
                .when()
                    .get("/reservations/{reservation_number}", INEXISTANT_RESERVATION_NUMBER)
                .then()
                    .statusCode(NOT_FOUND.getStatusCode());
    }

}