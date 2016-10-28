package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationsResource;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ReservationsResourceTest {
    private static final int VALID_RESERVATION_NUMBER = 37353;
    private static final int RESERVATION_NUMBER = 37353;
    private static final String RESERVATION_DATE = "2016-01-31";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final String FLIGHT_NUMBER = "AC1765";
    private static final String FLIGHT_DATE = "2016-10-30";
    private ArrayList<Passenger> PASSENGERS = new ArrayList<Passenger>() {{
        add(mock(Passenger.class));
    }};

    private Reservation newReservation;
    private ReservationsResource reservationsResource;

    @Before
    public void newReservationStored() {
        reservationsResource = new ReservationsResource();

        newReservation = new Reservation(RESERVATION_NUMBER,
                RESERVATION_DATE,
                RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER,
                FLIGHT_DATE,
                PAYMENT_LOCATION,
                PASSENGERS);
        newReservation.save();
    }

    @Test
    public void givenAValidReservationNumber_whenFetchingReservation_shouldReturnReservation() {
        Response responseActual = reservationsResource.fetchReservation(VALID_RESERVATION_NUMBER);
        Response responseExpected = Response.ok(newReservation, MediaType.APPLICATION_JSON).build();
        int statusActual = 200;
        int statusExpected = OK.getStatusCode();

        int reservationActual = ((Reservation) responseActual.getEntity()).getReservationNumber();
        int reservationExpected = ((Reservation) responseExpected.getEntity()).getReservationNumber();

        assertEquals(statusExpected, statusActual);
        assertEquals(reservationExpected, reservationActual);
    }
}
