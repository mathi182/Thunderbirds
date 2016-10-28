package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.EventsResource;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;

public class EventsResourceTest {
    private static final int RESERVATION_NUMBER = 37353;
    private static final String RESERVATION_DATE = "2016-01-31";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final String FLIGHT_NUMBER = "AC1765";
    private static final String FLIGHT_DATE = "2016-10-30";
    private static final ArrayList<Passenger> PASSENGERS = new ArrayList<>();
    private static final String RESERVATION_CREATED_URI = "/reservations/" + RESERVATION_NUMBER;

    private EventsResource eventsResource;
    private Reservation validReservation;

    @Before
    public void setUp() {
        eventsResource = new EventsResource();

        validReservation = new Reservation(RESERVATION_NUMBER,
                RESERVATION_DATE,
                RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER,
                FLIGHT_DATE,
                PAYMENT_LOCATION,
                PASSENGERS);
    }

    @After
    public void resetReservationStore() {
        Reservation.resetReservationStore();
    }

    @Test
    public void givenValidReservation_whenCreatingReservation_shouldCreateNewReservation() {
        Response responseActual = eventsResource.createReservation(validReservation);

        String locationActual = responseActual.getLocation().toString();
        int statusActual = responseActual.getStatus();
        assertEquals(RESERVATION_CREATED_URI, locationActual);
        assertEquals(CREATED.getStatusCode(), statusActual);
    }

    @Test
    public void givenValidReservation_whenCreatingReservation_shouldBeSavedInTheStore() {
        eventsResource.createReservation(validReservation);

        Reservation savedReservation = Reservation.findByReservationNumber(RESERVATION_NUMBER);
        assertEquals(validReservation.getReservationNumber(), savedReservation.getReservationNumber());
    }
}