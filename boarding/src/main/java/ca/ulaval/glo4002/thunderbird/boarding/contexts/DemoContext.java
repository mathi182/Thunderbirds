package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.*;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneServiceGlo3000;

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
        registerBaggageFactories();

        EntityManagerProvider.clearEntityManager();
        entityManager.close();
    }

    private void registerBaggageFactories() {
        //Order important to instantiate the factories
        CheckedBaggageFactory checkedBaggageFactory = new CheckedBaggageFactory();
        ServiceLocator.registerSingleton(CheckedBaggageFactory.class, checkedBaggageFactory);
        BaggageFactory baggageFactory = new BaggageFactory();
        ServiceLocator.registerSingleton(BaggageFactory.class, baggageFactory);
    }

    private void registerFlightRepository() {
        AMSSystem amsSystem = new AMSSystemFactory().create();
        PlaneService planeService = new PlaneServiceGlo3000();
        FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeService);

        ServiceLocator.registerSingleton(FlightRepository.class, flightRepository);
        initializeDefaultFlights(flightRepository, amsSystem, planeService);
    }

    private void registerPassengerRepository() {
        PassengerService service = new PassengerService();
        PassengerRepository passengerRepository = new HibernatePassengerRepository(service);

        ServiceLocator.registerSingleton(PassengerRepository.class, passengerRepository);
    }

    private void initializeDefaultFlights(FlightRepository flightRepository, AMSSystem amsSystem, PlaneService planeService) {
        String dash8FlightNumber = "QK-918";
        String a320FlightNumber = "NK-750";
        String boeingFlightNumber = "QK-432";

        Flight qk918Flight = getFlight(amsSystem, planeService, dash8FlightNumber);
        Flight nk750Flight = getFlight(amsSystem, planeService, a320FlightNumber);
        Flight qk432Flight = getFlight(amsSystem, planeService, boeingFlightNumber);

        flightRepository.saveFlight(qk918Flight);
        flightRepository.saveFlight(nk750Flight);
        flightRepository.saveFlight(qk432Flight);
    }

    private Flight getFlight(AMSSystem amsSystem, PlaneService planeService, String flightNumber) {
        FlightId flightId = new FlightId(flightNumber, Instant.now());
        String dash8Model = amsSystem.getPlaneModel(flightNumber);
        Plane dash8Plane = planeService.getPlaneInformation(dash8Model);
        List<Seat> dash8Seats = planeService.getSeats(dash8Model);

        return new Flight(flightId, dash8Plane, dash8Seats);
    }
}