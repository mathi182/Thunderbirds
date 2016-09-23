package ca.ulaval.glo4002.thunderbird.reservation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ReservationsResourceTest {

    private ReservationsResource RESERVATIONS_RESSOURCE;
    private int VALID_RESERVATION_NUMBER;

    @Test
    public void givenAValidReservationNumber_whenFetchingReservation_shouldReturnReservation() {
        Response responseActual = RESERVATIONS_RESSOURCE.fetchReservation(VALID_RESERVATION_NUMBER);
        int statusActual = responseActual.getStatus();

        int statusExpected = OK.getStatusCode();

        assertEquals(statusExpected, statusActual);
    }
}
