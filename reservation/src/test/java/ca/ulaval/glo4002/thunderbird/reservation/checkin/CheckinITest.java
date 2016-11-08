package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions.CheckinNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CheckinITest {
    private static final UUID NON_EXISTENT_CHECKIN_HASH = new UUID(1L, 2L);
    private static final UUID NON_EXISTENT_PASSENGER_HASH = new UUID(3L, 4L);

    private static final UUID PASSENGER_HASH = new UUID(5L, 6L);
    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;

    private static EntityManagerFactory entityManagerFactory;

    private static EntityManager entityManager;

    @BeforeClass
    public static void beforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("reservation-test");
        entityManager = entityManagerFactory.createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);
    }

    @AfterClass
    public static void afterClass() {
        entityManager.close();
        EntityManagerProvider.clearEntityManager();
        entityManagerFactory.close();
    }

    @Test
    public void givenCheckinWithExistentPassenger_whenFindingPassenger_shouldBeAbleToRetrieve() {
        Passenger passenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        passenger.save();
        UUID existentPassengerHash = passenger.getId();
        Checkin checkin = new Checkin(existentPassengerHash, Checkin.SELF);

        Passenger retrievedPassenger = checkin.getPassenger();

        assertEquals(passenger.getId().toString(), retrievedPassenger.getId().toString());
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenCheckinWithNonExistentPassenger_whenFindingPassenger_shouldThrowNotFound() {
        Checkin checkin = new Checkin(NON_EXISTENT_PASSENGER_HASH, Checkin.SELF);

        checkin.getPassenger();
    }

    @Test
    public void givenValidCheckin_whenCheckinIsSaved_shouldBeAbleToRetrieve() {
        Checkin checkin = new Checkin(PASSENGER_HASH, Checkin.SELF);

        checkin.save();

        Checkin retrievedCheckin = Checkin.findByCheckinHash(checkin.getId());
        assertEquals(checkin.getId().toString(), retrievedCheckin.getId().toString());
    }

    @Test(expected = CheckinNotFoundException.class)
    public void whenFinding_shouldThrowNotFound() {
        Checkin.findByCheckinHash(NON_EXISTENT_CHECKIN_HASH);
    }
}