package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.CheckedBaggageEconomy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.LinearDimensionUnits.CM;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.WeightUnits.KG;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class HibernatePassengerRepositoryITest  {
    private static final UUID VALID_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID NON_EXISTENT_PASSENGER_UUID= UUID.randomUUID();
    private static final UUID PASSENGER_UUID_WITH_BAGGAGE = UUID.randomUUID();
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
    public void givenEmptyRepository_whenGettingPassenger_shouldThrowException() throws PassengerNotFoundException {
        hibernatePassengerRepository.getPassenger(NON_EXISTENT_PASSENGER_UUID);
    }

    @Test
    public void givenEmptyRepository_whenSavingPassenger_shouldBeSavedCorrectly() throws RepositorySavingException {
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID,Seat.SeatClass.ANY);
        hibernatePassengerRepository.savePassenger(expectedPassenger);
        Passenger actualPassenger = hibernatePassengerRepository.getPassenger(VALID_PASSENGER_UUID);
        assertEquals(expectedPassenger,actualPassenger);
    }

    //TODO Ajouter un test pour si le passager n'est pas dans la BD de boarding mais présent dans celle de reservation

    @Test
    public void givenEmptyRepository_whenSavingPassengerWithBaggages_shouldSaveBaggageCorrectly() throws RepositorySavingException {
        Baggage baggage = new CheckedBaggageEconomy(CM, 10, KG, 10);
        List<Baggage> baggageList = Arrays.asList(baggage);
        Passenger expectedPassenger = new Passenger(PASSENGER_UUID_WITH_BAGGAGE,Seat.SeatClass.ANY, baggageList);
        hibernatePassengerRepository.savePassenger(expectedPassenger);
        Passenger actualPassenger = hibernatePassengerRepository.getPassenger(PASSENGER_UUID_WITH_BAGGAGE);
        assertEquals(expectedPassenger, actualPassenger);
    }
}
