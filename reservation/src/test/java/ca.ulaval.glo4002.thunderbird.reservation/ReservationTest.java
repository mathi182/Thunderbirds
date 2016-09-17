package ca.ulaval.glo4002.thunderbird.reservation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class ReservationTest {

    private int RESERVATION_NUMBER = 37353;
    private String RESERVATION_DATE = "2016-01-31";
    private String RESERVATION_CONFIRMATION = "A3833";
    private String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private Reservation newReservation;

    @Before
    public void newReservation() {

        newReservation = new Reservation(RESERVATION_NUMBER, RESERVATION_DATE, RESERVATION_CONFIRMATION, PAYMENT_LOCATION);
    }

    @Test
    public void whenCreatingReservation_ShouldHaveNumberAndDateAndConfirmation() {

        assertEquals(RESERVATION_NUMBER, newReservation.reservationNumber);
        assertEquals(RESERVATION_DATE, newReservation.reservationDate);
        assertEquals(RESERVATION_CONFIRMATION, newReservation.reservationConfirmation);
        assertEquals(PAYMENT_LOCATION, newReservation.paymentLocation);
    }
}
