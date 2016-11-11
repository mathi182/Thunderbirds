package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystemFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneServiceGlo3000;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAssembler;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerRequest;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.Instant;
import java.util.List;

public class DemoContext implements Context {
    @Override
    public void apply() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);

        registerFlightRepository();
        registerPassengerRepository();

        EntityManagerProvider.clearEntityManager();
        entityManager.close();
    }

    private void registerFlightRepository() {
        AMSSystem amsSystem = new AMSSystemFactory().create();
        PlaneService planeService = new PlaneServiceGlo3000();
        FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeService);

        ServiceLocator.registerSingleton(FlightRepository.class, flightRepository);
        initializeDefaultFlights(flightRepository, amsSystem, planeService);
    }

    private void registerPassengerRepository() {
        PassengerAssembler assembler = new PassengerAssembler();
        PassengerRequest Request = new PassengerRequest();
        PassengerService service = new PassengerService(assembler,Request);
        PassengerRepository passengerRepository = new HibernatePassengerRepository(service);

        ServiceLocator.registerSingleton(PassengerRepository.class, passengerRepository);
    }

    private void initializeDefaultFlights(FlightRepository flightRepository, AMSSystem amsSystem, PlaneService planeService) {
        String dash8FlightNumber = "QK-918";
        String a320FlightNumber = "NK-750";
        String boeingFlightNumber = "QK-432";

        Flight qk918Flight = getFlight(amsSystem, planeService, dash8FlightNumber);
        Flight nk750Flight = getFlight(amsSystem,planeService,a320FlightNumber);
        Flight qk432Flight = getFlight(amsSystem, planeService, boeingFlightNumber);

        flightRepository.saveFlight(qk918Flight);
        flightRepository.saveFlight(nk750Flight);
        flightRepository.saveFlight(qk432Flight);
    }

    private Flight getFlight(AMSSystem amsSystem, PlaneService planeService, String flightNumber) {
        String dash8Model = amsSystem.getPlaneModel(flightNumber);
        Plane dash8Plane = planeService.getPlaneInformation(dash8Model);
        List<Seat> dash8Seats = planeService.getSeats(dash8Model);

        return new Flight(flightNumber, Instant.now(),dash8Plane,dash8Seats);
    }
}