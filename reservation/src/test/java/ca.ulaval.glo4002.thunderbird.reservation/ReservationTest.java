package ca.ulaval.glo4002.thunderbird.reservation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class ReservationTest {

    private int RESERVATION_NUMBER = 37353;
    private String RESERVATION_DATE = "2016-01-31";
    private String RESERVATION_CONFIRMATION = "A3833";
    private Reservation newReservation;

    @Before
    public void newReservation() {

        newReservation = new Reservation(RESERVATION_NUMBER, RESERVATION_DATE, RESERVATION_CONFIRMATION);
    }

    @Test
    public void whenCreatingReservation_ShouldHaveNumberAndDateAndConfirmation() {

        assertEquals(RESERVATION_NUMBER, newReservation.reservationNumber);
        assertEquals(RESERVATION_DATE, newReservation.reservationDate);
        assertEquals(RESERVATION_CONFIRMATION, newReservation.reservationConfirmation);
    }
}
