package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.boarding.Passenger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ReservationsResourceTest {

    private static final int VALID_RESERVATION_NUMBER = 37353;
    private int NON_EXISTENT_RESERVATION_NUMBER = 12345;
    private int RESERVATION_NUMBER = 37353;
    private String RESERVATION_DATE = "2016-01-31";
    private String RESERVATION_CONFIRMATION = "A3833";
    private String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private String FLIGHT_NUMBER = "AC1765";
    private String FLIGHT_DATE = "2016-10-30";
    private ArrayList<Passenger> PASSENGERS = new ArrayList<Passenger>() {{
        add(mock(Passenger.class));
    }};

    private Reservation newReservation;
    @InjectMocks
    private ReservationsResource reservationsResource;


    @Before
    public void newReservationStored() {
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

        Reservation reservationActual = (Reservation) responseActual.getEntity();
        Reservation reservationExpected = (Reservation) responseExpected.getEntity();

        assertEquals(statusExpected, statusActual);
        assertEquals(reservationExpected.reservation_number, reservationActual.reservation_number);
    }

}
