package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;

import javax.persistence.EntityManager;
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
        Flight flight = getFlightFromDB(flightNumber, flightDate);
        if (flight != null) {
            return flight;
        }
        return createFlight(flightNumber, flightDate);
    }

    @Override
    public void saveFlight(Flight flight) {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(flight);
    }

    private Flight getFlightFromDB(String flightNumber, Instant flightDate) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        FlightId flightId = new FlightId(flightNumber, flightDate);
        return entityManager.find(Flight.class, flightId);
    }

    private Flight createFlight(String flightNumber, Instant flightDate) {
        FlightId flightId = new FlightId(flightNumber, flightDate);
        String modelID = amsSystem.getPlaneModel(flightNumber);
        Plane plane = planeService.getPlaneInformation(modelID);
        List<Seat> seats = planeService.getSeats(modelID);

        return new Flight(flightId, plane, seats);
    }
}
