package ca.ulaval.glo4002.thunderbird.reservation.contexts;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.UUID;

public class DevContext implements Context {

    public final static int EXISTENT_RESERVATION_NUMBER = 100;
    public static UUID EXISTENT_PASSENGER_HASH;

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
        String firstName = "firstName";
        String lastName = "lastName";
        int age = 100;
        String passportNumber = "passportNumber";
        String seatClass = "seatClass";

        return new Passenger(firstName, lastName, age, passportNumber, seatClass);
    }

    private Reservation createReservation(Passenger passenger) {
        int number = EXISTENT_RESERVATION_NUMBER;
        String date = "2016-01-31";
        String confirmation = "A3833";
        String payment = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
        String flightNumber = "AC1765";
        String flightDate = "2016-09-06T13:00:00Z";
        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        return new Reservation(number, date, confirmation, flightNumber, flightDate, payment, passengers);
    }

}