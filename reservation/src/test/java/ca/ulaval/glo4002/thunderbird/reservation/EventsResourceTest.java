package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerStorage;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.EventsResource;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventsResourceTest {
    private static final int RESERVATION_NUMBER = 37353;
    private static final String RESERVATION_DATE = "2016-01-31";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final String FLIGHT_NUMBER = "AC1765";
    private static final String FLIGHT_DATE = "2016-10-30";
    private static final ArrayList<PassengerStorage> PASSENGERS = new ArrayList<>();
    private static final String RESERVATION_CREATED_URI = "/reservations/" + RESERVATION_NUMBER;

    @InjectMocks
    EventsResource eventsResource;

    @Before
    public void resetReservationStore() {
        Reservation.resetReservationStore();
    }

    @Test
    public void givenValidReservation_whenCreatingReservation_shouldCreateNewReservation() throws Exception {
        Reservation validReservation = new Reservation(
                RESERVATION_NUMBER,
                RESERVATION_DATE,
                RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER,
                FLIGHT_DATE,
                PAYMENT_LOCATION,
                PASSENGERS);

        Response responseActual = this.eventsResource.createReservation(validReservation);

        String locationActual = responseActual.getLocation().toString();
        int statusActual = responseActual.getStatus();
        assertEquals(RESERVATION_CREATED_URI, locationActual);
        assertEquals(CREATED.getStatusCode(), statusActual);
    }

    @Test
    public void givenInvalidReservation_whenCreatingReservation_shouldNotCreateNewReservation() throws Exception {
        Reservation invalidReservation = new Reservation(0, null, null, null, null, null, new ArrayList<>());

        Response responseActual = this.eventsResource.createReservation(invalidReservation);

        int statusActual = responseActual.getStatus();
        assertEquals(BAD_REQUEST.getStatusCode(), statusActual);
    }

    @Test
    public void givenValidReservation_whenCreatingReservation_shouldBeSavedInTheStore() {
        Reservation validReservation = new Reservation(
                RESERVATION_NUMBER,
                RESERVATION_DATE,
                RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER,
                FLIGHT_DATE,
                PAYMENT_LOCATION,
                PASSENGERS);

        this.eventsResource.createReservation(validReservation);

        Reservation savedReservation = Reservation.findByReservationNumber(RESERVATION_NUMBER);
        assertEquals(validReservation.getReservationNumber(), savedReservation.getReservationNumber());
    }
}