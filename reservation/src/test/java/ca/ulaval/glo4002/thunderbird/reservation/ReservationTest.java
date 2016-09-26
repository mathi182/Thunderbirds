package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.boarding.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationAlreadySavedException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ReservationTest {
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
    public void givenNewReservation_whenGetPassengers_shouldHavePassengersWithTheReservationNumber() throws Exception {
        ArrayList<Passenger> passengers = newReservation.passengers;
        passengers.forEach(passenger -> assertEquals(RESERVATION_NUMBER, passenger.reservationNumber));
    }

    @Test
    public void whenSaving_ActuallySave() {
        newReservation.save();

        Reservation reservationFound = Reservation.findByReservationNumber(newReservation.reservationNumber);

        assertEquals(reservationFound.reservationNumber, newReservation.reservationNumber);
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
