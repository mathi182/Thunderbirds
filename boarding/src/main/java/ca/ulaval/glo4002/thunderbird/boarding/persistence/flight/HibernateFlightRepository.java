package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;

public class HibernateFlightRepository implements FlightRepository {
    private AMSSystem amsSystem;
    private PlaneService planeService;

    public HibernateFlightRepository(AMSSystem amsSystem, PlaneService planeService) {
        this.amsSystem = amsSystem;
        this.planeService = planeService;
    }

    @Override
    public Flight getFlight(String flightNumber, Instant flightDate) {
        Flight flight;
        try {
            flight = getFlightFromDB(flightNumber, flightDate);
        } catch (NoResultException ex) {
            flight = createFlight(flightNumber, flightDate);
        }
        return flight;
    }

    @Override
    public void saveFlight(Flight flight) {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        entityManagerProvider.executeInTransaction(() -> entityManager.persist(flight));
    }

    private Flight getFlightFromDB(String flightNumber, Instant flightDate) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> criteria = builder.createQuery(Flight.class);
        Root<Flight> from = criteria.from(Flight.class);
        Expression<Boolean> flightNumberCriteria = builder.equal(from.get("flightNumber"), flightNumber);
        Expression<Boolean> flightDateCriteria = builder.equal(from.get("flightDate"), flightDate);

        criteria.select(from);
        criteria.where(builder.and(flightNumberCriteria,
                flightDateCriteria));

        return entityManager.createQuery(criteria).getSingleResult();
    }

    private Flight createFlight(String flightNumber, Instant flightDate) {
        String modelID = amsSystem.getPlaneModel(flightNumber);
        Plane plane = planeService.getPlaneInformation(modelID);
        List<Seat> seats = planeService.getSeats(modelID);
        return new Flight(flightNumber, flightDate, plane, seats);
    }
}
