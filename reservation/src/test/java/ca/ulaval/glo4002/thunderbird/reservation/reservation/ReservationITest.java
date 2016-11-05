package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReservationITest {

    private static final int NON_EXISTENT_RESERVATION_NUMBER = 12345;

    private static final int RESERVATION_NUMBER = 37353;
    private static final String RESERVATION_DATE = "2016-01-31";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final String FLIGHT_NUMBER = "AC1765";
    private static final String FLIGHT_DATE = "2016-09-06T13:00:00Z";

    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;

    private Reservation reservation;
    private Passenger passenger;

    @Before
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("reservation-test");
        EntityManagerProvider.setEntityManager(entityManagerFactory.createEntityManager());

        passenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        ArrayList<Passenger> passengers = new ArrayList<>(Arrays.asList(passenger));

        reservation = new Reservation(RESERVATION_NUMBER,
                RESERVATION_DATE,
                RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER,
                FLIGHT_DATE,
                PAYMENT_LOCATION,
                passengers);
    }

    @Test
    public void whenAReservationIsSaved_shouldBeAbleToRetrieve() {
        reservation.save();

        Reservation retrievedReservation = Reservation.findByReservationNumber(RESERVATION_NUMBER);
        List<Passenger> retrievedPassengers = retrievedReservation.getPassengers();
        assertEquals(reservation.getReservationNumber(), retrievedReservation.getReservationNumber());
        assertEquals(1, retrievedPassengers.size());
        assertEquals(passenger.getId(), retrievedPassengers.get(0).getId());
    }

    @Test(expected = ReservationNotFoundException.class)
    public void whenFinding_ThrowIfNotFound() {
        Reservation.findByReservationNumber(NON_EXISTENT_RESERVATION_NUMBER);
    }
}