package ca.ulaval.glo4002.thunderbird.reservation.contexts;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class DevContext implements Context {
    public final static int EXISTENT_RESERVATION_NUMBER = 100;
    public static UUID EXISTENT_PASSENGER_HASH;
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final int AGE = 100;
    public static final String SEAT_CLASS = "seatClass";
    public static final String FLIGHT_NUMBER = "AC1765";
    public static final String FLIGHT_DATE_STRING = "2016-09-06T13:00:00Z";

    @Override
    public void apply() {
        fillDatabase();
    }

    private void fillDatabase() {
        Passenger passenger = createPassenger();
        Reservation reservation = createReservation(passenger);
        EXISTENT_PASSENGER_HASH = passenger.getId();

        EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);

        reservation.save();

        EntityManagerProvider.clearEntityManager();
        entityManager.close();
    }

    private Passenger createPassenger() {
        String firstName = FIRST_NAME;
        String lastName = LAST_NAME;
        int age = AGE;
        String passportNumber = "passportNumber";
        String seatClass = SEAT_CLASS;

        return new Passenger(firstName, lastName, age, passportNumber, seatClass);
    }

    private Reservation createReservation(Passenger passenger) {
        int number = EXISTENT_RESERVATION_NUMBER;
        String date = "2016-01-31";
        String confirmation = "A3833";
        String payment = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
        String flightNumber = FLIGHT_NUMBER;
        Instant flightDate = ISO_INSTANT.parse(FLIGHT_DATE_STRING, Instant::from);
        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        return new Reservation(number, date, confirmation, flightNumber, flightDate, payment, passengers);
    }
}