package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.PassengerNotCheckedInException;
import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;

import javax.persistence.EntityManager;
import java.util.UUID;

public class HibernatePassengerRepository implements PassengerRepository {

    private PassengerService passengerService;

    public HibernatePassengerRepository(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Override
    public Passenger getPassenger(UUID passengerHash) {
        Passenger passenger = getPassengerFromHibernate(passengerHash);

        if (passenger == null) {
            passenger = getPassengerFromAPI(passengerHash);
            savePassenger(passenger);
        }

        return passenger;
    }

    private Passenger getPassengerFromHibernate(UUID passengerHash) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        return entityManager.find(Passenger.class, passengerHash);
    }

    private Passenger getPassengerFromAPI(UUID passengerHash) {
        Passenger passenger = passengerService.fetchPassenger(passengerHash);
        if (!passenger.isCheckin()) {
            throw new PassengerNotCheckedInException();
        }
        return passenger;
    }

    @Override
    public void savePassenger(Passenger passenger) {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(passenger);
    }
}
