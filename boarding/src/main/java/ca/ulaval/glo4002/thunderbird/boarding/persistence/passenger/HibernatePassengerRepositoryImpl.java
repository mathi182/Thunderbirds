package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.exceptions.RepositorySavingException;
import org.hibernate.HibernateException;
import org.hibernate.cfg.NotYetImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.UUID;

public class HibernatePassengerRepositoryImpl implements PassengerRepository {

    @Override
    public Passenger getPassenger(UUID passengerHash) throws PassengerNotFoundException {
        try {
            EntityManager entityManager = new EntityManagerProvider().getEntityManager();
            Passenger passenger = entityManager.find(Passenger.class, passengerHash);
            if (passenger == null) {
                throw  new PassengerNotFoundException(passengerHash);
            }
            return passenger;
        } catch (NoResultException ex) {
            try {
                //TODO Demander au fetcher de voir si reservation a le passager
                return null;
            } catch (NotYetImplementedException e) {
                //TODO Determiner le type d'exception
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
