package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.CheckinITest;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerITest;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationITest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PassengerITest.class,
        ReservationITest.class,
        CheckinITest.class
})
public class ITestSuite {
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
}