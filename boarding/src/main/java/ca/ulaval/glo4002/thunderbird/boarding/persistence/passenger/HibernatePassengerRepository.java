package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.exceptions.RepositorySavingException;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.UUID;

public class HibernatePassengerRepository implements PassengerRepository {

    private PassengerFetcher passengerFetcher;

    public HibernatePassengerRepository(PassengerFetcher passengerFetcher) {
        this.passengerFetcher = passengerFetcher;
    }

    @Override
    public Passenger getPassenger(UUID passengerHash){
        Passenger passenger;
        passenger = getPassengerFromHibernate(passengerHash);
        if (passenger == null) {
            passenger = getPassengerFromAPI(passengerHash);
            savePassenger(passenger);
        }
        return passenger;
    }

    private Passenger getPassengerFromHibernate(UUID passengerHash) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        Passenger passenger = entityManager.find(Passenger.class, passengerHash);
        return passenger;
    }

    private Passenger getPassengerFromAPI(UUID passengerHash) {
        return passengerFetcher.fetchPassenger(passengerHash);
    }

    @Override
    public void savePassenger(Passenger passenger) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(passenger);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw new RepositorySavingException("Could not save passenger " + passenger.getHash() + "");
        }
    }
}
