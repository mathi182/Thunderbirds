package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.boarding.Passenger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

public class ReservationTest {

    private int RESERVATION_NUMBER = 37353;
    private String RESERVATION_DATE = "2016-01-31";
    private String RESERVATION_CONFIRMATION = "A3833";
    private String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private String FLIGHT_NUMBER = "AC1765";
    private String FLIGHT_DATE = "2016-10-30";
    private ArrayList<Passenger> PASSENGERS = new ArrayList<>();
    private Reservation newReservation;

    @Before
    public void newReservation() {
        newReservation = new Reservation(RESERVATION_NUMBER,
                RESERVATION_DATE,
                RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER,
                FLIGHT_DATE,
                PAYMENT_LOCATION,
                PASSENGERS);
    }

    @Test
    public void whenCreatingAReservation_shouldReservationBeValid() {
        boolean isNewReservationValid = newReservation.isValid();

        assertTrue(isNewReservationValid);
    }


}
