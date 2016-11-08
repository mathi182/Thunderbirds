package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.exceptions.RepositorySavingException;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger.PassengerFetcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class HibernatePassengerRepositoryITest  {
    private static final UUID VALID_PASSENGER_UUID = UUID.randomUUID();
    private PassengerRepository hibernatePassengerRepository;
    private static EntityManagerFactory entityManagerFactory;
    private PassengerFetcher passengerFetcher;

    @BeforeClass
    public static void beforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("boarding-test");
    }

    @Before
    public void setup(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);
        hibernatePassengerRepository = new HibernatePassengerRepositoryImpl();
        passengerFetcher = mock(PassengerFetcher.class);
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenEmptyRepository_whenGettingPassenger_shouldThrowException(){
        willThrow(new PassengerNotFoundException(VALID_PASSENGER_UUID))
                .given(passengerFetcher).fetchPassenger(VALID_PASSENGER_UUID);

        hibernatePassengerRepository.getPassenger(VALID_PASSENGER_UUID);
    }

    @Test
    public void givenEmptyRepository_whenSavingPassenger_shouldBeSavedCorrectly() throws RepositorySavingException {
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID,Seat.SeatClass.ANY);
        hibernatePassengerRepository.savePassenger(expectedPassenger);
        Passenger actualPassenger = hibernatePassengerRepository.getPassenger(VALID_PASSENGER_UUID);
        assertEquals(expectedPassenger,actualPassenger);
    }

    //TODO Ajouter un test pour si le passager n'est pas dans la BD de boarding mais présent dans celle de reservation
}
