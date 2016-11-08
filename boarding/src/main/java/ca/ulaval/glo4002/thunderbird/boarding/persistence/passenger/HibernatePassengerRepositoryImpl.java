package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.exceptions.RepositorySavingException;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger.PassengerFetcher;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.UUID;

public class HibernatePassengerRepositoryImpl implements PassengerRepository {

    @Override
    public Passenger getPassenger(UUID passengerHash) throws PassengerNotFoundException {
        try {
            EntityManager entityManager = new EntityManagerProvider().getEntityManager();
            Passenger foundPassenger = entityManager.find(Passenger.class, passengerHash);
            return foundPassenger;
        } catch (NoResultException ex) {
            try {
                PassengerFetcher passengerFetcher = new PassengerFetcher();
                return passengerFetcher.fetchPassenger(passengerHash);
            } catch (PassengerNotFoundException e) {
                throw new PassengerNotFoundException(passengerHash);
            }
        }
    }

    @Override
    public void savePassenger(Passenger passenger) throws RepositorySavingException {
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
