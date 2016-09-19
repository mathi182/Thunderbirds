package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.boarding.Passenger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;

/**
 * Created by rives on 9/18/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventsRessourceTest {
    private Reservation reservationValid;
    private Reservation reservationInvalid;
    public static final int RESERVATION_NUMBER = 37353;
    public static final String RESERVATION_DATE = "2016-01-31";
    public static final String RESERVATION_CONFIRMATION = "A3833";
    public static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    public static final String FLIGHT_NUMBER = "AC1765";
    public static final String FLIGHT_DATE = "2016-10-30";
    public static final ArrayList<Passenger> PASSENGERS = new ArrayList<>();
    public static final String RESERVATION_CREATED_URI = "/reservations/" + RESERVATION_NUMBER;

    @InjectMocks
    EventsRessource eventsRessource;

    @Before
    public void setUp() throws Exception {
        this.reservationValid = new Reservation(RESERVATION_NUMBER,
                RESERVATION_DATE,
                RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER,
                FLIGHT_DATE,
                PAYMENT_LOCATION,
                PASSENGERS);
        this.reservationInvalid = new Reservation(0, null, null, null, null, null, new ArrayList<>());
    }

    @Test
    public void givenReservationValid_whenCreatingReservation_shouldCreateNewReservation() throws Exception {
        Response responseActual = this.eventsRessource.createReservation(this.reservationValid);
        String locationActual = responseActual.getLocation().toString();
        int statusActual = responseActual.getStatus();

        String locationExpected = RESERVATION_CREATED_URI;
        int statusExpected = CREATED.getStatusCode();
        assertEquals(locationExpected, locationActual);
        assertEquals(statusExpected, statusActual);
    }

    @Test
    public void givenReservationInvalid_whenCreatingReservation_shouldNotCreateNewReservation() throws Exception {
        Response responseActual = this.eventsRessource.createReservation(this.reservationInvalid);
        int statusActual = responseActual.getStatus();

        int statusExpected = BAD_REQUEST.getStatusCode();
        assertEquals(statusExpected, statusActual);
    }
}