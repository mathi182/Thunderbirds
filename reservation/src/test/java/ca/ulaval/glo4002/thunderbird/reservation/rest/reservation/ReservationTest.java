package ca.ulaval.glo4002.thunderbird.reservation.rest.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.rest.passenger.Passenger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class ReservationTest {
    private static final int NON_EXISTENT_RESERVATION_NUMBER = 12345;
    private static final String RESERVATION_DATE = "2016-01-31";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final String FLIGHT_NUMBER = "AC1765";
    private static final String FLIGHT_DATE = "2016-10-30";
    private Passenger mockPassenger = mock(Passenger.class);
    private int RESERVATION_NUMBER = 37353;
    private ArrayList<Passenger> PASSENGERS = new ArrayList<Passenger>() {{
        add(mockPassenger);
    }};
    private Reservation newReservation;

    @Before
    public void newReservation() {
        willReturn(RESERVATION_NUMBER).given(mockPassenger).getReservationNumber();

        newReservation = new Reservation(RESERVATION_NUMBER,
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
    public void whenCreatingAReservation_shouldReservationBeValid() {
        boolean isNewReservationValid = newReservation.isValid();

        assertTrue(isNewReservationValid);
    }

    @Test
    public void givenNewReservation_whenGettingReservationNumber_shouldReturnCorrectReservationNumber() {
        int actualValue = newReservation.getReservationNumber();

        int expectedValue = RESERVATION_NUMBER;
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenSavingReservation_shouldSuccessfullySave() {
        newReservation.save();
        Reservation reservationFound = Reservation.findByReservationNumber(newReservation.getReservationNumber());
        int actualReservationNumber = reservationFound.getReservationNumber();

        int expectedReservationNumber = newReservation.getReservationNumber();
        assertEquals(expectedReservationNumber, actualReservationNumber);
    }

    @Test
    public void whenChekingIfReservationExists_ifNotExists_shouldReturnFalse() {
        boolean reservationExists = Reservation.reservationExists(NON_EXISTENT_RESERVATION_NUMBER);

        assertFalse(reservationExists);
    }

    @Test
    public void whenCheckingIfReservationExists_ifExists_shouldReturnTrue() {
        newReservation.save();

        boolean reservationExists = Reservation.reservationExists(RESERVATION_NUMBER);

        assertTrue(reservationExists);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void whenFinding_ThrowIfNotFound() {
        Reservation.findByReservationNumber(NON_EXISTENT_RESERVATION_NUMBER);
    }

    @Test(expected = ReservationAlreadySavedException.class)
    public void whenSavingAnExistingReservation_shouldFail() {
        newReservation.save();

        newReservation.save();
    }
}
